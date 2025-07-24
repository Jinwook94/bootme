#!/bin/bash

set -e  # 에러 발생 시 즉시 종료

# 간단한 로깅 함수 (시각 및 스크립트명 포함)
log() {
    echo "[$(date '+%Y-%m-%dT%H:%M:%S')][$0] $1"
}

# 환경(dev/prod) 입력
read -r -p "환경을 입력하세요 (dev/prod): " ENVIRONMENT

# 환경에 따른 변수 설정
if [[ "$ENVIRONMENT" == "dev" ]]; then
    S3_BUCKET=""
    CLOUDFRONT_DISTRIBUTION_ID=""
    AWS_PROFILE=""
    BUILD_COMMAND=""
elif [[ "$ENVIRONMENT" == "prod" ]]; then
    S3_BUCKET="bootme.co.kr"
    CLOUDFRONT_DISTRIBUTION_ID="EB18CLL4N618A"
    AWS_PROFILE="cac-sso-workloads-prod"
    BUILD_COMMAND="build"

    # 운영 배포 재확인
    echo -e "\033[31m정말 운영환경($ENVIRONMENT)으로 배포하시겠습니까? (yes/no): \033[0m"
    read -r confirm
    if [[ "$confirm" != "yes" ]]; then
        log "배포가 취소되었습니다."
        exit 0
    fi
else
    log "잘못된 환경입니다: $ENVIRONMENT (dev 또는 prod)"
    exit 1
fi

# 스크립트 위치 기준으로 프로젝트 루트 찾기
SCRIPT_DIR=$(cd "$(dirname "$0")" && pwd)
PROJECT_ROOT="$SCRIPT_DIR/.."

log "웹팩 프로젝트 빌드 시작: npm run $BUILD_COMMAND"
cd "$PROJECT_ROOT"
npm run "$BUILD_COMMAND"

log "기존 S3 파일 삭제 중: $S3_BUCKET"
aws s3 rm "s3://$S3_BUCKET" --recursive --profile "$AWS_PROFILE"

# dist 폴더로 이동
cd "$PROJECT_ROOT/dist"

# GTM ID가 설정된 경우 index.html 내의 __GTM_ID__를 실제 GTM ID로 대체
if [[ -n "$GTM_ID" ]]; then
    sed -i '' "s/__GTM_ID__/$GTM_ID/g" index.html
fi

log "새 빌드 파일 업로드 중: dist -> s3://$S3_BUCKET"
aws s3 cp . "s3://$S3_BUCKET" --recursive --profile "$AWS_PROFILE"

# CloudFront 캐시 무효화
if [[ -n "$CLOUDFRONT_DISTRIBUTION_ID" ]]; then
    log "CloudFront 캐시 무효화 중: $CLOUDFRONT_DISTRIBUTION_ID"
    INVALIDATION_ID=$(aws cloudfront create-invalidation \
        --distribution-id "$CLOUDFRONT_DISTRIBUTION_ID" \
        --paths "/*" \
        --profile "$AWS_PROFILE" \
        --query 'Invalidation.Id' \
        --output text)
    log "CloudFront 무효화 생성됨: $INVALIDATION_ID"
else
    log "CLOUDFRONT_DISTRIBUTION_ID가 설정되지 않아 무효화를 건너뜁니다."
fi

log "정적 파일 배포 완료!"
