# 🌾 FarmDirect (Vivasayi)
### Agricultural Delivery Platform — Spring Boot Microservices

## 📖 About
FarmDirect directly connects farmers with consumers in Tamil Nadu,
eliminating middlemen and ensuring fair prices for farmers.

## 🚀 Tech Stack
| Technology | Usage |
|---|---|
| Spring Boot 4.x | Microservices Backend |
| Netflix Eureka | Service Discovery |
| Spring Cloud Gateway | API Gateway |
| PostgreSQL | Database |
| Apache Kafka (KRaft) | Event Messaging |
| JWT | Security |
| Cloudinary | Media Storage |
| Docker & Docker Compose | Containerization |
| Swagger OpenAPI | API Documentation |

## 🏗️ Microservices Architecture
| Service | Port | Description |
|---|---|---|
| Discovery Service | 8761 | Eureka Server |
| API Gateway | 8080 | Single Entry Point + JWT |
| User Service | 8081 | Consumer Management |
| Farmer Service | 8082 | Farmer Management |
| Runner Service | 8083 | Delivery Runner |
| Admin Service | 8084 | Admin Operations |
| Product Service | 8085 | Crop Management |
| Order Service | 8086 | Order Processing |
| Location Service | 8087 | Geolocation |
| Notification Service | 8088 | Kafka Notifications |
| Media Service | 8089 | File Upload |

## ✨ Key Features
- 🌾 Farmer registration with Admin approval
- 📍 Location based nearby farmer discovery
- 🛒 Direct crop ordering without middlemen
- 📅 Auto product expiry scheduler
- 🔔 Real time notifications via Apache Kafka
- 🔐 JWT security via API Gateway
- 📸 Crop photo/video upload via Cloudinary
- 🚚 Runner assignment and delivery tracking

## 🔄 Application Flow

Farmer registers → Admin approves → Farmer posts crop
User discovers nearby farmers → Places order
Admin assigns runner → Runner delivers
Everyone gets notified via Kafka ✅

## 🚀 How To Run

### Prerequisites
- Java 17+
- Docker Desktop
- PostgreSQL

### Run with Docker
```bash
# Clone repository
git clone https://github.com/yourusername/farmdirect-microservices.git

# Go to project
cd farmdirect-microservices

# Build all services
cd user-service && mvn clean package -DskipTests && cd ..
cd farmer-service && mvn clean package -DskipTests && cd ..
# ... repeat for all services

# Run everything
docker-compose up --build
```

### Access
Eureka Dashboard  → http://localhost:8761
API Gateway       → http://localhost:8080
Swagger UI        → http://localhost:808x/swagger-ui/index.html

## 📱 API Endpoints

### User Service
POST /api/users/register
POST /api/users/login
GET  /api/users/{id}
### Order Service
POST /api/orders
GET  /api/orders/{id}
PUT  /api/orders/{id}/status
PUT  /api/orders/{orderId}/assign-runner/{runnerId}

## 👨‍💻 Developer
**Sampath Kumar**
Final Year B.Tech Student
Tamil Nadu, India
