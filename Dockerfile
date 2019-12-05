FROM openjdk:8-jdk-alpine
RUN apk add --no-cache bash
MAINTAINER himynameisfil@gmail.com
RUN gradle build --no-daemon
COPY build/libs/*.jar lotto.jar
ENTRYPOINT ["java","-jar","lotto.jar", "bootRun"]