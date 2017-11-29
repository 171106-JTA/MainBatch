--------------------------------------------------------------------------------
-- Author: Matthew Snee
-- Revature - Assigned 11/14/2017
-- SQL Lab
--------------------------------------------------------------------------------
-- 1.0 Setup Database by running Chinook_Oracle.sql-----------------------------

--------------------------------------------------------------------------------
-- 2.0 SQL Queries--------------------------------------------------------------

--------------------------------------------------------------------------------
-- 2.1 SELECT

-- All records from the employee table
SELECT *
FROM Employee;

-- All records from the Employee table where last name is King
SELECT *
FROM Employee
WHERE lastname = 'King';

-- All records from the Employee tale where first name is Andrew
-- and REPORTSTO is NULL
SELECT *
FROM Employee
WHERE firstname = 'Andrew' AND reportsto IS NULL;

--------------------------------------------------------------------------------
-- 2.2 ORDER BY

-- All ablums in Albums & Sort in descending order by title
SELECT *
FROM Album
ORDER BY Title DESC;

-- Select first name from Customer and sort results in in ascending
-- order
SELECT firstname
FROM Customer
ORDER BY firstname;

------------------------------------------------------------------
-- 2.3 INSERT INTO

--Insert two new records into Genre table
INSERT INTO Genre (genreID, Name)
VALUES (26, 'Noise')
INSERT INTO Genre (genreID, Name)
VALUES (27, 'More Noise')

--Insert two new records into Employee table
INSERT INTO Employee (employeeID, lastname, firstname, title, reportsTo, birthdate, hireDate, address, city, state, country, postalCode, phone, fax, email)
VALUES (9, 'Just Joe', 'Joe', 'Resident Joe', NULL, TO_DATE('13-AUG-66','DD-MON-YY'), TO_DATE('14-NOV-17', 'DD-MON-YY'), '111 Joseph St.', 'Joeville', 'New Josy', 'USA', 11111, '+91 080 2390794', '+91 080 2390795', 'joe@homewithjoe.com');
INSERT INTO Employee (employeeID, lastname, firstname, title, reportsTo, birthdate, hireDate, address, city, state, country, postalCode, phone, fax, email)
VALUES (10, 'Cooper', 'Beth', 'IT Staff', NULL, TO_DATE('13-AUG-66','DD-MON-YY'), TO_DATE('13-AUG-66','DD-MON-YY'), '115 Joseph St.', 'Joeville', 'New Josy', 'USA', 11111, '+91 080 2390784', '+91 080 2390785', 'joe@homewithjoe.com');

--Insert two new records into Customer table
INSERT INTO Customer (CustomerID, FirstName, LastName, Company, Address, City, State, Country, PostalCode, Phone, Fax, Email, SupportRepID)
VALUES (60, 'Joe', 'Just Joe', NULL, '111 Joseph St.', 'Joeville', 'New Josy', 'USA', 11111, '+91 080 2390794', '+91 080 2390795', 'joe@homewithjoe.com', 5);
INSERT INTO Customer (CustomerID, FirstName, LastName, Company, Address, City, State, Country, PostalCode, Phone, Fax, Email, SupportRepID)
VALUES (61, 'Beth', 'Cooper', 'GIS', '115 Joseph St.', 'Joeville', 'New Josy', 'USA', 11111, '+91 080 2390784', '+91 080 2390785', 'beth@gis.com', 2);

--------------------------------------------------------------------------------
-- 2.4 UPDATE

-- Update Aaron Mitchell in Customer table to Robert Walter
UPDATE Customer
SET FirstName = 'Robert', LastName = 'Walter'
WHERE FirstName = 'Aaron' AND LastName = 'Mitchell';

-- Update name of artist in Artist table "Creedence Clearwater Revival" to "CCR"
UPDATE Artist
SET Name = 'CCR'
WHERE Name = 'Creedence Clearwater Revival';

--------------------------------------------------------------------------------
-- 2.5 LIKE

-- Select all invoices with a billing address like "T%"
SELECT *
FROM Invoice
WHERE billingAddress LIKE 'T%';

--------------------------------------------------------------------------------
-- 2.6 BETWEEN

-- Select all invoice that have a total between 15 and 50
SELECT *
FROM Invoice
WHERE Total BETWEEN 15 AND 50;

-- Select all employees hired between 1st of June 2003 and 1st of March 2004
SELECT *
FROM Employee
WHERE HireDate BETWEEN TO_DATE('01-JUN-03', 'DD-MON-YY') AND TO_DATE('01-MAR-04', 'DD-MON-YY')

--------------------------------------------------------------------------------
-- 2.7 DELETE

-- Delete a record in Customer where the name is Robert Walter (There may be
-- constraints that rely on this, find out how to resolve them).
ALTER TABLE Invoice DROP CONSTRAINT FK_INVOICECUSTOMERID;
DELETE FROM Customer
WHERE FirstName = 'Robert' AND LastName = 'Walter';

--DELETE FROM InvoiceLine
--WHERE InvoiceID = (SELECT InvoiceID
--                    FROM Invoice
--                    WHERE CustomerID = (SELECT CustomerID
--                                        FROM Customer
--                                        WHERE FirstName = 'Robert' AND LastName = 'Walter'));
--
--DELETE FROM Invoice
--WHERE CustomerID = (SELECT CustomerID
--                    FROM Customer
--                    WHERE FirstName = 'Robert' AND LastName = 'Walter');

--------------------------------------------------------------------------------
-- 3.0 SQL Functions------------------------------------------------------------

--------------------------------------------------------------------------------
-- 3.1 System Define Functions
-- Create a function that reutnrs the current time
ALTER SESSION SET TIME_ZONE = '-5:0';
ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MON-YYYY HH24:MI:SS';
SELECT SESSIONTIMEZONE, CURRENT_TIMESTAMP FROM DUAL;

-- Create a function that returns the length of a mediatype from the 
-- mediatype table
SELECT LENGTH(name) FROM mediatype;

--------------------------------------------------------------------------------
-- 3.2 System Define Functions

-- Create a function that returns the average total of all invoice
SELECT AVG(Total) FROM Invoice;

-- Create a function that returns the most expensive track
SELECT MAX(unitPrice) FROM track;

--------------------------------------------------------------------------------
-- 3.3 User Defined Scalar Function

-- Create a function that returns the average price of invoiceline
-- items in the invoiceline table
SELECT AVG(unitPrice) FROM Invoiceline;
--this isn't SCALAR...

--------------------------------------------------------------------------------
-- 3.4 User Defined Table Valued Functions

-- Create a function that returns all employees who are born after 1968
SELECT *
FROM Employee
WHERE Birthdate > TO_DATE('01-JAN-1968', 'DD-MON-YYYY');

--------------------------------------------------------------------------------
--4.0 Stored Procedures---------------------------------------------------------

--------------------------------------------------------------------------------
--4.1 Basic Stored Procedure

-- Create  a stored procedure that selects the first and last names of all the
-- employees
CREATE OR REPLACE PROCEDURE  get_employee_name
IS
BEGIN
  FOR c1 IN (SELECT Firstname, Lastname FROM Employee) LOOP
        dbms_output.put_line('First Name: ' || c1.firstname);
        dbms_output.put_line('Last Name: ' ||c1.lastname);
    END LOOP;
END get_employee_name;
/


EXEC get_employee_name;
/

--------------------------------------------------------------------------------
--4.2 Stored Procedure Input Parameters

Select * from employee;

-- Create a stored procedure that updates the personal information of an 
-- employee
CREATE OR REPLACE PROCEDURE update_employee_data (e_id IN NUMBER, e_lname IN VARCHAR2,
                        e_fname IN VARCHAR2, e_title IN VARCHAR2, e_reports IN VARCHAR2,
                        e_bday IN DATE, e_hday IN DATE, e_addr IN VARCHAR2, 
                        e_city IN VARCHAR2, e_state IN VARCHAR2, e_country IN VARCHAR2,
                        e_zip IN NUMBER, e_phone IN VARCHAR2, e_fax IN VARCHAR2,
                        e_email IN VARCHAR2)
IS
BEGIN
    INSERT INTO employee
    VALUES (e_id, e_lname,  e_fname, e_title, e_reports, e_bday, 
            e_hday, e_addr, e_city, e_state, e_country, e_zip, 
            e_phone, e_fax, e_email);
END update_employee_data;
/

CALL update_employee_data(99, 'Cooper', 'Beth', 'IT Staff', NULL, 
            TO_DATE('13-AUG-66','DD-MON-YY'), TO_DATE('13-AUG-66','DD-MON-YY'), 
            '115 Joseph St.', 'Joeville', 'New Josy', 'USA', 11111, 
            '+91 080 2390784', '+91 080 2390785', 'joe@homewithjoe.com');
/

-- Create a stored procedure that returns the managers of an employee
CREATE OR REPLACE PROCEDURE get_manager (emp_id IN NUMBER, manager_id OUT NUMBER)
IS
BEGIN
    SELECT reportsTo INTO manager_id FROM employee WHERE employeeId = emp_id;
END;
/

DECLARE
    input NUMBER(10);
    output NUMBER(10);
BEGIN
    input := 3;
    get_manager(input, output);
    DBMS_OUTPUT.PUT_LINE(output);
END;
/

--------------------------------------------------------------------------------
--4.3 Create a stored procedure that returns the name and company of a customer
CREATE OR REPLACE PROCEDURE get_cust_comp (cust_id IN NUMBER, cust_name OUT VARCHAR2, 
                                            cust_comp OUT VARCHAR2)
IS
BEGIN
    SELECT firstname, company INTO cust_name, cust_comp FROM customer
    WHERE customerId = cust_id;
END;
/

DECLARE
    input NUMBER(10);
    output1 VARCHAR2(200);
    output2 VARCHAR2(200);
BEGIN
    input := 10;
    get_cust_comp(input, output1, output2);
    DBMS_OUTPUT.PUT_LINE('CustomerID: ' || input || 'Name: ' || output1 || ' '
                            || 'Company: ' || output2);
END;
/

--------------------------------------------------------------------------------
--5.0 Transactions

-- Create a transacton that given a invoiceID will delete that invoice (There 
-- may be constraints that rely on this, find out how to resolve them).
CREATE OR REPLACE PROCEDURE del_invoice (invoice_id IN NUMBER)
IS
BEGIN
    DELETE FROM invoiceline WHERE invoiceId = invoice_id;
    DELETE FROM invoice WHERE invoiceId = invoice_id;
END;
/

DECLARE
    input NUMBER(10);
BEGIN
    input := 1;
    del_invoice (input);
    DBMS_OUTPUT.PUT_LINE('InvoiceID: ' || input || ' was deleted successfully.');
END;
/

-- Create a transaction nested within a stored procedure that inserts a new
-- record in the Customer table
CREATE OR REPLACE PROCEDURE add_new_cust_rec (c_id IN NUMBER, c_fname IN VARCHAR2,
                        c_lname IN VARCHAR2, c_company IN VARCHAR2, c_addr IN VARCHAR2,
                        c_city IN VARCHAR2, c_state IN VARCHAR2, c_country IN VARCHAR2,
                        c_zip IN NUMBER, c_phone IN VARCHAR2, c_fax IN VARCHAR2,
                        c_email IN VARCHAR2, c_sup_rep_id IN NUMBER)
IS
BEGIN
    INSERT INTO customer
    VALUES (c_id, c_fname, c_lname, c_company, c_addr, c_city, c_state,
            c_country, c_zip, c_phone, c_fax, c_email, c_sup_rep_id);
END add_new_cust_rec;
/

CALL add_new_cust_rec(99, 'Beth', 'Cooper', 'IT Company', '115 Joseph St.', 
                        'Joeville', 'New Josy', 'USA', 11111,  
                        '+91 080 2390784', '+91 080 2390785', 
                        'joe@homewithjoe.com', 3);
/

--------------------------------------------------------------------------------
--6.0 Triggers

--------------------------------------------------------------------------------
--6.1 AFTER/FOR

-- Create an after insert trigger on the employee table fires after a new 
-- record is inserted into the table
CREATE OR REPLACE TRIGGER new_emp_trigger
AFTER INSERT ON employee
BEGIN 
    DBMS_OUTPUT.PUT_LINE('employee added');    
END;    
/

-- Create an after update trigger on the album table that fires after a row
-- is inserted in in the table
CREATE OR REPLACE TRIGGER updated_album_trigger
AFTER UPDATE ON album
BEGIN 
    DBMS_OUTPUT.PUT_LINE('album updated');    
END;    
/

-- Create an after update trigger on the customer table that fires after a row
-- is inserted in in the table
CREATE OR REPLACE TRIGGER cust_deleted_trigger
AFTER DELETE ON customer
BEGIN 
    DBMS_OUTPUT.PUT_LINE('Customer deleted');    
END;    
/

--------------------------------------------------------------------------------

--------------------------------------------------------------------------------
--7.0 JOINS

--------------------------------------------------------------------------------
-- 7.1 INNER

-- Create an inner join that joins the customer and orders, and specifies
-- the name of the customer and the invoiceid
SELECT firstname, lastname, invoiceid
FROM customer NATURAL INNER JOIN invoice;

--------------------------------------------------------------------------------
-- 7.2 OUTER

-- Create an outer join that joins the customer and invoice table, specifying
-- the CustomerID, Firstname, Lastname, InvoiceId, and Total
SELECT customer.customerid, customer.firstname, customer.lastname, invoice.invoiceid, invoice.total
FROM customer FULL OUTER JOIN invoice 
ON customer.customerid = invoice.customerid;

--------------------------------------------------------------------------------
-- 7.3 RIGHT

-- Create a right join that joins album and artist and sorts by artist name 
-- and title
SELECT *
FROM album 
RIGHT JOIN artist ON album.artistid = artist.artistid
ORDER BY artist.name, album.title;

--------------------------------------------------------------------------------
-- 7.4 CROSS

-- Create a cross join that joins album and artist and sorts by
-- artist name in ascending order
SELECT *
FROM album CROSS JOIN artist
ORDER BY artist.name;

--------------------------------------------------------------------------------
-- 7.5 SELF

-- Perform a self-join on the employee table, joining on the reportsto
-- column
SELECT *
FROM employee SELF JOIN employee 
ON employee.reportsto = employee.reportsto;

--------------------------------------------------------------------------------
-- 9.0 Administration

-- Create a .bak file for the database

--make the backup
--exp system/manager@xyz FULL=Y FILE=FULL.bak

--recreate from backup
--imp system/manager@abc FULL=Y FILE=FULL.bak


