FROM maven:3.8.1-openjdk-11-slim as builder

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build application
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:11-jre-slim

WORKDIR /app

# Copy built JAR from builder stage
COPY --from=builder /app/target/ev-demand-prediction-*.jar app.jar

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Set environment variables
ENV JAVA_OPTS="-Xmx256m -Xms128m"

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
