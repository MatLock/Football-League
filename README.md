![Java CI](https://github.com/MatLock/championship/workflows/Java%20CI/badge.svg)
[![Travis CI](https://travis-ci.org/MatLock/championship.svg?branch=master)](https://travis-ci.org/MatLock/championship)

# Championship
========================
### Run it with Docker

 * Have [docker](https://www.docker.com/) installed
 * Execute ```docker-compose build```
 * Execute ```docker-compose up```
 * Go to http://localhost:8080/swagger-ui.html to see swagger documentation
 
 **NOTE:** to see MySQL tables connect to **localhost:3306/championship** (credentials provided in **docker-compose.yml** file). To see MySQL [Schema](https://github.com/MatLock/championship/blob/master/src/main/resources/schema-prod.sql) it is provided in resources folder.
 
### Run it in debug mode

 * Have [Maven](https://maven.apache.org/install.html) installed
 * Have **JDK 8** installed
 * Execute ```mvn clean install```
 * Execute ```mvn spring-boot:run```
 * Go to http://localhost:8080/swagger-ui.html to see swagger documentation
 
 **NOTE:** This is development mode. The Application will create Tables in memory with H2 and It will print 
 all the queries done to the data base.
 
 
 
 ___________________________________________________
   Jorge Flores - [LinkedIn Profile](https://www.linkedin.com/in/jorge-flores-45b58988/?locale=en_US) 
