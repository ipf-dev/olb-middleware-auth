FROM amazoncorretto:17
COPY build/libs/*.jar /app/
WORKDIR /app
CMD ["java", "-jar", "olb-middleware-auth-0.0.1-SNAPSHOT.jar"]
