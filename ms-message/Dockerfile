FROM openjdk:17-jdk-alpine
COPY ./build/libs/ms-message-0.0.1-SNAPSHOT.jar message.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/message.jar"]
