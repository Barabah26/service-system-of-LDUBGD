# Вказуємо базовий образ, який містить JDK для запуску Spring Boot
FROM openjdk:17-jdk-slim

WORKDIR /crm_system

COPY target/serviceRegistry-0.0.1-SNAPSHOT.jar serviceRegistry.jar


ENTRYPOINT ["java", "-jar", "serviceRegistry.jar"]
