# College Library Availability Check (Project Number 42)

## Project Objective
Build a full-stack application for students and administrators to search books and check real-time library availability status.

## Problem Statement
Students should be able to search books quickly by title, author, ISBN, or category and view availability status (`AVAILABLE`, `ISSUED`, `RESERVED`), while admins should manage books and issue/return/reserve operations.

## Team Members
- 2400030256
- 2400032311
- 2400030754
- 2400090178

## Proposed Solution
This repository contains:
- **Frontend**: React + Vite UI with student and admin pages.
- **Backend**: Spring Boot REST APIs with layered architecture.
- **Database**: PostgreSQL schema + seed scripts.
- **Containerization**: Dockerfiles and docker-compose for one-command startup.

## Features
### Student Interface
- Search books by title, author, ISBN, category
- View book details and shelf/rack location
- Check availability and available copies
- See `AVAILABLE`, `ISSUED`, `RESERVED` status

### Admin Interface
- Add, edit, delete books
- View all books
- Issue, return, reserve books
- Check current availability

## Technology Stack
- **Frontend**: React.js, JavaScript, HTML5, CSS3
- **Backend**: Java 17, Spring Boot, Spring Web, Spring Data JPA, Maven
- **Database**: PostgreSQL
- **Tools**: Git, GitHub, Docker, GitHub Copilot

## System Architecture
Backend uses a clean layered architecture under `backend/src/main/java/com/college/library`:
- `controller/`
- `service/`
- `repository/`
- `entity/`
- `dto/`
- `exception/`
- `config/`

## Project Structure
```text
college-library-availability/
├── frontend/
├── backend/
├── database/
│   ├── schema.sql
│   └── seed.sql
├── docker-compose.yml
└── README.md
```

## Database Setup
1. Create database `college_library` in PostgreSQL.
2. Run SQL from `database/schema.sql` and `database/seed.sql`.

## Backend Setup
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Backend base URL: `http://localhost:8080/api`

### Backend Configuration
`backend/src/main/resources/application.properties` supports environment-based datasource values:
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`

## Frontend Setup
```bash
cd frontend
npm install
npm run dev
```

Frontend URL: `http://localhost:5173`

## Docker Setup
```bash
docker compose up --build
```

Service URLs:
- Frontend: `http://localhost:3000`
- Backend: `http://localhost:8080/api`
- PostgreSQL: `localhost:5432`

## API Documentation
### Book APIs
- `POST /api/books` - Create book
- `GET /api/books` - Get all books (optional `q` search query)
- `GET /api/books/{id}` - Get book by ID
- `GET /api/books/search?query=...` - Search books
- `PUT /api/books/{id}` - Update book
- `DELETE /api/books/{id}` - Delete book
- `PUT /api/books/{id}/issue` - Issue a book
- `PUT /api/books/{id}/return` - Return a book
- `PUT /api/books/{id}/reserve` - Reserve a book
- `GET /api/books/{id}/availability` - Check availability

## Testing
Backend tests are in `backend/src/test/java/com/college/library/BookControllerIntegrationTest.java` and cover:
- Book creation
- Book search
- Availability checking
- Issue operation
- Return operation

Run tests:
```bash
cd backend
mvn test
```

## How to Run the Complete Application Locally
1. Start PostgreSQL and create/use `college_library`.
2. Start backend (`mvn spring-boot:run` in `backend`).
3. Start frontend (`npm run dev` in `frontend`).
4. Open frontend and use student/admin pages.

Or run all services with Docker Compose.
