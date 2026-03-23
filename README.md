# Spring Boot Microservices Demo

![CI](https://github.com/denzilantony/springboot-microservices-demo/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java_17-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=flat&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=flat)

A production-inspired microservices system built with Java 17 and
Spring Boot 3, demonstrating real-world backend engineering patterns
used in high-scale enterprise platforms.

---

## Architecture
```
┌─────────────────┐     REST      ┌─────────────────┐
│  product-service│──────────────▶│  order-service  │
│  (PostgreSQL)   │               │  (in progress)  │
└─────────────────┘               └────────┬────────┘
                                           │ Events
                                  ┌────────▼────────┐
                                  │notification-svc │
                                  │  (in progress)  │
                                  └─────────────────┘
```

---

## Services

| Service | Status | Port | Description |
|---|---|---|---|
| `product-service` | ✅ Complete | 8081 | Product catalogue REST API |
| `order-service` | 🔄 In progress | 8082 | Order management |
| `notification-service` | 🔄 In progress | 8083 | Async notifications |

---

## Tech Stack

- **Java 17** + **Spring Boot 3.x**
- **Spring Security** with OAuth2
- **PostgreSQL** (production) + **H2** (tests)
- **JUnit 5 + Mockito** — TDD approach, 13/13 tests passing
- **Docker + Docker Compose** — one command startup
- **GitHub Actions** — CI pipeline on every push

---

## Test Coverage
```
ProductServiceApplicationTests    ✅
Product Service Tests             ✅  7/7
Product Controller Tests          ✅  5/5
─────────────────────────────────────────
Total                             ✅ 13/13
```

---

## Running Locally

### Prerequisites
- Docker Desktop installed and running
- Java 17+
- Maven 3.8+

### Start everything with one command
```bash
docker-compose up --build
```

### Access the API
```
http://localhost:8081/api/v1/products
http://localhost:8081/swagger-ui.html
http://localhost:8081/actuator/health
```

### Run tests
```bash
mvn test
```

---

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/products` | Get all products |
| GET | `/api/v1/products/{id}` | Get product by ID |
| POST | `/api/v1/products` | Create new product |
| PUT | `/api/v1/products/{id}` | Update product |
| DELETE | `/api/v1/products/{id}` | Delete product |
| GET | `/api/v1/products/search?name=` | Search by name |
| GET | `/api/v1/products/out-of-stock` | Get out of stock |

---

## Design Decisions

See [ARCHITECTURE.md](./ARCHITECTURE.md) for detailed notes on
design decisions, trade-offs, and production considerations.

---

## Author

**Denzil Antony** — Senior Java Backend Engineer, Germany
[linkedin.com/in/denzilantony](https://linkedin.com/in/denzilantony)