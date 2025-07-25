# 🏡 Real Estate Microservice System

A microservice-based system for managing real estate information, including properties, land ownership, users, and transactions. Designed for scalability, modularity, and ease of integration.

## 🚀 Technologies Used

- **Java 17** / **Spring Boot**
- **Spring Cloud** (Eureka, Config, Gateway)
- **PostgreSQL** (or MySQL)
- **Euruka**
- **Docker & Docker Compose**
- **Spring Security / JWT**

## 🧱 Microservice Architecture



## 🧩 Modules

| Service              | Description                               |
|----------------------|-------------------------------------------|
| `gateway-service`    | API Gateway (routing, auth forwarding)    |
| `auth-service`       | Handles login, JWT, roles/permissions     |
| `user-service`       | Manages user profiles, owners             |
| `property-service`   | Manages real estate assets & land info    |
| `transaction-service`| Records purchases, transfers              |
| `config-service`     | Centralized config for microservices      |
| `discovery-service`  | Eureka registry for service discovery     |


🔒 Security
JWT Authentication

Spring Security

Role-based authorization (admin, user...)

📚 Authors & License
Developed by [Lê Xuân Hùng]
