<div align="center">
  <h1>📚 AUCA Library Management System</h1>
  <p><em>A Learning Project: Mastering Java, Hibernate & JUnit Testing</em></p>
  
  [![Java](https://img.shields.io/badge/Java-17-007396?logo=java&logoColor=white)](https://www.java.com/)
  [![JUnit](https://img.shields.io/badge/JUnit-4.13-25A162?logo=junit5&logoColor=white)](https://junit.org/)
  [![Hibernate](https://img.shields.io/badge/Hibernate-6.6.15-59666C?logo=hibernate&logoColor=white)](https://hibernate.org/)
  [![Maven](https://img.shields.io/badge/Maven-3.8+-C71A36?logo=apache-maven&logoColor=white)](https://maven.apache.org/)
  [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
</div>

<div align="center">
  <p>An educational project focusing on Java, Hibernate ORM, and comprehensive JUnit testing practices.</p>
</div>

## 📋 Project Overview

The AUCA Library System is a Java-based application that provides an efficient way to manage library operations including book management, member management, and borrowing processes. The system is built using Java 17 and utilizes Hibernate ORM for database interactions with a PostgreSQL database.

## 🎯 Learning Objectives

<div align="center">
  <table>
    <tr>
      <td align="center">🧪 <strong>JUnit Testing</strong></td>
      <td>Comprehensive test cases for DAO and service layers</td>
    </tr>
    <tr>
      <td align="center">🔍 <strong>Test Coverage</strong></td>
      <td>Focused on achieving high test coverage for critical components</td>
    </tr>
    <tr>
      <td align="center">🔄 <strong>CRUD Operations</strong></td>
      <td>Testing Create, Read, Update, and Delete operations</td>
    </tr>
    <tr>
      <td align="center">🧠 <strong>Best Practices</strong></td>
      <td>Following TDD principles and clean test architecture</td>
    </tr>
  </table>
</div>

## 🛠️ Tech Stack

### Core Technologies
<div align="center">
  <table>
    <tr>
      <td align="center"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg" width="50" height="50" alt="Java" /></td>
      <td align="center"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/hibernate/hibernate-original-wordmark.svg" width="50" height="50" alt="Hibernate" /></td>
      <td align="center"><img src="https://junit.org/junit5/assets/img/junit5-logo.png" width="50" height="50" alt="JUnit" /></td>
      <td align="center"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/maven/maven-original-wordmark.svg" width="50" height="50" alt="Maven" /></td>
    </tr>
    <tr>
      <td>Java 17</td>
      <td>Hibernate ORM 6.6</td>
      <td>JUnit 4.13</td>
      <td>Maven 3.8+</td>
    </tr>
  </table>
</div>

### Testing Tools
- **Unit Testing**: JUnit 4.13
- **Test Coverage**: JaCoCo
- **Database Testing**: H2 in-memory DB
- **Build Tool**: Maven Surefire Plugin

### Development Tools
- **IDE**: Any Java IDE (IntelliJ, Eclipse, VS Code)
- **Version Control**: Git

## 🗃️ Database Schema

The system uses a relational database with the following main entities:

- **Book**: Stores book information (title, author, ISBN, etc.)
- **User**: Library users with different roles
- **Borrower**: Tracks book borrowing transactions
- **Membership**: Manages member subscriptions
- **Location**: Physical locations (Rooms, Shelves) in the library
- **MembershipType**: Different types of memberships available

## 🚀 Quick Start

### Prerequisites

<div align="center">
  <table>
    <tr>
      <td align="center">☕ Java 17+</td>
      <td align="center">🐘 PostgreSQL 13+</td>
      <td align="center">🛠️ Maven 3.8+</td>
    </tr>
  </table>
</div>

### 🛠️ Installation Guide

```bash
# 1. Clone the repository
git clone https://github.com/Alaindeve1/software-testing-java-hibernate-.git
cd Auca_Library_Sys/library_sys

# 2. Set up the database
createdb auca_library
# Update database credentials in hibernate.cfg.xml

# 3. Build the project
mvn clean install

# 4. Launch the application
mvn exec:java -Dexec.mainClass="com.auca.App"
```

<div align="center" style="margin: 20px 0;">
  <img src="https://img.icons8.com/color/48/000000/console.png" alt="Terminal" width="40" height="40"/>
  <p>Get started in minutes with our straightforward setup process!</p>
</div>

## 🏗️ Project Structure

```
📦 src/
├── 📂 main/
│   ├── 📂 java/com/auca/
│   │   ├── 📂 Models/         # Entity classes
│   │   ├── 📂 dao/            # Data Access Objects
│   │   └── 📂 util/           # Utility classes
│   └── 📂 resources/          # Configuration files
└── 📂 test/
    └── 📂 java/com/auca/
        ├── BookDaoTest.java     # Tests for Book DAO
        ├── BorrowerDaoTest.java # Tests for Borrower DAO
        ├── LocationTest.java    # Location related tests
        └── RoomShelfDaoTest.java # Room and Shelf tests
```

### Test Naming Conventions
- Test classes: `{ClassName}Test.java`
- Test methods: `test{MethodName}_When{Scenario}_Should{ExpectedBehavior}()`
- Example: `testSaveBook_WhenTitleIsNull_ShouldThrowException()`

## 🧪 Testing Approach

This project emphasizes test-driven development (TDD) principles with comprehensive JUnit test coverage. Here's how testing is implemented:

### 🔍 Test Structure

```
📦 src/test/java/com/auca/
├── BookDaoTest.java
├── BorrowerDaoTest.java
├── LocationTest.java
└── RoomShelfDaoTest.java
```

### 🚀 Running Tests

```bash
# Run all tests
mvn test

# Run a specific test class
mvn test -Dtest=BookDaoTest

# Generate test coverage report (requires JaCoCo plugin)
mvn jacoco:report
```

### 📊 Test Coverage

Key areas covered by tests:
- **DAO Layer**: CRUD operations for all entities
- **Business Logic**: Validation and business rules
- **Edge Cases**: Boundary conditions and error handling

### 🛠 Testing Tools
- JUnit 4.13 for test framework
- H2 in-memory database for integration tests
- Maven Surefire Plugin for test execution

## 🤝 Contributing

We welcome contributions! Here's how you can help:

1. 🍴 Fork the repository
2. 🌿 Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. 💾 Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. 🔀 Push to the branch (`git push origin feature/AmazingFeature`)
5. ✨ Open a Pull Request

## 📄 License

This project is licensed under the [MIT License](LICENSE).

## 📬 Contact

<div align="center">
  <p>Have questions or need support?</p>
  <p>
    <a href="mailto:alainndizeye1@gmail.com">
      <img src="https://img.icons8.com/color/48/000000/gmail-new.png" width="30" height="30" alt="Email"/>
    </a>
  </p>
  <p>We'd love to hear from you!</p>
</div>

---

<div align="center">
  <p>developed by Alain Ndizeye</p>
  
</div>
