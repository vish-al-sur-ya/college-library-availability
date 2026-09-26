CREATE TABLE IF NOT EXISTS books (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(100) UNIQUE NOT NULL,
    category VARCHAR(100) NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    edition VARCHAR(100) NOT NULL,
    shelf_number VARCHAR(100) NOT NULL,
    total_copies INTEGER NOT NULL CHECK (total_copies >= 1),
    available_copies INTEGER NOT NULL CHECK (available_copies >= 0 AND available_copies <= total_copies),
    status VARCHAR(20) NOT NULL,
    reserved BOOLEAN NOT NULL DEFAULT FALSE
);
