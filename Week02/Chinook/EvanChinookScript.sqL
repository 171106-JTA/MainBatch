/************************************************************************
2.0 SQL QUERIES
************************************************************************/

/************************************
2.1 SELECT
************************************/
SELECT * FROM EMPLOYEE;

SELECT * FROM EMPLOYEE
    WHERE UPPER(LASTNAME) = 'KING';

--Why does IS work when = failed. What's the difference?
SELECT * FROM EMPLOYEE E
    WHERE UPPER(E.FIRSTNAME) = 'ANDREW' AND E.REPORTSTO IS NULL;
/
/************************************
2.2 ORDER BY
************************************/
SELECT * FROM ALBUM 
    ORDER BY TITLE DESC;

SELECT FIRSTNAME FROM CUSTOMER
    ORDER BY CITY ASC; 
/
/************************************
2.4 UPDATE
************************************/
UPDATE CUSTOMER
    SET FIRSTNAME='ROBERT', LASTNAME='WALTER'
    WHERE UPPER(FIRSTNAME)='AARON' AND UPPER(LASTNAME)='MITCHELL'; 
/
--UPDATE RECORD AS SPECIFIED
UPDATE ARTIST
    SET NAME='CCR'
    WHERE UPPER(NAME)='CREEDENCE CLEARWATER REVIVAL';
/
/************************************
2.5 LIKE
************************************/
SELECT * FROM INVOICE
    WHERE UPPER(BILLINGADDRESS) LIKE 'T%';
/ 
/************************************
2.6 BETWEEN
************************************/
SELECT * FROM INVOICE
    WHERE TOTAL BETWEEN 15 AND 50;
    
SELECT * FROM EMPLOYEE
    WHERE HIREDATE BETWEEN TO_DATE('1-JUN-2003') AND TO_DATE('1-MAR-2004');
/
/************************************
2.7 DELETE
************************************/
--Disable constraint. Research why this is bad!
--Delete all cascading contraint stuff
--ALTER TABLE INVOICE
--    DISABLE CONSTRAINT FK_INVOICECUSTOMERID;

DELETE FROM INVOICELINE
    WHERE INVOICEID IN (SELECT INVOICEID FROM INVOICE
            WHERE CUSTOMERID = (SELECT CUSTOMERID FROM CUSTOMER 
                    WHERE UPPER(FIRSTNAME)='ROBERT' AND UPPER(LASTNAME)='WALTER'));
DELETE FROM INVOICE
    WHERE CUSTOMERID = (SELECT CUSTOMERID FROM CUSTOMER 
                    WHERE UPPER(FIRSTNAME)='ROBERT' AND UPPER(LASTNAME)='WALTER');
DELETE FROM CUSTOMER 
    WHERE UPPER(FIRSTNAME)='ROBERT' AND UPPER(LASTNAME)='WALTER';
/

/************************************************************************
3.0 SQL FUNCTIONS
************************************************************************/
/************************************
3.1 SYSETM DEFINED FUNCTIONS
************************************/
-------TASK 3.1.1------
CREATE OR REPLACE FUNCTION CUR_TIME
RETURN VARCHAR2
IS
    THE_TIME VARCHAR2(50);
BEGIN 
    THE_TIME := TO_CHAR (SYSDATE, 'HH24:MI:SS');
    RETURN THE_TIME;    
END;
/
--Test the CUR_TIME function
BEGIN 
    DBMS_OUTPUT.PUT_LINE(CUR_TIME());
END;
/
-------TASK 3.1.2-------
CREATE OR REPLACE FUNCTION len_of_mediatype(media_id IN NUMBER)
RETURN NUMBER
IS
    the_length NUMBER;
BEGIN
    SELECT LENGTH(NAME) INTO the_length FROM MEDIATYPE
        WHERE MEDIATYPEID=media_id;
    RETURN the_length;
END;
/
--Test len_of_mediatype()
DECLARE
    media_id NUMBER(4);
    the_length NUMBER(4);
BEGIN
    media_id := 3;
    the_length := len_of_mediatype(media_id);
    DBMS_OUTPUT.PUT_LINE(the_length);
END;
/
/************************************
3.2 SYSETM DEFINED AGGREGATE FUNCTIONS
************************************/
-------TASK 3.2.1-------
CREATE OR REPLACE FUNCTION GET_AVG_INVOICE_TOTAL
RETURN NUMBER
IS
    INVOICE_TOTAL NUMBER;
BEGIN
    SELECT AVG(TOTAL) INTO INVOICE_TOTAL FROM INVOICE;
    RETURN INVOICE_TOTAL;
END;
/
--TEST GET_AVG_INVOICE_TOTAL()
DECLARE
    INVOICE_TOTAL NUMBER;
BEGIN
    INVOICE_TOTAL := GET_AVG_INVOICE_TOTAL();
    DBMS_OUTPUT.PUT_LINE(INVOICE_TOTAL);
END;
/

-------TASK 3.2.2-------
CREATE OR REPLACE FUNCTION GET_MOST_EXPENSIVE_TRACK
RETURN NUMBER
IS
    MOST_EXPENSIVE_TRACK NUMBER;
BEGIN
    SELECT MAX(UNITPRICE) INTO MOST_EXPENSIVE_TRACK FROM TRACK;
    RETURN MOST_EXPENSIVE_TRACK;
END;
/
--TEST GET_MOST_EXPENSIVE_TRACK()
DECLARE
    MOST_EXPEN_TRACK NUMBER;
BEGIN
    MOST_EXPEN_TRACK := GET_MOST_EXPENSIVE_TRACK();
    DBMS_OUTPUT.PUT_LINE(MOST_EXPEN_TRACK);
END;
/

/************************************
3.3 USER DEFINED SCALAR FUNCTIONS
--How is this question asking us for a scalar function????
--This appears to be another aggregate function questions
************************************/
CREATE OR REPLACE FUNCTION GET_AVG_PRICE
RETURN NUMBER
IS
    AVG_PRICE NUMBER;
BEGIN
    SELECT AVG(UNITPRICE) INTO AVG_PRICE FROM INVOICELINE;
    RETURN AVG_PRICE;
END;
/
--TEST GET_MOST_EXPENSIVE_TRACK()
DECLARE
    AVG_PRICE NUMBER;
BEGIN
    AVG_PRICE := GET_AVG_PRICE();
    DBMS_OUTPUT.PUT_LINE(AVG_PRICE);
END;
/
/************************************
3.4 USER DEFINED TABLE VALUED FUNCTIONS
************************************/
CREATE OR REPLACE FUNCTION GET_EMP
RETURN SYS_REFCURSOR
IS
    EMP_REF SYS_REFCURSOR;
BEGIN
    OPEN EMP_REF FOR 
        SELECT * FROM EMPLOYEE
        WHERE BIRTHDATE > TO_DATE('31-12-68', 'DD-MM-YY');
    RETURN EMP_REF;
END;
/

--DECLARE
--    EMP_CURSOR SYS_REFCURSOR;
--    SOME_EMP_ID EMPLOYEE.EMPLOYEEID%TYPE;
--    SOME_EMP_FIRSTNAME EMPLOYEE.FIRSTNAME%TYPE;
--    SOME_EMP_LASTNAME EMPLOYEE.LASTNAME%TYPE;
--    SOME_EMP_BIRTHDATE EMPLOYEE.BIRTHDATE%TYPE;
--BEGIN
--    EMP_CURSOR := GET_EMP();
--    DBMS_OUTPUT.PUT_LINE('HELLO THERE');
--    LOOP
--        FETCH EMP_CURSOR INTO SOME_EMP_ID, SOME_EMP_FIRSTNAME, SOME_EMP_LASTNAME, SOME_EMP_BIRTHDATE; --Grab the current recods its pointing at
--        EXIT WHEN EMP_CURSOR%NOTFOUND; --%NOTFOUND does not exist until therer are no records left. 
--        
--        DBMS_OUTPUT.PUT_LINE(SOME_EMP_ID);
----        DBMS_OUTPUT.PUT_LINE(SOME_EMP_ID || ' ' || SOME_EMP_FIRSTNAME || ' ' || SOME_EMP_LASTNAME ' ' || SOME_EMP_BIRTHDATE);
--    END LOOP;
--END;


--Test query for comparing dates
--SELECT * FROM EMPLOYEE 
--    WHERE BIRTHDATE > TO_DATE('1968-12-31', 'YYYY-MM-DD');

/************************************************************************
4.0 Stored Procedures
************************************************************************/
/************************************
4.1 BASIC STORED PROCEDURE
************************************/
CREATE OR REPLACE PROCEDURE GET_EMP_NAMES(EMP_CURSOR OUT SYS_REFCURSOR)
IS
BEGIN
    OPEN EMP_CURSOR FOR 
        SELECT FIRSTNAME, LASTNAME FROM EMPLOYEE;
END;
/
--Test GET_EMP_NAMES() 
DECLARE
    EMP_NAME_CURSOR SYS_REFCURSOR;
    SOME_FNAME EMPLOYEE.FIRSTNAME%TYPE;
    SOME_LNAME EMPLOYEE.LASTNAME%TYPE;
BEGIN
    GET_EMP_NAMES(EMP_NAME_CURSOR);
    DBMS_OUTPUT.PUT_LINE('HELLO THERE');
    LOOP
        FETCH EMP_NAME_CURSOR INTO SOME_FNAME, SOME_LNAME; --Grab the current recods its pointing at
        EXIT WHEN EMP_NAME_CURSOR%NOTFOUND; --%NOTFOUND does not exist until therer are no records left. 
        
        DBMS_OUTPUT.PUT_LINE(SOME_FNAME || ' ' || SOME_LNAME);
--        DBMS_OUTPUT.PUT_LINE(SOME_EMP_ID || ' ' || SOME_EMP_FIRSTNAME || ' ' || SOME_EMP_LASTNAME ' ' || SOME_EMP_BIRTHDATE);
    END LOOP;
END;

SELECT * FROM EMPLOYEE;

/************************************
4.2 Stored Procedure Input Parameters
************************************/
CREATE OR REPLACE PROCEDURE alter_employee(
    Emp_ID  IN EMPLOYEE.EmployeeId%TYPE,
    LName   IN EMPLOYEE.LastName%TYPE, 
    FName   IN EMPLOYEE.FirstName%TYPE,
    Tit     IN EMPLOYEE.Title%TYPE, 
    BD      IN EMPLOYEE.BirthDate%TYPE,
    HD      IN EMPLOYEE.HireDate%TYPE,
    Add     IN EMPLOYEE.Address%TYPE,
    Cit     IN EMPLOYEE.City%TYPE,
    St      IN EMPLOYEE.State%TYPE,
    Ctry    IN EMPLOYEE.Country%TYPE,
    PC      IN EMPLOYEE.PostalCode%TYPE,
    PH      IN EMPLOYEE.Phone%TYPE,
    FX      IN EMPLOYEE.Fax%TYPE,
    EML     IN EMPLOYEE.Email%TYPE)
IS
BEGIN
    UPDATE EMPLOYEE
    SET LastName = LName, FirstName = FName, Title = Tit, BirthDate = BD, HireDate = HD, Address = Add, City = Cit, 
        State = St, Country = Ctry, PostalCode = PC, Phone = PH, Fax = FX, Email = EML
    WHERE EMPLOYEEID = Emp_ID; 
END;
/

CREATE OR REPLACE PROCEDURE GET_EMP_MANAGERS(EMP_ID IN NUMBER, EMP_MANAGER_CURSOR OUT SYS_REFCURSOR)
IS
BEGIN
    OPEN EMP_MANAGER_CURSOR FOR 
        SELECT * FROM EMPLOYEE
        WHERE EMPLOYEEID IN (SELECT ReportsTo FROM EMPLOYEE 
                            WHERE EMPLOYEEID = EMP_ID);
END;
/

/************************************
4.3 Stored Procedure Output Parameters
************************************/
CREATE OR REPLACE PROCEDURE GET_EMP_MANAGERS(CUST_ID IN NUMBER, CUST_NAME OUT VARCHAR2, COMPANY OUT VARCHAR2)
IS
    TEMP_FIRST VARCHAR2(100);
    TEMP_LAST VARCHAR2(100);
BEGIN
    SELECT FirstName, LastName, Company INTO TEMP_FIRST, TEMP_LAST, COMPANY FROM CUSTOMER
    WHERE CustomerId = CUST_ID;
    CUST_NAME := TEMP_FIRST || ' ' || TEMP_LAST;
END;
/

/************************************************************************
5.0 Transactions
************************************************************************/
CREATE OR REPLACE PROCEDURE Remove_Invoice(Inv_ID IN NUMBER)
IS
BEGIN
    delete from InvoiceLine where INVOICEID = Inv_ID;
    delete from Invoice where INVOICEID = Inv_ID;
    COMMIT;
END;

CREATE OR REPLACE PROCEDURE create_customer(
    Cust_ID  IN CUSTOMER.CustomerId%TYPE,
    LName   IN CUSTOMER.LastName%TYPE, 
    FName   IN CUSTOMER.FirstName%TYPE,
    Comp     IN CUSTOMER.Company%TYPE, 
    Add     IN CUSTOMER.Address%TYPE,
    Cit     IN CUSTOMER.City%TYPE,
    St      IN CUSTOMER.State%TYPE,
    Ctry    IN CUSTOMER.Country%TYPE,
    PC      IN CUSTOMER.PostalCode%TYPE,
    PH      IN CUSTOMER.Phone%TYPE,
    FX      IN CUSTOMER.Fax%TYPE,
    EML     IN CUSTOMER.Email%TYPE)
IS
BEGIN
    INSERT INTO CUSTOMER (CustomerId, LastName, FirstName, Company, Address, 
    City, State, Country, PostalCode, Phone, Fax, Email)
    VALUES(Cust_ID, LName, FName, Comp, Add, Cit, 
         St, Ctry, PC, PH, FX, EML);
END;

/************************************************************************
6.0 Triggers
************************************************************************/
