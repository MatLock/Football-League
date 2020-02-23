FROM maven:3.3-jdk-8 AS build
COPY . /usr/src/dockerApp/
WORKDIR /usr/src/dockerApp/
RUN mvn clean install

FROM openjdk:8-jre-slim
COPY --from=build /usr/src/dockerApp/target/championship-0.0.1-SNAPSHOT.jar /usr/service/championship.jar
COPY --from=build /usr/src/dockerApp/wait-for-it.sh /usr/service/wait-for-it.sh
RUN chmod +x /usr/service/wait-for-it.sh
EXPOSE 8080
ENTRYPOINT ["./usr/service/wait-for-it.sh", "db:3306", "--" ,"java","-jar","/usr/service/championship.jar","--spring.profiles.active=prod"]