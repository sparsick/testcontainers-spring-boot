# Testcontainers Integration in a Spring Boot Application

[![MavenBuild](https://github.com/sparsick/testcontainers-spring-boot/actions/workflows/build.yml/badge.svg)](https://github.com/sparsick/testcontainers-spring-boot/actions/workflows/build.yml)

This repository shows some samples how to integrate Testcontainers in JUnit5 tests in Spring Boot context.

It is the base for several [blog posts](https://blog.sandra-parsick.de/tags/testcontainers/). 

## Show Cases

It exists samples for following use cases:

### Relational Database Integration
- [HeroClassicJDBCRepositoryIT](https://github.com/sparsick/testcontainers-spring-boot/blob/master/src/test/java/com/github/sparsick/testcontainerspringboot/hero/universum/HeroClassicJDBCRepositoryIT.java)
- [HeroClassicJpaRepositoryIT](https://github.com/sparsick/testcontainers-spring-boot/blob/master/src/test/java/com/github/sparsick/testcontainerspringboot/hero/universum/HeroClassicJpaRepositoryIT.java)
- [HeroClassicJpaRepositoryReuseDatabaseIT](https://github.com/sparsick/testcontainers-spring-boot/blob/master/src/test/java/com/github/sparsick/testcontainerspringboot/hero/universum/HeroClassicJpaRepositoryReuseDatabaseIT.java)\
- [HeroSpringDataJpaRepositoryIT](https://github.com/sparsick/testcontainers-spring-boot/blob/master/src/test/java/com/github/sparsick/testcontainerspringboot/hero/universum/HeroSpringDataJpaRepositoryIT.java)
- [HeroSpringDataJpaRepositoryReuseDatabaseIT](https://github.com/sparsick/testcontainers-spring-boot/blob/master/src/test/java/com/github/sparsick/testcontainerspringboot/hero/universum/HeroSpringDataJpaRepositoryReuseDatabaseIT.java)
- [HeroRestControllerIT](https://github.com/sparsick/testcontainers-spring-boot/blob/master/src/test/java/com/github/sparsick/testcontainerspringboot/hero/universum/HeroRestControllerIT.java)


### UI Tests
- [HeroStartPagePlaywrightIT](https://github.com/sparsick/testcontainers-spring-boot/blob/master/src/test/java/com/github/sparsick/testcontainerspringboot/hero/universum/HeroStartPagePlaywrightIT.java)
- [HeroStartPageSeleniumIT](https://github.com/sparsick/testcontainers-spring-boot/blob/master/src/test/java/com/github/sparsick/testcontainerspringboot/hero/universum/HeroStartPageSeleniumIT.java)

### Test with AWS S3 Database Integration / Localstack
- [HeroS3RepositoryIT](https://github.com/sparsick/testcontainers-spring-boot/blob/master/src/test/java/com/github/sparsick/testcontainerspringboot/hero/universum/HeroS3RepositoryIT.java)

## Spring Boot Demo Application

If you want to run the whole Spring Boot application, you have to follow the follwing steps.

First at all, a database is needed

```shell
docker run --name my-sql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=my-secret-pw -e MYSQL_DATABASE=test -e MYSQL_USER=user -e MYSQL_PASSWORD=pwd -d mysql
```

Then you can start the application via the spring-boot-maven-plugin:

```shell
mvn spring-boot:run
```

After that, the application is available in a browser under the URL `http://localhost:8080/hero`

