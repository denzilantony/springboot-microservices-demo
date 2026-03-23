# Architecture & Design Decisions

## Why three services?

The split reflects a real domain boundary pattern used in 
production systems I have worked on:

- **product-service** owns catalogue data — it changes slowly 
  and is read-heavy. Isolating it allows independent scaling 
  and caching strategies without affecting order processing.

- **order-service** owns transactional state — it changes fast, 
  needs strong consistency, and has different SLA requirements 
  than the product catalogue.

- **notification-service** is intentionally fire-and-forget — 
  using async communication here means a slow email provider 
  never blocks an order being placed.

## Why Spring Events for async communication?

For a demo system, Spring Events keeps the infrastructure simple 
without sacrificing the architectural pattern. In a production 
system at scale I would replace this with Apache Kafka or IBM MQ 
(which I have used in production) to get persistence, replay, 
and consumer group support.

## Why Testcontainers for integration tests?

H2 in-memory databases mask real SQL issues — type differences, 
constraint behaviour, and query performance all differ from 
production PostgreSQL. Testcontainers spins up a real PostgreSQL 
instance per test run, giving integration tests that actually 
reflect production behaviour. This approach reduced production 
defects significantly in my AB InBev work.

## What I would add at production scale

- API Gateway (Spring Cloud Gateway) for routing and rate limiting
- Distributed tracing with OpenTelemetry and Jaeger
- Centralized logging with ELK stack
- Kubernetes manifests with Helm charts
- Circuit breaker pattern with Resilience4j
- Kafka replacing Spring Events for true async decoupling

## Trade-offs made for demo scope

- No authentication between services (would use mTLS or JWT 
  propagation in production)
- Single PostgreSQL instance shared (production would have 
  one database per service — database-per-service pattern)
- No API versioning strategy shown (I use URI versioning 
  /api/v1/ in production — see order-service for an example)
