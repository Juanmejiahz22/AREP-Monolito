# Twitter Secure Monolith

> **Autores:** Juan Jose Mejia & David Castro

Una aplicación tipo Twitter con arquitectura monolítica que implementa patrones de seguridad mediante OAuth2/JWT. Backend en Spring Boot (Java 21) y frontend en React.

## Descripción Rápida

Proyecto académico que demuestra: autenticación con Auth0, validación de JWT (issuer + audience), feed público, creación de posts protegida y extracción de perfiles. Monolítico pero escalable a microservicios.

## Características

- OAuth2 con Auth0 + JWT (issuer y audience validados)
- Feed global público + creación protegida
- Perfil de usuario desde JWT claims
- Swagger/OpenAPI para testing
- Persistencia en memoria (diseño académico)
- CORS configurado para local y producción
- Posts limitados a 140 caracteres
- Manejo centralizado de errores

## Stack

- **Backend:** Java 21 + Spring Boot 3.3.5 + Spring Security
- **Frontend:** React 18.3 + Vite
- **Auth:** Auth0 (SPA flow)
- **API Docs:** Swagger/OpenAPI

## Requisitos

- Node.js 18+ y npm
- Java 21
- Maven 3.8+

## Instalación Rápida

### Backend
```bash
cd backend-monolith-springboot
mvn clean install
mvn spring-boot:run
```
Backend en: `http://localhost:8080` | Swagger: `http://localhost:8080/swagger-ui.html`

### Frontend
```bash
cd frontend-react
cp .env.example .env
npm install
npm run dev
```
Frontend en: `http://localhost:5173`

> Los valores de Auth0 ya están configurados. Solo copia `.env.example` a `.env`.

## Flujo de Autenticación

1. Usuario hace login en React
2. Auth0 emite JWT con audience `https://twitter-api`
3. Frontend envía `Authorization: Bearer <token>`
4. Backend valida: firma RSA + issuer + audience + timestamp
5. Sin token → 401 en endpoints protegidos

## Estructura

```
twitter-secure-monolith/
├── backend-monolith-springboot/
│   ├── src/main/java/co/edu/escuelaing/twitterapi/
│   │   ├── controller/          # REST endpoints
│   │   ├── service/             # Lógica de negocio
│   │   ├── repository/          # Acceso a datos (in-memory)
│   │   ├── security/            # AudienceValidator
│   │   └── config/              # Security + OpenAPI
│   ├── src/test/java/           # Tests
│   └── pom.xml
│
├── frontend-react/
│   ├── src/
│   │   ├── components/          # AuthBar, Feed, CreatePostForm, etc.
│   │   ├── services/api.js      # Cliente HTTP
│   │   ├── App.jsx
│   │   └── styles.css
│   ├── .env.example
│   └── package.json
│
└── README.md
```

## API Endpoints

### Públicos
- `GET /api/posts` → Feed global

### Protegidos (requieren JWT)
- `POST /api/posts` → Crear post (`{"message": "..."}`)
- `GET /api/me` → Perfil del usuario

**Swagger:** `http://localhost:8080/swagger-ui.html`

### Ejemplos

```bash
# Feed público
curl http://localhost:8080/api/posts

# Crear post (necesita token)
curl -X POST http://localhost:8080/api/posts \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"message": "Mi primer post"}'

# Perfil (necesita token)
curl http://localhost:8080/api/me \
  -H "Authorization: Bearer <token>"
```

## Seguridad

- **JWT Validation:** Firma RSA + issuer (`https://dev-4l1jhwzfu73203o3.us.auth0.com/`) + audience (`https://twitter-api`)
- **CORS:** Acepta `localhost:5173` (dev) y `production.d3bbro1qxkh12x.amplifyapp.com` (prod)
- **Endpoints sin protección:** GET /api/posts, GET /swagger-ui.html, OPTIONS
- **Endpoints protegidos:** POST /api/posts, GET /api/me

## Testing

```bash
cd backend-monolith-springboot
mvn test
```

**Tests incluidos:**
- `PostServiceTest` → Lógica de posts
- `ProfileServiceTest` → Extracción de JWT claims
- `AudienceValidatorTest` → Validación de audience
- `PostControllerIntegrationTest` → Endpoints públicos y protegidos (401, 400)
- `ProfileControllerIntegrationTest` → GET /me con/sin token

## Configuración

### Backend (application.yml)
```yaml
server:
  port: 5000

auth0:
  issuer-uri: https://dev-4l1jhwzfu73203o3.us.auth0.com/
  audience: https://twitter-api
  frontend-local-url: http://localhost:5173
  frontend-prod-url: https://production.d3bbro1qxkh12x.amplifyapp.com
```

### Frontend (.env)
```
VITE_AUTH0_DOMAIN=dev-4l1jhwzfu73203o3.us.auth0.com
VITE_AUTH0_CLIENT_ID=SDRKfg7fPnlqw9iuTdXWApwgh7HZqN7r
VITE_AUTH0_AUDIENCE=https://twitter-api
VITE_AUTH0_REDIRECT_URI=http://localhost:5173
VITE_API_BASE_URL=http://localhost:8080
```

## Persistencia

Los posts se guardan en memoria (`CopyOnWriteArrayList`). Al reiniciar el servidor, se pierden. Es intencional para desarrollo local sin base de datos. En producción se reemplazaría con DynamoDB o PostgreSQL.

## Build & Deployment

### Backend
```bash
mvn clean package
java -jar target/twitter-api-1.0.0.jar
```

### Frontend
```bash
npm run build
# Genera dist/ → Subir a S3 o Amplify
```

## Próximas Fases

- Base de datos real (PostgreSQL / DynamoDB)
- Refactor a microservicios (Lambda + API Gateway)
- Cache (Redis)
- Búsqueda (Elasticsearch)
- Real-time updates (WebSockets)

## Troubleshooting

| Problema | Solución |
|----------|----------|
| Frontend no conecta al backend | Verifica que backend corra en `http://localhost:8080` |
| "Audience mismatch" error | Comprueba que Auth0 use audience `https://twitter-api` |
| Posts no persisten tras restart | Comportamiento normal. Usa in-memory. |
| Tests fallan | Usa Java 21+, ejecuta `mvn clean install` |

## Licencia

MIT

---

**Para más información:** Ver `run-local.md` y `tests-report.md`
