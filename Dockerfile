FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY .env /app/.env
WORKDIR /app
ENTRYPOINT ["java","-jar","/app.jar"]
