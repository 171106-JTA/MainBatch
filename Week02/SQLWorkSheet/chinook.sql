-- Antony lulciuc
--2.1
SELECT * FROM Employee;
SELECT * FROM Employee WHERE lastname='King';
SELECT * FROM Employee WHERE firstname='Andrew' AND reportsto IS NULL;

--2.2
SELECT * FROM Album ORDER BY title DESC;
SELECT firstname FROM Customer ORDER BY city;

--2.3
INSERT INTO Genre VALUES(1000, 'TEST 1');
INSERT INTO Genre VALUES(18249, 'TEST 2');

INSERT INTO Employee(EMPLOYEEID,LASTNAME,FIRSTNAME)VALUES(102872,'b','b');
INSERT INTO Employee(EMPLOYEEID,LASTNAME,FIRSTNAME)VALUES(318736,'A','b');

INSERT INTO Customer(CUSTOMERID,FIRSTNAME,LASTNAME,EMAIL)VALUES(7326,'g','g','t');
INSERT INTO Customer(CUSTOMERID,FIRSTNAME,LASTNAME,EMAIL)VALUES(9326,'n','n','n');

-- 2.4
UPDATE Customer
SET firstname='Robert', lastname='Walter'
WHERE firstname='Aaron' AND lastname='Mitchell';

UPDATE Artist
SET name='CCR'
WHERE name='Creedence Clearwater Revival';

-- 2.5

SELECT * FROM Invoice WHERE billingaddress LIKE 'T%';

-- 2.6

SELECT * FROM Invoice WHERE total BETWEEN 15 AND 50;
SELECT * FROM Employee WHERE hiredate BETWEEN TO_DATE('01/01/2003', 'DD-MM-YYYY') AND TO_DATE('01/03/2004','DD-MM-YYYY');

-- 2.7

DELETE FROM InvoiceLine WHERE invoiceid IN (SELECT invoiceid FROM Invoice WHERE customerid IN (SELECT customerid FROM Customer WHERE firstname='Robert' AND lastname='Walter'));
DELETE FROM Invoice WHERE customerid IN (SELECT customerid FROM Customer WHERE firstname='Robert' AND lastname='Walter');
DELETE FROM Customer WHERE firstname='Robert' AND lastname='Walter';

-- 3.1

CREATE OR REPLACE FUNCTION get_time
    RETURN DATE
    IS
BEGIN
    RETURN SYSDATE;
END;
/

CREATE OR REPLACE FUNCTION get_length(id IN MEDIATYPE.MEDIATYPEID%TYPE)
    RETURN NUMBER
AS
    myname NUMBER;
BEGIN
    SELECT LENGTH(name) INTO myname FROM MEDIATYPE WHERE mediatypeid=id;
    RETURN myname;
END;
/

-- 3.2

CREATE OR REPLACE FUNCTION get_invoice_avg
    RETURN NUMBER
AS 
    myavg NUMBER;
BEGIN
    SELECT avg(total) INTO myavg FROM Invoice;
    RETURN myavg;
END;
/

CREATE OR REPLACE FUNCTION get_most_exp_track
    RETURN NUMBER
AS 
    price NUMBER;
BEGIN
    SELECT max(unitprice) INTO price FROM TRACK;
    RETURN price;
END;
/

-- 3.3

CREATE OR REPLACE FUNCTION get_invoiceline_avg
    RETURN NUMBER
AS 
    myavg NUMBER;
BEGIN
    SELECT avg(unitprice) INTO myavg FROM InvoiceLine;
    RETURN myavg;
END;
/

-- 3.4

CREATE OR REPLACE FUNCTION get_emp_aft_1968
    RETURN SYS_REFCURSOR
AS
    my_cursor SYS_REFCURSOR;
BEGIN
    OPEN my_cursor FOR SELECT * FROM Employee WHERE EXTRACT(YEAR FROM TO_DATE(birthdate, 'DD-MON-RR')) > 1968;
    RETURN my_cursor;
END;
/

-- 4.1

CREATE OR REPLACE PROCEDURE select_fl_emp
IS
    CURSOR s IS SELECT firstname, lastname FROM Employee;
    fname Employee.firstname%TYPE;
    lname  Employee.lastname%TYPE;
BEGIN
    OPEN s;
    LOOP
        FETCH s INTO fname, lname;
            dbms_output.put_line(fname);
            dbms_output.put_line(lname);
        EXIT WHEN s%NOTFOUND;
    END LOOP;
    CLOSE s;
END;
/

-- 4.2

CREATE OR REPLACE PROCEDURE my_input(id IN Employee.employeeid%TYPE, my_addr IN Employee.address%TYPE)
    IS
BEGIN
    UPDATE Employee
    SET address=my_addr
    WHERE employeeid=id;
END;
/

CREATE OR REPLACE PROCEDURE my_mans(id IN Employee.employeeid%TYPE, mans OUT SYS_REFCURSOR)
    IS
BEGIN
    OPEN mans FOR SELECT man.* FROM Employee e JOIN Employee man 
        ON e.employeeid = id AND e.reportsto = man.employeeid;
END;
/

-- 4.3

CREATE OR REPLACE PROCEDURE get_company(id IN Customer.customerid%TYPE, name OUT Customer.company%TYPE)
    IS
    CURSOR c IS SELECT company FROM Customer WHERE customerid = id;
BEGIN
    OPEN c;
    FETCH c INTO name;
    CLOSE c;
END;
/

-- 5.0

CREATE OR REPLACE PROCEDURE delete_invoice(id IN Invoice.invoiceid%TYPE) 
IS
BEGIN
    DELETE FROM InvoiceLine WHERE invoiceid = id;
    DELETE FROM Invoice WHERE invoiceid = id;
END;
/

CREATE OR REPLACE PROCEDURE create_customer(id IN Customer.customerid%TYPE,fname IN Customer.firstname%TYPE, 
    lname IN Customer.lastname%TYPE, mail IN Customer.email%TYPE)
IS
BEGIN
    INSERT INTO Customer (customerid,firstname,lastname,email)VALUES(id,fname,lname,mail);
END;
/

BEGIN
    create_customer(32240, 'billy', 'bob', 'bb@gmail.com');
END;

-- 6.1

CREATE OR REPLACE TRIGGER aft 
    AFTER INSERT ON Employee
    FOR EACH ROW
BEGIN
    dbms_output.put_line('after insert');
END;

CREATE OR REPLACE TRIGGER aft 
    AFTER UPDATE ON Album
    FOR EACH ROW
BEGIN
    dbms_output.put_line('after update');
END;

CREATE OR REPLACE TRIGGER aft 
    AFTER DELETE ON Customer
    FOR EACH ROW
BEGIN
    dbms_output.put_line('after delete');
END;

-- 7.1

SELECT c.firstname, c.lastname, i.invoiceid FROM Customer c INNER JOIN Invoice i
    ON c.customerid = i.customerid;
    
-- 7.2

SELECT c.firstname, c.lastname, i.invoiceid, i.total FROM Customer c FULL OUTER JOIN Invoice i
    ON c.customerid = i.customerid;

-- 7.3

SELECT t.name, a.title FROM Album a RIGHT JOIN Artist t
    ON a.artistid = t.artistid;

-- 7.4

SELECT t.name, a.title FROM Album a CROSS JOIN Artist t
ORDER BY t.name;

-- 7.5

SELECT * FROM Employee e1 JOIN Employee e2
    ON e1.reportsto = e2.reportsto;







