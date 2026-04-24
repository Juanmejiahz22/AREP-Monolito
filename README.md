# Twitter Secure Monolith

A Twitter-like web application built with a **monolithic architecture**, focused on implementing secure authentication using OAuth2 and JWT. The backend is developed with Spring Boot (Java 21), and the frontend uses React.

---

## Project Overview

This academic project demonstrates how to build a secure full-stack application using modern authentication practices. It includes public and protected endpoints, JWT validation (issuer and audience), and a simple in-memory persistence layer.

Although implemented as a monolith, the architecture is designed with scalability in mind and can be refactored into microservices.

---

## Architecture Diagram

![alt text](/images/image-6.png)
---

## API Documentation (Swagger)

![alt text](/images/image-1.png)

![alt text](/images/image-3.png)

![alt text](/images/image-4.png)

![alt text](/images/image-5.png)

![alt text](/images/image-2.png)
---

## Getting Started

These instructions will help you set up and run the project locally for development and testing.

---

## Prerequisites

Make sure you have the following installed:

* Java 21
* Maven 3.8+
* Node.js 18+
* npm

---

## Installation

### 1. Clone the repository

```bash
git clone https://github.com/Juanmejiahz22/AREP-Monolito
cd twitter-secure-monolith
```

---

### 2. Run the Backend

```bash
cd backend-monolith-springboot
mvn clean install
mvn spring-boot:run
```

Backend will be available at:
 http://localhost:8080

Swagger UI:
 http://localhost:8080/swagger-ui.html

---

### 3. Run the Frontend

```bash
cd frontend-react
cp .env.example .env
npm install
npm run dev
```

Frontend will be available at:
 http://localhost:5173

---

### 4. Quick Demo

* Open the frontend
* Log in using Auth0
* View public posts
* Create a protected post
* Check your profile information

---

## Authentication Flow

1. User logs in via React frontend
2. Auth0 issues a JWT with audience `https://twitter-api`
3. Frontend sends the token in the `Authorization` header
4. Backend validates:

   * RSA signature
   * Issuer
   * Audience
   * Token expiration
5. Protected endpoints reject requests without valid tokens (401)

---

## API Endpoints

### Public

* `GET /api/posts` → Retrieve global feed

### Protected (JWT required)

* `POST /api/posts` → Create a post (`{"message": "..."}`)
* `GET /api/me` → Retrieve user profile

### Example Requests

```bash
# Public feed
curl http://localhost:8080/api/posts

# Create post (requires token)
curl -X POST http://localhost:8080/api/posts \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"message": "My first post"}'

# Get profile (requires token)
curl http://localhost:8080/api/me \
  -H "Authorization: Bearer <token>"
```

---

## Running the Tests

### Backend Tests

```bash
cd backend-monolith-springboot
mvn test
```

### What is Tested?

* Business logic (post creation, validation)
* JWT claim extraction
* Audience validation
* Public vs protected endpoints
* Error handling (400, 401)

### Example Test Cases

* `PostServiceTest` → Post logic validation
* `ProfileServiceTest` → JWT claim extraction
* `AudienceValidatorTest` → Token audience validation
* Integration tests for controllers

---
## Demonstration video
If the video doesn't play for any reason, please contact us and we'll look into it

https://drive.google.com/file/d/1Wu02W9s8GxpPPUWPcsa2KHg8-AyUsNRE/view?usp=sharing

##  Deployment

### Backend

```bash
mvn clean package
java -jar target/twitter-api-1.0.0.jar
```

### Frontend

```bash
npm run build
```

Deploy the `dist/` folder to services like:

* AWS S3

---

## Built With

* **Spring Boot** – Backend framework
* **Spring Security** – Authentication & authorization
* **React** – Frontend library
* **Vite** – Frontend build tool
* **Auth0** – Authentication provider
* **Swagger/OpenAPI** – API documentation

---

## Contributing

Please read `CONTRIBUTING.md` for details about:

* Code of conduct
* Pull request process

---

## Versioning

This project follows **Semantic Versioning (SemVer)**.
Check repository tags for available versions.

---

##  Authors

* Juan José Mejía
* David Santiago Castro

---

## License

This project is licensed under the MIT License. See `LICENSE.md` for details.

---

## Acknowledgments

* Inspiration from modern secure web architectures
* Thanks to open-source tools and libraries used in this project

---

## Troubleshooting

| Issue                         | Solution                                             |
| ----------------------------- | ---------------------------------------------------- |
| Frontend cannot reach backend | Ensure backend is running on `http://localhost:8080` |
| "Audience mismatch" error     | Verify Auth0 audience is `https://twitter-api`       |
| Data not persisted            | This is expected (in-memory storage)                 |
| Tests failing                 | Ensure Java 21 and run `mvn clean install`           |

---

## Future Improvements

* Replace in-memory storage with a real database (PostgreSQL / DynamoDB)
* Migrate to microservices architecture
* Add caching layer (Redis)
* Implement search (Elasticsearch)
* Enable real-time updates (WebSockets)

---
