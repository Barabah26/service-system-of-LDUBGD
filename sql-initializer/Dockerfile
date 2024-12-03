# Вказуємо базовий образ, який містить JDK для запуску Spring Boot
FROM openjdk:17-jdk-slim

# Додаємо тимчасову змінну для порту
ENV PORT 9080

# Вказуємо робочий каталог для Docker-контейнера
WORKDIR /crm_system

# Копіюємо jar-файл, який буде виконуватись
COPY target/sql-initializer-0.0.1-SNAPSHOT.jar sql-initializer.jar

# Копіюємо SQL скрипти до контейнера
COPY src/main/resources/schema-postgres.sql /db/schema-postgres.sql
COPY src/main/resources/data.sql /db/data.sql

# Відкриваємо порт 9080
EXPOSE 9080

# Вказуємо команду для запуску програми

ENTRYPOINT ["java", "-jar", "sql-initializer.jar"]

