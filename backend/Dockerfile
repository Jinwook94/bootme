# 베이스 이미지로 Amazon Corretto 17 사용 (이미지 크기 최소화)
FROM amazoncorretto:17

WORKDIR /app

# 빌드 아규먼트 정의
ARG JAR_FILE=build/libs/*.jar
ARG SPRING_PROFILE

# 환경 변수 설정
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILE}

# JAR 파일 복사
COPY ${JAR_FILE} app.jar

# 컨테이너 실행 시 수행할 명령
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# 포트 노출
EXPOSE 8000
