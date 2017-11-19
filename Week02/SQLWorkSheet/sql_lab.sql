-- 2.1 SELECT
SELECT * FROM Employee;

SELECT * FROM Employee 
WHERE LastName='King';

SELECT * FROM Employee
WHERE FirstName='Andrew'
AND REPORTSTO IS NULL;

--2.2 ORDER BY
SELECT * FROM ALBUM
ORDER BY title DESC;

SELECT FirstName FROM CUSTOMER
ORDER BY FirstName ASC;

--2.3 INSERT INTO 
SELECT * FROM GENRE;

INSERT INTO GENRE(GENREID, NAME)
VALUES(26, 'Acid Punk');

INSERT INTO GENRE(GENREID, NAME)
VALUES(27, 'Electro Raggae');
-- INSERT 2 into Employee
INSERT INTO EMPLOYEE (EmployeeId, LastName, FirstName)
VALUES(20, 'Dingus', 'Tonga');
INSERT INTO EMPLOYEE (EmployeeID,FIRSTNAME,LASTNAME, BIRTHDATE, STATE) 
VALUES(9,'NULLY','NOTNULLMAN' ,TO_DATE('1991-02-08','YYYY-MM-DD'), 'WA');

-- Insert 2 into Customer
SELECT * FROM USER_CONSTRAINTS;

SELECT * FROM CUSTOMER;

INSERT INTO CUSTOMER (CUSTOMERID, FIRSTNAME, LASTNAME, EMAIL)
VALUES(62, 'Yawae', 'Nang', 'ynang@wizards.io');
INSERT INTO CUSTOMER (CUSTOMERID, FIRSTNAME, LASTNAME, EMAIL)
VALUES(63, 'Daemionion', 'Yahs', 'dyahs@wizards.io');

COMMIT;

-- 2.4 Update
SELECT * FROM CUSTOMER;
UPDATE CUSTOMER
SET FIRSTNAME='Robert', LASTNAME='Walter'
WHERE FIRSTNAME='Aaron' AND LASTNAME='Mitchell';

SELECT * FROM CUSTOMER WHERE FIRSTNAME='Robert';
SELECT * FROM ARTIST;

UPDATE ARTIST
SET NAME='CCR' WHERE NAME='Creedence Clearwater Revival';

--2.5 LiKE

SELECT * FROM INVOICE
WHERE BILLINGADDRESS LIKE 'T%';

--2.6 BETWEEN 

SELECT * FROM INVOICE 
WHERE TOTAL > 15 AND TOTAL < 50
ORDER BY TOTAL;

SELECT * FROM EMPLOYEE
WHERE HIREDATE > TO_DATE('2003-06-01','YYYY-MM-DD')
AND HIREDATE < TO_DATE('2004-03-01','YYYY-MM-DD');

--2.7 DELETE 
ALTER TABLE INVOICE DROP CONSTRAINT FK_INVOICECUSTOMERID;
DELETE FROM CUSTOMER WHERE FIRSTNAME='Robert' AND LASTNAME='Walter';

COMMIT;

--3.0 FUNCTIONS
--3.1 System Defined Fucntions
CREATE OR REPLACE FUNCTION now_time
RETURN VARCHAR2
IS
BEGIN
    RETURN TO_CHAR(CURRENT_TIMESTAMP, 'HH24:MI:SS');
END;
/


BEGIN
DBMS_OUTPUT.PUT_LINE(now_time);
END;
/

CREATE OR REPLACE FUNCTION MediaTableLength
RETURN NUMBER IS
    TOTAL NUMBER(30) := 0;
BEGIN
    SELECT COUNT(*) INTO TOTAL 
    FROM MEDIATYPE;    
    RETURN TOTAL;
END;
/

DECLARE
 -- note to self NUMBER precision constraints are in range (1.. 38)
 temp number(23);
BEGIN
    temp := MediaTableLength();
    DBMS_OUTPUT.PUT_LINE('mediatable.length() =  ' ||  temp);
END;
/

--3.2
CREATE OR REPLACE FUNCTION getInvoiceTotalAvg
RETURN NUMBER IS 
    TOTAL_AVG NUMBER(8,2);
BEGIN
    SELECT AVG(TOTAL) INTO TOTAL_AVG FROM INVOICE;
    RETURN TOTAL_AVG;
END;
/

DECLARE
 temp NUMBER;
BEGIN
    temp :=getInvoiceTotalAvg();
    DBMS_OUTPUT.PUT_LINE('Average total is $' ||  temp);
END;
/

CREATE OR REPLACE FUNCTION getMostExpTrack
RETURN VARCHAR2 IS 
    Track_name VARCHAR2(100);
BEGIN
    SELECT NAME INTO TRACK_NAME FROM TRACK
    WHERE UNITPRICE=(SELECT MAX(UNITPRICE) FROM TRACK)AND ROWNUM=1;
    -- There are many tracks with same highest price so
    -- so this will return first entry in unordered result
    RETURN TRACK_NAME;
END;
/

DECLARE
 temp VARCHAR2(100);
BEGIN
    temp :=getMostExpTrack();
    DBMS_OUTPUT.PUT_LINE('THE MOST EXPENSIVE TRACK IS ' ||  temp);
END;
/

--3.3 scalar function
CREATE OR REPLACE FUNCTION AVG_INVOICELINE
RETURN NUMBER IS
    TOTAL_AVG NUMBER(5,2);
BEGIN
    SELECT AVG(UNITPRICE*QUANTITY) INTO TOTAL_AVG FROM INVOICELINE;
    RETURN TOTAL_AVG;
END;
/
--3.4 
CREATE OR REPLACE FUNCTION AFTER1968_CURSOR return number
IS
    c_id EMPLOYEE.employeeID%type; 
    c_fname EMPLOYEE.firstname%type; 
    c_lname EMPLOYEE.lastname%type; 
    CURSOR c_employee is 
      SELECT employeeID, firstname, lastname FROM EMPLOYEE WHERE BIRTHDATE >= '01-JAN-68';
BEGIN
    OPEN c_employee;
    LOOP
    FETCH c_employee into c_id, c_fname, c_lname;
        EXIT WHEN c_employee%notfound;
         dbms_output.put_line(c_id || ' ' || c_fname || ' ' || c_lname); 
   END LOOP; 
   CLOSE c_employee;
   return 0;
END;
/

--4.1 BASIC STORED PROCEDURE

CREATE OR REPLACE PROCEDURE get_name
IS
    c_fname EMPLOYEE.FIRSTNAME%TYPE; 
    c_lname EMPLOYEE.LASTNAME%TYPE;
    CURSOR c_employee IS
        SELECT firstname, lastname from EMPLOYEE;
BEGIN
    open c_employee;
    loop
    fetch c_employee into c_fname, c_lname;
    exit when c_employee%notfound;
    dbms_output.put_line(c_fname || ' ' || c_lname);
    END loop;
    close c_employee;
END;
/

BEGIN
get_name();
END;
/

SELECT * FROM EMPLOYEE;
--4.2 stored pc input param
CREATE OR REPLACE PROCEDURE update_emp_TITLE (emid IN NUMBER, title_in in VARCHAR2)
IS
BEGIN
    UPDATE EMPLOYEE 
    SET TITLE=title_in
    WHERE EMPLOYEEID=emid;
END;
/
DECLARE
BEGIn
update_emp_TITLE(1,'wizard');
end;
/
SELECT * FROM EMPLOYEE;
--return manager

CREATE OR REPLACE PROCEDURE return_manager (fname in VARCHAR2, lname IN VARCHAR2)
IS 
    c_id EMPLOYEE.EMPLOYEEID%TYPE;
    c_fname EMPLOYEE.FIRSTNAME%TYPE;
    c_lname EMPLOYEE.LASTNAME%TYPE;
    CURSOR c_employee IS
        SELECT employeeid, firstname, lastname FROM EMPLOYEE WHERE EMPLOYEEID = (SELECT REPORTSTO FROM EMPLOYEE WHERE FIRSTNAME=fname AND LASTNAME=lname);
BEGIN
    open c_employee;
    loop
    fetch c_employee into c_id, c_fname, c_lname;
    exit when c_employee%notfound;
    dbms_output.put_line(c_id || ' ' || c_fname || ' ' || c_lname);
    END loop;
    close c_employee;
END;
/

SELECT * FROM EMPLOYEE;
BEGIN
    RETURN_MANAGER('Jane', 'Peacock');
END;
/

--4.3
select * FROM CUSTOMER;
CREATE OR REPLACE PROCEDURE return_name_com(i_id in NUMBER, o_lname out VARCHAR2, o_fname out VARCHAR2, o_comp out varchar2)
IS
BEGIN
    SELECT firstname, lastname, company  INTO o_fname, o_lname, o_comp FROM CUSTOMER WHERE CUSTOMERID=i_id;
END;
/


SELECT * FROM INVOICE;

ALTER TABLE INVOICELINE DROP CONSTRAINT FK_INVOICELINEINVOICEID;
CREATE OR REPLACE PROCEDURE del_invoice(i_invid in number)
IS
begin
    DELETE FROM INVOICE WHERE INVOICEID=i_invid;
end;
/

begin
del_invoice(320);
end;
/

SELECT * FROM CUSTOMER;
CREATE OR REPLACE PROCEDURE ADD_NEW(i_cid in number, i_fname in varchar2, i_lname in varchar2,
i_company in varchar2, i_addr in varchar2, i_email in varchar2)
IS 
BEGIN
    INSERT INTO CUSTOMER (customerid, firstname, lastname, company, address, email)
    VALUES(i_cid, i_fname, i_lname, i_company, i_addr, i_email);
END;
/

--TRIGGER   
--6.1
CREATE OR REPLACE TRIGGER emp_trigger
AFTER INSERT
    ON EMPLOYEE
BEGIN
    dbms_output.put_line('Insert has been made');
END;
/

CREATE OR REPLACE TRIGGER alb_trigger
AFTER UPDATE 
    ON ALBUM
BEGIN
    dbms_output.put_line('update has been made');
END;
/

CREATE OR REPLACE TRIGGER cus_trigger
AFTER DELETE
    ON CUSTOMER
BEGIN
   dbms_output.put_line('deletion has been made');
END;
/

SELECT * FROM CUSTOMER; --customerID fn ln com add city state countr post phone fax email supportid
SELECT * FROM INVOICE; -- INVOICEid customerid invdate billingaddr billing city st count post total
--inner 7.1
SELECT  INVOICE.INVOICEID, CUSTOMER.FIRSTNAME, CUSTOMER.LASTNAME
FROM CUSTOMER
INNER JOIN INVOICE ON CUSTOMER.CUSTOMERID = INVOICE.CUSTOMERID
ORDER BY INVOICEID ASC;
--outer 7.2
SELECT CUSTOMER.CUSTOMERID, CUSTOMER.FIRSTNAME, CUSTOMER.LASTNAME, INVOICE.INVOICEID, Invoice.TOTAL
FROM CUSTOMER
FULL OUTER JOIN INVOICE ON CUSTOMER.CUSTOMERID = INVOICE.CUSTOMERID;
--7.3

SELECT ALBUM.TITLE, ARTIST.NAME
FROM ALBUM
RIGHT JOIN ARTIST ON ARTIST.ARTISTID=ALBUM.ARTISTID;
--7.4
SELECT * FROM ALBUM
CROSS JOIN ARTIST;
--7.5
SELECT * FROM employee;
SELECT A.firstname as firstname, B.firstname as firstname, A.Title
FROM Employee a, employee b
WHERE A.employeeID <> B.employeeID
AND a.title= b.title
ORDER by a.title;

