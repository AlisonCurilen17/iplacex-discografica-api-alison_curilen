# Stage 1: Build con Gradle
FROM gradle:8.5-jdk17 AS builder

WORKDIR /app

COPY . .

RUN gradle build --no-daemon

# Stage 2: Ejecutar aplicación
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=builder /app/build/libs/discografia-1.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]