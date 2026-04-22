# Ahoi Burger API

A REST API for managing a burger restaurant menu, built with **Spring Boot 3.5 + Kotlin + MongoDB**.

## Live Demo

**Deployed on Railway:**
https://ahoi-challenge-production.up.railway.app/swagger-ui/index.html#/

**API Key (for testing):**
```
Found in test.env
```

In Swagger UI click **Authorize** (🔒), paste the key above, then try any endpoint.

---

## Tech Stack

- **Kotlin 2.3** + **Spring Boot 3.5**
- **MongoDB** — document storage
- **SpringDoc OpenAPI** — Swagger UI
- **Docker** — containerized deployment

---

## Run Locally

### Prerequisites
- Java 21+
- MongoDB running on `localhost:27017`

### Start
```bash
./gradlew bootRun
```

API available at `http://localhost:8080`

### Run with Docker
```bash
docker-compose up --build
```
Starts both the app and MongoDB in containers.

---

## Authentication

All API endpoints require an API key in the request header:

```
X-API-Key: ahoi-secret-key-2024
```

Without it, the API returns `401 Unauthorized`.

The key is configured via the `API_KEY` environment variable (falls back to `ahoi-secret-key-2024` locally).

---

## API Documentation

Swagger UI (no API key required):
```
http://localhost:8080/swagger-ui/index.html
```

---

## Endpoints

### Burgers — `/api/burgers`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/burgers` | List all burgers |
| `GET` | `/api/burgers/{id}` | Get burger by ID |
| `POST` | `/api/burgers` | Create a burger |
| `PUT` | `/api/burgers/{id}` | Update a burger |
| `DELETE` | `/api/burgers/{id}` | Delete a burger |

**Query filters:**

| Param | Example | Description |
|-------|---------|-------------|
| `name` | `?name=ahoi` | Case-insensitive partial match |
| `minPrice` | `?minPrice=5.00` | Minimum price (USD) |
| `maxPrice` | `?maxPrice=15.00` | Maximum price (USD) |
| `vegetarian` | `?vegetarian=true` | Vegetarian only |
| `available` | `?available=true` | Available only |

---

### Drinks — `/api/drinks`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/drinks` | List all drinks |
| `GET` | `/api/drinks/{id}` | Get drink by ID |
| `POST` | `/api/drinks` | Create a drink |
| `PUT` | `/api/drinks/{id}` | Update a drink |
| `DELETE` | `/api/drinks/{id}` | Delete a drink |

**Query filters:** `name`, `minPrice`, `maxPrice`, `available`

---

## Example Requests

**Create a burger:**
```bash
curl -X POST http://localhost:8080/api/burgers \
  -H "X-API-Key: ahoi-secret-key-2024" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Ahoi Classic",
    "price": 12.99,
    "weightInGrams": 300,
    "vegetarian": false,
    "description": "Our signature beef burger",
    "specialIngredients": ["truffle mayo", "aged cheddar"],
    "allergens": ["gluten", "dairy"],
    "available": true
  }'
```

**Filter burgers:**
```bash
curl "http://localhost:8080/api/burgers?available=true&maxPrice=12&vegetarian=false" \
  -H "X-API-Key: ahoi-secret-key-2024"
```

---

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `API_KEY` | `ahoi-secret-key-2024` | API authentication key |
| `SPRING_DATA_MONGODB_URI` | `mongodb://localhost:27017/ahoiburger` | MongoDB connection |
| `SERVER_PORT` | `8080` | Server port |

---

## Deploy on Railway

1. Push to GitHub
2. Create a new project on [railway.app](https://railway.app)
3. Add a **MongoDB** service
4. Deploy from your GitHub repo (Railway detects the `Dockerfile`)
5. Set environment variables:
   - `SPRING_DATA_MONGODB_URI` → reference the Railway MongoDB `MONGO_URL`
   - `API_KEY` → your secret key
6. Generate a public domain under **Settings → Networking**

---

## Run Tests

```bash
./gradlew test
```
