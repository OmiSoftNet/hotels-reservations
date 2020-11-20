Hotels reservation application
======================================

## Technologies

* [Java](https://www.oracle.com/java)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Gradle](https://gradle.org)
* [MySQL](https://www.mysql.com)
* [Liquibase](https://www.liquibase.org)
* [Docker](https://www.docker.com)

## CLI

* Build application:

```sh
./gradlew build
```

* Run application from gradle:

```sh
./gradlew bootRun
```

* Build a Docker image

```sh
./gradlew bootBuildImage --imageName=fedlviv/sb-hotels-reservations
```

* Run a docker image

```sh
docker run -d --network="host" --name test-api fedlviv/sb-hotels-reservations
```

* Run app with Compose

```sh
docker-compose up -d
```

---
Exploring Swagger Documentation - http://localhost:8080/swagger-ui/index.html
