# ========= BUILD STAGE =========
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /build

COPY pom.xml .
# 2️⃣ Copy pom của TẤT CẢ module (để Maven không lỗi)
COPY common-service/pom.xml common-service/pom.xml
COPY core-service/pom.xml core-service/pom.xml
COPY proxy-client/pom.xml proxy-client/pom.xml
COPY service-discovery/pom.xml service-discovery/pom.xml
COPY api-gateway/pom.xml api-gateway/pom.xml
COPY identity-service/pom.xml identity-service/pom.xml
COPY property-service/pom.xml property-service/pom.xml
COPY system-service/pom.xml system-service/pom.xml

# 3️⃣ Tạo folder rỗng cho module (QUAN TRỌNG)
RUN mkdir -p \
    common-service core-service proxy-client service-discovery \
    api-gateway identity-service property-service system-service
RUN mvn -B -DskipTests dependency:go-offline

COPY . .

RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app

#ENV SPRING_PROFILES_ACTIVE=prod

COPY --from=build /build/service-discovery/target/*.jar app.jar

EXPOSE 8761
ENTRYPOINT ["java","-jar","app.jar"]