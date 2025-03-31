#!/usr/bin/env bash

set -e

##############################################
# 0) 스크립트 위치 기준으로 상위 디렉토리(client-web)로 이동
##############################################
cd "$(dirname "$0")/.."

##############################################
# 1) 결과를 저장할 파일들 (client-web 기준)
##############################################
INFO_FILE="script/combined_all_1_project_info.txt"
STRUCTURE_FILE="script/combined_all_2_project_structure.txt"
CODE_FILE="script/combined_all_3_code.txt"
CURRENT_DIR="$(basename "$(pwd)")"

# 기존 파일 삭제 & 디렉토리 생성
rm -f "$INFO_FILE" "$STRUCTURE_FILE" "$CODE_FILE"
mkdir -p "$(dirname "$INFO_FILE")"

# 프로젝트 소개 (INFO_FILE에 저장)
echo "# BootMe 프로젝트 소개:" >> "$INFO_FILE"
echo "" >> "$INFO_FILE"

# 구현시 주의사항
echo "# Bootme React Web Client 구현시 주의사항:" >> "$INFO_FILE"
echo "" >> "$INFO_FILE"

# 상태 관리 방법:
echo "# BootMe React 프로젝트 상태 관리 방법:" >> "$INFO_FILE"
echo "" >> "$INFO_FILE"

##############################################
# 프로젝트 기술 스택 출력
##############################################
echo "# Project Tech stack:" >> "$INFO_FILE"
echo "- React" >> "$INFO_FILE"
echo "- Vite" >> "$INFO_FILE"
echo "- TypeScript" >> "$INFO_FILE"
echo "" >> "$INFO_FILE"

##############################################
# 프로젝트 구조(tree) 출력
##############################################
echo "# Project Structure:" >> "$STRUCTURE_FILE"
tree . --charset=ASCII -I "node_modules|dist|.idea|package-lock.json|fonts" >> "$STRUCTURE_FILE"
echo "" >> "$STRUCTURE_FILE"

##############################################
# 4) 소스코드만 별도 파일에 병합
##############################################
echo "# Full Project Source Code:" >> "$CODE_FILE"
echo "" >> "$CODE_FILE"

# 한 번의 find 로 필요한 확장자만 검색
find . \
  \( -name "*.js" \
     -o -name "*.jsx" \
     -o -name "*.ts" \
     -o -name "*.tsx" \
     -o -name "*.yml" \
     -o -name "*.css" \
     -o -name "*.html" \
     -o -name "*.json" \
     -o -name "*.sh" \) \
  -type f \
  ! -path "*/node_modules/*" \
  ! -path "*/dist/*" \
  ! -path "*/.idea/*" \
  ! -path "*/fonts/*" \
  ! -path "*/script/*" \
  ! -name "package-lock.json" \
  ! -name "*frontend/stories" \
  | sort \
  | while read -r file
do
  # file 에서 "./" 제거 후 "client-web/" 형태로 상대 경로 표현
  relative_path="$CURRENT_DIR/${file#./}"

  echo "# $relative_path:" >> "$CODE_FILE"
  cat "$file" >> "$CODE_FILE"
  echo "" >> "$CODE_FILE"
done

##############################################
# 5) 마무리 안내
##############################################
echo "프로젝트 정보가 $CURRENT_DIR/$INFO_FILE 에 성공적으로 저장되었습니다."
echo "프로젝트 구조가 $CURRENT_DIR/$STRUCTURE_FILE 에 성공적으로 저장되었습니다."
echo "소스 코드가 $CURRENT_DIR/$CODE_FILE 에 성공적으로 저장되었습니다."
