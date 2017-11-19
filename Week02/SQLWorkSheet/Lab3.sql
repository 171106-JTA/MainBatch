--4.0 Stored Procedures

---4.1 Basic stored Procedure
--Create a stored procedure that selects the first and last names of all the employees
CREATE OR REPLACE PROCEDURE get_names
IS
    CURSOR cursorParam IS
    SELECT FIRSTNAME, LASTNAME FROM Employee;
    fname EMPLOYEE.FIRSTNAME%TYPE;
    lname EMPLOYEE.LASTNAME%TYPE;
BEGIN
    OPEN cursorParam;
    LOOP
        FETCH cursorParam INTO fname, lname;
        EXIT WHEN cursorParam%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE(fname || ' ' || lname);
    END LOOP;
    CLOSE cursorParam;
END;
/
DECLARE
BEGIN
    get_names();
END;
/
---------------------------------------------------------------------------------------------------------------------------------
---4.2 Stored Procedure Input Parameter
--Create a stored procedure that updates the personal information of an employee
CREATE OR REPLACE PROCEDURE update_info(empid IN NUMBER, new_email IN VARCHAR2)
IS
BEGIN
    UPDATE Employee SET EMAIL = new_email WHERE EMPLOYEEID = empid;
END;
/
DECLARE
    eid NUMBER;
BEGIN
    eid := 6;
    update_info(6, 'mit@chinookcorp.com');
END;
/

--Create a stored procedure that returns the managers of the employee
CREATE OR REPLACE PROCEDURE get_managers(empid IN NUMBER)
IS
    man_lname VARCHAR2(200);
    man_fname VARCHAR2(200);
BEGIN
    SELECT E2.LASTNAME, E2.FIRSTNAME INTO man_lname, man_fname 
    FROM (Employee E1 INNER JOIN Employee E2
    ON E1.REPORTSTO = E2.EMPLOYEEID)
    WHERE E1.EMPLOYEEID = empid;
    DBMS_OUTPUT.PUT_LINE(man_lname || ' ' || man_fname);
    
    EXCEPTION 
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('No Managers Found');
END;
/
DECLARE
BEGIN
    get_managers(3);
END;
/
---------------------------------------------------------------------------------------------------------------------------------
---4.3 Stored Procedure Output Parameter
--Create a stored procedure that returns the name and company of a customer
CREATE OR REPLACE PROCEDURE get_name_company(customid IN NUMBER, fname OUT VARCHAR2, lname OUT VARCHAR2, comp OUT VARCHAR2)
IS  
BEGIN
    SELECT FIRSTNAME, LASTNAME, COMPANY INTO 
    fname, lname, comp FROM Customer
    WHERE CUSTOMERID = customid;
    
END;
/
DECLARE
    cid NUMBER;
    fname VARCHAR2(2000);
    lname VARCHAR2(2000);
    comp VARCHAR2(2000);
BEGIN
    cid := 10;
    get_name_company(cid, fname, lname, comp);
    DBMS_OUTPUT.PUT_LINE(fname || ' ' || lname || ' - ' || comp);
END;