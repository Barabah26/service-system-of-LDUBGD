# Вказуємо базовий образ, який містить JDK для запуску Spring Boot
FROM openjdk:17-jdk-slim

# Додаємо тимчасову змінну для порту
ENV PORT 8080

# Вказуємо робочий каталог для Docker-контейнера
WORKDIR /crm_system

# Копіюємо jar-файл, який буде виконуватись
COPY target/restService-0.0.1-SNAPSHOT.jar restService.jar

# Відкриваємо порт 8080
EXPOSE 8080

# Вказуємо команду для запуску програми
ENTRYPOINT ["java", "-jar", "restService.jar"]
