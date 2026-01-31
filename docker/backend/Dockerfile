# Используем многостадийную сборку для уменьшения размера
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

# Копируем только файлы для сборки сначала
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Скачиваем зависимости
RUN gradle dependencies --no-daemon

# Копируем исходный код
COPY src ./src

# Собираем приложение
RUN gradle bootJar --no-daemon

# Финальный образ
FROM openjdk:21-jdk-slim

# Устанавливаем переменные окружения
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE="docker"

# Создаем пользователя для безопасности
RUN groupadd -r javauser && useradd -r -g javauser javauser

WORKDIR /app

# Копируем собранный JAR из стадии builder
COPY --from=builder /app/build/libs/*.jar app.jar

# Меняем владельца файлов
RUN chown -R javauser:javauser /app
USER javauser

# Открываем порт приложения
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
