# College Library Availability Check

Project Number: 42

Team IDs:
- 2400030256
- 2400032311
- 2400030754
- 2400090178

A beginner-friendly full-stack college library project built with React, Spring Boot, Spring Data JPA, PostgreSQL, and Docker.

## Features

Students can search books by title, author, or ISBN, open book details, and see availability, copies, and shelf information.

Admins can add, edit, delete, issue, and return books from the admin view.

## Project Structure

```text
college-library-availability-check/
  backend/       Spring Boot REST API
  frontend/      React + Vite web application
  docker-compose.yml
```

## Run PostgreSQL

Install Docker Desktop, then run from the project root:

```bash
docker compose up -d
```

The database is created with:
- Database: `library_db`
- Username: `library_user`
- Password: `library_password`
- Port: `5432`

## Run the backend

A JDK 17 or newer and Maven are required.

```bash
cd backend
mvn spring-boot:run
```

The API runs at `http://localhost:8080`.

## Run the frontend

```bash
cd frontend
npm install
npm run dev
```

Open `http://localhost:5173`.

The Vite development server proxies `/api` requests to Spring Boot.

## REST API

- `GET /api/books?search=java` - search or list books
- `GET /api/books/{id}` - book details
- `POST /api/books` - add a book
- `PUT /api/books/{id}` - edit a book
- `DELETE /api/books/{id}` - delete a book
- `POST /api/books/{id}/issue` - issue one available copy
- `POST /api/books/{id}/return` - return one issued copy

Sample books are loaded automatically when the database is empty.

## Viva Notes

The `Book` entity maps to the `books` table. `BookRepository` extends `JpaRepository`, and `BookController` exposes simple REST endpoints. Availability is calculated from `totalCopies - issuedCopies`; issuing and returning only update the copy counters.

## Team and Project

Project 42 | College Library Availability Check
