CREATE DATABASE school;
CREATE TABLE student(
name VARCHAR(50),
age INT NOT NULL,
rollNo INT PRIMARY KEY);

SELECT rollNo, COUNT(name) 
FROM student 
GROUP BY rollNo;
ALTER TABLE student ADD pocketMoney INT DEFAULT 2000;
DELETE FROM student WHERE rollNo = 26;
SELECT * FROM student2;
SELECT * FROM student;
SELECT * FROM student LEFT JOIN student2 ON student.rollNo = student2.rollNo WHERE student2.rollNo IS NULL
UNION
SELECT * FROM student RIGHT JOIN student2 ON student.rollNo = student2.rollNo WHERE student.rollNo IS NULL;
TRUNCATE TABLE student;
CREATE TABLE student2(
name VARCHAR(50),
age INT NOT NULL,
rollNo INT PRIMARY KEY);
INSERT INTO student2 (name,age,rollno) VALUES ('SANIA',21,052),('TAHIRA',20,017),('HASSAN',19,024);
INSERT INTO student2 (name,age,rollno) VALUES ('YUSRA',19,009),('MARFOWA',21,030),('FARUKH',21,022);