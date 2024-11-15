-- Create a new database called 'Student records'
CREATE DATABASE Student
-- Use the database 'Student records'
USE Student
-- Create a new table called 'Student'
CREATE TABLE Student (
    StudentID INT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Surname VARCHAR(25) NOT NULL,
    Program VARCHAR(25) NOT NULL,
    Year INT NOT NULL,
    Dob DATE NOT NULL
);
INSERT INTO Student(StudentID, Name, Surname, Program, Year, Dob)
VALUES
(1, 'John', 'Doe', 'Computer Science', 1, '1990-01-01'),
(2, 'Jane', 'Doe', 'Computer Science', 1, '1991-01-01');
