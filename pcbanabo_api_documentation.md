
# PCBanabo API Documentation

## Base URL
```
https://api.pcbanabo.com/v1
```

---

## Authentication
- **JWT-based Authentication**
- Login and Register endpoints return a JWT token that must be included in all protected endpoints using the `Authorization: Bearer <token>` header.

---

## Endpoints

### Auth
#### POST /auth/register
Registers a new user.
```json
Request Body:
{
  "username": "string",
  "email": "string",
  "password": "string",
  "userType": "USER | VENDOR"
}
```
```json
Response:
{
  "message": "A verification code has been sent to your provided email address."
}
```

#### POST /auth/register/verify
Verifies the email address of a new user.
```json
Request Body:
{
  "email": "string",
  "code": "string"
}
```
```json
Response:
{
  "message": "User registered successfully"
}
```

#### POST /auth/login
Logs in a user.
```json
Request Body:
{
  "email": "string",
  "password": "string"
}
```
```json
Response:
{
  "token": "jwt-token",
  "user": {
    "id": "uuid",
    "username": "string",
    "userType": "USER | VENDOR | ADMIN"
  }
}
```

---

### Users
#### GET /users/{id}
Returns profile info.
- **Auth Required**
```json
Response:
{
  "id": "uuid",
  "username": "string",
  "email": "string",
  "userType": "USER | VENDOR | ADMIN"
}
```

---

### Components
#### GET /components
Returns list of all PC components.
```json
Query Parameters:
- type: CPU | GPU | RAM | Storage | PSU | Case | Motherboard | CPU Cooler | RGB Strips | Case Fans
```
```json
Response:
[
  {
    "id": "uuid",
    "name": "Intel Core i5-13600K",
    "type": "CPU",
    "vendorId": "uuid",
    "specs": { ... },
    "price": 250.00
  }
]
```

#### POST /components
Add a new component (Vendor only)
- **Auth Required (VENDOR)**
```json
Request Body:
{
  "name": "string",
  "type": "CPU | GPU | ...",
  "specs": { ... },
  "price": 199.99
}
```

---

### Builds
#### POST /builds
Create a new build.
- **Auth Required (USER)**
```json
Request Body:
{
  "name": "string",
  "componentIds": ["uuid", "uuid"]
}
```

#### GET /builds/{id}
Fetch a single build.
- **Auth Required (USER)**
```json
Response:
{
  "id": "uuid",
  "name": "My Gaming PC",
  "userId": "uuid",
  "components": [ { ... } ],
  "benchmarks": { ... },
  "ratings": [ ... ]
}
```

#### GET /builds
Returns public builds.
```json
Query Parameters (optional):
- userId: string
```

#### POST /builds/{id}/rate
Rate a build.
- **Auth Required (USER)**
```json
Request Body:
{
  "rating": 1 - 5,
  "comment": "Great build!"
}
```

---

### Benchmarks
#### POST /benchmarks
Get benchmark results for a build.
```json
Request Body:
{
  "componentIds": ["uuid", "uuid"]
}
```
```json
Response:
{
  "score": 85,
  "performance": "High",
  "notes": "This build is optimized for gaming"
}
```

---

### Chatbot
#### POST /chat
Interact with the AI assistant.
```json
Request Body:
{
  "message": "What is a good GPU for gaming?"
}
```
```json
Response:
{
  "response": "A good GPU for gaming in 2025 is the NVIDIA RTX 4070 Super."
}
```

---

### Community
#### GET /community/posts
Returns all community posts.

#### POST /community/posts
- **Auth Required (USER)**
```json
Request Body:
{
  "title": "string",
  "content": "string"
}
```

#### POST /community/posts/{id}/comments
- **Auth Required (USER)**
```json
Request Body:
{
  "content": "Nice post!"
}
```

---

### Admin
#### GET /admin/components/pending
List of pending component submissions.
- **Auth Required (ADMIN)**

#### POST /admin/components/{id}/approve
Approve a submitted component.
- **Auth Required (ADMIN)**

#### DELETE /admin/components/{id}
Delete an invalid or rejected component.
- **Auth Required (ADMIN)**

---

## Status Codes
- **200 OK**: Successful operation
- **201 Created**: Resource successfully created
- **400 Bad Request**: Malformed request
- **401 Unauthorized**: Missing or invalid credentials
- **403 Forbidden**: Not enough privileges
- **404 Not Found**: Resource doesn’t exist
- **500 Internal Server Error**: Server-side error

---

## Notes
- All IDs are UUIDs
- All requests and responses are in JSON
- Rate limiting and spam control measures will be enforced in production

---

## Future Enhancements
- OAuth2 login (Google/GitHub)
- WebSocket for real-time build chat
- Vendor analytics dashboard
