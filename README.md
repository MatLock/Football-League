![Java CI](https://github.com/MatLock/championship/workflows/Java%20CI/badge.svg)
![Travis CI](https://travis-ci.org/MatLock/championship.svg?branch=master)
# Championship

### Run it with Docker

 * Have [docker](https://www.docker.com/) installed
 * Execute ```docker-compose up```
 * Go to http://localhost:8080/swagger-ui.html to see swagger documentation
 
 **NOTE:** to see MySQL tables connect to **localhost:3306** (credentials provided in **docker-compose.yml** file)
 
### Run it in debug mode

 * Execute ```mvn clean install```
 * Execute ```mvn spring-boot:run```
 * Go to http://localhost:8080/swagger-ui.html to see swagger documentation
 
 **NOTE:** This is develop mode. The Application will create Tables in memory with H2 and It will print 
 all the queries done to the data base.
 
