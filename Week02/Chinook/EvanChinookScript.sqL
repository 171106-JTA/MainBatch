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

/************************************
2.2 ORDER BY
************************************/
SELECT * FROM ALBUM 
    ORDER BY TITLE DESC;

SELECT FIRSTNAME FROM CUSTOMER
    ORDER BY CITY ASC; 

/************************************
2.4 UPDATE
************************************/
UPDATE CUSTOMER
    SET FIRSTNAME='ROBERT', LASTNAME='WALTER'
    WHERE UPPER(FIRSTNAME)='AARON' AND UPPER(LASTNAME)='MITCHELL'; 

--UPDATE RECORD AS SPECIFIED
UPDATE ARTIST
    SET NAME='CCR'
    WHERE UPPER(NAME)='CREEDENCE CLEARWATER REVIVAL';

/************************************
2.5 LIKE
************************************/
SELECT * FROM INVOICE
    WHERE UPPER(BILLINGADDRESS) LIKE 'T%';
    
/************************************
2.6 BETWEEN
************************************/
SELECT * FROM INVOICE
    WHERE TOTAL BETWEEN 15 AND 50;
    
SELECT * FROM EMPLOYEE
    WHERE HIREDATE BETWEEN TO_DATE('1-JUN-2003') AND TO_DATE('1-MAR-2004');
    
/************************************
2.7 DELETE
************************************/
--Disable constraint. Research why this is bad!
--Delete all cascading contraint stuff
ALTER TABLE INVOICE
    DISABLE CONSTRAINT FK_INVOICECUSTOMERID;
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


SELECT * FROM MEDIATYPE;
-------TASK 3.1.2-------
--CREATE OR REPLACE len_of_mediatype(media_id IN NUMBER)
--RETURN NUMBER
--IS
--    the_length NUMBER;
--BEGIN
--    SELECT LENGTH(NAME) INTO the_length FROM MEDIATYPE
--        WHERE MEDIATYPEID=media_id;
--    RETURN the_length;
--END;

DECLARE
    media_id NUMBER(4);
    the_length NUMBER(4);
BEGIN
    media_id := 1;
    SELECT LENGTH(NAME) INTO the_length FROM MEDIATYPE 
        WHERE MEDIATYPEID=media_id;
    DBMS_OUTPUT.PUT_LINE(the_length);
END;

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
--How is this question asking us for a scalar function????
--This appears to be another aggregate function questions
************************************/
--SELECT * FROM EMPLOYEE;
--
--CREATE OR REPLACE TYPE MY_EMP AS OBJECT (
--    EMPLOYEEID NUMBER,
--    LASTNAME VARCHAR2(100),
--    FIRSTNAME VARCHAR2(100), 
--    TITLE VARCHAR2(100)
--    --EXTEND THIS
--);
--
--CREATE OR REPLACE TYPE MY_EMP_TABLE AS TABLE OF MY_EMP;
--
--CREATE OR REPLACE FUNCTION GET_EMP(MIN_BIRTH_DATE IN DATE)
----CREATE OR REPLACE FUNCTION GET_EMP()
--RETURN MY_EMP_TABLE
--IS
--    CUST_EMP_TABLE MY_EMP_TABLE;
--BEGIN
--    CUST_EMP_TABLE := MY_EMP_TABLE();
--    CUST_EMP_TABLE.EXTEND;
--    CUST_EMP_TABLE(CUST_EMP_TABLE.COUNT) := MY_EMP(1, 'WEST', 'EVAN', 'MR.');
--    DBMS_OUTPUT.PUT_LINE(MIN_BIRTH_DATE);
--    RETURN CUST_EMP_TABLE;
--END;
--
----Test GET_EMP()
--SELECT * FROM TABLE(GET_EMP(TO_DATE('1968-12-31', 'YYYY-MM-DD')));
--
--DECLARE
--    CUST_DATE DATE;
--    TEMP VARCHAR2(100);
--BEGIN
--    CUST_DATE := TO_DATE('1968-12-31', 'YYYY-MM-DD');
--    DBMS_OUTPUT.PUT_LINE(CUST_DATE);
--    GET_EMP(CUST_DATE);
--END;