INSERT INTO books (title, author, isbn, category, publisher, edition, shelf_number, total_copies, available_copies, status, reserved) VALUES
('Database System Concepts', 'Abraham Silberschatz', '9780078022159', 'Database', 'McGraw Hill', '7th', 'D3-R4', 6, 4, 'AVAILABLE', FALSE),
('Computer Networks', 'Andrew S. Tanenbaum', '9780132126953', 'Networking', 'Pearson', '5th', 'N1-R2', 3, 0, 'ISSUED', FALSE)
ON CONFLICT (isbn) DO NOTHING;
