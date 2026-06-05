CREATE DATABASE IF NOT EXISTS marksdb;
USE marksdb;

CREATE TABLE teacher (
                         username VARCHAR(50) PRIMARY KEY,
                         password VARCHAR(50)
);

CREATE TABLE student (
                         roll VARCHAR(20) PRIMARY KEY,
                         name VARCHAR(50),
                         department VARCHAR(50),
                         section VARCHAR(10)
);

CREATE TABLE marks (
                       roll VARCHAR(20) PRIMARY KEY,
                       java INT,
                       c INT,
                       cpp INT,
                       python INT,
                       FOREIGN KEY (roll) REFERENCES student(roll)
);

INSERT INTO teacher VALUES ('admin', 'admin123');
