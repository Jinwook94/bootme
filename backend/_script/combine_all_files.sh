#!/usr/bin/env bash

set -e

# 공통 설정 로드
source "$(dirname "$0")/_config.sh"

# 스크립트 위치 기준으로 상위 디렉토리로 이동
cd "$(dirname "$0")/.."

# 결과를 저장할 파일들
INFO_FILE="_script/combined_all_1_project_info.txt"
STRUCTURE_FILE="_script/combined_all_2_project_structure.txt"
CODE_FILE="_script/combined_all_3_code.txt"
CURRENT_DIR="$(basename "$(pwd)")"

# 기존 파일 삭제 & 디렉토리 생성
rm -f "$INFO_FILE" "$STRUCTURE_FILE" "$CODE_FILE"
mkdir -p "$(dirname "$INFO_FILE")"

echo "# ${PROJECT_NAME} 구현시 주의사항:" >> "$INFO_FILE"
echo "- 너의 답변의 코드를 그대로 복사하여 붙여넣어서 prod 배포할 예정이므로 주석으로 생략하는 부분 없이 완전한 코드로 보여줘. 하지만 수정사항이 없는 파일의 코드는 보여주지마." >> "$INFO_FILE"
echo "- 구현을 위해 조금이라도 수정이 된 파일은 해당 파일의 전체 코드를 완전하게 보여줘. 하지만 수정사항이 없는 파일의 코드는 보여주지마." >> "$INFO_FILE"
echo "" >> "$INFO_FILE"

# 프로젝트 구조(tree) 출력 - 제외 폴더를 동적으로 구성
TREE_IGNORE=""
for dir in "${EXCLUDE_DIRS[@]}"; do
    if [ -n "$TREE_IGNORE" ]; then
        TREE_IGNORE="${TREE_IGNORE}|${dir}"
    else
        TREE_IGNORE="${dir}"
    fi
done

echo "# ${PROJECT_NAME} Project Structure:" >> "$STRUCTURE_FILE"
tree . --charset=ASCII -I "$TREE_IGNORE" >> "$STRUCTURE_FILE"
echo "" >> "$STRUCTURE_FILE"

# 소스코드만 별도 파일에 병합
echo "# ${PROJECT_NAME} Project Full Source Code:" >> "$CODE_FILE"
echo "" >> "$CODE_FILE"

# find 명령을 위한 옵션 구성
INCLUDE_OPTS=$(build_include_extensions)
EXCLUDE_OPTS=$(build_exclude_paths)

# find 명령 실행
eval "find . $INCLUDE_OPTS -type f $EXCLUDE_OPTS" | sort | while read -r file
do
    # file 에서 "./" 제거 후 상대 경로 표현
    relative_path="$CURRENT_DIR/${file#./}"

    echo "### $relative_path:" >> "$CODE_FILE"
    cat "$file" >> "$CODE_FILE"
    echo "" >> "$CODE_FILE"
    echo "" >> "$CODE_FILE"
done

# 마무리 안내
echo "프로젝트 정보가 $CURRENT_DIR/$INFO_FILE 에 성공적으로 저장되었습니다."
echo "프로젝트 구조가 $CURRENT_DIR/$STRUCTURE_FILE 에 성공적으로 저장되었습니다."
echo "소스 코드가 $CURRENT_DIR/$CODE_FILE 에 성공적으로 저장되었습니다."