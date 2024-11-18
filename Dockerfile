FROM openjdk:17
EXPOSE 8080:8080
RUN mkdir /app
COPY build/libs/*.jar /app/app.jar
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "/app/app.jar"]
