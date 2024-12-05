# Вказуємо базовий образ, який містить JDK для запуску Spring Boot
FROM openjdk:17-jdk-slim

# Додаємо тимчасову змінну для порту
ENV PORT 9000

# Вказуємо робочий каталог для Docker-контейнера
WORKDIR /auth-service

# Копіюємо jar-файл, який буде виконуватись
COPY target/crm_for_bot-0.0.1-SNAPSHOT.jar crm_for_bot.jar

# Відкриваємо порт 8080
EXPOSE 9000

# Вказуємо команду для запуску програми
ENTRYPOINT ["java", "-jar", "crm_for_bot.jar"]