<div align="center">
  <h1>📚 AUCA Library Management System</h1>
  <p><em>Empowering Knowledge Access at Adventist University of Central Africa</em></p>
  
  [![Java](https://img.shields.io/badge/Java-17-007396?logo=java&logoColor=white)](https://www.java.com/)
  [![Hibernate](https://img.shields.io/badge/Hibernate-6.6.15-59666C?logo=hibernate&logoColor=white)](https://hibernate.org/)
  [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13-336791?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
  [![Maven](https://img.shields.io/badge/Maven-3.8+-C71A36?logo=apache-maven&logoColor=white)](https://maven.apache.org/)
  [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
</div>

<div align="center">
  <p>A modern, efficient library management solution designed to streamline operations and enhance user experience at AUCA.</p>
</div>

## 📋 Project Overview

The AUCA Library System is a Java-based application that provides an efficient way to manage library operations including book management, member management, and borrowing processes. The system is built using Java 17 and utilizes Hibernate ORM for database interactions with a PostgreSQL database.

## ✨ Key Features

<div align="center">
  <table>
    <tr>
      <td align="center">📖 <strong>Book Management</strong></td>
      <td>Add, update, and manage book inventory with detailed cataloging</td>
    </tr>
    <tr>
      <td align="center">👥 <strong>Member Portal</strong></td>
      <td>Comprehensive user accounts with role-based access control</td>
    </tr>
    <tr>
      <td align="center">🔄 <strong>Borrowing System</strong></td>
      <td>Automated tracking of loans, returns, and fine calculations</td>
    </tr>
    <tr>
      <td align="center">📍 <strong>Smart Location</strong></td>
      <td>Efficient room and shelf management for physical resources</td>
    </tr>
    <tr>
      <td align="center">🔒 <strong>Security</strong></td>
      <td>Secure authentication and authorization system</td>
    </tr>
    <tr>
      <td align="center">📊 <strong>Reporting</strong></td>
      <td>Generate insights on library operations and user activities</td>
    </tr>
  </table>
</div>

## 🛠️ Tech Stack

<div align="center">
  <table>
    <tr>
      <td align="center"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg" width="60" height="60" alt="Java" /></td>
      <td align="center"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/hibernate/hibernate-original-wordmark.svg" width="60" height="60" alt="Hibernate" /></td>
      <td align="center"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original-wordmark.svg" width="60" height="60" alt="PostgreSQL" /></td>
      <td align="center"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/maven/maven-original-wordmark.svg" width="60" height="60" alt="Maven" /></td>
    </tr>
    <tr>
      <td align="center">Java 17</td>
      <td align="center">Hibernate ORM</td>
      <td align="center">PostgreSQL</td>
      <td align="center">Maven</td>
    </tr>
  </table>
</div>

### Additional Technologies
- **Security**: Spring Security Crypto 6.0.2
- **Testing**: JUnit 4.11
- **Build**: Maven Wrapper
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
│   │   ├── 📂 Models/         # Entity classes (Book, User, Borrower, etc.)
│   │   ├── 📂 dao/            # Data Access Objects
│   │   ├── 📂 util/           # Utility classes
│   │   └── 📄 App.java        # Main application class
│   └── 📂 resources/          # Configuration files
└── 📂 test/                   # Test classes
```

## 🧪 Testing

Run the complete test suite with coverage:

```bash
# Run all tests
mvn test

# Generate test coverage report
mvn jacoco:report
```

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
    <a href="mailto:your-email@example.com">
      <img src="https://img.icons8.com/color/48/000000/gmail-new.png" width="30" height="30" alt="Email"/>
    </a>
    <a href="https://github.com/Alaindeve1" target="_blank">
      <img src="https://img.icons8.com/fluent/48/000000/github.png" width="30" height="30" alt="GitHub"/>
    </a>
  </p>
  <p>We'd love to hear from you!</p>
</div>

---

<div align="center">
  <p>Built with ❤️ by the AUCA Development Team</p>
  <p>© 2025 AUCA Library System. All rights reserved.</p>
</div>
