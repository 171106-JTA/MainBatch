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
--Return average total of all invoices
CREATE OR REPLACE FUNCTION get_avg_invc
RETURN NUMBER
IS
    avg_invc NUMBER;
BEGIN
    SELECT avg(TOTAL) INTO avg_invc FROM INVOICE;
END;
/

--Return most expensive track
CREATE OR REPLACE FUNCTION get_exp_track
RETURN CURSOR;
DECLARE
    max_price NUMBER;
    CURSOR track_cursor IS SELECT * FROM TRACK;
BEGIN
    max_price := max(UNITPRICE);
    OPEN track_cursor; 
        LOOP 
        FETCH c_customers into c_id, c_name, c_addr; 
        EXIT WHEN track_cursor%notfound;
            IF 
        END LOOP; 
        CLOSE c_customers; 
    SELECT * FROM TRACK WHERE UNITPRICE = max(UNITPRICE) INTO exp_track FROM TRACK;
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
CREATE OR REPLACE PROCEDURE get_all_emp(cursorPARAM OUT SYS_REFCURSOR)
IS
BEGIN
    open cursorPARAM FOR
    SELECT FIRSTNAME, LASTNAME, BIRTHDAY FROM EMPLOYEE;
END;
/

--Create function to return all employees after 1968
CREATE OR REPLACE FUNCTION emp_bday
RETURN SYS_REFCURSOR;
DECLARE 
    emp_cursor SYS_REFCURSOR;
    emp_fname VARCHAR2;
    emp_lname VARCHAR2;
    emp_bday NUMBER;
IS
BEGIN
    get_all_emp(emp_cursor);
    LOOP
        FETCH emp_cursor INTO emp_bday where EXTRACT(YEAR, BIRTHDATE) > 1968;
        EXIT WHEN emp_cursor%NOTFOUND;
    END LOOP;
END;
/

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
CREATE OR REPLACE PROCEDURE get_managers(cust_id IN NUMBER, 
                                         cus_fname OUT NUMBER,
                                         cus_lname OUT NUMBER,
                                         cus_company OUT VARCHAR2)
IS
BEGIN
    SELECT FIRSTNAME INTO cus_fname FROM CUSTOMER WHERE CUSTOMERID = cust_id;
    SELECT LASTNAME INTO cus_lname FROM CUSTOMER WHERE CUSTOMERID = cust_id;
    SELECT COMPANY INTO cus_company FROM CUSTOMER WHERE CUSTOMERID = cust_id;
END;
/

--------------------------------------------------------------------------------
-------------------------------------5.0----------------------------------------
--a procedure holding a transaction to delete an invoice
CREATE OR REPLACE PROCEDURE delete_invoice(invoice_id IN NUMBER)
IS
BEGIN
    DELETE FROM INVOICELINE WHERE INVOICEID = invoice_id;
    DELETE FROM INVOICE WHERE INVOICEID = invoice_id;
END;
/

--A transaction for inserting a new record into customer table
CREATE OR REPLACE PROCEDURE inserts_record(customer_id IN NUMBER,
                                           cus_firstname IN VARCHAR2,
                                           cus_lastname IN  VARCHAR2,
                                           cus_company IN VARCHAR2,
                                           cus_address IN VARCHAR2,
                                           cus_city IN VARCHAR2,
                                           cus_state IN VARCHAR2,
                                           cus_country IN VARCHAR2,
                                           cus_postalcode IN VARCHAR2,
                                           cus_phone IN VARCHAR2,
                                           cus_fax IN VARCHAR2,
                                           cus_email IN VARCHAR2,
                                           cus_repid IN NUMBER)
IS
BEGIN
    INSERT INTO CUSTOMER (CUSTOMERID, FIRSTNAME, LASTNAME, COMPANY,
                          ADDRESS, CITY, STATE, COUNTRY, POSTALCODE,
                          PHONE, FAX, EMAIL, SUPPORTREPID)
    VALUES(customer_id, cus_firstname, cus_lastname, cus_company, cus_address,
           cus_city, cus_state, cus_country, cus_postalcode, cus_phone,
           cus_fax, cus_email, cus_repid);
END;
/
--------------------------------------------------------------------------------
-------------------------------------6.1----------------------------------------
CREATE OR REPLACE TRIGGER after_insert_employee
AFTER INSERT ON EMPLOYEE
FOR EACH ROW
BEGIN
  IF :NEW.EMPLOYEEID IS NULL THEN 
    DBMS_OUTPUT.PUT_LINE('Trigger fired for inserting a new employee');
    END IF;
END;
/

CREATE OR REPLACE TRIGGER after_insert_album
AFTER UPDATE ON ALBUM
FOR EACH ROW
BEGIN
    IF :NEW.ALBUMID IS NULL THEN 
    DBMS_OUTPUT.PUT_LINE('Trigger fired for updating album');
    END IF;
END;
/

CREATE OR REPLACE TRIGGER after_delete_customer
AFTER DELETE ON CUSTOMER
FOR EACH ROW
BEGIN
    IF :NEW.CUSTOMERID IS NULL THEN 
    DBMS_OUTPUT.PUT_LINE('Trigger fired for deleting a customer');
    END IF;
END;
/

--------------------------------------------------------------------------------
-------------------------------------7.1----------------------------------------
--Inner join
SELECT FIRSTNAME, LASTNAME, INVOICEID FROM CUSTOMER A
INNER JOIN INVOICE B
ON A.CUSTOMERID = B.INVOICEID;

--------------------------------------------------------------------------------
-------------------------------------7.2----------------------------------------
--Outer join
SELECT A.CUSTOMERID, FIRSTNAME, LASTNAME, INVOICEID, TOTAL FROM CUSTOMER A
FULL OUTER JOIN INVOICE B
ON A.CUSTOMERID = B.CUSTOMERID;
--------------------------------------------------------------------------------
-------------------------------------7.3----------------------------------------
--Right Join
SELECT NAME, TITLE FROM ALBUM A
RIGHT JOIN ARTIST B
ON A.ARTISTID = B.ARTISTID;
--------------------------------------------------------------------------------
-------------------------------------7.4----------------------------------------
--Cross join
SELECT * FROM ARTIST
CROSS JOIN ALBUM
ORDER BY NAME ASC;
--------------------------------------------------------------------------------
-------------------------------------7.5----------------------------------------
--Inner join
SELECT * FROM EMPLOYEE A
INNER JOIN EMPLOYEE B
ON A.EMPLOYEEID = B.REPORTSTO;

--------------------------------------------------------------------------------
-------------------------------------9.0----------------------------------------
--Create backups
--Back up file created at:
--C:\ORACLEXE\APP\ORACLE\FAST_RECOVERY_AREA\XE\AUTOBACKUP\2017_11_17\


commit;