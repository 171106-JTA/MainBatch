--3.0 SQL Functions

---3.1 System Defined Functions
--Create a function returns the current time
CREATE OR REPLACE FUNCTION get_current_time
RETURN VARCHAR2
IS
BEGIN
    RETURN CURRENT_TIMESTAMP;
END;
/
DECLARE
    time_now VARCHAR2(2000);
BEGIN
    time_now := get_current_time();
    DBMS_OUTPUT.PUT_LINE(time_now);
END;
/

--Create a function that returns the length of a mediatype from the mediatype table
CREATE OR REPLACE FUNCTION get_medianame_length(mediaId IN NUMBER)
RETURN NUMBER
IS
    media_length NUMBER;
BEGIN
    SELECT LENGTH(NAME) INTO media_length 
    FROM Mediatype
    WHERE MEDIATYPEID = mediaId;
    RETURN media_length;
END;
/
DECLARE
    rs NUMBER;
BEGIN
    rs := get_medianame_length(1);
    DBMS_OUTPUT.PUT_LINE(rs);
END;
/
---------------------------------------------------------------------------------------------------------------------------------
---3.2 System Defined Aggregate Functions
--Create a function that returns the average total of all invoices
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

--Create a function that returns the most expensive track
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
---------------------------------------------------------------------------------------------------------------------------------
---3.4 User Defined Scalar Functions
--Create a function that returns all employees who are born after 1968
CREATE OR REPLACE FUNCTION born_after_1968
RETURN SYS_REFCURSOR
IS
    curse SYS_REFCURSOR;
BEGIN
    OPEN curse FOR 
    SELECT FIRSTNAME, LASTNAME
    FROM Employee
    WHERE BIRTHDATE > TO_DATE('1968-12-31', 'YYYY-MM-DD HH24:MI:SS');
    RETURN curse;
END;
/
DECLARE
    emp_cursor SYS_REFCURSOR;
    fname VARCHAR2(4000);
    lname VARCHAR2(4000);
BEGIN
    emp_cursor := born_after_1968;
    
    LOOP
        FETCH emp_cursor INTO fname, lname;
        EXIT WHEN emp_cursor%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE(fname || ' ' || lname);
    END LOOP;
END;