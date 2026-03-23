# Spring Boot Microservices Demo

![Java](https://img.shields.io/badge/Java_17-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=flat&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)
![CI](https://img.shields.io/badge/GitHub_Actions-2088FF?style=flat&logo=githubactions&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=flat)

A production-inspired microservices system built with Java 17 and 
Spring Boot, demonstrating real-world patterns used in high-scale 
backend platforms.

---

## Architecture
```
┌─────────────────┐     REST      ┌─────────────────┐
│  product-service│──────────────▶│  order-service  │
│  (PostgreSQL)   │               │  (H2 / Postgres)│
└─────────────────┘               └────────┬────────┘
                                           │ Spring
                                           │ Events
                                  ┌────────▼────────┐
                                  │notification-svc │
                                  │  (logs/email)   │
                                  └─────────────────┘
```

---

## Services

| Service | Responsibility | Port |
|---|---|---|
| `product-service` | Manage product catalogue, stock levels | 8081 |
| `order-service` | Place and track orders | 8082 |
| `notification-service` | Handle order notifications async | 8083 |

---

## Tech Stack

- **Java 17** + **Spring Boot 3.x**
- **Spring Security** with OAuth2 resource server
- **PostgreSQL** (product-service) + **H2** (order-service, tests)
- **Spring Events** for async inter-service communication
- **OpenAPI / Swagger UI** on every service
- **JUnit 5 + Mockito** — TDD approach throughout
- **Testcontainers** — integration tests with real PostgreSQL
- **Docker + Docker Compose** — run everything with one command
- **GitHub Actions** — CI pipeline runs all tests on every push

---

## Running Locally

### Prerequisites
- Docker Desktop installed and running
- Java 17+ installed
- Maven 3.8+ installed

### Start all services
```bash
docker-compose up --build
```

### Access the APIs
- Product Service: http://localhost:8081/swagger-ui.html
- Order Service:   http://localhost:8082/swagger-ui.html
- Notification:    http://localhost:8083/swagger-ui.html

---

## Running Tests
```bash
# All tests
mvn test

# With coverage report
mvn verify
```

---

## Key Design Decisions

See [ARCHITECTURE.md](./ARCHITECTURE.md) for detailed notes on 
design decisions, trade-offs, and what I would do differently 
at larger scale.

---

## Author

**Denzil Antony** — Senior Java Backend Engineer, Germany  
[linkedin.com/in/denzilantony](https://linkedin.com/in/denzilantony)
