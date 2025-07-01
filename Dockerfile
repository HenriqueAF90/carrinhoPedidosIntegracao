# Dockerfile para pedidos-service
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . /app
RUN ./mvnw clean package -DskipTests
EXPOSE 8080
ENTRYPOINT ["./mvnw", "spring-boot:run"]
