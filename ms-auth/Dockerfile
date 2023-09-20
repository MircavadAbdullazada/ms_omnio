FROM openjdk:17-jdk-alpine
COPY ./build/libs/atl_omnio-microservices-0.0.1-SNAPSHOT.jar auth.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/auth.jar"]
