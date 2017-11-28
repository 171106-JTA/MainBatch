--     2: SQL Queries  --

--     2.1 SELECT      --

SELECT * FROM mani.employee;

SELECT * FROM mani.employee
WHERE lower(lastname) = 'king';

SELECT * FROM mani.employee
WHERE lower(firstname) = 'andrew' 
AND reportsto IS NULL;

--     2.2 ORDER BY      --

SELECT * FROM mani.album
ORDER BY title DESC;

SELECT firstname FROM mani.customer
ORDER BY city ASC;

--     2.3 INSERT INTO      --

INSERT INTO mani.genre
VALUES (26, 'Folk');

INSERT INTO mani.genre 
VALUES (27, 'KPop');

INSERT INTO mani.employee
VALUES (9, 'Waajid', 'Taliah', 'Marketing Manager', 1, '11-APR-89', '21-JUN-04', '813 5 ST NW',	'Lethbridge', 'AB', 'Canada', 'T1H 1Y8', '+1 (403) 930-1172', '+1 (403) 094-8421', 'taliah@chinookcorp.com');

INSERT INTO mani.employee 
VALUES (10, 'Busara', 'Xavier', 'Marketing Staff', 9, '07-AUG-82', '13-OCT-04', '237 9 ST NW',	'Lethbridge', 'AB', 'Canada', 'T1H 1Y8', '+1 (403) 296-4501', '+1 (403) 420-1984', 'xavier@chinookcorp.com');

INSERT INTO mani.customer (customerid, firstname, lastname, city, state, country, email)
VALUES (60, 'Luca', 'Williams', 'Reston', 'VA', 'USA', 'luca_williams@icloud.com'); 

INSERT INTO mani.customer (customerid, firstname, lastname, city, state, country, email)
VALUES (61, 'Nan', 'Farsaad', 'Charlotte', 'NC', 'USA', 'nan.farsaad@gmail.com');

--     2.4 UPDATE      --

UPDATE mani.customer
SET firstname = 'Robert', lastname = 'Walter' 
WHERE firstname = 'Aaron' AND lastname = 'Mitchell';

UPDATE mani.artist
SET NAME = 'CCR'
WHERE NAME = 'Creedence Clearwater Revival';

--     2.5 LIKE      --

SELECT * FROM mani.invoice
WHERE billingaddress LIKE 'T%';

--     2.6 BETWEEN      --

SELECT * FROM mani.invoice
WHERE total BETWEEN 15 AND 50;

SELECT * FROM mani.employee 
WHERE hiredate BETWEEN '01-JUN-03' AND '1-MAR-04';

--     2.7 DELETE      --

DELETE FROM mani.invoiceline 
WHERE invoicelineid IN 
(SELECT invoicelineid FROM mani.invoiceline
WHERE invoiceid IN 
(SELECT invoiceid FROM mani.invoice 
WHERE customerid IN 
(SELECT customerid FROM mani.customer 
WHERE firstname = 'Robert' AND lastname = 'Walter')
));
--deletes invoicelineid → invoiceid first

DELETE FROM mani.invoice
WHERE customerid = 32;
--deletes invoiceid → customerid


DELETE FROM mani.customer 
WHERE firstname = 'Robert' AND lastname = 'Walter';     
--deletes customerid



--     3: SQL Functions  --

--     3.1 System Defined Functions                --

SELECT TO_CHAR(LOCALTIMESTAMP, 'HH24:MI') FROM DUAL;

SELECT LENGTH(name), name FROM mani.mediatype;

--     3.2 System Defined Aggregate Functions      --

SELECT avg(total) FROM mani.invoice;

SELECT max(unitprice) FROM mani.track;--returns price
SELECT * FROM mani.track WHERE unitprice = (SELECT max(unitprice) FROM mani.track); --returns set of all most expensive tracks

--     3.3 User Defined Scalar Functions           --

CREATE OR REPLACE FUNCTION get_invoice_avg
RETURN NUMBER 
IS 
    invoice_avg NUMBER;
BEGIN
    SELECT avg(unitprice) INTO invoice_avg FROM mani.invoiceline;
    RETURN invoice_avg;
END;

DECLARE
    invoice_avg number;
BEGIN
    invoice_avg := get_invoice_avg();
    DBMS_OUTPUT.PUT_LINE(invoice_avg);
END;

--     3.4 User Defined Table Valued Functions     --

CREATE OR REPLACE FUNCTION get_emp_after_1968(cursorParam OUT SYS_REFCURSOR)
RETURN VARCHAR2 IS
    all_emp VARCHAR2;
BEGIN 
    
    RETURN all_emp
END;
--????????


--     4: Stored Procedures                       --

--     4.1 Basic Stored Procedure                 --

CREATE OR REPLACE PROCEDURE get_full_name(cursor_param OUT SYS_REFCURSOR)
IS
BEGIN
    OPEN cursor_param FOR
    SELECT firstname, lastname FROM mani.employee;
END;

DECLARE 
    name_cursor SYS_REFCURSOR;
    firstname   VARCHAR2(32);
    lastname    VARCHAR2(32);
BEGIN
    get_full_name(name_cursor);
    
    LOOP --Begin loop block
        FETCH name_cursor INTO firstname, lastname; --Grab the current its pointing at.
        EXIT WHEN name_cursor%NOTFOUND; --%NOTFOUND does not exist until there are no records left
        DBMS_OUTPUT.PUT_LINE(firstname || ' ' || lastname );
    END LOOP; --End of loop block
END;

--     4.2 Stored Procedure Input Parameters      --

CREATE OR REPLACE PROCEDURE insert_fc_procedure(some_q IN VARCHAR2, some_a IN flash_cards.fc_answer%TYPE)
IS
BEGIN
    INSERT INTO flash_cards(fc_question, fc_answer)
    VALUES(some_q, some_a);
    commit;
END;

--     4.3 Stored Procedure Output Parameters     --

  
CREATE OR REPLACE PROCEDURE insert_fc_procedure(some_q IN VARCHAR2, some_a IN flash_cards.fc_answer%TYPE)
IS
BEGIN
    INSERT INTO flash_cards(fc_question, fc_answer)
    VALUES(some_q, some_a);
    commit;
END;


--     5: Transactions      --





--     6: Triggers      --

--     6.1 After/For    --

CREATE OR REPLACE TRIGGER new_employee_trigger --auto increment
AFTER INSERT ON m.employee
--FOR EACH ROW
BEGIN 
    ORDER BY employeeid DESC;
END;

CREATE OR REPLACE TRIGGER update_album_trigger --auto increment
AFTER UPDATE ON m.album
BEGIN 
    ORDER BY albumid ASC;
END;

CREATE OR REPLACE TRIGGER delete_customer_trigger --auto increment
AFTER DELETE ON m.customer
BEGIN 
    DBMS_OUTPUT.PUT_LINE('Customer has been successfully deleted.');
END;



--     7: Joins         --

--     7.1 Inner        --

SELECT c.firstname, c.lastname, i.invoiceid FROM mani.customer C
INNER JOIN mani.invoice I 
ON c.customerid = i.customerid;


--     7.2 Outer        --

SELECT c.customerid, c.firstname, c.lastname, i.invoiceid, i.total FROM mani.customer C
FULL OUTER JOIN mani.invoice I
ON c.customerid = i.customerid 
ORDER BY customerid ASC;

--     7.3 Right        --

SELECT t.name, m.title FROM mani.artist T
RIGHT JOIN mani.album M
ON m.artistid = t.artistid;

--     7.4 Cross        --

SELECT t.name, m.title FROM mani.artist T
CROSS JOIN mani.album M
ORDER BY t.name ASC;


--     7.5 Self    --
 
SELECT * FROM mani.employee A
INNER JOIN mani.employee B
ON a.reportsto = b.reportsto;



--     8: Administration      --

BACKUP DATABASE RevatureSQL TO DISK='/Users/zaire/Downloads/myDB.bak';


