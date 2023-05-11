#!/usr/bin/env bash

REPOSITORY=/home/ec2-user/deployment
cd $REPOSITORY
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep "bootme" | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME
echo ">Starting spring boot application: $JAR_PATH" > /var/log/user-data.log
nohup java -jar $JAR_PATH
echo ">Spring boot application has been started: $JAR_PATH " > /var/log/user-data.log