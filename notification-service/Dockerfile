FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/notification-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8088
ENTRYPOINT ["java", "-Xms64m", "-Xmx200m", "-jar", "app.jar"]