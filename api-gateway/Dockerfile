# Stage 1: Cài dependency
FROM maven:3.9.6-eclipse-temurin-17 AS my-platform-builder
WORKDIR /build
COPY . .
RUN mvn clean install -DskipTests

# Stage 2: Build JAR
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 3: Runtime
FROM alpine:3.20
RUN apk update && apk add --no-cache openjdk17-jre

ARG PROJECT_VERSION=0.0.1-SNAPSHOT
WORKDIR /home/app
ENV SPRING_PROFILES_ACTIVE=prod

# Copy JAR đúng đường dẫn
COPY --from=builder /app/api-gateway/target/api-gateway-${PROJECT_VERSION}.jar api-gateway.jar

EXPOSE 8443

ENTRYPOINT [ "sh", "-c", "java -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -jar api-gateway.jar" ]



