# Demo
Spring Boot Demo Application

```
mvn clean install
mvn spring-boot:run
```

Application URL: `http://localhost:8080/hero`

API for Prometheus: `http://localhost:8080/actuator/prometheus`

# Application Monitoring

```
cd application-monitoring
docker-compose up
```

start Prometheus and Grafana

Grafana: http://localhost:3000
Username: admin
Passwort: pass

Prometheus: http://localhost:9090
