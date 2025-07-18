#!/bin/bash

# 기본 경로 정의
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"

# 프로젝트 기본 설정
PROJECT_NAME="bootme"
APPLICATION_IMAGES_BUCKET_PREFIX="${PROJECT_NAME}-application-images"

# 환경별 설정 적용 함수
apply_environment_config() {
    local env="$1"

    if [[ "$env" == "dev" ]]; then
        AWS_PROFILE="cac-sso-workloads-dev"
        APPLICATION_IMAGES_BUCKET_NAME="dev-${APPLICATION_IMAGES_BUCKET_PREFIX}"
    elif [[ "$env" == "prod" ]]; then
        AWS_PROFILE="cac-sso-workloads-prod"
        APPLICATION_IMAGES_BUCKET_NAME="prod-${APPLICATION_IMAGES_BUCKET_PREFIX}"
    else
        echo "Invalid environment: $env"
        return 1
    fi

    # 환경 변수 내보내기
    export PROJECT_ROOT
    export PROJECT_NAME
    export ENVIRONMENT="$env"
    export AWS_PROFILE
    export APPLICATION_IMAGES_BUCKET_NAME

    return 0
}

# 환경 확인 및 프롬프트 표시 함수
prompt_environment() {
    local action="$1"
    read -r -p "환경을 입력하세요 (dev/prod): " ENV_INPUT

    # 환경 설정 적용
    if ! apply_environment_config "$ENV_INPUT"; then
        return 1
    fi

    # 운영 환경 추가 확인
    if [[ "$ENVIRONMENT" == "prod" ]]; then
        case "$action" in
            "delete")
                echo -e "\033[31m⚠️  경고: 운영환경 버킷 $APPLICATION_IMAGES_BUCKET_NAME 삭제를 진행하시겠습니까? (yes/no): \033[0m"
                ;;
            "create")
                echo -e "\033[31m⚠️  경고: 운영환경 버킷 $APPLICATION_IMAGES_BUCKET_NAME 생성을 진행하시겠습니까? (yes/no): \033[0m"
                ;;
            *)
                echo -e "\033[31m⚠️  경고: 운영환경용 작업을 진행하시겠습니까? (yes/no): \033[0m"
                ;;
        esac

        read -r confirm
        if [[ "$confirm" != "yes" ]]; then
            echo "작업이 취소되었습니다."
            return 1
        fi
    fi

    return 0
}

# 스크립트 공통 로깅 함수
log() {
    local script_name="$1"
    local message="$2"
    echo "[$(date '+%Y-%m-%dT%H:%M:%S')][${script_name}] ${message}"
}

# 임시 로그 파일 생성 함수
create_log_file() {
    local log_file
    log_file=$(mktemp)
    echo "$log_file"
}
