# Multi-stage build — imagem final enxuta (~200MB vs ~600MB)
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn --batch-mode clean package -DskipTests -Dnexus.url=${NEXUS_URL:-http://localhost:8082}

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Usuário não-root por segurança
RUN addgroup -S rml && adduser -S rml -G rml
USER rml

# TODO: Alterar o nome do JAR se necessário
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-jar", "app.jar"]
