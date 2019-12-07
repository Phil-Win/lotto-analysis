FROM gradle:jdk10 as builder
RUN gradle build --no-daemon

FROM openjdk:8-jdk-alpine
RUN apk add --no-cache bash
MAINTAINER himynameisfil@gmail.com
COPY build/libs/*.jar lotto.jar
ENTRYPOINT ["java","-jar","lotto.jar", "bootRun"]