# --- Stage 1: Build và install toàn bộ my-platform (bao gồm parent) ---
FROM maven:3.9.6-eclipse-temurin-17 AS my-platform-builder

WORKDIR /build
COPY . .
RUN mvn clean install -DskipTests

# --- Stage 2: Build common-service sử dụng local .m2 ---
FROM maven:3.9.6-eclipse-temurin-17 AS common-service-builder

WORKDIR /build

# Chỉ copy source cần thiết cho common-service
COPY ../common-service /build/common-service
COPY pom.xml /build/pom.xml

# Copy lại .m2 repo từ stage trước (đã có parent)
COPY --from=my-platform-builder /root/.m2 /root/.m2

WORKDIR /build/common-service
RUN mvn clean package -DskipTests
