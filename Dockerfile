FROM maven:3.8.3-adoptopenjdk-15 AS builder
ADD . /app
WORKDIR /app
RUN --mount=type=cache,target=/root/.m2 mvn -f /app/pom.xml clean package
FROM adoptopenjdk/openjdk15:jdk-15.0.2_7-alpine-slim

COPY --from=builder /app/target/workflow-0.0.1-SNAPSHOT.jar  workflow.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","workflow.jar"]