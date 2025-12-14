# 1. 베이스 이미지 선택 (Java 17 사용)
FROM eclipse-temurin:17-jdk-jammy

# 2. 앱 JAR 파일 복사
COPY ./build/libs/*.jar /app.jar

# 3. 컨테이너 실행 명령
ENTRYPOINT ["java", "-jar", "/app.jar"]
