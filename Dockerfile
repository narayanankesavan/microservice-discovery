FROM openjdk:17-oracle
EXPOSE 8761

COPY ./target/microservice-discovery-service-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

ENTRYPOINT ["java","-jar","microservice-discovery-service-0.0.1-SNAPSHOT.jar"]
