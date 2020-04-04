# Demo
Spring Boot Demo Application

```
mvn clean install
mvn spring-boot:run
```

Application URL: `http://localhost:8080/hero`

A database is needed

```
docker run --name my-sql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql
```
