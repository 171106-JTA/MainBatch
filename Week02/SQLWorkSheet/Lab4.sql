--5.0 Transactions

--Create a transaction that given a invoiceid will delete that invoice
ALTER TABLE InvoiceLine DROP CONSTRAINT FK_InvoiceLineInvoiceId;

ALTER TABLE InvoiceLine ADD CONSTRAINT FK_InvoiceLineInvoiceId
    FOREIGN KEY (InvoiceId) REFERENCES Invoice (InvoiceId) ON DELETE CASCADE;

CREATE OR REPLACE PROCEDURE delete_invoice(delete_id IN NUMBER)
IS
BEGIN
    DELETE FROM Invoice WHERE INVOICEID = delete_id;
    COMMIT;
    
    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
END;
/
BEGIN
    delete_invoice(391);
END;
/

--Create a transaction nested within a stored procedure taht inserts a new record in the customer
CREATE OR REPLACE PROCEDURE new_customer(cid IN NUMBER, fname IN VARCHAR2, lname IN VARCHAR2, new_email IN VARCHAR2)
IS
BEGIN
    INSERT INTO Customer (CUSTOMERID, FIRSTNAME, LASTNAME, EMAIL) VALUES (cid, fname, lname, new_email);
    COMMIT;
    
    EXCEPTION
        WHEN OTHERS THEN
           ROLLBACK;
END;
/
BEGIN
    new_customer(61, 'Jeffy', 'Jeff', 'jeff@gmail.com');
END;
/
---------------------------------------------------------------------------------------------------------------------------------
---6.0 Triggers
--Create an after insert trigger on the employee table fired after a new record is inserted into the table
CREATE OR REPLACE TRIGGER emp_trigger
AFTER INSERT ON Employee
FOR EACH ROW
BEGIN
    INSERT INTO NewEmployee VALUES (:new.EMPLOYEEID, :new.FIRSTNAME, :new.LASTNAME);
END;

--Create an after update trigger on the album table that fires after a row is inserted in the table  
CREATE OR REPLACE TRIGGER update_trigger
AFTER UPDATE ON Album
FOR EACH ROW
BEGIN 
    INSERT INTO Album_Audit VALUES(:old.ALBUMID, :new.TITLE, :old.TITLE, :old.ARTISTID);
END;

--Create an after delete trigger on the customer table taht fires after a row is deleted from the table
CREATE OR REPLACE TRIGGER delete_trigger
AFTER DELETE ON Customer
FOR EACH ROW
BEGIN
    INSERT INTO Customer_Audit VALUES (:old.CUSTOMERID, :old.FIRSTNAME, :old.LASTNAME, :old.PHONE, :old.EMAIL);
END;
-------------------------------------------------------------------------------------------------------------------------------------
--7.0 JOIN
--Create an inner join tht joins customers and orders and specifies the name of the customer and the invoiceID
SELECT C.FIRSTNAME AS "First Name", C.LASTNAME AS "Last Name",  I.INVOICEID AS "InvoiceID"
FROM Customer C
INNER JOIN
INVOICE I
ON C.CUSTOMERID = I.CUSTOMERID;

--Create an outer join that joins the customer and invoice table, specifying the CustomerId, firstname, lastname, invoiceId, and total
SELECT C.CUSTOMERID as "CustomerId", C.FIRSTNAME as "First Name", C.LASTNAME as "Last Name", I.INVOICEID as "InvoiceId", I.TOTAL as "Total"
FROM Customer C
FULL OUTER JOIN
INVOICE I
ON C.CUSTOMERID = I.CUSTOMERID;

--Create a right join that joins album and artist specifying artist name and title
SELECT AL.TITLE as "Title", AR.NAME as "Name"
FROM Album AL
RIGHT JOIN
Artist AR
ON AR.ARTISTID = AL.ARTISTID;

--Create a cross join that joins album and artist and sorts by artist name in ascending order
SELECT * FROM Artist A
CROSS JOIN
Album
ORDER BY A.NAME ASC;

--Perform a self-join on the employee table, joining on the reportsto column
SELECT * FROM Employee E1
INNER JOIN Employee E2
ON E1.REPORTSTO = E2.REPORTSTO;

----------------------------------------------------------------------------------------------------------------------------------------
--9.0 Administration
--See Github folder