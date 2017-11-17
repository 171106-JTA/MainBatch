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
CREATE OR REPLACE FUNCTION CUR_TMIE
RETURN TIME
IS
    THE_TIME TIME;
BEGIN 
    DBMS_OUTPUT.PUT_LINE(LOCALTIMESTAMP );
    THE_TIME := LOCALTIMESTAMP;
    RETURN THE_TIME;    
END;
/
