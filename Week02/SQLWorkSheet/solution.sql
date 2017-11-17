/* (TABLE FOR PRINTING MESSAGES AND ACCOMPANYING PROCEDURE) */
DROP TABLE debugoutput;
CREATE TABLE debugoutput(
    doid NUMBER PRIMARY KEY,
    message VARCHAR(4000)
);
/

DROP SEQUENCE do_seq;
CREATE SEQUENCE do_seq
    START WITH 0
    INCREMENT BY 1;


CREATE OR REPLACE TRIGGER do_seq_trigger
BEFORE INSERT ON debugoutput
FOR EACH ROW
BEGIN
    IF :NEW.doid IS NULL THEN
        SELECT do_seq.nextval INTO :NEW.doid FROM dual;
    END IF;
END;
/

CREATE OR REPLACE PROCEDURE print_message(msg IN VARCHAR2)
IS
BEGIN
    INSERT INTO debugoutput(message) VALUES (msg);
END;
/
------------------------------------------------------------------------
------------------------------------------------------------------------









-------------------------------------------------------2.1
SELECT * FROM employee;

SELECT * FROM employee
WHERE lastname='King';

SELECT * FROM employee
WHERE LOWER(firstname) = 'andrew' AND reportsto IS NULL;

-------------------------------------------------------2.2
SELECT * FROM employee;

SELECT * FROM employee
WHERE lastname='King';

SELECT * FROM employee
WHERE LOWER(firstname) = 'andrew' AND reportsto IS NULL;

--------------------------------------------------------
SELECT * FROM album ORDER BY title DESC;
SELECT * FROM customer ORDER BY city ASC;

------------------------------------------------------- 2.3
INSERT INTO GENRE(GENREID, NAME) VALUES (26, 'ROMANCE');
INSERT INTO GENRE(GENREID, NAME) VALUES (27, 'ADVENTURE');

INSERT INTO EMPLOYEE (EMPLOYEEID, FIRSTNAME, LASTNAME)
VALUES (9, 'first-name', 'last-name');

INSERT INTO EMPLOYEE (EMPLOYEEID, FIRSTNAME, LASTNAME)
VALUES (10, 'first-name-1', 'last-name-2');

INSERT INTO CUSTOMER (CUSTOMERID, FIRSTNAME, LASTNAME, EMAIL)
VALUES (60, 'first-name-1', 'last-name-1', 'email.email@email.com');

INSERT INTO CUSTOMER (CUSTOMERID, FIRSTNAME, LASTNAME, EMAIL)
VALUES (61, 'first-name-2', 'last-name-2', 'email.email@email.com');

------------------------------------------------------- 2.4
UPDATE CUSTOMER
SET firstname='Robert', lastname='Walter'
WHERE firstname = 'Aaron' AND lastname = 'Mitchell';

UPDATE ARTIST
SET name='CCR'
WHERE name='Creedence Clearwater Revival';
------------------------------------------------------- 2.5
SELECT * FROM invoice
WHERE billingaddress LIKE 'T%';

------------------------------------------------------- 2.6

SELECT * FROM invoice
WHERE total BETWEEN 15 AND 50;

------------------------------------------------------- 2.7
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




-------------------------------------3.1---------------------------------------
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

--------------------------------------3.2--------------------------------------
CREATE OR REPLACE FUNCTION func_3_2_1
RETURN NUMBER
IS
    avg_total NUMBER;
BEGIN
    SELECT AVG(total) INTO avg_total FROM invoice;
    RETURN avg_total;
END;
/

---------------------------------------3.3.1-----------------------------------

CREATE OR REPLACE FUNCTION func_3_2_2(cres OUT SYS_REFCURSOR)
RETURN SYS_REFCURSOR
IS
    tname track.name%TYPE;
    tprice track.unitprice%TYPE;
BEGIN
    OPEN cres FOR
        SELECT name, unitprice FROM track WHERE unitprice = (SELECT MAX(unitprice) FROM track);
    RETURN cres;
END;
/

DECLARE
    cres SYS_REFCURSOR;
    tname track.name%TYPE;
    tprice track.unitprice%TYPE;
BEGIN
    cres := func_3_2_2(cres);
    LOOP
        FETCH cres into tname, tprice;
        EXIT WHEN cres%NOTFOUND;
        print_message(tname || ' ' || tprice);
    END LOOP;
END;
/
---------------------------------------3.3-------------------------------------
CREATE OR REPLACE FUNCTION func_3_3
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
CALL print_message(' ' || func_3_3());



---------------------------------------3.4-------------------------------------
DROP FUNCTION func_3_4;
CREATE OR REPLACE FUNCTION func_3_4(res OUT SYS_REFCURSOR)
RETURN SYS_REFCURSOR
IS
BEGIN
    OPEN res FOR
        SELECT lastname, firstname, title FROM employee
        WHERE birthdate > TO_DATE('1968/12/31','yyyy/mm/dd');
    RETURN res;
END;
/


DECLARE
    res SYS_REFCURSOR;
    lname employee.lastname%TYPE;
    fname employee.firstname%TYPE;
    title employee.title%TYPE;
BEGIN
    res := func_3_4(res);
    LOOP
        FETCH res INTO lname, fname, title;
        EXIT WHEN res%NOTFOUND;
        print_message(' ' || fname || ' ' || lname);
    END LOOP;
END;
/


COMMIT;



------------------------------------------------------------------------ 4
CREATE OR REPLACE PROCEDURE proc_4_1(rcursor OUT SYS_REFCURSOR)
IS
BEGIN
    OPEN rcursor FOR
        SELECT firstname, lastname FROM employee;
END;
/

DECLARE
    res SYS_REFCURSOR;
    lname employee.lastname%TYPE;
    fname employee.firstname%TYPE;
BEGIN
    proc_4_1(res);
    LOOP
        FETCH res INTO lname, fname;
        EXIT WHEN res%NOTFOUND;
        print_message(' ' || fname || ' ' || lname); END LOOP;
END;
/

------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE proc_4_2_1(
    empid IN employee.employeeid%TYPE,
    newfname IN employee.firstname%TYPE,
    newlname IN employee.lastname%TYPE,
    newemail IN employee.email%TYPE
)
IS
BEGIN
    UPDATE employee
    SET firstname = newfname, lastname = newlname, email = newemail
    WHERE employeeid = empid;
END;
/

CALL proc_4_2_1(10, 'updated-first-name', 'updated-last-name', 'updated-email');
/


------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE proc_4_2_2(empid IN employee.employeeid%TYPE, rcursor OUT SYS_REFCURSOR)
IS
BEGIN
    OPEN rcursor FOR
        SELECT employeeid, firstname, lastname FROM employee
        WHERE employeeid =
            (SELECT reportsto FROM employee
            WHERE employeeid = empid) ;
END;
/

DECLARE
    res SYS_REFCURSOR;
    lname employee.lastname%TYPE;
    fname employee.firstname%TYPE;
    eid employee.employeeid%TYPE;
BEGIN
    proc_4_2_2(7, res);
    LOOP
        FETCH res INTO eid, fname, lname;
        EXIT WHEN res%NOTFOUND;
        print_message('manager: ' || eid || ' ' || fname || ' ' || lname);
        END LOOP;
END;
/

-----------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE proc_4_3(
        cid IN customer.customerid%TYPE,
        fname IN OUT customer.firstname%TYPE,
        lname IN OUT customer.lastname%TYPE,
        ccompany IN OUT customer.company%TYPE
    )
IS
BEGIN
    SELECT firstname, lastname, company INTO fname, lname, ccompany FROM customer WHERE customerid = cid;
END;
/

DECLARE
    cid customer.customerid%TYPE;
    fname customer.firstname%TYPE;
    lname customer.lastname%TYPE;
    ccompany customer.company%TYPE;
BEGIN
    cid := 12;
    proc_4_3(cid, fname, lname, ccompany);
    print_message('info: ' || fname || ' ' || lname || ' ' || ccompany);

END;
/

COMMIT;

----------------------------------------------------------------------------------------------- 5
ALTER TABLE invoiceline
DROP CONSTRAINT fk_invoicelineinvoiceid;

ALTER TABLE invoiceline
ADD CONSTRAINT fk_invoicelineinvoiceid
FOREIGN KEY (invoiceid) REFERENCES invoice (invoiceid) ON DELETE CASCADE;

CREATE OR REPLACE PROCEDURE proc_51(given_id IN NUMBER)
IS
BEGIN
    DBMS_OUTPUT.PUT_LINE('called the procedure');
    DELETE FROM invoice
    WHERE invoiceid = given_id;
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE proc_52(customer_id IN NUMBER, first_name IN VARCHAR2, last_name IN VARCHAR2, c_email IN VARCHAR2)
IS
BEGIN
    DBMS_OUTPUT.PUT_LINE('called the procedure');
    INSERT INTO customer(customerid, firstname, lastname, email)
        VALUES(customer_id, first_name, last_name, c_email);
    COMMIT;
END;
/

CALL proc_51(324);
CALL proc_52(100, 'first', 'last', 'email@email.com');

COMMIT;

--------------------------------------------------------------- 6
CREATE OR REPLACE TRIGGER trig_employee_before_insert
    BEFORE INSERT ON employee
    FOR EACH ROW
    BEGIN
        print_message(:NEW.firstname || ' ' || :NEW.lastname || ' ' || :NEW.employeeid || ' inserted into employees table' );
    END;
/


---------------------------------------------------------
CREATE OR REPLACE TRIGGER trig_album_before_insert
    BEFORE INSERT ON album
    FOR EACH ROW
    BEGIN
        print_message(:NEW.albumid || ' ' || :NEW.title || ' by ' || :NEW.artistid || ' inserted into album table' );
    END;
/

INSERT INTO album
VALUES (348, 'the-fred-album', 30);
COMMIT;
/


---------------------------------------------------------
CREATE OR REPLACE TRIGGER trig_album_after_delete
    AFTER DELETE ON album
    FOR EACH ROW
    BEGIN
        print_message(:OLD.albumid || ' ' || :OLD.title || ' by ' || :OLD.artistid || ' delted from album table' );
    END;
/

DELETE FROM album
WHERE albumid = 348;
/

------------------------------------------------------------------------------- 7
SELECT c.firstname, c.lastname, o.invoiceid, o.total
FROM customer c INNER JOIN invoice o
ON o.customerid = c.customerid;

SELECT c.customerid, i.invoiceid, i.total
FROM customer c FULL OUTER JOIN invoice i
ON c.customerid = i.customerid;

SELECT c.customerid, i.invoiceid, i.total
FROM customer c FULL OUTER JOIN invoice i
ON c.customerid = i.customerid;


SELECT ar.name, al.title
FROM artist ar CROSS JOIN album al
ORDER BY ar.name ASC;


SELECT e1.firstname, e1.lastname, e2.firstname as "boss-first", e2.lastname as "boss-last"
FROM employee e1 INNER JOIN employee e2
ON e1.reportsto = e2.employeeid;
