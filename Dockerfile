
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app

COPY build.gradle settings.gradle gradlew gradle/ ./
RUN gradle --no-daemon dependencies

COPY . .
RUN gradle --no-daemon clean build -x test


FROM eclipse-temurin:17-jre-jammy
WORKDIR /app


COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
