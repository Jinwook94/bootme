#!/bin/bash

set -e                        # 에러 발생 시 즉시 종료
THIS_SCRIPT_NAME=$(basename "$0")  # 스크립트 파일명

# ECR 로그인
echo "[$SCRIPT_NAME][$THIS_SCRIPT_NAME] ECR 로그인 시작"
ECR_LOGIN_LOG=$(mktemp)
if ! aws ecr get-login-password --region $AWS_REGION --profile $AWS_PROFILE \
   | docker login --username AWS --password-stdin $ECR_URL > $ECR_LOGIN_LOG 2>&1; then
    echo "[$SCRIPT_NAME][$THIS_SCRIPT_NAME] 에러: ECR 로그인 실패"
    cat $ECR_LOGIN_LOG
    rm $ECR_LOGIN_LOG
    exit 1
fi
rm $ECR_LOGIN_LOG
echo "[$SCRIPT_NAME][$THIS_SCRIPT_NAME] ECR 로그인 완료"

# Docker 이미지 빌드 및 ECR 푸시
echo "[$SCRIPT_NAME][$THIS_SCRIPT_NAME] Docker 이미지 빌드 및 ECR 푸시 시작"
DOCKER_BUILD_LOG=$(mktemp) # 임시 로그 파일 생성

if ! docker buildx build \
    --file $DOCKERFILE_PATH \
    --platform linux/arm64 \
    --build-arg $ENV_ARG \
    --tag $DOCKER_IMAGE_TAG \
    --no-cache \
    --push "$(dirname $DOCKERFILE_PATH)" > $DOCKER_BUILD_LOG 2>&1; then
    echo "[$SCRIPT_NAME][$THIS_SCRIPT_NAME] 오류: Docker 이미지 빌드 및 ECR 푸시 실패"
    cat $DOCKER_BUILD_LOG
    rm $DOCKER_BUILD_LOG
    exit 1
fi
rm $DOCKER_BUILD_LOG

echo "[$SCRIPT_NAME][$THIS_SCRIPT_NAME] Docker 이미지 빌드 및 ECR 푸시 완료"

# 최신 ECS 태스크 정의 가져오기
echo "[$SCRIPT_NAME][$THIS_SCRIPT_NAME] 최신 ECS 태스크 정의 가져오기 시작"
LATEST_TASK_DEFINITION=$(aws ecs describe-task-definition \
    --task-definition $TASK_DEFINITION_NAME \
    --profile $AWS_PROFILE \
    | jq -r '.taskDefinition.taskDefinitionArn')

echo "[$SCRIPT_NAME][$THIS_SCRIPT_NAME] 최신 ECS 태스크 정의 가져오기 완료: $LATEST_TASK_DEFINITION"

# ECS 서비스 업데이트
echo "[$SCRIPT_NAME][$THIS_SCRIPT_NAME] ECS 서비스 업데이트 시작"
aws ecs update-service \
    --cluster $CLUSTER_NAME \
    --service $SERVICE_NAME \
    --task-definition $LATEST_TASK_DEFINITION \
    --profile $AWS_PROFILE \
    --force-new-deployment > /dev/null

echo "[$SCRIPT_NAME][$THIS_SCRIPT_NAME] ECS 서비스 업데이트 완료"
