/*
 * SQL Lab
 * Alex Peterson
 */
  
--------------------------------------------------------------------------------
-------------------------------------2.1----------------------------------------
SELECT * FROM EMPLOYEE;

SELECT * FROM EMPLOYEE
WHERE LASTNAME = 'King';

SELECT * FROM EMPLOYEE
WHERE FIRSTNAME = 'Andrew' AND
      REPORTSTO = NULL;
          
--------------------------------------------------------------------------------       
------------------------------------2.2-----------------------------------------
SELECT * FROM ALBUM
ORDER BY TITLE DESC;

SELECT FIRSTNAME FROM CUSTOMER
ORDER BY CITY ASC;

--------------------------------------------------------------------------------
---------------------------------------2.3--------------------------------------
INSERT INTO GENRE
VALUES(26, 'Bobbert music');

INSERT INTO GENRE
VALUES(27, 'Bobbert movies');

INSERT INTO EMPLOYEE
VALUES(9, 'Bob', 'Bobbert', 'Electromagician', 9, TO_DATE('04-AUG-1992', 'DD/MM/YYYY'), 
       TO_DATE('18-JUN-2015', 'DD/MM/YYYY'), '1234 Apple Lane', 'Reston', 'VA', 'USA', '29001',
       '123-456-7890', '1-190-1900', 'bobbobbert@bobbert.com');
INSERT INTO EMPLOYEE
VALUES(9, 'Bob', 'Bobbert', 'Electromagician', 9, TO_DATE('04-AUG-1992', 'DD/MM/YYYY'), 
       TO_DATE('18-JUN-2015', 'DD/MM/YYYY'), '1234 Apple Lane', 'Reston', 'VA', 'USA', '29001',
       '123-456-7890', '1-190-1900', 'bobbobbert@bobbert.com');

INSERT INTO EMPLOYEE
VALUES(10, 'alex', 'peterson', 'Electromagician apprentice', 9, TO_DATE('05-may-1993', 'DD/MM/YYYY'), 
       TO_DATE('18-JUN-2017', 'DD/MM/YYYY'), '5678 Apple Lane', 'Reston', 'VA', 'USA', '29001',
       '123-456-6478', '1-190-1901', 'amp1993@gmail.com');

SELECT * FROM customer;
INSERT INTO CUSTOMER
VALUES(60, 'Bobbert', 'Bobberto', 'Revature', '1235 apple lane', 'Reston', 'VA', 'USA', '91413', '12411515', '124124124', 'bobbert@email.com', 3);

INSERT INTO CUSTOMER
VALUES(61, 'fred', 'frederto', null, '1235 apple lane', 'Reston', 'VA', 'USA', '1677', '41', '1415', 'fred@email.com', 4);

--------------------------------------------------------------------------------
-------------------------------------2.4----------------------------------------
UPDATE CUSTOMER
SET FIRSTNAME = 'Robert', LASTNAME = 'Walter'
WHERE FIRSTNAME = 'Aaron' AND LASTNAME = 'Mitchell';

UPDATE ARTIST
SET NAME = 'CCR'
WHERE NAME = 'Creedence Clearwater Revival';

--------------------------------------------------------------------------------
--------------------------------------2.5---------------------------------------
SELECT * FROM INVOICE
WHERE BILLINGADDRESS LIKE 'T%';

--------------------------------------------------------------------------------
--------------------------------------2.6---------------------------------------
--Fetch all invoice totals between 15 and 50
SELECT * FROM INVOICE
WHERE TOTAL BETWEEN 15 AND 50;

--Fetch all employees who were hired between said dates
SELECT * FROM EMPLOYEE
WHERE HIREDATE BETWEEN '01-JUN-03' AND '01-MAR-04';

--------------------------------------------------------------------------------
-------------------------------------2.7----------------------------------------            
--delete robert's invoicelines
DELETE FROM INVOICELINE 
WHERE INVOICEID = (SELECT INVOICEID FROM (SELECT CUSTOMERID FROM CUSTOMER WHERE FIRSTNAME = 'Robert' 
                    AND LASTNAME = 'Walter'));
               
--delete robert's invoices                    
DELETE FROM INVOICE WHERE CUSTOMERID = (SELECT CUSTOMERID FROM CUSTOMER WHERE FIRSTNAME = 'Robert' 
                    AND LASTNAME = 'Walter');

--delete robert
DELETE FROM CUSTOMER WHERE FIRSTNAME = 'Robert' 
                    AND LASTNAME = 'Walter';
--------------------------------------------------------------------------------
-------------------------------------3.1----------------------------------------
CREATE OR REPLACE FUNCTION get_time
RETURN DATE
IS
    cur_time DATE;
BEGIN
    SELECT TO_CHAR(SYSDATE, 'MM-DD-YYYY HH24:MI:SS') INTO cur_time FROM dual;
    RETURN cur_time;
END;
/

--This function returns length of a media type
--This means string length of media type's name?
CREATE OR REPLACE FUNCTION get_length(med_id IN VARCHAR2)
RETURN NUMBER
IS
    med_length NUMBER;
BEGIN
    SELECT LENGTH(med_id) INTO med_length FROM MEDIATYPE;
END;
/
--------------------------------------------------------------------------------
-------------------------------------3.2----------------------------------------
CREATE OR REPLACE FUNCTION get_avg_invc
RETURN NUMBER
IS
    avg_invc NUMBER;
BEGIN
    SELECT avg(TOTAL) INTO avg_invc FROM INVOICE;
END;
/

CREATE OR REPLACE FUNCTION get_exp_track
RETURN NUMBER
IS
    exp_track NUMBER;
BEGIN
    SELECT max(UNITPRICE) INTO exp_track FROM TRACK;
END;
/

--------------------------------------------------------------------------------
-------------------------------------3.3----------------------------------------
CREATE OR REPLACE FUNCTION get_avg_invprice
RETURN NUMBER
IS
    avg_invprice NUMBER;
BEGIN
    SELECT avg(UNITPRICE) INTO avg_invprice FROM INVOICELINE;
END;
/

--------------------------------------------------------------------------------
-------------------------------------3.4----------------------------------------
/*
CREATE TYPE emp_typ
    IS TABLE OF NUMBER;

DECLARE
    emp_year NUMBER;
    
    FUNCTION get_emps_bdays
    RETURN emp_typ
    IS
         emps_bdays emp_typ
    BEGIN
        IF EXTRACT(YEAR FROM BIRTHDATE) > 1968 INTO emps_bdays from EMPLOYEE;
        END IF;
*/
--------------------------------------------------------------------------------
-------------------------------------4.1----------------------------------------
CREATE OR REPLACE PROCEDURE get_names(cursorPARAM OUT SYS_REFCURSOR)
IS
BEGIN
    open cursorPARAM FOR
    SELECT FIRSTNAME, LASTNAME FROM EMPLOYEE;
END;
/


--------------------------------------------------------------------------------
-------------------------------------4.2----------------------------------------
--Update personal info of an employee
CREATE OR REPLACE PROCEDURE get_names(emp_city IN VARCHAR2,
                                      emp_birthday IN VARCHAR2,
                                      emp_state IN VARCHAR2,
                                      emp_phone IN VARCHAR2,
                                      emp_email IN VARCHAR2,
                                      emp_id IN NUMBER)
IS
BEGIN
    UPDATE EMPLOYEE SET CITY = emp_city WHERE emp_id = EMPLOYEEID;
    UPDATE EMPLOYEE SET STATE = emp_state WHERE emp_id = EMPLOYEEID;
    UPDATE EMPLOYEE SET BIRTHDATE = emp_birthday WHERE emp_id = EMPLOYEEID;
    UPDATE EMPLOYEE SET PHONE = emp_phone WHERE emp_id = EMPLOYEEID;
    UPDATE EMPLOYEE SET EMAIL = emp_email WHERE emp_id = EMPLOYEEID;
END;
/

--Return managers of an employee
CREATE OR REPLACE PROCEDURE get_managers(emp_id IN NUMBER, manager_id OUT NUMBER)
IS
BEGIN
    SELECT MGR INTO manager_id FROM EMP WHERE EMPNO = emp_id;
END;
/

--------------------------------------------------------------------------------
-------------------------------------4.3----------------------------------------
--Return name and company of a customer
CREATE OR REPLACE PROCEDURE get_managers(cust_id IN NUMBER, cus_fname OUT NUMBER,
                                         cus_lname OUT NUMBER,cus_company OUT VARCHAR2)
IS
BEGIN
    SELECT FIRSTNAME INTO cus_fname FROM CUSTOMER WHERE CUSTOMERID = cust_id;
    SELECT LASTNAME INTO cus_lname FROM CUSTOMER WHERE CUSTOMERID = cust_id;
    SELECT COMPANY INTO cus_company FROM CUSTOMER WHERE CUSTOMERID = cust_id;
END;
/

--------------------------------------------------------------------------------
-------------------------------------5.0----------------------------------------

commit;