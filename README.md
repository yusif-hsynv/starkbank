# 🏦 Starkbank Application (Spring Boot)

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0-brightgreen?logo=springboot)
![Database](https://img.shields.io/badge/H2-Database-blue?logo=h2)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-Message%20Broker-FF6600?logo=rabbitmq)
![Redis](https://img.shields.io/badge/Redis-Cache-DC382D?logo=redis)
![Gradle](https://img.shields.io/badge/Gradle-Build-02303A?logo=gradle)

A comprehensive **Banking Simulation API** built with **Spring Boot**.  
It allows users to manage customers and accounts, perform balance operations, and execute secure, asynchronous money transfers using RabbitMQ with built-in compensation (charge-back) mechanisms.

---

## 🚀 Features
- **Account & Customer Management:** CRUD operations for customers, accounts, and cities.
- **Asynchronous Money Transfer:** 3-step message-driven transfer process using RabbitMQ.
- **Compensation Mechanism:** Automatic rollback (charge-back) to the sender if the receiver's account is not found.
- **Caching:** Fast data retrieval for City entities using Redis.
- **Global Exception Handling:** Standardized error responses using `@RestControllerAdvice`.
- Layered Architecture (Controller → Service → Repository)
- RESTful API design with structured endpoints (`/v1/account`, `/v1/customer`, `/v1/cities`)

---

## ⚙️ Tech Stack
- Java 21  
- Spring Boot (Web, Data JPA, AMQP, Data Redis, Validation)  
- H2 Database (In-memory)
- RabbitMQ (Message Broker for transfers)
- Redis (Data Caching)
- Docker & Docker Compose (For infrastructure services)
- Gradle  
- Lombok

---

## 🧰 How to Run
```bash
# Clone the repository
git clone [https://github.com/yusif-hsynv/starkbank.git](https://github.com/yusif-hsynv/starkbank.git)
cd starkbank

# Start Redis and RabbitMQ via Docker Compose
docker-compose up -d

# Run the application using Gradle
./gradlew bootRun
```


## 👨‍💻 Author  
**Yusif Hüseynov**  
*Java Developer | Spring Boot | REST APIs*  
[GitHub Profile](https://github.com/yusif-hsynv)
