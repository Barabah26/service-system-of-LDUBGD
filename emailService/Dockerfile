# Вказуємо базовий образ, який містить JDK для запуску Spring Boot
FROM openjdk:17-jdk-slim

# Вказуємо робочий каталог для Docker-контейнера
WORKDIR /crm_system

# Копіюємо jar-файл, який буде виконуватись
COPY target/emailService-0.0.1-SNAPSHOT.jar emailService.jar

# Вказуємо команду для запуску програми
ENTRYPOINT ["java", "-jar", "emailService.jar"]
