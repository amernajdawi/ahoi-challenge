# Mini-API

A simple REST API built with **Spring Boot 3.5 + Kotlin 2.3 + MongoDB**.

## Prerequisites

- Java 21+
- MongoDB running on `localhost:27017`

## Run

```bash
./gradlew bootRun
```

The API starts on `http://localhost:8080`.

## API Endpoints

| Method   | Endpoint                  | Description          |
|----------|---------------------------|----------------------|
| `GET`    | `/api/items`              | List all items       |
| `GET`    | `/api/items/{id}`         | Get item by ID       |
| `GET`    | `/api/items/search?name=` | Search items by name |
| `GET`    | `/api/items/tag/{tag}`    | Filter items by tag  |
| `POST`   | `/api/items`              | Create a new item    |
| `PUT`    | `/api/items/{id}`         | Update an item       |
| `DELETE` | `/api/items/{id}`         | Delete an item       |

## Example Requests

**Create an item:**
```bash
curl -X POST http://localhost:8080/api/items \
  -H "Content-Type: application/json" \
  -d '{"name": "Laptop", "description": "A fast laptop", "price": 999.99, "tags": ["electronics", "computers"]}'
```

**List all items:**
```bash
curl http://localhost:8080/api/items
```

**Search by name:**
```bash
curl "http://localhost:8080/api/items/search?name=laptop"
```

**Update an item:**
```bash
curl -X PUT http://localhost:8080/api/items/{id} \
  -H "Content-Type: application/json" \
  -d '{"name": "Gaming Laptop", "description": "A very fast laptop", "price": 1499.99, "tags": ["electronics", "gaming"]}'
```

**Delete an item:**
```bash
curl -X DELETE http://localhost:8080/api/items/{id}
```
