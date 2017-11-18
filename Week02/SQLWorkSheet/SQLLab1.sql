--2.0 SQL Queries
--2.1 SELECT
    --Select all records from Employee table
SELECT * FROM EMPLOYEE;
    --Select all records from Employee table where name is King
SELECT * FROM EMPLOYEE 
    WHERE LASTNAME = 'King';
    --Select all records from the Employee table where name is Andrew and ReportsTo is NULL
SELECT * FROM EMPLOYEE
    WHERE FIRSTNAME = 'Andrew' 
    AND REPORTSTO = null;
--2.2 ORDER BY
    --Select all albums in the Album table and sort results in descending order by title
SELECT * FROM ALBUM 
    ORDER BY TITLE Desc;
    --Select first name from Customer and sort results in ascending order by city
SELECT FIRSTNAME FROM CUSTOMER
    ORDER BY City;
--2.3 INSERT INTO
    --Insert two new records into Genre table
INSERT INTO Genre (GENREID, NAME)
    VALUES (26, 'Alternative Hip Hop');
INSERT INTO Genre (GENREID, NAME)
    VALUES (27, 'Christian Rock');
    --Insert two new records into Employee table
INSERT INTO Employee (EMPLOYEEID, LASTNAME, FIRSTNAME, TITLE, 
    REPORTSTO, BIRTHDATE, HIREDATE, ADDRESS, CITY, STATE, COUNTRY,
    POSTALCODE, PHONE, FAX, EMAIL)
    VALUES (9, 'Grant', 'Ulysses', 'IT Staff', 6, '23-JUL-85','15-NOV-17',
        '12 122 St', 'New York City','NY','USA','11111','+1 (212) 222-1212',
        '+1 (212) 222-1213','ulysses.grant@gmail.com');

INSERT INTO Employee
    VALUES (10, 'Lee', 'Robert', 'IT Staff', 6, '25-JUL-85','15-NOV-17',
        '92 922 St', 'Houston','TX','USA','15111','+1 (999) 222-1212',
        '+1 (999) 222-1213','robert.lee@gmail.com');
    --Insert two new records into Customer
INSERT INTO CUSTOMER VALUES ( 
    666999, 'Joe', 'Smith', '100 Brigham Road', NULL, NULL, NULL, NULL, 
        NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO CUSTOMER VALUES (
    60, 'John', 'Doe', '18222 Princewill Lane', NULL, NULL, NULL, NULL, 
      NULL, NULL, NULL, NULL, NULL, NULL, NULL);

--2.4 UPDATE  
    --Update Aaron Mitchell in Customer table to Robert Walter
UPDATE CUSTOMER
  SET FIRSTNAME = 'Robert',
  LASTNAME = 'Walter'
  WHERE CUSTOMERID = 32;
    --Update name of artist in the Artist table "Creedence Clearwater Revival" to "CCR"
UPDATE ARTIST
    SET NAME = 'CCR'
    WHERE ARTISTID = 76;
--2.5 LIKE
    --Select all invoices with a billing address like 'T%'
SELECT * FROM INVOICE
    WHERE BILLINGADDRESS
    LIKE 'T%';
--2.6 BETWEEN
    --Select all invoices that have a total between 15 and 50
SELECT * FROM INVOICE
    WHERE TOTAL
    BETWEEN 15 AND 50;
    --Select all employees hired between the 1st of June 2003 and 1st of March 2004
SELECT * FROM EMPLOYEE
    WHERE HIREDATE
    BETWEEN '01-JUN-03' AND '01-MAR-04';
--2.7 DELETE
    --Delete a record in the Customer table where the name is Robert Walter
/*
SELECT * FROM CUSTOMER
    WHERE FIRSTNAME = 'Robert' 
        AND LASTNAME = 'Walter';
*/

DELETE FROM INVOICELINE CASCADE
    WHERE INVOICEID IN
        (SELECT INVOICEID FROM INVOICE
            WHERE CUSTOMERID = 32);
            
DELETE FROM INVOICE
    WHERE CUSTOMERID = 32;

DELETE FROM CUSTOMER
    WHERE FIRSTNAME = 'Robert' 
        AND LASTNAME = 'Walter';
--3.0 SQL Functions
    --3.1 System Defined Functions
        --Create a function that returns the current time
CREATE OR REPLACE FUNCTION CURR_TIME
    RETURN varchar2
    AS
    BEGIN
        RETURN CURRENT_TIMESTAMP;
END;
/
BEGIN
    DBMS_OUTPUT.PUT_LINE(CURR_TIME());
END;
/
    --Create a function that returns the length of a mediatype from the mediatype table
CREATE OR REPLACE FUNCTION MEDIA_LEN(mediaID IN MEDIATYPE.MEDIATYPEID%TYPE)
    RETURN NUMBER
    IS
        mediaName MEDIATYPE.NAME%TYPE;
        finalLen NUMBER;
    BEGIN
        SELECT NAME INTO mediaName FROM MEDIATYPE WHERE MEDIATYPEID = mediaID;
        finalLen := LENGTH(mediaName);
        RETURN finalLen;
END;
/
DECLARE
    dummy NUMBER;
BEGIN
    dummy :=(MEDIA_LEN(3));
    DBMS_OUTPUT.PUT_LINE(dummy);
END;
/
    --3.2 System Defined Aggregate Functions
        --Create a function that returns the avg total of all invoices
CREATE OR REPLACE FUNCTION get_avg_invoice
    RETURN NUMBER
    IS
        avgtotal NUMBER(4);
    BEGIN
        SELECT AVG(TOTAL) INTO avgtotal FROM INVOICE;
        RETURN avgtotal;
    END;
/
DECLARE
    dummy NUMBER;
BEGIN
    dummy :=(GET_AVG_INVOICE);
    DBMS_OUTPUT.PUT_LINE(dummy);
END;
/
        --Create a function that returns the most expensive track
CREATE OR REPLACE FUNCTION get_most_expensive_track
    RETURN SYS_REFCURSOR
    IS
        track_cursor SYS_REFCURSOR;
    BEGIN
        OPEN track_cursor
            FOR
                SELECT TRACKID,NAME FROM (SELECT TRACKID,NAME, UNITPRICE, max(UNITPRICE) OVER () AS max_uni FROM TRACK) WHERE UNITPRICE = max_uni;
        RETURN track_cursor;
    END;
/
    --3.3 User Defined Scalar Functions
        --Create a function that returns the avg price of invoiceline items 
CREATE OR REPLACE FUNCTION get_AVG_price_INVOICELINE
    RETURN NUMBER
    IS
        avg_invoiceLINE_price NUMBER(3);
    BEGIN
        SELECT AVG(UNITPRICE) INTO avg_invoiceline_price FROM INVOICELINE;
        RETURN avg_invoiceline_price;
    END;
/
DECLARE
    dummy NUMBER;
BEGIN
    dummy :=(get_AVG_price_INVOICELINE);
    DBMS_OUTPUT.PUT_LINE(dummy);
END;
/
    --3.4 User Defined Table Valued Functions
        --Create a function tha returns all employees born after 1968
CREATE OR REPLACE FUNCTION employees_born_after(input_birthdate IN DATE)     
    RETURN SYS_REFCURSOR
    IS
        track_cursor SYS_REFCURSOR;
    BEGIN
        OPEN track_cursor
            FOR
                SELECT EMPLOYEEID,FIRSTNAME,LASTNAME,BIRTHDATE FROM EMPLOYEE WHERE BIRTHDATE > input_birthdate;--(SELECT TRACKID,NAME, UNITPRICE, max(UNITPRICE) OVER () AS max_uni FROM TRACK) WHERE UNITPRICE = max_uni;
        RETURN track_cursor;
        END;
/
DECLARE
    fc_cursor SYS_REFCURSOR := employees_born_after('1-JAN-68');
    empID EMPLOYEE.EMPLOYEEID%TYPE;
    empFIRSTNAME EMPLOYEE.FIRSTNAME%TYPE;
    empLASTNAME EMPLOYEE.LASTNAME%TYPE;
    empBIRTHDATE EMPLOYEE.BIRTHDATE%TYPE;
BEGIN
    LOOP
        FETCH fc_cursor INTO empID,empFIRSTNAME,empLASTNAME,empBIRTHDATE;
        EXIT WHEN fc_cursor%NOTFOUND; --doesn't exist until no records are left
        DBMS_OUTPUT.PUT_LINE(empID || ' ' || empFIRSTNAME || ' ' || empLASTNAME || ' '|| empBIRTHDATE);
        END LOOP;
    END;  
    
--4.0 Stored Procedures
    --4.1 Create a stored procedure that selects the first and last names of all the employees
CREATE OR REPLACE PROCEDURE get_emp_names(cursorParm OUT SYS_REFCURSOR)
    IS
    BEGIN
        OPEN cursorParm 
            FOR 
                SELECT LASTNAME, FIRSTNAME FROM EMPLOYEE;
    END;
/
DECLARE
    emp_cursor SYS_REFCURSOR;
    firstN EMPLOYEE.FIRSTNAME%TYPE;
    lastN EMPLOYEE.LASTNAME%TYPE;
BEGIN
    get_emp_names(emp_cursor);
    LOOP
        FETCH emp_cursor INTO lastN, firstN;
        EXIT WHEN emp_cursor%NOTFOUND; --doesn't exist until no records are left
        DBMS_OUTPUT.PUT_LINE(firstN || ' ' || lastN);
        END LOOP;
    END; 
    --4.2 Stored Procedure
        --Create a stored procedure that updates the personal info of an employee
CREATE OR REPLACE PROCEDURE update_employee_address(empID IN EMPLOYEE.EMPLOYEEID%TYPE, newAdd IN EMPLOYEE.ADDRESS%TYPE)
    IS  
    BEGIN
        UPDATE EMPLOYEE
        SET ADDRESS = newAdd
        WHERE EMPLOYEEID = empID;
        commit;
    END;
        --Create a stored procedure tht returns the manager of an employee
CREATE OR REPLACE PROCEDURE manager_of_employee(empID IN EMPLOYEE.EMPLOYEEID%TYPE, manID OUT EMPLOYEE.REPORTSTO%TYPE)
    IS
    BEGIN
        SELECT REPORTSTO INTO manID FROM EMPLOYEE WHERE EMPLOYEEID = empID;
    END;
/
        --Create a stored procedure that returns the name and company of a customer
CREATE OR REPLACE PROCEDURE customer_name_company(custID IN CUSTOMER.CUSTOMERID%TYPE, cust_fname OUT CUSTOMER.FIRSTNAME%TYPE, cust_lname OUT CUSTOMER.LASTNAME%TYPE, cust_comp OUT CUSTOMER.COMPANY%TYPE)
    IS
    BEGIN
        SELECT FIRSTNAME, LASTNAME, COMPANY INTO cust_fname, cust_lname, cust_comp FROM CUSTOMER WHERE CUSTOMERID = custID;
    END;
    
--5.0 Transanctions
    --Create a Transaction that given an invoiceID will delete that invoice
ALTER TABLE INVOICELINE
DROP CONSTRAINT fk_invoicelineinvoiceid;

ALTER TABLE INVOICELINE
ADD CONSTRAINT fk_invoicelineinvoiceid
FOREIGN KEY (invoiceid) REFERENCES INVOICE (invoiceID) ON DELETE CASCADE;

CREATE OR REPLACE PROCEDURE delete_invoice_proc(invoice_index IN INVOICE.INVOICEID%TYPE)
    IS  
    BEGIN
        DELETE FROM INVOICE
            WHERE INVOICEID = invoice_index;
        DBMS_OUTPUT.PUT_LINE('Record  succesfully deleted!');
        commit;
    END;  
/
--CALL delete_invoice_proc(400);
    --Create a transaction that inserts a new record in the Customer table
CREATE OR REPLACE PROCEDURE insert_cust_proc(newID IN CUSTOMER.CUSTOMERID%TYPE, 
    newF IN CUSTOMER.FIRSTNAME%TYPE, newL IN CUSTOMER.LASTNAME%TYPE, newEmail IN CUSTOMER.EMAIL%TYPE)
    IS  
    BEGIN
        INSERT INTO CUSTOMER(CUSTOMERID, FIRSTNAME, LASTNAME, EMAIL)
            VALUES(newID, newF, newL, newEmail);
        commit;
    END;  
/
CALL INSERT_CUST_PROC(69, 'Princewill', 'Ibe','pibe@gmail.com');
--6.0 Triggers
    --6.1 AFTER/FOR
        --Create an after insert trigger on the employee table fired after a new record is inserted
CREATE OR REPLACE TRIGGER employee_insert_trigger --auto-inc
    AFTER INSERT ON EMPLOYEE
    FOR EACH ROW
    BEGIN --This keyword signifies a block for a transaction
        DBMS_OUTPUT.PUT_LINE('Row inserted into Employee Successfully!');
    END;
        --Create an after update trigger on the album table
CREATE OR REPLACE TRIGGER album_after_update_trigger --auto-inc
    AFTER UPDATE ON ALBUM
    FOR EACH ROW
    BEGIN --This keyword signifies a block for a transaction
        DBMS_OUTPUT.PUT_LINE('Row Updated Successfully!');
    END;
        --Create an after delete trigger on the customer table
CREATE OR REPLACE TRIGGER cust_after_delete_trigger --auto-inc
    AFTER DELETE ON CUSTOMER
    FOR EACH ROW
    BEGIN --This keyword signifies a block for a transaction
        DBMS_OUTPUT.PUT_LINE('Row Deleted Succesfully!');
    END;
    
--7.0 JOINS
    --7.1 INNER 
        --Create an inner join that joins customers and orders and specifies the
        --name of the customer and the invoiceID
CREATE VIEW Customer_Invoice AS
SELECT C.CUSTOMERID, C.FIRSTNAME, C.LASTNAME, I.INVOICEID FROM CUSTOMER C
    INNER JOIN INVOICE I
    ON C.CUSTOMERID = I.CUSTOMERID;
    --7.2 OUTER
        --Create an outer join that joins the customer and invoice table, specifying the
        --CustID, FirstName, LastName, InvoiceId, & Total
SELECT C.CUSTOMERID, C.FIRSTNAME, C.LASTNAME, I.INVOICEID, I.TOTAL FROM CUSTOMER C
    FULL OUTER JOIN INVOICE I
    ON C.CUSTOMERID = I.CUSTOMERID;
    --7.3 RIGHT
        --Create a right join that joins album and artist specifying artist name & title
SELECT ar.NAME, al.TITLE FROM ARTIST ar
    RIGHT JOIN ALBUM al
    ON ar.ARTISTID = al.ARTISTID;
    --7.4 CROSS
SELECT ar.NAME, al.TITLE FROM ARTIST ar
    CROSS JOIN ALBUM al
    ORDER BY ar.NAME;
    --7.5 SELF
        --Perform a self-join on the employee table, joining on the reportso col
SELECT  e2.EMPLOYEEID, e2.FIRSTNAME, e2.LASTNAME, e1.REPORTSTO, e1.EMPLOYEEID, e1.FIRSTNAME, e1.LASTNAME FROM EMPLOYEE e1
    JOIN EMPLOYEE e2
    ON e1.REPORTSTO = e2.EMPLOYEEID
    ORDER BY e2.EMPLOYEEID;
--9.0 Administration
    --Create a .bak file
ALTER DATABASE BEGIN BACKUP;
--BACKUP DATABASE oraclebootcamp TO DISK='C:\Users\Denny\Documents\Coding\SQL\SQL BACKUPS\SQL Lab1.bak';
commit;