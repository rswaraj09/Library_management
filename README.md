# College Library Management System

A modern web-based library management system designed for colleges and educational institutions. This system helps librarians manage books, student access, and library resources efficiently.

## Features

- 📚 Book Management
  - Add, edit, and remove books
  - Track total and available copies
  - Categorize books by academic year and subject
  - ISBN-based book identification

- 👥 User Management
  - Role-based access control (Student, Librarian, Admin)
  - Secure authentication
  - User registration and login

- 📱 Dashboard Views
  - Librarian dashboard for inventory management
  - Student dashboard for book browsing
  - Real-time availability tracking

- 🔍 Search & Filter
  - Advanced search capabilities
  - Filter by category, year, and subject
  - Quick access to reference materials

## Technology Stack

### Backend
- Java 23
- Spring Boot 3.2.0
- Spring Security
- Spring Data JPA
- MySQL 8 Database
- Hibernate ORM

### Frontend
- Thymeleaf templating engine
- HTML5/CSS3
- JavaScript (Vanilla)
- Responsive Design

### Build & Development
- Maven for dependency management
- JUnit for testing
- Spring DevTools for development
- Git for version control

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 23 or later
- MySQL 8.0 or later
- Maven 3.9+ (included as wrapper)
- Git

### Setup & Installation

1. Clone the repository:
   ```bash
   git clone [repository-url]
   cd library
   ```

2. Configure MySQL database:
   ```properties
   # Update src/main/resources/application.properties with your database credentials
   spring.datasource.url=jdbc:mysql://localhost:3306/library
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. Build and run the application:
   ```bash
   # Using Maven wrapper
   ./mvnw spring-boot:run   # For Unix/Linux/macOS
   mvnw.cmd spring-boot:run # For Windows
   ```

4. Access the application:
   ```
   http://localhost:8080
   ```



## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── library/
│   │               ├── LibraryApplication.java
│   │               ├── auth/           # Authentication & security
│   │               ├── config/         # Configuration classes
│   │               ├── model/          # Entity classes
│   │               ├── repository/     # Data access layer
│   │               ├── service/        # Business logic
│   │               └── web/            # Controllers & web layer
│   └── resources/
│       ├── static/          # CSS, JavaScript
│       ├── templates/       # Thymeleaf templates
│       └── application.properties
└── test/                    # Test classes
```

## Acknowledgments

- Spring Boot team for the excellent framework
- Bootstrap team for the responsive design components
- All contributors who have invested time in helping this project
