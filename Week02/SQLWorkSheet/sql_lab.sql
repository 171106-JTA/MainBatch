/* Problem 2.1 */ 
SELECT * FROM employee; 
SELECT * FROM employee 
WHERE lastname = 'King'; 
SELECT * FROM employee 
WHERE firstname = 'Andrew' AND reportsto = NULL; -- COME BACK TO


/* Problem 2.2 */ 
SELECT * FROM album
ORDER BY title DESC; 

SELECT firstname FROM customer
ORDER BY city ASC;


/* Problem 2.3 */
INSERT INTO genre 
VALUES(26, 'blues'); 

INSERT INTO employee
VALUES(9, 'caleb', 'schumake', 'software dev', 9, '10-oct-1994', '11-nov-17', '1234 davids', 'belleville', 'MI', 'US', '48164', '13134156798', '89', 'yo@gmail.com'); 
INSERT INTO employee
VALUES(10, 'earl', 'schumake', 'software dev', 9, '10-oct-1994', '11-nov-17', '1234 davids', 'belleville', 'MI', 'US', '48164', '13134156798', '89', 'yo@gmail.com'); 

INSERT INTO customer 
VALUES(70, 'caleb', 'schumake', 'revature', '12234 revature', 'revature', 'revature', 'revature', '48164', '+1 734 556-1234', '123', 'yoho@gmail.com', 9); 

INSERT INTO customer 
VALUES(71, 'earl', 'schumake', 'revature', '12234 revature', 'revature', 'revature', 'revature', '48164', '+1 734 556-1234', '123', 'yoho@gmail.com', 9);

/* Problem 2.4 */ 
UPDATE customer 
SET firstname = 'Robert', lastname ='Walter'
WHERE firstname = 'Aaron' AND lastname ='Mitchell'; 

UPDATE artist 
SET name='CCR'
WHERE NAME='Creedence Clearwater Revival'; 

/* Problem 2.5 */
SELECT * FROM invoice 
WHERE billingaddress LIKE 'T%'; 


/* Problem 2.6 */ 
SELECT * FROM invoice 
WHERE total BETWEEN 15 AND 20; 

/* Problem 2.7 */
ALTER TABLE invoice
DROP CONSTRAINT fk_invoicecustomerid;

ALTER TABLE invoice
ADD CONSTRAINT fk_invoicecustomerid
FOREIGN KEY (customerid) REFERENCES customer(customerid) ON DELETE CASCADE;

ALTER TABLE invoiceline
DROP CONSTRAINT fk_invoicelineinvoiceid;

ALTER TABLE invoiceline
ADD CONSTRAINT fk_invoicelineinvoiceid
FOREIGN KEY (invoiceid) REFERENCES invoice (invoiceid) ON DELETE CASCADE;

DELETE FROM customer
WHERE firstname='Robert' AND lastname='Walter';

/* Problem 3.1 */
CREATE OR REPLACE FUNCTION get_time RETURN TIMESTAMP
IS
BEGIN
    RETURN CURRENT_TIMESTAMP;
END;
/

CALL print_message(' ' || get_time());



CREATE OR REPLACE FUNCTION get_length (mediaTId IN mediatype.mediatypeid%TYPE)
RETURN NUMBER
IS
    media_name mediatype.name%TYPE;
BEGIN
    SELECT name INTO media_name FROM mediatype WHERE mediatypeid = mediaTid;
    RETURN LENGTH(media_name);
END;
/

CALL print_message(' ' || get_length(1));
/

/* Problem 3.2 */ 
CREATE OR REPLACE FUNCTION avg_total_invoice
RETURN NUMBER
IS
    avg_total NUMBER;
BEGIN
    SELECT AVG(TOTAL) INTO avg_total FROM Invoice;
    RETURN avg_total;
END;
/
DECLARE
    avg_total NUMBER;
BEGIN
    avg_total := avg_total_invoice();
    DBMS_OUTPUT.PUT_LINE(avg_total);
END;
/

CREATE OR REPLACE FUNCTION most_expensive
RETURN NUMBER
IS
    max_price NUMBER;
BEGIN
    SELECT MAX(UNITPRICE) INTO max_price FROM Track;
    RETURN max_price;
END;
/
DECLARE
    price NUMBER;
BEGIN
    price := most_expensive;
    DBMS_OUTPUT.PUT_LINE(price);
END;
/




/* Problem 3.3 */ 
CREATE OR REPLACE FUNCTION func_avg
RETURN NUMBER
IS
    quantity_sum NUMBER;
    price_sum NUMBER;
    cur_q NUMBER;
    cur_p NUMBER;
    CURSOR m_cursor IS
        SELECT quantity AS q, unitprice AS u FROM invoiceline;
BEGIN
    OPEN m_cursor;

    quantity_sum := 0;
    price_sum := 0;
    LOOP
        FETCH m_cursor INTO cur_q, cur_p;
        EXIT WHEN m_cursor%NOTFOUND;
        price_sum := price_sum + (cur_q * cur_p);
        quantity_sum := quantity_sum + cur_q;
    END LOOP;
    IF (quantity_sum <= 0) THEN
        RETURN 0;
    END IF;
    RETURN price_sum/quantity_sum;
END;
/
CALL print_message(' ' || func_avg);

/* Problem 3.4 */ 
CREATE OR REPLACE FUNCTION employees_born_after(input_birthdate IN DATE)     
    RETURN SYS_REFCURSOR
    IS
        track_cursor SYS_REFCURSOR;
    BEGIN
        OPEN track_cursor
            FOR
                SELECT EMPLOYEEID,FIRSTNAME,LASTNAME,BIRTHDATE FROM EMPLOYEE WHERE BIRTHDATE > input_birthdate;
        RETURN track_cursor;
        END;
/
DECLARE
    fc_cursor SYS_REFCURSOR := employees_born_after('1-JAN-68');
    eID EMPLOYEE.EMPLOYEEID%TYPE;
    eFIRSTNAME EMPLOYEE.FIRSTNAME%TYPE;
    eLASTNAME EMPLOYEE.LASTNAME%TYPE;
    eBIRTHDATE EMPLOYEE.BIRTHDATE%TYPE;
BEGIN
    LOOP
        FETCH fc_cursor INTO eID,eFIRSTNAME,eLASTNAME,eBIRTHDATE;
        EXIT WHEN fc_cursor%NOTFOUND; 
        DBMS_OUTPUT.PUT_LINE(eID || ' ' || eFIRSTNAME || ' ' || eLASTNAME || ' '|| eBIRTHDATE);
        END LOOP;
    END;  



/*Question 4.1*/
/
SET SERVEROUTPUT ON
CREATE OR REPLACE PROCEDURE GETNAME(NAMEROW OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN NAMEROW FOR
    SELECT FIRSTNAME, LASTNAME FROM EMPLOYEE;
END;
/

DECLARE
    FNAME VARCHAR2(20);
    LNAME VARCHAR2(20);
    NAMEROW SYS_REFCURSOR;
    
    BEGIN
        GETNAME(NAMEROW);
        
        LOOP
            FETCH NAMEROW INTO FNAME, LNAME;
        EXIT WHEN NAMEROW%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('First Name: ' || FNAME || ' Last Name: ' || LNAME);
        END LOOP;
        CLOSE NAMEROW;
    END;
/


/*Question 4.2*/
CREATE OR REPLACE PROCEDURE updateEmployee(
p_eid IN EMPLOYEE.EMPLOYEEID%TYPE,
p_pname IN EMPLOYEE.FIRSTNAME%TYPE)
IS
BEGIN
    UPDATE EMPLOYEE SET FIRSTNAME = p_pname WHERE EMPLOYEEID = p_eid;
    COMMIT;
END;
/
BEGIN
    updateEmployee(1, 'Works');
END;
/

CREATE OR REPLACE PROCEDURE MYMANAGERS (E_ID IN NUMBER,MANAGERS OUT SYS_REFCURSOR)
IS
BEGIN
  OPEN MANAGERS FOR 
  SELECT FIRSTNAME, LASTNAME FROM EMPLOYEE 
  WHERE EMPLOYEEID = (SELECT REPORTSTO FROM EMPLOYEE WHERE EMPLOYEEID=E_ID);
END;
/








/*Question 4.3*/
CREATE OR REPLACE PROCEDURE NAMECOMP (C_ID IN NUMBER, CUST_INFO OUT SYS_REFCURSOR)
IS
BEGIN
  OPEN CUST_INFO FOR 
  SELECT FIRSTNAME, LASTNAME, COMPANY FROM CUSTOMER 
  WHERE CUSTOMERID = C_ID;
END;
/








/* Question 5 */
CREATE OR REPLACE PROCEDURE DELINVOICE (I_ID IN NUMBER)
IS
    I_COUNT NUMBER;
BEGIN
    SAVEPOINT DEL_INVOICE;
    SELECT COUNT(INVOICEID) INTO I_COUNT FROM INVOICE WHERE INVOICEID=I_ID;
    IF(I_COUNT>0) THEN
        DELETE FROM INVOICELINE
        WHERE INVOICEID = I_ID;
        DELETE FROM INVOICE
        WHERE INVOICEID=I_ID;
    ELSE
        DBMS_OUTPUT.PUT_LINE('INVOICE ID NOT FOUND');
    END IF;
    COMMIT;
    EXCEPTION
    WHEN OTHERS THEN
  DBMS_OUTPUT.PUT_LINE('FAILED TO DELETE INVOICE');
  ROLLBACK TO DEL_INVOICE;
END;
/




/* Question 6 */
CREATE OR REPLACE TRIGGER INSERT_EMPLOYEE
AFTER INSERT ON EMPLOYEE
BEGIN
    DBMS_OUTPUT.PUT_LINE('INSERTED NEW EMPLOYEE RECORD');
END;
/
CREATE OR REPLACE TRIGGER UDATE_ALBUM
AFTER UPDATE ON ALBUM
BEGIN
    DBMS_OUTPUT.PUT_LINE('UPDATED ALBUM RECORD');
END;
/
CREATE OR REPLACE TRIGGER DELETE_CUST
AFTER DELETE ON CUSTOMER
BEGIN
    DBMS_OUTPUT.PUT_LINE('DELETED CUSTOMER RECORD');
END;
/





/* Question 7.1 */
SELECT CONCAT(C.FIRSTNAME, CONCAT(' ', C.LASTNAME)) AS CUSTOMER_NAME, I.INVOICEID
FROM CUSTOMER C INNER JOIN INVOICE I ON C.CUSTOMERID = I.CUSTOMERID;





/* Question 7.2 */
SELECT C.CUSTOMERID, C.FIRSTNAME, C.LASTNAME, I.INVOICEID, I.TOTAL 
FROM CUSTOMER C LEFT OUTER JOIN INVOICE I ON C.CUSTOMERID = I.CUSTOMERID;




/*Question 7.3*/
SELECT ARTIST.NAME, ALBUM.TITLE
FROM ALBUM RIGHT OUTER JOIN ARTIST ON ALBUM.ARTISTID = ARTIST.ARTISTID;




/*Question 7.4*/
SELECT * FROM ALBUM, ARTIST ORDER BY ARTIST.NAME ASC;





/*Question 7.5*/
SELECT * FROM EMPLOYEE E1 JOIN EMPLOYEE E2 ON E1.REPORTSTO = E2.REPORTSTO;


