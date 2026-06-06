# Transaction Aggregator

A microservices-based transaction processing system built with **Spring Boot**, **Apache Kafka**, and **PostgreSQL**. Transactions are ingested via a REST API, streamed through Kafka, and aggregated into a queryable analytics store.

---

## Architecture

```
Client
  │
  ▼
┌─────────────────────┐
│   ingestor-service  │  :8081
│   (REST → Kafka)    │
└────────┬────────────┘
         │  Kafka topic
         ▼
┌─────────────────────┐
│ aggregator-service  │  :8082
│  (Kafka → Postgres) │
└────────┬────────────┘
         │
         ▼
┌─────────────────────┐
│     PostgreSQL      │  :5432
└─────────────────────┘
```

| Service | Port | Responsibility |
|---|---|---|
| `ingestor-service` | 8081 | Accepts transactions, validates, publishes to Kafka |
| `aggregator-service` | 8082 | Consumes Kafka events, persists and exposes analytics |
| `kafka` | 9092 (internal) / 29092 (host) | Message broker (KRaft mode) |
| `postgres` | 5432 | Persistent store for aggregated transactions |
| `kafka-ui` | 8080 | Kafka topic browser |

---

## Prerequisites

- [Docker](https://docs.docker.com/get-docker/) and [Docker Compose](https://docs.docker.com/compose/install/) v2+
- Java 21+ and Maven (only needed if running services outside Docker)

---

## Running the Full Stack

```bash
git clone https://github.com/gntimba/transanction-agrgregator.git
cd transanction-agrgregator

docker compose up --build
```

All services start in dependency order. Kafka and Postgres healthchecks gate the Spring Boot services — wait until you see both services log `Started ... in ... seconds` before sending requests.

To stop:

```bash
docker compose down
```

To stop and wipe the Postgres volume:

```bash
docker compose down -v
```

---

## Running Services Locally (Outside Docker)

If you want to run `ingestor-service` or `aggregator-service` from your IDE or Maven while keeping infrastructure in Docker:

**1. Start only infrastructure:**

```bash
docker compose up kafka postgres kafka-ui
```



---

## Configuration

### Environment Variables

All service config is driven by environment variables, with sane local defaults baked into `application.yml`.

#### `ingestor-service`

| Variable | Docker value | Local default |
|---|---|---|
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | `kafka:9092` | `localhost:29092` |
| `AGGREGATOR_BASE_URL` | `http://aggregator-service:8082` | `http://localhost:8082` |
| `SERVER_PORT` | `8081` | `8081` |

#### `aggregator-service`

| Variable | Docker value | Local default |
|---|---|---|
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | `kafka:9092` | `localhost:29092` |
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://postgres:5432/transactions_db` | `jdbc:postgresql://localhost:5432/transactions_db` |
| `SPRING_DATASOURCE_USERNAME` | `postgres` | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | `postgres` | `postgres` |
| `SERVER_PORT` | `8082` | `8082` |

---

## API Reference

### Ingestor Service — `http://localhost:8081`

#### `POST /api/v1/transaction`

Submit a new transaction for processing.

**Request body:**

```json
{
  "id": "a1b2c3d4-0000-0000-0000-000000000001",
  "accountId": "ACC-001",
  "merchantId": 42,
  "amount": 149.99,
  "currency": "USD",
  "source": "mobile-app",
  "transactionDate": "2024-06-01T10:30:00Z"
}
```

| Field | Type | Required | Notes                                            |
|---|---|---|--------------------------------------------------|
| `id` | UUID | ✅ | ID must not be  duplicate                        |
| `accountId` | string | ✅ | acoount of the user                              |
| `merchantId` | integer | ✅ | Must exist in the aggregator's merchant registry |
| `amount` | decimal | ✅ | Must be ≥ 0                                      |
| `currency` | string | ✅ | ISO 4217 format e.g. `USD`, `ZAR`                |
| `source` | string | ✅ | Channel identifier e.g. `mobile-app`, `pos`      |
| `transactionDate` | ISO datetime | ✅ |                                                  |
| `createdDate` | ISO datetime | ❌ | Defaults to now                                  |

**Response:** `200 OK` with a confirmation string.

---

### Aggregator Service — `http://localhost:8082`

#### Merchant Reference

| Endpoint | Method | Description |
|---|---|---|
| `/api/v1/merchants` | GET | List all merchants with categories |
| `/api/v1/merchant?merchant={id}` | GET | Get a single merchant by ID |
| `/api/v1/categores` | GET | List all unique categories |
| `/api/v1/tran?id={uuid}` | GET | Look up a transaction by ID |

#### Analytics

| Endpoint | Method | Description |
|---|---|---|
| `/analytics/monthlySpend` | GET | Total spend grouped by month |
| `/analytics/dailyTxnCount` | GET | Transaction count grouped by day |
| `/analytics/topMerchant` | GET | Merchants ranked by total spend |
| `/analytics/spendPerCategory?account={id}` | GET | Spend breakdown by category for an account |
| `/analytics/txnPeriod?dateStart=YYYY-MM-DD&dateEnd=YYYY-MM-DD` | GET | Transactions within a date range |
| `/analytics/TotalSpendBetweenDates?dateStart=YYYY-MM-DD&dateEnd=YYYY-MM-DD&account={id}` | GET | Total spend for an account between dates |
| `/analytics/findByMerchantId/{merchant}` | GET | All transactions for a merchant |

---

## Example Walkthrough

```bash
# 1. Submit a transaction
curl -X POST http://localhost:8081/api/v1/transaction \
  -H "Content-Type: application/json" \
  -d '{
    "id": "a1b2c3d4-0000-0000-0000-000000000001",
    "accountId": "ACC-001",
    "merchantId": 1,
    "amount": 250.00,
    "currency": "ZAR",
    "source": "mobile-app",
    "transactionDate": "2024-06-01T10:30:00Z"
  }'

# 2. Check monthly spend
curl http://localhost:8082/analytics/monthlySpend

# 3. Browse Kafka topics
open http://localhost:8080

# 4. Check top merchants
curl http://localhost:8082/analytics/topMerchant
```

---

## Observability

**Kafka UI** is available at [http://localhost:8080](http://localhost:8080). Use it to:
- Browse topics and partitions
- Inspect messages (including DLQ/dead-letter topics)
- Monitor consumer group lag

---