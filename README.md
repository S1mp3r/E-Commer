# E-Commerce Full Stack - Setup & Initialization

This project consists of multiple services: **Frontend (Next.js)**, **Authentication Service (Spring Boot)**, **E-commerce Service (Spring Boot)**, two PostgreSQL databases, and a Redis service. The entire application relies on `.env` files and local Maven builds to run properly within Docker.

## Prerequisites

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Java 21+](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Node.js](https://nodejs.org/en/download)

## Application Startup

### 1. Generate build artifacts with Maven

#### Authentication Service

```bash
cd ./back/auth/
mvn clean install -U
```

#### E-commerce Service

```bash
cd ./back/ecommerce/
mvn clean install -U
```

> Both commands create the `target/` directory, which is required for Docker image builds.

### 2. Configure environment variables

#### .env for Authentication (PostgreSQL)

**Path:** `./back/auth/auth-container/.env.postgres.auth`

```env
POSTGRES_HOST=postgres-auth
POSTGRES_PORT=5432
POSTGRES_DB=database
POSTGRES_USER=user
POSTGRES_PASSWORD=password
```

#### .env for E-commerce (PostgreSQL)

**Path:** `./back/ecommerce/e-commerce-db/.env.postgres.ecommerce`

```env
POSTGRES_HOST=postgres-ecommerce
POSTGRES_PORT=5432
POSTGRES_DB=database
POSTGRES_USER=user
POSTGRES_PASSWORD=password
```

#### .env for Redis

**Path:** `./back/auth/.env.redis`

```env
REDIS_PASSWORD=your_redis_password
```

#### .env for Frontend (Next.js)

**Path:** `./front/.env.next`

```env
NEXT_PUBLIC_BASE_URL=http://nginx/ecommerce
```

#### Global .env

**Path:** `./.env`

```env
PORT=3000

# JWT
JWT_SECRET_KEY=secret_key
EXPIRATION_TIME=0

# Authentication DB
DB_POSTGRES_USER=user
DB_POSTGRES_PASSWORD=password

# E-commerce DB
DB_POSTGRES_USER_ECOMMERCE=user
DB_POSTGRES_PASSWORD_ECOMMERCE=password

# Redis
DB_REDIS_PASSWORD=your_redis_password

# URLs
FRONT_URL=http://localhost:3000
API_URL=http://nginx/ecommerce
```

## Launching with Docker

In the project root, run:

```bash
docker compose up -d --build
```

## Checklist

- `target/` directories generated for both backend services  
- All `.env` files created  
- Docker running successfully  
- Redis password configured  
- Frontend available at `http://localhost:3000`

## Notes

- **Do not change default ports** (5432 for PostgreSQL, 3000 for frontend) unless necessary.  
- The project uses **NGINX** as a **reverse proxy** to route between services.

## License

Distributed under the MIT License. See `LICENSE` for details.
