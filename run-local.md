# Run locally

## 1. Backend

```bash
cd backend-monolith-springboot
mvn spring-boot:run
```

Backend URL:

- `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui.html`

## 2. Frontend

Copy the example env file:

```bash
cd frontend-react
cp .env.example .env
```

The important values are already aligned to the provided configuration:

```env
VITE_AUTH0_DOMAIN=your-auth0-domain
VITE_AUTH0_CLIENT_ID=your-auth0-client-id
VITE_AUTH0_AUDIENCE=your-api-audience
VITE_AUTH0_REDIRECT_URI=http://localhost:5173
VITE_API_BASE_URL=http://localhost:8080
VITE_APP_TITLE=Secure Twitter Monolith
```

Then run:

```bash
npm install
npm run dev
```

Frontend URL:

- `http://localhost:5173`

## 3. Local flow

1. Open the React app.
2. Login with Auth0.
3. The frontend requests an access token for audience `https://twitter-api`.
4. The backend validates the JWT against issuer `https://example.us.auth0.com/` and audience `https://twitter-api`.
5. Public feed works without login.
6. `/api/me` and post creation require a valid token.

## 4. What is local and what is real here?

- Real locally: React app, Auth0 SPA login, Spring Boot API, JWT validation, Swagger.
- Simulated locally: persistence is in-memory.
- Not required in this phase: AWS services.
