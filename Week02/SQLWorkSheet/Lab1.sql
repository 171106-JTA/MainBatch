--2.0 SQL Queries

---2.1 SELECT
--Select all records from the Employee table.
SELECT * FROM Employee;

--Select all records from the Employee table where last name is King
SELECT * FROM Employee
WHERE LASTNAME = 'King';

--Select all records from the Employee table where first name is Andrew and REPORTSTO is NULL
SELECT * FROM Employee
WHERE FIRSTNAME = 'Andrew' AND REPORTSTO IS NULL;
--------------------------------------------------------------------------------------------------------------------------------
---2.2 ORDER BY
--Select all albums in Album table and sort result set in descending order by title
SELECT * FROM Album
ORDER BY TITLE DESC;

--Select first name from Customer and sort result set in ascending order by city
SELECT FIRSTNAME FROM Customer
ORDER BY CITY ASC;
---------------------------------------------------------------------------------------------------------------------------------
---2.3 INSERT INTO
--Insert two new records into Genre table
INSERT INTO Genre (GENREID, NAME) VALUES (26, 'K-Pop');
INSERT INTO Genre (GENREID, NAME) VALUES (27, 'J-Pop');

--Insert two new records into Employee table
INSERT INTO Employee VALUES (9, 'Robert', 'Bobbert', 'Chief Executive Officer', 
3, TO_DATE('1995-2-18 00:00:00','yyyy-mm-dd hh24:mi:ss'), TO_DATE('2009-2-18 00:00:00','yyyy-mm-dd hh24:mi:ss'), '230 W 8th St', 'Reston', 'VA', 'United States', '345 345','+1 (540) 345-0987', '+1 (540) 345-0988', 'bobbert@revature.com');
INSERT INTO Employee VALUES (10, 'Washington', 'George', 'President', 
3, TO_DATE('1732-2-22 00:00:00','yyyy-mm-dd hh24:mi:ss'), TO_DATE('1775-5-10 00:00:00','yyyy-mm-dd hh24:mi:ss'), '231 E 8th St', 'Bridges Creek', 'VA', 'British America', '543 543','+1 (671) 765-8000', '+1 (671) 765-8001', 'george@revature.com');

--Insert two new records into Customer table
INSERT INTO Customer (CUSTOMERID, FIRSTNAME, LASTNAME, EMAIL) VALUES (400, 'Han', 'Jang', 'han@jang.com');
INSERT INTO Customer (CUSTOMERID, FIRSTNAME, LASTNAME, EMAIL) VALUES (4001, 'Jordan', 'PG', 'jordan@pg.com');
---------------------------------------------------------------------------------------------------------------------------------
---2.4 UPDATE
--Update Aaron Mitchell in Customer table to Robert Walter
UPDATE Customer SET FIRSTNAME = 'Robert', LASTNAME = 'Walter'
WHERE FIRSTNAME = 'Aaron' AND LASTNAME = 'Mitchell';

--Update name of artist in the Artist table "Creedence Clearwater Revival" to "CCR"
UPDATE Artist SET NAME = 'CCR' 
WHERE lower(NAME) LIKE 'creedence%';
---------------------------------------------------------------------------------------------------------------------------------
---2.5 LIKE
--Select all invoices with a billing address like "T%"
SELECT * FROM Invoice
WHERE BILLINGADDRESS LIKE 'T%';
---------------------------------------------------------------------------------------------------------------------------------
---2.6 BETWEEN
--Select all invoices that have a total between 15 and 50
SELECT * FROM Invoice
WHERE TOTAL BETWEEN 15 AND 50;

--Select all employees hired between 1st of June 2003 and 1st of March 2004
SELECT * FROM Employee
WHERE HIREDATE BETWEEN TO_DATE('01/06/2003', 'DD/MM/YYYY') AND TO_DATE('03/01/2004', 'MM/DD/YYYY');
---------------------------------------------------------------------------------------------------------------------------------
---2.7 DELETE 
--Delete a record in Customer table where the name Robert Walter
ALTER TABLE InvoiceLine DROP CONSTRAINT FK_InvoiceLineInvoiceId;
ALTER TABLE Invoice DROP CONSTRAINT FK_InvoiceCustomerId;

ALTER TABLE Invoice ADD CONSTRAINT FK_InvoiceCustomerId
    FOREIGN KEY (CustomerId) REFERENCES Customer (CustomerId) ON DELETE CASCADE;

ALTER TABLE InvoiceLine ADD CONSTRAINT FK_InvoiceLineInvoiceId
    FOREIGN KEY (InvoiceId) REFERENCES Invoice (InvoiceId) ON DELETE CASCADE;
    
DELETE FROM Customer
WHERE FIRSTNAME = 'Robert' AND LASTNAME = 'Walter';