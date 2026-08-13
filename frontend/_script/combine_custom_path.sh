#!/usr/bin/env bash

set -e

# 공통 설정 로드
source "$(dirname "$0")/_config.sh"

# 병합할 경로 목록 (프로젝트 내 상대 경로 또는 절대 경로)
TARGET_PATHS=(
    "frontend/package.json"
    "frontend/webpack/webpack.common.js"
    "frontend/webpack/webpack.dev.js"
    "frontend/webpack/webpack.prod.js"

)

# 결과를 저장할 파일 (프로젝트 기준 상대 경로)
OUTPUT_FILE="_script/combined_paths.txt"

# 메인 로직

# 스크립트 위치 기준으로 상위 디렉토리로 이동
cd "$(dirname "$0")/.."
CURRENT_DIR="$(basename "$(pwd)")"
PROJECT_PATH="$(pwd)"

# Git 루트 디렉토리 찾기 (있으면 사용, 없으면 현재 디렉토리)
GIT_ROOT="$(git rev-parse --show-toplevel 2>/dev/null || echo "$PROJECT_PATH")"

# 기존 파일 삭제 & 디렉토리 생성
rm -f "$OUTPUT_FILE"
mkdir -p "$(dirname "$OUTPUT_FILE")"

# 헤더 추가
echo "# Selected Files from ${TARGET_DIR}" >> "$OUTPUT_FILE"
echo "" >> "$OUTPUT_FILE"

# 처리된 파일 수를 카운트
PROCESSED_FILES=0

# 각 입력 경로 처리
for TARGET_PATH in "${TARGET_PATHS[@]}"; do
    ORIGINAL_PATH="$TARGET_PATH"

    # TARGET_DIR 접두사 제거 (있는 경우)
    if [[ "$TARGET_PATH" == ${TARGET_DIR}/* ]]; then
        TARGET_PATH="${TARGET_PATH#${TARGET_DIR}/}"
    fi

    # 절대 경로인지 확인
    if [[ "$TARGET_PATH" == /* ]]; then
        # 절대 경로인 경우
        FULL_PATH="$TARGET_PATH"
        RELATIVE_PATH="$TARGET_PATH"
    else
        # 상대 경로인 경우
        # 먼저 현재 디렉토리 기준으로 확인
        if [ -e "$PROJECT_PATH/$TARGET_PATH" ]; then
            FULL_PATH="$PROJECT_PATH/$TARGET_PATH"
            RELATIVE_PATH="$TARGET_PATH"
        # Git 루트 기준으로 확인
        elif [ -e "$GIT_ROOT/$ORIGINAL_PATH" ]; then
            FULL_PATH="$GIT_ROOT/$ORIGINAL_PATH"
            RELATIVE_PATH="$ORIGINAL_PATH"
        else
            # 경로를 찾을 수 없음
            echo "경고: '$ORIGINAL_PATH' 경로를 찾을 수 없습니다. 건너뜁니다."
            echo "  - 시도한 경로: $PROJECT_PATH/$TARGET_PATH"
            echo "  - 시도한 경로: $GIT_ROOT/$ORIGINAL_PATH"
            continue
        fi
    fi

    # 경로가 존재하는지 재확인
    if [ ! -e "$FULL_PATH" ]; then
        echo "경고: '$FULL_PATH' 경로가 존재하지 않습니다. 건너뜁니다."
        continue
    fi

    # 경로 정보 출력
    echo "## Files from: $RELATIVE_PATH" >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"

    # 경로가 파일인 경우 단일 파일만 포함
    if [ -f "$FULL_PATH" ]; then
        echo "### $RELATIVE_PATH:" >> "$OUTPUT_FILE"
        cat "$FULL_PATH" >> "$OUTPUT_FILE"
        echo "" >> "$OUTPUT_FILE"
        echo "" >> "$OUTPUT_FILE"  # 추가 구분 줄
        PROCESSED_FILES=$((PROCESSED_FILES + 1))
    # 경로가 디렉토리인 경우 하위 파일 모두 포함
    elif [ -d "$FULL_PATH" ]; then
        # 디렉토리 구조 출력
        TREE_IGNORE=""
        for dir in "${EXCLUDE_DIRS[@]}"; do
            if [ -n "$TREE_IGNORE" ]; then
                TREE_IGNORE="${TREE_IGNORE}|${dir}"
            else
                TREE_IGNORE="${dir}"
            fi
        done

        echo "### Directory Structure:" >> "$OUTPUT_FILE"
        tree "$FULL_PATH" --charset=ASCII -I "$TREE_IGNORE" >> "$OUTPUT_FILE"
        echo "" >> "$OUTPUT_FILE"

        # 디렉토리 내의 파일 개수 세기
        FILE_COUNT=0

        # find 명령을 위한 옵션 구성
        INCLUDE_OPTS=$(build_include_extensions)
        EXCLUDE_OPTS=$(build_exclude_paths)

        # 디렉토리 내의 모든 파일 수집
        eval "find \"$FULL_PATH\" $INCLUDE_OPTS -type f $EXCLUDE_OPTS -print0" | sort -z | while IFS= read -r -d '' file; do
            # 상대 경로 계산
            if [[ "$file" == "$GIT_ROOT"/* ]]; then
                file_relative="${file#$GIT_ROOT/}"
            else
                file_relative="${file#$PROJECT_PATH/}"
            fi

            echo "### $file_relative:" >> "$OUTPUT_FILE"
            cat "$file" >> "$OUTPUT_FILE"
            echo "" >> "$OUTPUT_FILE"
            echo "" >> "$OUTPUT_FILE"  # 추가 구분 줄
            FILE_COUNT=$((FILE_COUNT + 1))
        done

        PROCESSED_FILES=$((PROCESSED_FILES + FILE_COUNT))
    fi

    echo "" >> "$OUTPUT_FILE"
done

# 처리된 파일이 없는 경우 메시지 추가
if [ $PROCESSED_FILES -eq 0 ]; then
    echo "처리된 파일이 없습니다." >> "$OUTPUT_FILE"
fi

# 마무리 안내
if [ $PROCESSED_FILES -eq 0 ]; then
    echo "처리된 파일이 없습니다. '$CURRENT_DIR/$OUTPUT_FILE' 에 저장되었습니다."
else
    echo "$PROCESSED_FILES개의 파일이 $CURRENT_DIR/$OUTPUT_FILE 에 성공적으로 병합되었습니다."
fi
