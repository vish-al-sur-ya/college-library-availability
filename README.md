# College Library Availability Check

Project Number: 42

Team IDs:
- 2400030256
- 2400032311
- 2400030754
- 2400090178

A beginner-friendly full-stack college library project built with React and Spring Boot. It is designed to run locally without PostgreSQL or any other database.

## Features

Students can sign in, search books by title, author, or ISBN, open book details, see availability, and order available books.

Admins can sign in and add, edit, delete, issue, and return books from the admin view.

## Project Structure

```text
college-library-availability-check/
  backend/       Spring Boot REST API with in-memory storage
  frontend/      React + Vite web application
```

## Run the backend

A JDK 17 or newer and Maven are required. No database setup is needed.

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
- `POST /api/auth/login` - demo login
- `GET /api/orders?username=student` - view a student's orders
- `POST /api/orders/{bookId}` - order an available book

Sample books are loaded automatically when the backend starts. Books added from the Admin desk are stored in the backend memory and remain available until the backend is restarted.

## Viva Notes

`BookRepository` and `OrderRepository` are beginner-friendly in-memory repositories backed by Java collections. `BookController`, `AuthController`, and `OrderController` expose simple REST endpoints. Availability is calculated from `totalCopies - issuedCopies`; ordering and issuing update the copy counters.

## Demo Login

- Student: `student` / `student123`
- Admin: `admin` / `admin123`

## Team and Project

Project 42 | College Library Availability Check
