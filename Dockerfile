FROM openjdk:17

# Создаём рабочую директорию
WORKDIR /app

# Открываем порт
EXPOSE 8080

# Копируем JAR-файл (замени, если имя файла отличается)
COPY build/libs/com.goods_warehouse-0.0.1-SNAPSHOT.jar /app/app.jar

# Устанавливаем переменную окружения
ENV KTOR_CONFIG application-local.conf

# Запускаем приложение
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-Dktor.config=${KTOR_CONFIG}", "-jar", "/app/app.jar"]
