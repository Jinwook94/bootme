import asyncio
import os
import aiomysql
import pandas as pd
from datetime import datetime
from elasticsearch import AsyncElasticsearch, helpers
import logging
from dotenv import load_dotenv
from pathlib import Path

# 로깅 설정
logging.basicConfig(level=logging.INFO,
                    format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

# .env 파일 로드
env_path = Path(__file__).parent / '.env'
load_dotenv(dotenv_path=env_path)

# 환경변수에서 MariaDB 연결 정보 가져오기
MARIADB_CONNECTION = {
    "host": os.getenv("MARIADB_HOST"),
    "port": int(os.getenv("MARIADB_PORT", 3306)),
    "user": os.getenv("MARIADB_USER"),
    "password": os.getenv("MARIADB_PASSWORD"),
    "db": os.getenv("MARIADB_DB_PROD"),
    "charset": 'utf8mb4'
}

# 환경변수에서 Elasticsearch 연결 정보 가져오기
ES_CONNECTION = {
    "host": os.getenv("ES_HOST"),
    "port": int(os.getenv("ES_PORT", 9200)),
    "username": os.getenv("ES_USERNAME"),
    "password": os.getenv("ES_PASSWORD")
}


# 연결 정보 유효성 검사
def validate_connection_info():
    """연결 정보가 모두 설정되었는지 확인합니다."""
    for key, value in MARIADB_CONNECTION.items():
        if value is None:
            raise ValueError(f"MariaDB 연결 정보가 부족합니다: {key}")

    for key, value in ES_CONNECTION.items():
        if value is None:
            raise ValueError(f"Elasticsearch 연결 정보가 부족합니다: {key}")


async def connect_to_elasticsearch():
    """Elasticsearch에 비동기적으로 연결"""
    es_client = AsyncElasticsearch(
        [f"http://{ES_CONNECTION['host']}:{ES_CONNECTION['port']}"],
        basic_auth=(ES_CONNECTION['username'], ES_CONNECTION['password'])
    )
    logger.info("Elasticsearch에 연결되었습니다.")
    return es_client


async def reset_elasticsearch_index(es_client):
    """Elasticsearch post 인덱스 초기화"""
    index_name = "post"

    # 인덱스 존재 여부 확인 및 삭제
    if await es_client.indices.exists(index=index_name):
        logger.info(f"기존 {index_name} 인덱스를 삭제합니다...")
        await es_client.indices.delete(index=index_name)
        logger.info(f"{index_name} 인덱스가 삭제되었습니다.")

    # 인덱스 생성
    index_settings = {
        "settings": {
            "number_of_shards": 1,
            "number_of_replicas": 0
        },
        "mappings": {
            "properties": {
                "postId": {"type": "long"},
                "memberId": {"type": "long"},
                "topic": {"type": "text"},
                "title": {"type": "text"},
                "content": {"type": "text"},
                "likes": {"type": "integer"},
                "clicks": {"type": "integer"},
                "bookmarks": {"type": "integer"},
                "status": {"type": "keyword"},
                "createdAt": {"type": "long"},
                "modifiedAt": {"type": "long"},
                "commentCount": {"type": "integer"},
                "writerId": {"type": "long"},
                "writerNickname": {"type": "text"},
                "writerProfileImage": {"type": "text"},
                "_class": {
                    "type": "text",
                    "fields": {
                        "keyword": {
                            "type": "keyword",
                            "ignore_above": 256
                        }
                    }
                }
            }
        }
    }

    await es_client.indices.create(index=index_name, body=index_settings)
    logger.info(f"새 {index_name} 인덱스가 생성되었습니다.")


async def fetch_posts_from_mariadb():
    """MariaDB에서 posts 테이블 데이터와 작성자 정보를 비동기적으로 가져옵니다."""
    async with aiomysql.connect(**MARIADB_CONNECTION) as conn:
        async with conn.cursor(aiomysql.DictCursor) as cursor:
            await cursor.execute("""
               SELECT 
                   p.post_id, p.member_id, p.topic, p.title, p.content,
                   p.likes, p.clicks, p.bookmarks, p.status,
                   UNIX_TIMESTAMP(p.created_at) * 1000 as created_at,
                   UNIX_TIMESTAMP(p.modified_at) * 1000 as modified_at,
                   m.nickname as writer_nickname,
                   m.profile_image as writer_profile_image
               FROM post p
               LEFT JOIN member m ON p.member_id = m.member_id
               WHERE p.status != 'DELETED'
           """)
            posts = await cursor.fetchall()
            logger.info(f"MariaDB에서 {len(posts)}개의 게시글 데이터를 가져왔습니다.")
            return posts


async def index_posts_to_elasticsearch(es_client, posts):
    """MariaDB에서 가져온 게시글 데이터를 Elasticsearch로 인덱싱합니다."""
    index_name = "post"
    total_posts = len(posts)

    # 데이터 변환 함수
    def transform_post(post):
        return {
            "postId": post["post_id"],
            "memberId": post["member_id"],
            "topic": post["topic"] if post["topic"] else "",
            "title": post["title"],
            "content": post["content"],
            "likes": post["likes"],
            "clicks": post["clicks"],
            "bookmarks": post["bookmarks"],
            "status": post["status"],
            "createdAt": post["created_at"],
            "modifiedAt": post["modified_at"],
            "commentCount": 0,  # 기본값 설정
            "writerId": post["member_id"],  # member_id를 writerId로 사용
            "writerNickname": post["writer_nickname"] if post["writer_nickname"] else "",  # 작성자 닉네임
            "writerProfileImage": post["writer_profile_image"] if post["writer_profile_image"] else "",  # 작성자 프로필 이미지
        }

    # 벌크 인덱싱을 위한 제너레이터
    def generate_actions():
        for i, post in enumerate(posts, 1):
            if i % 100 == 0 or i == total_posts:
                logger.info(f"Elasticsearch 인덱싱 진행 중: {i}/{total_posts} ({(i / total_posts * 100):.1f}%)")

            yield {
                "_index": index_name,
                "_id": post["post_id"],
                "_source": transform_post(post)
            }

    logger.info(f"총 {total_posts}개의 게시글을 Elasticsearch에 인덱싱합니다...")

    # 벌크 인덱싱 수행
    indexed_count = 0
    errors = []

    try:
        async for ok, result in helpers.async_streaming_bulk(
                es_client,
                generate_actions(),
                chunk_size=500,
                max_retries=3
        ):
            if ok:
                indexed_count += 1
            else:
                errors.append(result)
    except Exception as e:
        logger.error(f"인덱싱 중 오류 발생: {str(e)}")

    logger.info(f"인덱싱 완료: {indexed_count}/{total_posts} 게시글이 성공적으로 인덱싱되었습니다.")

    if errors:
        logger.warning(f"{len(errors)}개의 오류가 발생했습니다.")
        save_errors_to_log(errors)

    return indexed_count, errors


def save_errors_to_log(errors):
    """오류 데이터를 엑셀 파일로 저장합니다."""
    data_dir = "./logs"
    if not os.path.exists(data_dir):
        os.makedirs(data_dir)

    df = pd.DataFrame(errors)
    current_time = datetime.now().strftime("%Y%m%d%H%M%S")
    file_name = f"./logs/{current_time}_elasticsearch_indexing_errors.xlsx"
    df.to_excel(file_name, index=False)
    logger.info(f"오류 로그가 {file_name}에 저장되었습니다.")


async def main():
    """메인 비동기 함수."""
    start_time = datetime.now()
    logger.info(f"작업 시작 시간: {start_time}")

    try:
        # 연결 정보 검증
        validate_connection_info()

        # Elasticsearch 연결
        es_client = await connect_to_elasticsearch()

        try:
            # Elasticsearch 인덱스 초기화 확인
            proceed = input("Elasticsearch post 인덱스를 초기화하시겠습니까? (기존 데이터가 모두 삭제됩니다) (yes로 입력 시 진행): ").strip().lower()
            if proceed == 'yes':
                # Elasticsearch 인덱스 초기화
                await reset_elasticsearch_index(es_client)

                # MariaDB에서 게시글 데이터 가져오기
                logger.info("MariaDB에서 게시글 데이터를 가져오는 중...")
                posts = await fetch_posts_from_mariadb()

                # Elasticsearch에 인덱싱 수행
                indexed_count, errors = await index_posts_to_elasticsearch(es_client, posts)

                # 완료 메시지
                logger.info(f"총 {indexed_count}/{len(posts)} 게시글이 Elasticsearch에 인덱싱되었습니다.")
                if errors:
                    logger.warning(f"{len(errors)}개의 오류가 발생했습니다.")
            else:
                logger.info("작업이 취소되었습니다.")
        finally:
            # Elasticsearch 연결 종료
            await es_client.close()
            logger.info("Elasticsearch 연결을 종료했습니다.")
    except Exception as e:
        logger.error(f"오류 발생: {str(e)}")

    end_time = datetime.now()
    logger.info(f"작업 종료 시간: {end_time}")
    total_time = end_time - start_time
    logger.info(f"총 소요 시간: {total_time}")


if __name__ == "__main__":
    asyncio.run(main())