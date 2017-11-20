/*SQL Lab - Revature

@Autor:         Mahamadou TRAORE
@Instructor:    Ryan LESSLEY
@Date:          11/15/2017
*/

--1.0 Setting Up Oracle Chinook
    --Completed
    
--2.0 SQL Queries
--2.1 SELECT
    --Select all records from the Employee table
    SELECT * FROM employee;
    --Select all records from the Employee table where last name is King
    SELECT * FROM employee WHERE lastname = 'King';
    --Select all records from the Employee table where first name is Andrew and REPORTSTO is NULL
    SELECT * FROM employee WHERE firstname = 'Andrew' AND reportsto is NULL;
    
--2.2 ORDER BY
    --Select all albums in Album table and sort result set in descending order by title
    SELECT * FROM album ORDER BY title DESC;
    --Select all first name from Customer and sort result set in ascending order by city
    SELECT firstname FROM customer ORDER BY city ASC;
    
--2.3 INSERT INTO
    --Insert two new records into Genre table
    INSERT INTO genre(genreid, name) VALUES(25, 'Contry');
    INSERT INTO genre(genreid, name) VALUES(26, 'Latino');
    --Insert two new records into Eployee table
        --select employeeid from employee; //To find the highest employeeId value which is 8.
    INSERT INTO employee(EmployeeId, LastName, FirstName, Title, ReportsTo, BirthDate, HireDate,
    Address, City, State, Country, PostalCode, Phone, Fax, Email) 
    VALUES(9, 'TRAORE', 'Mahamadou', 'Java Tech', 1, TO_DATE('1990-1-1 00:00:00','yyyy-mm-dd hh24:mi:ss'), TO_DATE('2017-1-1 00:00:00','yyyy-mm-dd hh24:mi:ss'), 'Plaza America', 
    'Hendon', 'Virginia', 'US', '00000', '8882223131', '8882223131', 'mahamadou@rev.com');

   INSERT INTO employee(EmployeeId, LastName, FirstName, Title, ReportsTo, BirthDate, HireDate,
    Address, City, State, Country, PostalCode, Phone, Fax, Email) 
    VALUES(10, 'KUSH', 'Luc', 'Senior Java Developer', 1, TO_DATE('1986-7-3 00:00:00','yyyy-mm-dd hh24:mi:ss'), TO_DATE('2017-1-1 00:00:00','yyyy-mm-dd hh24:mi:ss'), 'Plaza America', 
    'Hendon', 'Virginia', 'US', '00000', '8882223132', '8882223145', 'luc@rev.com');
    --Insert two new records into Customer table
        select * from customer;
    INSERT INTO Customer (CustomerId, FirstName, LastName, Company, Address, City, State, Country, PostalCode, Phone, Fax, Email, SupportRepId) 
    VALUES (60, 'THOMAS', 'Bruce', 'Revature', 'Plaza America', 'Hendon', 'Virginia', 'US', '00000', '4562358741', '4562358742', 'breuce@rev.com', 1);

    INSERT INTO Customer (CustomerId, FirstName, LastName, Company, Address, City, State, Country, PostalCode, Phone, Fax, Email, SupportRepId) 
    VALUES (60, 'BORGIAS', 'Celine', 'Revature', 'Plaza America', 'Hendon', 'Virginia', 'US', '00000', '4562358741', '4562358742', 'celine@rev.com', 1);

--2.4 UPDATE
    --Update Aaron Mitchell in Customer table to Robert Walter
    UPDATE Customer SET FirstName = 'Robert', LastName = 'Walter' WHERE FirstName = 'Aaron' AND LastName = 'Mitchell';
    --Update name of artist in Artist table "Creedence Revival" to "CCR"
    UPDATE Artist SET name = 'CCR' WHERE name = 'Creedence Revival';
--2.5 LIKE
    --Select all invoices with billing address like "T%"
    SELECT * FROM Invoice WHERE billingadderss LIKE 'T%';
--2.6 BETWEEN
    --Select all invoices that have a total between 15 and 50
    SELECT * FROM Invoice WHERE Total BETWEEN 15 AND 50;
    --Select all employees hired between 1st of June 2003 and 1st of March 2004
    SELECT * FROM Employee WHERE Hiredate BETWEEN TO_DATE('2003-6-1 00:00:00','yyyy-mm-dd hh24:mi:ss') AND
    TO_DATE('2004-3-1 00:00:00','yyyy-mm-dd hh24:mi:ss');
--2.7 DELETE
    --Delete a record in Customer table where the name is Robert Walter 
        /*
        Deleting this record won't be possible while still having invoices with the same customerID
        A way to handle this will be to drop the constraint that create a foreign key from customer into invoice and 
        recreate it with the 'ON DELETE CASCADE' clause and do the same for the Invoiceline table on its invoiceId foreign key.
        
        Another way is to first delete the records (after deleting the related invoiceline records) with this customer's id in the invoice table 
        before deleting it into customer table.
        */
        SELECT customerId FROM Customer WHERE firstname = 'Robert' AND Lastname = 'Walter';
        SELECT customerId FROM Invoice WHERE customerId IN (SELECT customerId FROM Customer WHERE firstname = 'Robert' AND Lastname = 'Walter');
        SELECT * FROM InvoiceLine WHERE invoiceId IN (
            SELECT invoiceId FROM Invoice WHERE customerId IN (
                SELECT customerId FROM Customer WHERE firstname = 'Robert' AND Lastname = 'Walter'));
        
        DELETE FROM InvoiceLine WHERE invoiceId IN (
            SELECT invoiceId FROM Invoice WHERE customerId IN (
                SELECT customerId FROM Customer WHERE firstname = 'Robert' AND Lastname = 'Walter'));
                
        DELETE FROM Invoice WHERE customerId IN (
            SELECT customerId FROM Customer WHERE firstname = 'Robert' AND Lastname = 'Walter');
            
        DELETE FROM Customer WHERE firstname = 'Robert' AND Lastname = 'Walter';
--3.0 SQL Functions
--3.1 System Defined Functions
    --Create a function that returns the current time
    CREATE OR REPLACE FUNCTION returnCurrentTime
    RETURN DATE
    IS
        myTime DATE;
    BEGIN
        SELECT SYSDATE INTO myTime FROM dual;
        RETURN MyTime;
    END;
    /*For testing purpose    
    select returnCurrentTime() from dual;*/ 
       
    --Create a function that returns the length of a mediatype name from Mediatype table
    CREATE OR REPLACE FUNCTION returnMediaTypeLength(columnId IN mediatype.mediatypeId%TYPE)
    RETURN NUMBER
    IS
        mylength NUMERIC;
    BEGIN
        SELECT length(name) INTO mylength FROM mediatype WHERE mediatypeId = columnId;
        RETURN mylength;
    END;
    /*/
    --For testing purpose    
    select returnMediaTypeLength(1) from dual; */
     
--3.2 System Defined Aggregate Functions
    --Create a function that returns the average total of all invoices
    CREATE OR REPLACE FUNCTION returnTotalInvoices
    RETURN NUMBER
    IS
        myTotal NUMERIC;
    BEGIN
        SELECT SUM(total) INTO myTotal FROM invoice;
        RETURN myTotal;
    END;
    /
    /*--For testing purpose    
    select returnTotalInvoices() from dual;*/
    
    --(To be review) Create a function that returns the most expensive track
    CREATE OR REPLACE FUNCTION returnMostExpensiveTrack
    RETURN NUMBER
    IS
        myTotal NUMBER(8,2);
    BEGIN
        SELECT max(unitprice) INTO myTotal FROM track;
        RETURN myTotal;
    END;
    / 
    /*--For testing purpose  
    select returnMostExpensiveTrack() from dual;*/
    
 --3.3 User Defined Scalar Functions
    --Create a function that returns the average price of invoiceline items in the Invoiceline table
    CREATE OR REPLACE FUNCTION returnAverage
    RETURN NUMBER
    IS
        myAverage NUMBER(8,2);
    BEGIN
        SELECT avg(unitprice) INTO myAverage FROM invoiceline;
        RETURN myAverage;
    END;
    /* 
    --For testing purpose  
    select returnAverage() from dual;*/
    
--3.4 User Defined Table Valued Functions
    --Create a function that returns all the employees who are born in 1969
    CREATE OR REPLACE FUNCTION return1969(employeesList OUT SYS_REFCURSOR)
    RETURN SYS_REFCURSOR
    IS
        myAverage NUMBER(8,2);
    BEGIN
    --open employeesList FOR
    SELECT * into employeesList FROM employee where EXTRACT(YEAR FROM birthdate) = 1973;
    END;

--4.0 Stored procedures
 --4.1 Basic Stored Procedures
    --Create a stored procedure that selects the first and lastname of all the employees
CREATE OR REPLACE PROCEDURE getEmployeesName(employeesList OUT SYS_REFCURSOR)
IS
BEGIN
    open employeesList FOR
    SELECT EMPLOYEEID, LASTNAME, FIRSTNAME FROM employee where birthdate > TO_DATE('1968-12-31 23:59:59','yyyy-mm-dd hh24:mi:ss');
END;
/
DECLARE
    myIterator SYS_REFCURSOR;
    someID EMPLOYEE.EMPLOYEEID%TYPE;
    someLastname EMPLOYEE.LASTNAME%TYPE;
    someFirstname EMPLOYEE.FIRSTNAME%TYPE;
BEGIN
    getEmployeesName(myIterator); --Have our cursor represent the cursor for flash_cards
    
    LOOP --Begin loop block
        FETCH myIterator INTO someID,someLastname,someFirstname; --Grab the current its pointing at.
        EXIT WHEN myIterator%NOTFOUND; --%NOTFOUND does not exist until there are no records left
        DBMS_OUTPUT.PUT_LINE(someID || ' ' || someLastname || ' ' || someFirstname);
    END LOOP; --End of loop block
END;   

--4.2 Stored Procedure Input Parameters
    --Create a Stored Procedure that upsates the personal information of an employee
    CREATE OR REPLACE PROCEDURE updateEmployee(employeeId_in IN EMPLOYEE.EMPLOYEEID%TYPE, 
                    firstname_in IN EMPLOYEE.FIRSTNAME%TYPE, lastname_in IN EMPLOYEE.LASTNAME%TYPE)
    IS
    BEGIN
        UPDATE employee SET firstname = firstname_in, lastname = lastname_in 
        WHERE employeeId = employeeId_in;
        commit;      
    END;
    /
    /*For Test purpose
    call updateEmployee(9, 'TRAHAURAY', 'Mahammad');*/
    
    --Create a Stored Procedure that returns the managers of an employe
    CREATE OR REPLACE PROCEDURE findManagers(employeeId_in IN EMPLOYEE.EMPLOYEEID%TYPE, manager_out OUT SYS_REFCURSOR)
    IS
    BEGIN
        OPEN manager_out FOR
        SELECT employeeId, firstname, lastname FROM EMPLOYEE WHERE EMPLOYEEID IN (
        SELECT REPORTSTO FROM EMPLOYEE WHERE EMPLOYEEID = employeeID_in);
    END;
    /
    DECLARE
        myIterator SYS_REFCURSOR;
        someID EMPLOYEE.EMPLOYEEID%TYPE;
        someLastname EMPLOYEE.LASTNAME%TYPE;
        someFirstname EMPLOYEE.FIRSTNAME%TYPE;
    BEGIN
        findManagers(someID, myIterator); --Have our cursor represent the cursor for flash_cards
        
        LOOP --Begin loop block
            FETCH myIterator INTO someID, someLastname, someFirstname; --Grab the current its pointing at.
            EXIT WHEN myIterator%NOTFOUND; --%NOTFOUND does not exist until there are no records left
            DBMS_OUTPUT.PUT_LINE(someID || ' ' || someLastname || ' ' || someFirstname);
        END LOOP; --End of loop block
    END;   

--4.2 Stored Procedure Output Parameters
    --Create a Stored Procedure that returns the name and company of a customer
    CREATE OR REPLACE PROCEDURE identifyCustomer(customerId_in IN CUSTOMER.CUSTOMERID%TYPE, customer_out OUT SYS_REFCURSOR)
    IS
    BEGIN
        OPEN customer_out FOR
        SELECT customerId, firstname, lastname, company FROM customer WHERE customerid = customerid_in;
    END;
    /
    DECLARE
        myIterator SYS_REFCURSOR;
        someID CUSTOMER.CUSTOMERID%TYPE;
        someLastname CUSTOMER.LASTNAME%TYPE;
        someFirstname CUSTOMER.FIRSTNAME%TYPE;
        someCompany CUSTOMER.COMPANY%TYPE;
    BEGIN
        identifyCustomer(someID, myIterator); --Have our cursor represent the cursor for flash_cards
        
        LOOP --Begin loop block
            FETCH myIterator INTO someID, someLastname, someFirstname, someCompany; --Grab the current its pointing at.
            EXIT WHEN myIterator%NOTFOUND; --%NOTFOUND does not exist until there are no records left
            DBMS_OUTPUT.PUT_LINE(someID || ' ' || someLastname || ' ' || someFirstname || ' ' || someCompany);
        END LOOP; --End of loop block
    END;   
/
--5.0 Transactions
    --Create a transaction that given a invoiceid will delete this invoice
    CREATE OR REPLACE PROCEDURE transactionHelper(invoiceId_IN IN INVOICE.INVOICEID%TYPE)
    IS
    BEGIN
        DELETE FROM InvoiceLine WHERE invoiceId = invoiceId_IN;
        
        DELETE FROM Invoice WHERE invoiceId = invoiceId_IN;
        
        COMMIT;
    END;
    
    --Create a transaction nested within a procedure that insert new record in the customer table
    CREATE OR REPLACE PROCEDURE newCustomer(customerid_in IN CUSTOMER.CUSTOMERID%TYPE, 
                    firstname_in IN CUSTOMER.FIRSTNAME%TYPE, lastname_in IN CUSTOMER.LASTNAME%TYPE, email_in IN CUSTOMER.EMAIL%TYPE)
    IS
    BEGIN
        INSERT INTO CUSTOMER(customerid, firstname, lastname, email) VALUES(
        customerid_in, firstname_in, lastname_in, email_in);
         
        COMMIT;
    END;
    /
--6.0 Triggers
--6.1 AFTER/FOR
    --Create an after insert trigger on the employee table fired after a new record is inserted into the table
    CREATE OR REPLACE TRIGGER employeeInsert_trigger
    AFTER INSERT ON employee
    FOR EACH ROW
    BEGIN  
        DBMS_OUTPUT.PUT_LINE('Fired after new insertion');
    END; 
    --Create an after update trigger on the album table that fires after a row is inserted in the table
    CREATE OR REPLACE TRIGGER albumUpdate_trigger
    AFTER UPDATE ON album
    FOR EACH ROW
    BEGIN  
        DBMS_OUTPUT.PUT_LINE('Fired after update');
    END; 
    --Create an after delete trigger on the customer table that fires after a row is deleted from the table
    CREATE OR REPLACE TRIGGER customerDelete_trigger
    AFTER DELETE ON customer
    FOR EACH ROW
    BEGIN  
        DBMS_OUTPUT.PUT_LINE('Fired after new insertion');
    END; 

--7.0 JOINS
--7.1 INNER
    --Create an inner join that joins customers and orders and specifies the name of the customer and the invoiced
    SELECT firstname, lastname, invoiceid FROM customer INNER JOIN invoice ON customer.customerid = invoice.customerid;
--7.2 OUTER
    --Create an outer join that joins customers and invoice table, specifying the customerid, firsname, lasname, invoiceid, and total.
    SELECT customer.customerid, firstname, lastname, invoiceid, total FROM customer FULL OUTER JOIN invoice ON customer.customerid = invoice.customerid;
--7.3 RIGHT
    --Create A RIGHT join that joins album and artist specifying artist name and title
    SELECT name, title from album RIGHT JOIN artist on artist.artistid = album.artistid;
--7.4 CROSS
    --Create an cross join that joins album and artist and sorts by artist name in ascending order
    SELECT * from album CROSS JOIN artist ORDER BY artist.name ASC;
--7.5 SELF
    --Perform a self-join on the employee table, joining on the reportsto column
    SELECT * FROM employee A INNER JOIN employee B ON A.reportsto = B.reportsto;
--9.0 Administration
    --Create a .bak file for the chinook database
    