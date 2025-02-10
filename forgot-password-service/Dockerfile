# Вказуємо базовий образ, який містить JDK для запуску Spring Boot
FROM openjdk:17-jdk-slim

# Вказуємо робочий каталог для Docker-контейнера
WORKDIR /crm_system

# Копіюємо jar-файл, який буде виконуватись
COPY target/forgot-password-service-0.0.1-SNAPSHOT.jar forgot-password-service.jar

# Вказуємо команду для запуску програми
ENTRYPOINT ["java", "-jar", "forgot-password-service.jar"]
