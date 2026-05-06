# 🏡 Real Estate Microservice System

A microservice-based platform for managing a matcha shop, supporting order processing, inventory management, staff scheduling, and user management. Designed for scalability, modularity, and real-time integration.

## 🚀 Technologies Used

- **Java 17** / **Spring Boot 3.2.5**
- **Spring Cloud** (Eureka, Config, Gateway)
- **PostgreSQL** 
- **Docker & Docker Compose**
- **Spring Security / JWT**
- **Firebase**
- **Kafka**
## 🧱 Microservice Architecture



## 🧩 Modules

| Service              | Description                               |
|----------------------|-------------------------------------------|
| `gateway-service`    | API Gateway (routing, auth forwarding)    |
| `auth-service`       | Handles login, JWT, roles/permissions     |
| `user-service`       | Manages user profiles, owners             |
| `property-service`   | Manages real estate assets & land info    |
| `order-service`      | Handles order processing, status management|
| `config-service`     | Centralized config for microservices      |
| `discovery-service`  | Eureka registry for service discovery     |
| `core-service`       | Configuration bean common                 |
| `system-service`     | AuditLog, Nofifycation and Theme Server   |


🔒 Security
JWT Authentication

Spring Security

Role-based authorization (admin, user...)

📚 Authors & License
Developed by [Lê Xuân Hùng]
