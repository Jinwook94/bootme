#!/usr/bin/env bash

sudo chmod 777 /var/log/user-data.log
sudo chmod 777 /home/ec2-user/logs 2>> /var/log/user-data.log
echo "$(date '+%Y-%m-%dT%H:%M:%S') INFO [deploy.sh] Starting deploy.sh" >> /var/log/user-data.log
REPOSITORY=/home/ec2-user/deployment
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep "bootme" | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME
JAVA_AGENT_PATH="/home/ec2-user/pinpoint/pinpoint-agent-2.5.2/pinpoint-bootstrap-2.5.2.jar"
AGENT_ID="Agent-PROD-WAS-1"
APP_NAME="BootMe-PROD-WAS"
echo "$(date '+%Y-%m-%dT%H:%M:%S') INFO [deploy.sh] Starting Spring Boot Application: $JAR_PATH" >> /var/log/user-data.log
nohup java -javaagent:$JAVA_AGENT_PATH -Dpinpoint.agentId=$AGENT_ID -Dpinpoint.applicationName=$APP_NAME -jar $JAR_PATH > /home/ec2-user/logs/nohup.out 2>&1 &
