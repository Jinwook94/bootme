#!/usr/bin/env bash

REPOSITORY=/home/ec2-user/deployment
cd $REPOSITORY
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep "bootme" | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME
sudo chmod 777 /var/log/user-data.log
sudo chmod 777 /home/ec2-user/logs
sudo chmod 777 /home/ec2-user/logs/general
sudo chmod 777 /home/ec2-user/logs/access
echo "$(date '+%Y-%m-%dT%H:%M:%S') INFO [deploy.sh] Starting Spring Boot Application: $JAR_PATH" >> /var/log/user-data.log
nohup java -jar $JAR_PATH > /home/ec2-user/logs/nohup.out 2>&1 &&
echo "$(date '+%Y-%m-%dT%H:%M:%S') INFO [deploy.sh] Spring Boot Application has been started: $JAR_PATH " >> /var/log/user-data.log