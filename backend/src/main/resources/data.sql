INSERT INTO books (title, author, isbn, category, publisher, edition, shelf_number, total_copies, available_copies, status, reserved)
SELECT 'Clean Code', 'Robert C. Martin', '9780132350884', 'Software Engineering', 'Prentice Hall', '1st', 'A1-R2', 5, 3, 'AVAILABLE', false
WHERE NOT EXISTS (SELECT 1 FROM books WHERE isbn = '9780132350884');

INSERT INTO books (title, author, isbn, category, publisher, edition, shelf_number, total_copies, available_copies, status, reserved)
SELECT 'Introduction to Algorithms', 'Thomas H. Cormen', '9780262046305', 'Computer Science', 'MIT Press', '4th', 'A2-R5', 4, 0, 'ISSUED', false
WHERE NOT EXISTS (SELECT 1 FROM books WHERE isbn = '9780262046305');
