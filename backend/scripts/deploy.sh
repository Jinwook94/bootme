#!/usr/bin/env bash

# deprecated. 현재는 backend/scripts/local-push/prod/prod-push-core.sh 스크립트로 배포함
sudo chmod 777 /var/log/user-data.log
sudo chmod 777 /home/ec2-user/logs 2>> /var/log/user-data.log
echo "$(date '+%Y-%m-%dT%H:%M:%S') INFO [deploy.sh] Starting deploy.sh" >> /var/log/user-data.log

# 인스턴스 실행 시각을 Pinpoint Agent ID로 사용
INSTANCE_ID=$(curl -s http://169.254.169.254/latest/meta-data/instance-id)
REGION=$(curl -s http://169.254.169.254/latest/dynamic/instance-identity/document | grep region | awk -F\" '{print $4}')
AGENT_ID=$(aws ec2 describe-tags --region $REGION --filters "Name=resource-id,Values=$INSTANCE_ID" "Name=key,Values=Name" --output text | cut -f5)
AGENT_ID=$(echo $AGENT_ID | sed 's/PROD_WAS_AS_//')

REPOSITORY=/home/ec2-user/deployment
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep "bootme" | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME
JAVA_AGENT_PATH="/home/ec2-user/pinpoint/pinpoint-agent-2.5.2/pinpoint-bootstrap-2.5.2.jar"
APP_NAME="BootMe-PROD-WAS"

echo "$(date '+%Y-%m-%dT%H:%M:%S') INFO [deploy.sh] Starting Spring Boot Application: $JAR_PATH" >> /var/log/user-data.log
nohup java -javaagent:$JAVA_AGENT_PATH -Dpinpoint.agentId=$AGENT_ID -Dpinpoint.applicationName=$APP_NAME -jar $JAR_PATH > /home/ec2-user/logs/nohup.out 2>&1 &
