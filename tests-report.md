# Tests report - monolith

## What exists

### Unit tests

- `PostServiceTest`
  - verifies post creation from JWT claims
  - verifies newest-first feed order
- `ProfileServiceTest`
  - verifies JWT-to-profile mapping
- `AudienceValidatorTest`
  - verifies audience acceptance and rejection

### Integration / endpoint tests

- `PostControllerIntegrationTest`
  - public `GET /api/posts`
  - protected `POST /api/posts` with valid JWT mock
  - 401 when token is missing
  - 400 when post is longer than 140 characters
- `ProfileControllerIntegrationTest`
  - 401 for `GET /api/me` without token
  - 200 for `GET /api/me` with JWT mock

## Why these tests matter

They demonstrate the minimum academic evidence requested:

- public endpoints work without authentication
- protected endpoints reject anonymous requests
- protected endpoints accept authenticated requests
- post validation is enforced
- profile extraction from the JWT works
- issuer/audience validation logic is present and unit-tested

## How to run

```bash
cd backend-monolith-springboot
mvn test
```

## Local vs deployed validation

- Local tests cover controller behavior, service behavior, and security wiring using Spring Security test support.
- Real Auth0 signature verification is exercised when the app runs against actual Auth0 tokens.
