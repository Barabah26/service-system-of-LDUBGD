# Вказуємо базовий образ, який містить JDK для запуску Spring Boot
FROM openjdk:17-jdk-slim
# Вказуємо робочий каталог для Docker-контейнера
WORKDIR /auth-service
# Копіюємо jar-файл, який буде виконуватись
COPY target/auth-service-0.0.1-SNAPSHOT.jar auth-service.jar
# Вказуємо команду для запуску програми
ENTRYPOINT ["java", "-jar", "auth-service.jar"]