# Вказуємо базовий образ, який містить JDK для запуску Spring Boot
FROM openjdk:17-jdk-slim
# Вказуємо робочий каталог для Docker-контейнера
WORKDIR /crm_system
# Копіюємо jar-файл, який буде виконуватись
COPY target/gatewayService-0.0.1-SNAPSHOT.jar gatewayService.jar
# Вказуємо команду для запуску програми
ENTRYPOINT ["java", "-jar", "gatewayService.jar"]
