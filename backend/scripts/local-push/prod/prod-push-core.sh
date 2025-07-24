#!/bin/bash

# 스크립트 실행 전제 조건
# 1. Docker 실행 중이어야 함
# 2. AWS CLI 설정 되어야함 (aws configure)

# 에러 발생시
# 1. 캐시 초기화: IntelliJ IDEA > File > Invalidate Caches... > Invalidate and Restart
# 2. 디스크 정리: docker system prune -a && docker builder prune

set -e                                    # 에러 발생 시 즉시 종료

# 실행 여부 확인
CONFIRMATION_STRING="prod-push-backend"
echo "운영 환경 backend 서버 배포 스크립트입니다. 실행하시려면 '${CONFIRMATION_STRING}'를 입력해주세요."
read input

if [ "$input" == "$CONFIRMATION_STRING" ]; then
    CURRENT_TIME=$(date "+%Y-%m-%d %H:%M:%S")
    echo "[$SCRIPT_NAME] 운영 환경 backend 서버 푸시 시작 at $CURRENT_TIME"

    # 사용할 Dockerfile 경로
    SCRIPT_DIR=$(cd $(dirname "$0") && pwd)
    DOCKERFILE_PATH="$SCRIPT_DIR/../../../Dockerfile"

    # 푸시 스크립트에서 사용될 환경 변수
    export SCRIPT_NAME=$(basename "$0")
    export AWS_PROFILE="cac-sso-workloads-prod"
    export AWS_REGION="ap-northeast-2"
    export ECR_URL="897729140403.dkr.ecr.ap-northeast-2.amazonaws.com/bootme/core"
    export DOCKERFILE_PATH
    export DOCKER_IMAGE_TAG="${ECR_URL}:latest"
    export ENV_ARG="SPRING_PROFILE=prod"
    export TASK_DEFINITION_NAME="bootme-core"
    export CLUSTER_NAME="prod-bootme-ecs-cluster"
    export SERVICE_NAME="prod-bootme-ecs-service-core"

    # JAR 빌드
    GRADLEW_PATH="../../.."
    GRADLE_LOG=$(mktemp)
    echo "[$SCRIPT_NAME] Gradle JAR 빌드 시작"
    if $GRADLEW_PATH/gradlew -p $GRADLEW_PATH clean bootJar > $GRADLE_LOG 2>&1; then
        JAR_FILE=$(find "$GRADLEW_PATH/build/libs" -name '*.jar' -print -quit)
        JAR_FILE_ABSOLUTE_PATH=$(realpath "$JAR_FILE")
        echo "[$SCRIPT_NAME] Gradle JAR 빌드 완료: $JAR_FILE_ABSOLUTE_PATH"
    else
        echo "[$SCRIPT_NAME] 에러: Gradle JAR 빌드 실패"
        cat $GRADLE_LOG
        exit 1
    fi
    rm $GRADLE_LOG

    # 푸시 스크립트 실행
    chmod +x ../../local-push/local-push.sh
    ../../local-push/local-push.sh
    echo "[$SCRIPT_NAME] local-push.sh 스크립트 실행 완료"

    # 실행 완료
    CURRENT_TIME=$(date "+%Y-%m-%d %H:%M:%S")
    echo "[$SCRIPT_NAME] 운영 환경 backend 서버 푸시 완료 at $CURRENT_TIME"
else
    echo "스크립트 실행 취소됨."
fi
