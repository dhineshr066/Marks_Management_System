# 🎓 Marks Management System

A Java-based console application integrated with MySQL for managing student records, marks, and academic reports. The system provides separate functionalities for teachers and students, enabling efficient academic record management through a simple command-line interface.

## 📌 Project Overview

The Marks Management System is designed to automate the process of managing student information and examination marks. Teachers can add students, update marks, and generate reports, while students can view their academic performance.

This project demonstrates the integration of:

* Core Java
* JDBC (Java Database Connectivity)
* MySQL Database
* Object-Oriented Programming Concepts

## ✨ Features

### 👨‍🏫 Teacher Module

* Secure teacher login
* Add new student records
* Enter and update student marks
* View student details
* Generate academic reports

### 👨‍🎓 Student Module

* View personal details
* Check subject-wise marks
* Access academic reports

### 🗄 Database Features

* Persistent data storage using MySQL
* Relational database design
* Secure database connectivity through JDBC

---

## 🏗️ Project Structure

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
└── README.md
```

### File Description

| File                | Description                                 |
| ------------------- | ------------------------------------------- |
| DBConnect.java      | Handles database connection using JDBC      |
| Student.java        | Student model class                         |
| Teacher.java        | Teacher operations and management functions |
| StudentActions.java | Student-related functionalities             |
| Main.java           | Application entry point and menu system     |
| marksdb.sql         | Database schema and initial data            |

---

## 🛠 Technologies Used

| Technology      | Purpose                        |
| --------------- | ------------------------------ |
| Java            | Application Development        |
| JDBC            | Database Connectivity          |
| MySQL           | Database Management            |
| MySQL Workbench | Database Administration        |
| OOP Concepts    | Code Structure and Reusability |

---

## 📋 Prerequisites

Before running the project, ensure the following software is installed:

* Java JDK 8 or later
* MySQL Server
* MySQL Workbench
* MySQL Connector/J (JDBC Driver)

---

## ⚙️ Installation & Setup

### Step 1: Create Database

Open MySQL Workbench and execute:

```sql
marksdb.sql
```

This will create:

* Database
* Required tables
* Default teacher account

### Step 2: Compile Source Files

Navigate to the `src` folder and run:

```bash
javac -cp .;"C:\path\to\mysql-connector-java-8.0.xx.jar" *.java
```

### Step 3: Run Application

```bash
java -cp .;"C:\path\to\mysql-connector-java-8.0.xx.jar" Main
```

### Step 4: Enter Database Credentials

When prompted, provide your MySQL credentials:

```text
Username: root
Password: your_mysql_password
```

---

## 🔐 Default Teacher Login

```text
Username : admin
Password : admin123
```

---

## 🗃 Database Design

The system uses MySQL tables to store:

* Teacher Information
* Student Records
* Subject Marks
* Academic Reports

The database schema is provided in:

```text
marksdb.sql
```

---

## 🎯 Learning Outcomes

This project demonstrates:

* JDBC Connectivity
* SQL Queries and Database Operations
* CRUD Operations
* Java Exception Handling
* Object-Oriented Programming
* Console-Based Application Development

---

## 🚀 Future Enhancements

* Graphical User Interface (Java Swing / JavaFX)
* Student Login Authentication
* Attendance Management
* Grade Calculation Automation
* Export Reports to PDF
* Web-Based Deployment

---

## 📸 Sample Workflow

1. Teacher logs into the system.
2. Teacher adds student records.
3. Teacher enters examination marks.
4. Data is stored in MySQL database.
5. Students can view their marks and reports.
6. Reports can be generated for academic analysis.

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
* MySQL Server
* MySQL Connector/J
* JDBC API
* Open Source Java Community
