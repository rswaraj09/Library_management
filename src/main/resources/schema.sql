-- Books Table: added missing fields
CREATE TABLE IF NOT EXISTS books (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  isbn VARCHAR(20) UNIQUE NOT NULL,
  title VARCHAR(255) NOT NULL,
  author VARCHAR(255) NOT NULL,
  published_year INT,
  category VARCHAR(100),
  academic_year VARCHAR(50),
  subject VARCHAR(255),
  copies_total INT NOT NULL DEFAULT 1,
  copies_available INT NOT NULL DEFAULT 1,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Members Table: added `updated_at` field
CREATE TABLE IF NOT EXISTS members (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  full_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Added updated_at
);

-- Loans Table: added `status` field and `updated_at` field
CREATE TABLE IF NOT EXISTS loans (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  book_id BIGINT NOT NULL,
  member_id BIGINT NOT NULL,
  loan_date DATE NOT NULL DEFAULT (CURRENT_DATE),
  due_date DATE NOT NULL,
  return_date DATE,
  status ENUM('active', 'returned', 'overdue') DEFAULT 'active', -- Added status
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Added updated_at
  CONSTRAINT fk_loans_book FOREIGN KEY (book_id) REFERENCES books(id),
  CONSTRAINT fk_loans_member FOREIGN KEY (member_id) REFERENCES members(id)
);

-- Users Table: added `updated_at` field
CREATE TABLE IF NOT EXISTS users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  full_name VARCHAR(255),
  email VARCHAR(255) UNIQUE,
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Added updated_at
);

-- Authorities Table (no changes needed here, it's fine)
CREATE TABLE IF NOT EXISTS authorities (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(100) NOT NULL,
  authority VARCHAR(100) NOT NULL,
  CONSTRAINT fk_auth_user FOREIGN KEY (username) REFERENCES users(username)
);

-- Index on ISBN in Books Table for faster searches
CREATE INDEX ix_books_isbn ON books(isbn); -- Added index on ISBN

-- Index for Authorities (already exists)
CREATE INDEX ix_auth_username ON authorities(username);

-- Clear existing users and authorities
DELETE FROM authorities;
DELETE FROM users;

-- Sample users with plain text passwords
INSERT INTO users (username, password, full_name, email, enabled) VALUES
('admin', 'password123', 'Administrator', 'admin@library.com', true),
('librarian1', 'password123', 'John Librarian', 'john@library.com', true),
('student1', 'password123', 'Jane Student', 'jane@student.com', true);

-- Sample authorities
INSERT INTO authorities (username, authority) VALUES
('admin', 'ROLE_LIBRARIAN'),
('librarian1', 'ROLE_LIBRARIAN'),
('student1', 'ROLE_STUDENT');

-- Sample books
INSERT INTO books (isbn, title, author, published_year, category, academic_year, subject, copies_total, copies_available) VALUES
('978-0134685991', 'Effective Java', 'Joshua Bloch', 2018, 'textbook', '3rd-year', 'Computer Science', 3, 3),
('978-0132350884', 'Clean Code', 'Robert C. Martin', 2008, 'textbook', '2nd-year', 'Software Engineering', 2, 2),
('978-0201633610', 'Design Patterns', 'Gang of Four', 1994, 'reference', '4th-year', 'Software Design', 1, 1),
('978-0262033848', 'Introduction to Algorithms', 'Thomas H. Cormen', 2009, 'textbook', '1st-year', 'Computer Science', 4, 4),
('978-0078022159', 'Database System Concepts', 'Abraham Silberschatz', 2019, 'textbook', '2nd-year', 'Database Systems', 2, 2);

