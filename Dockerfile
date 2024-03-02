FROM openjdk:8
COPY ./target/*.jar /data/app.jar
WORKDIR /data
ENTRYPOINT ["java","-jar","app.jar"]