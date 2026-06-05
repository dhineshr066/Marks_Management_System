MarksManagementSystem - Console (Java) + MySQL (Windows)
------------------------------------------------------
Contents:
  src/
    DBConnect.java   -- prompts for DB credentials and returns JDBC Connection
    Student.java     -- Student model (used by StudentActions)
    Teacher.java     -- Teacher actions (add student, marks, reports)
    Main.java        -- Program entry (main menu)
    StudentActions.java -- Student view actions
  marksdb.sql        -- SQL to create database & tables + default teacher
  README.txt         -- this file

Prerequisites (Windows):
1. Install Java JDK 8+ and set JAVA_HOME & PATH.
2. Install MySQL Server and MySQL Workbench.
3. Download MySQL Connector/J (JDBC driver) .jar and note its path, e.g. C:\mysql-connector-java-8.0.xx.jar

Steps:
1. Open MySQL Workbench and run 'marksdb.sql' to create the database, tables, and default teacher (admin/admin123).
2. Compile the Java files:
   Open Command Prompt and navigate to the 'src' folder.
   javac -cp .;"C:\path\to\mysql-connector-java-8.0.xx.jar" *.java
3. Run the program:
   java -cp .;"C:\path\to\mysql-connector-java-8.0.xx.jar" Main
4. When the program starts it will ask for DB username and password. Enter your MySQL credentials (e.g. username: root, password: <your mysql password>).
5. Teacher login: username = admin , password = admin123

Notes:
- The DBConnect class prompts for DB credentials at runtime (so you don't need to hardcode root/password).
- The application uses simple, clear SQL and is intended for learning and college submissions.
- If you prefer hardcoded DB credentials, edit DBConnect.java accordingly (not recommended for security reasons).
