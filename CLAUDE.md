# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a full-stack e-commerce application with three main components:
- **Backend**: Spring Boot application using Hexagonal Architecture (Ports & Adapters) with DDD principles
- **Customer Frontend**: React/TypeScript application (Vite) for end users
- **Admin Frontend**: React/TypeScript application using React Admin for administrative tasks

## Architecture

### Backend (Spring Boot - Hexagonal Architecture)

The backend follows **Hexagonal Architecture** with strict separation of concerns organized into modules (balance, cart, catalog, delivery, order, user, auth).

Each module is structured as:
```
<module>/
├── domain/           # Business logic, entities, value objects, domain events
├── application/      # Use cases and orchestration
│   ├── port/
│   │   ├── in/      # Input ports (interfaces for incoming operations)
│   │   └── out/     # Output ports (interfaces for external dependencies)
│   ├── usecase/     # Use case implementations (annotated with @UseCase)
│   └── service/     # Orchestrator services implementing input ports
└── infrastructure/   # Adapters (REST controllers, JPA repositories, configs)
    ├── adapter/
    │   ├── in/rest/ # REST controllers (admin/ and customer/ sub-folders)
    │   └── out/     # Persistence adapters
    └── config/      # Module Spring configuration
```

**Key Architectural Patterns:**
- **@UseCase Annotation**: All use case classes must be annotated with `@UseCase` (from `architecture/UseCase.java`). This enables:
  - Automatic component scanning via `DomainConfig.java`
  - Automatic transactional behavior via AOP (`TransactionalUseCaseAspect.java`)
  - Use cases are automatically wrapped in transactions without explicit `@Transactional` annotations

- **Dependency Flow**: `REST Controller → Orchestrator Service → Use Case → Repository Port`
  - Controllers depend on orchestrator interfaces (input ports)
  - Use cases depend on repository interfaces (output ports)
  - Adapters implement these interfaces in the infrastructure layer

- **Configuration Pattern**: Each module has a `<Module>Config.java` class that manually instantiates and wires orchestrator services with their use case dependencies as constructor arguments

### Frontend Applications

**Customer Frontend** (`online-store-frontend/`):
- React 19 with TypeScript
- Vite build tool
- Feature-based structure under `src/features/`: auth, balance, cart, delivery-addresses, orders, products, users
- React Query (@tanstack/react-query) for data fetching
- React Router for routing
- Tailwind CSS + shadcn/ui components
- Zod for validation
- React Hook Form for forms

**Admin Frontend** (`online-store-frontend-admin/`):
- React 19 with TypeScript using React Admin framework
- Vite build tool
- Material-UI components

## Common Development Commands

### Backend (Java/Maven)

From `online-store-backend/` directory:

```bash
# Build the project
mvn clean install

# Build without running tests
mvn clean install -DskipTests

# Run tests
mvn test

# Run a specific test class
mvn test -Dtest=ClassName

# Run the application
mvn spring-boot:run

# Package as JAR
mvn package
```

The backend runs on port 8080 by default with Swagger UI at `http://localhost:8080/swagger-ui`.

### Customer Frontend

From `online-store-frontend/` directory:

```bash
# Install dependencies
npm install

# Run development server (with hot reload)
npm run dev

# Build for production
npm run build

# Build with type checking first
npm run build  # already includes: tsc -b && vite build

# Run linter
npm run lint

# Preview production build
npm run preview
```

Development server runs on port 3000 (see application.yml CORS config).

### Admin Frontend

From `online-store-frontend-admin/` directory:

```bash
# Install dependencies
npm install

# Run development server
npm run dev

# Build for production
npm run build

# Type check without emitting files
npm run type-check

# Run linter with auto-fix
npm run lint

# Format code
npm run format

# Preview production build
npm run serve
```

Development server runs on port 3001 (see application.yml CORS config).

### Infrastructure (Docker)

From project root:

```bash
# Start PostgreSQL and Redis
docker compose up -d

# Stop services
docker compose down

# View logs
docker compose logs -f

# Restart services
docker compose restart
```

**Services:**
- PostgreSQL: localhost:5432 (database: online_store_db, user: myuser, password: mypassword)
- Redis: localhost:6379

## Backend Development Guidelines

### Adding a New Feature

When adding new functionality to an existing module:

1. **Define Domain Models** in `<module>/domain/` (entities, value objects)
2. **Create Use Case** in `<module>/application/usecase/`:
   - Annotate with `@UseCase`
   - Inject repository ports (output ports) via constructor
   - Implement business logic
3. **Define Repository Interface** (if needed) in `<module>/application/port/out/`
4. **Implement JPA Adapter** in `<module>/infrastructure/adapter/out/persistence/`
5. **Add to Orchestrator** by updating the service in `<module>/application/service/` and its interface in `<module>/application/port/in/`
6. **Wire in Config** by adding the new use case to the orchestrator bean in `<module>/infrastructure/config/<Module>Config.java`
7. **Create REST Endpoint** in `<module>/infrastructure/adapter/in/rest/admin/` or `.../customer/`
   - Controllers inject orchestrator interfaces (input ports)
   - Separate DTOs for requests/responses (avoid exposing domain models directly)

### Transaction Management

- Use cases annotated with `@UseCase` automatically run in transactions via `TransactionalUseCaseAspect`
- Do NOT add `@Transactional` to use case classes (handled by AOP)
- Orchestrator services coordinate multiple use cases but should not contain business logic

### Authentication & Security

- JWT-based authentication configured in `application.yml`
- Tokens: Access token (15 min TTL), Refresh token (7 days TTL)
- Security configuration in the `auth` module
- Use `@CurrentUserId` annotation to inject authenticated user ID in controllers

## Configuration

### Backend Configuration

- **Main config**: `online-store-backend/src/main/resources/application.yml`
- Database connection, Redis, JWT secrets, CORS origins are configured here
- Use environment variables for sensitive values (JWT secrets have defaults for development)
- Hibernate DDL mode is `update` (change to `validate` in production)

### Frontend Configuration

Both frontends use Vite with TypeScript. Configuration files:
- `vite.config.ts`: Build configuration
- `tsconfig.json`: TypeScript compiler options (references tsconfig.app.json and tsconfig.node.json)
- `package.json`: Scripts and dependencies

## Testing

- Backend tests use JUnit 5 with Testcontainers for integration tests
- Test files in `online-store-backend/src/test/java/`
- Integration test example: `AuthIntegrationTest.java`
- Testcontainers automatically spin up PostgreSQL for tests

## API Documentation

- Swagger UI available at `http://localhost:8080/swagger-ui` when backend is running
- OpenAPI configuration in `infrastructure/config/OpenApiConfig.java`

## Key Technologies

**Backend:**
- Java 21
- Spring Boot 3.5.5
- Spring Data JPA with PostgreSQL
- Spring Security with JWT
- MapStruct for DTO mapping
- Lombok for boilerplate reduction
- Redis for caching/sessions
- Testcontainers for testing

**Frontend:**
- React 19
- TypeScript 5
- Vite 7 (customer) / 6 (admin)
- React Query (customer)
- React Router 7
- Tailwind CSS (customer)
- Material-UI (admin)
- React Admin framework (admin)