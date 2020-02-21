FROM maven:3.3-jdk-8 AS build
COPY . /usr/src/dockerApp/
WORKDIR /usr/src/dockerApp/
RUN mvn clean install

FROM openjdk:8-jre-slim
COPY --from=build /usr/src/dockerApp/target/championship-0.0.1-SNAPSHOT.jar /usr/service/championship.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/service/championship.jar","--spring.profiles.active=prod"]