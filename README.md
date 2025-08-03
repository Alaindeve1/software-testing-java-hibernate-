# AUCA Library Management System

A comprehensive library management system developed for the Adventist University of Central Africa (AUCA) to manage library resources, memberships, and book borrowing operations.

## 📋 Project Overview

The AUCA Library System is a Java-based application that provides an efficient way to manage library operations including book management, member management, and borrowing processes. The system is built using Java 17 and utilizes Hibernate ORM for database interactions with a PostgreSQL database.

## 🚀 Features

- **Book Management**: Add, update, and manage book inventory
- **Member Management**: Handle library memberships and user accounts
- **Borrowing System**: Track book loans, returns, and fines
- **Location Management**: Manage physical book locations (Rooms, Shelves)
- **User Authentication**: Secure user accounts with role-based access
- **Reporting**: Track book availability and borrowing history

## 🛠️ Tech Stack

- **Language**: Java 17
- **Framework**: Hibernate ORM 6.6.15
- **Database**: PostgreSQL 42.4.4
- **Build Tool**: Maven
- **Security**: Spring Security Crypto for password hashing
- **Testing**: JUnit 4.11

## 🗃️ Database Schema

The system uses a relational database with the following main entities:

- **Book**: Stores book information (title, author, ISBN, etc.)
- **User**: Library users with different roles
- **Borrower**: Tracks book borrowing transactions
- **Membership**: Manages member subscriptions
- **Location**: Physical locations (Rooms, Shelves) in the library
- **MembershipType**: Different types of memberships available

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8.0 or higher
- PostgreSQL 13 or higher

### Installation

1. Clone the repository:
   ```bash
   git clone [repository-url]
   cd Auca_Library_Sys/library_sys
   ```

2. Set up the PostgreSQL database:
   - Create a new database named `auca_library`
   - Update the database connection details in `hibernate.cfg.xml`

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="com.auca.App"
   ```

## 📂 Project Structure

```
src/
├── main/
│   ├── java/com/auca/
│   │   ├── Models/         # Entity classes (Book, User, Borrower, etc.)
│   │   ├── dao/            # Data Access Objects
│   │   ├── util/           # Utility classes
│   │   └── App.java        # Main application class
│   └── resources/          # Configuration files
└── test/                   # Test classes
```

## 🧪 Running Tests

To run the test suite:

```bash
mvn test
```

## 📝 License

This project is licensed under the [MIT License](LICENSE).

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📧 Contact

For any inquiries, please contact the development team.
