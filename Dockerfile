FROM openjdk:17
EXPOSE 8080:8080
RUN mkdir /app
COPY build/libs/*.jar /app/app.jar
ENV KTOR_CONFIG application-local.conf
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-Dktor.config=${KTOR_CONFIG}", "-jar", "/app/app.jar"]

