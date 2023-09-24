#!/usr/bin/env bash

sudo chmod 777 /var/log/user-data.log
sudo chmod 777 /home/ec2-user/logs 2>> /var/log/user-data.log
echo "$(date '+%Y-%m-%dT%H:%M:%S') INFO [deploy.sh] Starting deploy.sh" >> /var/log/user-data.log
REPOSITORY=/home/ec2-user/deployment
cd $REPOSITORY
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep "bootme" | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME
echo "$(date '+%Y-%m-%dT%H:%M:%S') INFO [deploy.sh] Starting Spring Boot Application: $JAR_PATH" >> /var/log/user-data.log
nohup java -jar $JAR_PATH > /home/ec2-user/logs/nohup.out 2>&1 &