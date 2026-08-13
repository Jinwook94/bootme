#!/usr/bin/env bash

set -euo pipefail

# 상수 정의
CONFIG_FILE="$(cd "$(dirname "$0")" && pwd)/_config.sh"

# 에러 발생시 로그 출력 함수
error_handler() {
    local line_no=$1
    echo "에러 발생! (줄 번호: $line_no)"
    echo "마지막 실행 명령: $BASH_COMMAND"
    echo "종료 코드: $?"
}

# 에러 핸들러 등록
trap 'error_handler $LINENO' ERR

# 디버그 모드 (필요시 활성화)
DEBUG="${DEBUG:-false}"

debug_log() {
    if [ "${DEBUG}" = "true" ]; then
        echo "[DEBUG] $1" >&2
    fi
}

# 스크립트의 절대 경로 저장
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
debug_log "스크립트 디렉토리: ${SCRIPT_DIR}"

# Git 루트 찾기
if ! GIT_ROOT="$(git rev-parse --show-toplevel 2>/dev/null)"; then
    echo "에러: Git 저장소를 찾을 수 없습니다."
    exit 1
fi
debug_log "Git 루트: ${GIT_ROOT}"

# 공통 설정 로드 (Git 루트로 이동하기 전에 로드)
if [ ! -f "${CONFIG_FILE}" ]; then
    echo "에러: 설정 파일을 찾을 수 없습니다: ${CONFIG_FILE}"
    exit 1
fi

debug_log "설정 파일 로드: ${CONFIG_FILE}"
source "${CONFIG_FILE}"

# Git 루트로 이동
cd "${GIT_ROOT}"
debug_log "현재 디렉토리: $(pwd)"

# 결과를 저장할 파일 (루트 기준)
OUTPUT_FILE="${TARGET_DIR}/_script/combined_changed.txt"
debug_log "출력 파일: ${OUTPUT_FILE}"

# 디렉토리 생성 & 기존 파일 삭제
mkdir -p "$(dirname "${OUTPUT_FILE}")"
rm -f "${OUTPUT_FILE}"

# "커밋되지 않은 (Tracked) 변경 파일" 수집
echo "# Changed (Uncommitted) Source Code:" >> "${OUTPUT_FILE}"
echo "" >> "${OUTPUT_FILE}"

# Git pager 비활성화
export GIT_PAGER=cat

# 스테이징된 변경 (Tracked) - 에러 처리 추가
debug_log "스테이징된 파일 확인 중..."
STAGED=""
if git diff --cached --name-only HEAD -- "${TARGET_DIR}" >/dev/null 2>&1; then
    STAGED=$(git diff --cached --name-only HEAD -- "${TARGET_DIR}" 2>/dev/null || true)
fi
STAGED_COUNT=$(echo "${STAGED}" | grep -c . || echo 0)
debug_log "스테이징된 파일 수: ${STAGED_COUNT}"

# 스테이징 안 된 변경 (Tracked) - 에러 처리 추가
debug_log "스테이징 안 된 파일 확인 중..."
UNSTAGED=""
if git diff --name-only HEAD -- "${TARGET_DIR}" >/dev/null 2>&1; then
    UNSTAGED=$(git diff --name-only HEAD -- "${TARGET_DIR}" 2>/dev/null || true)
fi
UNSTAGED_COUNT=$(echo "${UNSTAGED}" | grep -c . || echo 0)
debug_log "스테이징 안 된 파일 수: ${UNSTAGED_COUNT}"

# 합쳐서 중복 제거
CHANGED_UNCOMMITTED_FILES=""
if [ -n "${STAGED}" ] || [ -n "${UNSTAGED}" ]; then
    CHANGED_UNCOMMITTED_FILES=$( (echo "${STAGED}"; echo "${UNSTAGED}") | grep -v '^$' | sort -u || true)
fi

TOTAL_COUNT=$(echo "${CHANGED_UNCOMMITTED_FILES}" | grep -c . || echo 0)
debug_log "총 변경된 파일 수: ${TOTAL_COUNT}"

# 변수 초기화 - 명시적으로 초기화
PROCESSED_FILES=0
SKIPPED_FILES=0

# 파일 내용 병합
if [ -n "${CHANGED_UNCOMMITTED_FILES}" ]; then
    while IFS= read -r file; do
        # 빈 줄 건너뛰기
        [ -z "${file}" ] && continue

        debug_log "파일 처리 중: ${file}"

        # 혹시 삭제되었거나 디렉토리이면 건너뛰기
        if [ ! -f "${file}" ]; then
            debug_log "  → 파일이 존재하지 않거나 디렉토리입니다. 건너뜁니다."
            SKIPPED_FILES=$((SKIPPED_FILES + 1))
            continue
        fi

        # 특정 폴더만 대상으로 하는 경우
        if [[ ! "${file}" == ${TARGET_DIR}/* ]]; then
            debug_log "  → 대상 디렉토리 외부 파일입니다. 건너뜁니다."
            SKIPPED_FILES=$((SKIPPED_FILES + 1))
            continue
        fi

        # 제외 여부 확인
        if should_exclude_file "${file}"; then
            debug_log "  → 제외 대상 파일입니다. 건너뜁니다."
            SKIPPED_FILES=$((SKIPPED_FILES + 1))
            continue
        fi

        # 포함 여부 확인
        if should_include_file "${file}"; then
            debug_log "  → 파일을 포함합니다."
            echo "" >> "${OUTPUT_FILE}"
            echo "### ${file}:" >> "${OUTPUT_FILE}"
            if ! cat "${file}" >> "${OUTPUT_FILE}" 2>/dev/null; then
                echo "경고: 파일을 읽을 수 없습니다: ${file}" >&2
            else
                echo "" >> "${OUTPUT_FILE}"
                PROCESSED_FILES=$((PROCESSED_FILES + 1))
            fi
        else
            debug_log "  → 포함 대상이 아닙니다. 건너뜁니다."
            SKIPPED_FILES=$((SKIPPED_FILES + 1))
        fi
    done <<< "${CHANGED_UNCOMMITTED_FILES}"
fi

# 변경사항이 없는 경우 메시지 추가
if [ ${PROCESSED_FILES} -eq 0 ]; then
    echo "변경된 파일이 없습니다." >> "${OUTPUT_FILE}"
fi

if [ ${PROCESSED_FILES} -eq 0 ]; then
    echo "변경된 파일이 없습니다. '${OUTPUT_FILE}' 에 저장되었습니다."
else
    echo "${PROCESSED_FILES}개의 변경된(Tracked) 파일이 '${OUTPUT_FILE}' 에 성공적으로 병합되었습니다."
fi

# 디버그 안내 메시지
if [ ${SKIPPED_FILES} -gt 0 ]; then
    echo ""
    echo "디버그 모드 실행 커맨드:"
    echo "   DEBUG=true ${0}"
fi

# 디버그 모드에서 출력 파일 내용 미리보기
if [ "${DEBUG}" = "true" ] && [ -f "${OUTPUT_FILE}" ]; then
    echo ""
    echo "[DEBUG] 출력 파일 첫 10줄:"
    head -10 "${OUTPUT_FILE}"
fi