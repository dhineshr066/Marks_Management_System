# 🎓 Marks Management System

A Java-based **Student Marks Management System** developed using **Core Java, JDBC, and MySQL**. The application provides an efficient way to manage student records, examination marks, and academic reports through a simple console-based interface.

## 🚀 Project Overview

Managing student academic records manually can be time-consuming and prone to errors. This project automates the process of storing, updating, and retrieving student information and marks using a MySQL database.

The system provides separate functionalities for teachers and students, ensuring organized academic record management.

### Key Objectives

* Store student information securely
* Manage examination marks efficiently
* Generate academic reports
* Reduce manual record-keeping efforts
* Demonstrate Java-MySQL database integration

---

## 🛠 Technologies Used

### Programming Language

* Java

### Database

* MySQL

### Database Connectivity

* JDBC (Java Database Connectivity)

### Development Tools

* MySQL Workbench
* Command Prompt
* Java JDK 8+

---

## 🏗 System Architecture

The application follows a simple layered architecture:

### Java Application Layer

* User Interface (Console)
* Business Logic
* Database Operations

### Database Layer

* MySQL Database
* Student Records
* Teacher Records
* Marks Management

### Connectivity Layer

* JDBC Driver
* SQL Queries
* Database Transactions

---

## ✨ Features

### 👨‍🏫 Teacher Module

✅ Teacher Authentication

✅ Add New Students

✅ Update Student Information

✅ Enter Examination Marks

✅ Generate Student Reports

✅ View Academic Performance

### 👨‍🎓 Student Module

✅ View Personal Information

✅ Check Subject-wise Marks

✅ Access Academic Reports

### 🗄 Database Features

✅ Persistent Data Storage

✅ Secure JDBC Connectivity

✅ Relational Database Structure

✅ Efficient Data Retrieval

---

## 📂 Database Setup

The project includes a SQL script:

```text
marksdb.sql
```

This script automatically creates:

* Database
* Required Tables
* Relationships
* Default Teacher Account

### Default Teacher Login

```text
Username : admin
Password : admin123
```

---

## 📊 Functional Modules

| Module                | Description                    |
| --------------------- | ------------------------------ |
| Teacher Login         | Authenticates teacher access   |
| Student Management    | Add and manage student records |
| Marks Management      | Insert and update marks        |
| Report Generation     | Generate academic reports      |
| Database Connectivity | JDBC-based MySQL integration   |

---

## ⚙️ Installation Guide

### Step 1: Install Prerequisites

* Java JDK 8 or Higher
* MySQL Server
* MySQL Workbench
* MySQL Connector/J (JDBC Driver)

---

### Step 2: Create Database

Open MySQL Workbench and execute:

```sql
marksdb.sql
```

This will create the complete database structure and default teacher account.

---

### Step 3: Compile Java Files

Navigate to the `src` folder and run:

```bash
javac -cp .;"C:\path\to\mysql-connector-java-8.0.xx.jar" *.java
```

---

### Step 4: Run Application

```bash
java -cp .;"C:\path\to\mysql-connector-java-8.0.xx.jar" Main
```

---

### Step 5: Enter Database Credentials

When prompted, enter your MySQL credentials:

```text
Database Username : root
Database Password : your_password
```

---

## 📁 Project Structure

```text
MarksManagementSystem/
│
├── src/
│   ├── DBConnect.java
│   ├── Student.java
│   ├── Teacher.java
│   ├── StudentActions.java
│   └── Main.java
│
├── marksdb.sql
│
└── README.md
```

---

## 📄 File Description

### DBConnect.java

Handles database connection by accepting MySQL credentials and establishing a JDBC connection.

### Student.java

Represents the Student model and stores student-related information.

### Teacher.java

Contains teacher operations such as adding students, entering marks, and generating reports.

### StudentActions.java

Provides functionalities available to students for viewing marks and reports.

### Main.java

Acts as the entry point of the application and manages menu navigation.

### marksdb.sql

Contains database creation scripts, table definitions, and default teacher credentials.

---

## 🔒 Security Features

* Database credentials entered at runtime
* No hardcoded database passwords
* Authentication for teacher access
* Structured database design

---

## 🎯 Learning Outcomes

This project demonstrates:

* Object-Oriented Programming (OOP)
* JDBC Connectivity
* SQL Database Operations
* CRUD Operations
* Exception Handling
* Database Design
* Console-Based Application Development

---

## 🚀 Future Enhancements

* Graphical User Interface (Java Swing / JavaFX)
* Student Authentication System
* Attendance Management
* Grade Calculation Automation
* Report Export to PDF
* Web-Based Version
* Cloud Database Integration

---

## 📷 Application Workflow

1. Teacher logs into the system.
2. Student records are created and stored.
3. Examination marks are entered.
4. Data is saved in MySQL database.
5. Students can view their marks.
6. Academic reports are generated and displayed.

---

## 👨‍💻 Author

**Dhinesh R**

Computer Science Student | Java Developer | Database Enthusiast

---

## 📜 License

This project is developed for educational and learning purposes.

---

## ⭐ Acknowledgements

* Java Development Kit (JDK)
* JDBC API
* MySQL Server
* MySQL Workbench
* MySQL Connector/J
* Open Source Java Community
