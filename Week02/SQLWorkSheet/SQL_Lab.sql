/***2.0 SQL QUERIES***/
--2.1
select * from Employee;
select * from Employee where lower(lastname) = 'king'; 
select * from Employee where lower(firstname) = 'andrew' AND ReportsTo is null;

--2.2
select * from Album order by title DESC; 
select FirstName from Customer order by City ASC; 

--2.3
--select * from Genre order by name ASC; 
insert into Genre values(26, 'Industrial Electronic');
insert into Genre values(27, 'Neddle'); 

--select * from Employee;
insert into Employee values(9, 'Wilson', 'Wilford', 
    'Customer Service Rep.', 1, 
    TO_DATE('1967-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss'),
    TO_DATE('2017-11-11 10:00:00','yyyy-mm-dd hh24:mi:ss'),
    '1234 Billson DR', 'Calgary', 'AB', 'Canada', 'T2P 5M5',
    '+1 (403) 262-1245', null, 'wilford@chinookcorp.com');
insert into Employee values(10, 'Mann', 'Nathanial', 
    'Intern', 6, 
    TO_DATE('1999-01-02 00:00:00','yyyy-mm-dd-hh24:mi:ss'),
    TO_DATE('2016-11-13 09:00:00','yyyy-mm-dd-hh24:mi:ss'),
    '7652 International  DR', 'Calgary', 'AB', 'Canada', 'T2P 5M5',
    '+1 (403) 262-2147', null, 'internationalmanofmystery@chinookcorp.com');

--select * from Customer; 
insert into Customer values(
    60, 'Michael', 'Mann', null,
    '121 Oblong Ave', 'Dublin', 'Dublin', 'Ireland', null,
    '+353 01 67521457', null, 'hafferlyfafferson@apple.ie', 
    3); 
insert into Customer values(
    61, 'Pharah', 'Amari', 'Overwatch',
    '121 Osiris LN', 'Cairo',null, 'Egypt', null, '+90 123 4567890', null,
    'justicerainse@overwatch.com',
    4); 

--2.4
update Customer set firstname = 'Robert', lastname = 'Walter'
    where firstname = 'Aaron' and lastname = 'Mitchell';
update Artist set name = 'CCR' where name = 'Creedence Clearwater Revival';

--2.5
select * from invoice where billingaddress like 'T%'; 

--2.6
select * from invoice where total between 15 and 50;
select * from employee where hiredate between
    TO_DATE('2003-06-01','yyyy-mm-dd') and TO_DATE('2004-03-01', 'yyyy-mm-dd'); 

--2.7
delete from (select * from invoiceline where invoiceid in (
    select invoiceid from invoice where customerid in (
    select customerid from customer where firstname = 'Robert' and lastname = 'Walter'))); 
delete from (select * from invoice where customerid in (
    select customerid from customer where firstname = 'Robert' and lastname = 'Walter')); 
delete from customer where firstname = 'Robert' and lastname = 'Walter';

/
--3.0 SQL FUNCTIONS
--3.1--------------------------------
create or replace function get_current_time
RETURN TIMESTAMP
IS
    cur_Time TIMESTAMP; 
BEGIN
    cur_Time := CURRENT_TIMESTAMP;
    RETURN cur_Time; 
END; 
/

create or replace function get_mediatype_length(mID IN NUMBER)
RETURN NUMBER
IS
    len number(3); 
BEGIN
    select LENGTH(name) into len from mediatype where mediatypeid = mID; 
    RETURN len; 
END;
/

--3.2--------------------------------
CREATE OR REPLACE FUNCTION avg_invoices
RETURN NUMBER
IS
    average float;
BEGIN
    select avg(total) into average from invoice; 
    RETURN average;
END; 
/

CREATE OR REPLACE FUNCTION max_track_price
RETURN VARCHAR
IS
    max_price_track VARCHAR2(200); 
BEGIN
    select name into max_price_track from track where unitprice = (
        select max(unitprice) from track) and rownum = 1 order by name asc;
    RETURN max_price_track; 
END;
/
--3.3--------------------------------
CREATE OR REPLACE FUNCTION avg_invoiceline_total
RETURN NUMBER
IS
    avg_price NUMBER;   
BEGIN
    select avg(unitprice) into avg_price from 
        (select unitprice from invoiceline group by unitprice);
    RETURN avg_price; 
END;
/

--3.4--------------------------------
CREATE OR REPLACE FUNCTION get_emp_50_or_younger
RETURN SYS_REFCURSOR
IS
    emps_cur SYS_REFCURSOR;
    id NUMBER;
    fn VARCHAR2(20);
    ln VARCHAR2(20); 
BEGIN
    OPEN emps_cur FOR select employeeid,firstname,lastname 
        from employee where birthdate > TO_DATE('1968-12-31','yyyy-mm-dd'); 

    RETURN emps_cur; 
END;
/

--3.0 Function calls --------------------------------
DECLARE
    cur_Time TIMESTAMP;
    mLen NUMBER;
    average float;
    maxP VARCHAR2(200); 
    avg_price NUMBER; 

    emps_cur SYS_REFCURSOR;
    id NUMBER;
    fn VARCHAR2(20);
    ln VARCHAR2(20); 
    
BEGIN
    cur_Time := get_current_time();
    mLen := get_mediatype_length(3); 
    
    average := avg_invoices(); 
    maxP := max_track_price();  
    
    avg_price := avg_invoiceline_total(); 
    
    emps_cur := get_emp_50_or_younger(); 
    
    --PRINT RESULTS
    DBMS_OUTPUT.PUT_LINE('current time: ' || cur_Time); 
    DBMS_OUTPUT.PUT_LINE('length of "MPEG audio file": ' || mLen);  
    
    DBMS_OUTPUT.PUT_LINE('average invoice total: ' || TO_CHAR(average, '99.99'));  
    DBMS_OUTPUT.PUT_LINE('a track with the highest price: ' || maxP);  
    
    DBMS_OUTPUT.PUT_LINE('average invoiceline price: ' || avg_price);  
    
    emps_cur := get_emp_50_or_younger(); 
    LOOP
        FETCH emps_cur INTO id, fn, ln; 
        EXIT WHEN emps_cur%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Employee ID ' || id || ': ' || fn || ' ' || ln);         
    END LOOP; 

    
END;
/


--4.0 STORED PROCEDURES

--4.1--------------------------------
CREATE OR REPLACE PROCEDURE select_emp_names(emp_cur OUT SYS_REFCURSOR)
IS
BEGIN
    OPEN emp_cur FOR SELECT firstname, lastname FROM employee;
END;
/
DECLARE
    emp_cursor SYS_REFCURSOR;
    fn VARCHAR2(20); 
    ln VARCHAR2(20); 
BEGIN
    select_emp_names(emp_cursor);
    LOOP
        FETCH emp_cursor INTO fn, ln; 
        EXIT WHEN emp_cursor%NOTFOUND; 
        DBMS_OUTPUT.put_line(fn || ' ' || ln); 
    END LOOP; 
END;
/

--4.2--------------------------------
CREATE OR REPLACE PROCEDURE update_emp(pers_info IN SYS_REFCURSOR, empID IN NUMBER)
IS
    blank NUMBER; 
    ln VARCHAR(20); 
    fn VARCHAR(20); 
    ttl VARCHAR(30); 
    rt NUMBER; 
    bd DATE;
    hd DATE;
    addr VARCHAR(70); 
    cty VARCHAR(40); 
    st VARCHAR(40); 
    ctn VARCHAR(40); 
    zc VARCHAR(10); 
    ph VARCHAR(24); 
    fx VARCHAR(24);
    eml VARCHAR(60); 
BEGIN
    FETCH pers_info INTO blank, ln, fn, ttl, rt, bd, hd, addr, cty, st, ctn, zc, ph, fx, eml; 
    UPDATE Employee SET 
        lastname = ln,
        firstname = fn,
        title = ttl,
        reportsto = rt,
        birthdate = bd,
        hiredate = hd,
        address = addr,
        city = city,
        state = st,
        country = ctn,
        postalcode = zc,
        phone = ph,
        fax = fx,
        email = eml
        WHERE employeeid = empID; 
END;
/
DECLARE
    emp_curs SYS_REFCURSOR; 
BEGIN
    insert into Employee values(11, 'Mann', 'Nathanial', 
        'Intern', 6, 
        TO_DATE('1999-01-02 00:00:00','yyyy-mm-dd-hh24:mi:ss'),
        TO_DATE('2016-11-13 09:00:00','yyyy-mm-dd-hh24:mi:ss'),
        '7652 International  DR', 'Calgary', 'AB', 'Canada', 'T2P 5M5',
        '+1 (403) 262-2147', null, 'internationalmanofmystery@chinookcorp.com');
    OPEN emp_curs FOR select * from employee where employeeid = 4; -- gets employee with id 4 for data
    update_emp(emp_curs,11); 
END;
/

CREATE OR REPLACE PROCEDURE get_managers(empID IN NUMBER, managers OUT SYS_REFCURSOR) 
IS
    
BEGIN
    OPEN managers FOR select firstname, lastname, title from employee 
        start with employeeid in (select reportsto from employee where employeeid = 7)
        connect by prior reportsto = employeeid;
END; 
/
DECLARE
    cur SYS_REFCURSOR; 
    fn VARCHAR2(20); 
    ln VARCHAR2(20); 
    ttl VARCHAR(30); 
BEGIN
    get_managers(7, cur);
    select firstname, lastname into fn, ln
        from employee where employeeid = 7; 
    DBMS_OUTPUT.put_line('MANAGERS FOR ' || fn || ' ' || ln); 
    
    LOOP
        FETCH cur INTO fn, ln, ttl; 
        EXIT WHEN cur%NOTFOUND; 
        DBMS_OUTPUT.put_line(ttl || ': ' ||fn || ' ' || ln); 
    END LOOP; 
END;
/

--4.3--------------------------------
CREATE OR REPLACE PROCEDURE get_name_company(custID IN NUMBER,
    fn OUT VARCHAR2, ln OUT VARCHAR2, cp OUT VARCHAR2)
IS
BEGIN
    select firstname, lastname, company into fn, ln, cp from Customer 
        where customerid = custID; 
END; 
/
DECLARE
    fn  VARCHAR2(40);
    ln  VARCHAR2(20);
    cp  VARCHAR2(80);
BEGIN
    get_name_company(61, fn, ln, cp); 
    if cp is null then
        cp := 'N/A';
    end if; 
    DBMS_OUTPUT.put_line(fn || ' ' || ln || ' - ' || cp); 
END;
/

--5.0 TRANSACTIONS----------------

CREATE OR REPLACE PROCEDURE delete_invoice(invID IN NUMBER)
IS
BEGIN
    SET TRANSACTION NAME 'delete_invoice'; 
    
    delete from (select * from invoiceline where invoiceid = invID);     
    SAVEPOINT after_delete_invoice_line;
    
    delete from invoice where invoiceid = invID; 
    SAVEPOINT after_delete_invoice;   
    
END; 
/
call delete_invoice(145);
rollback;

CREATE OR REPLACE PROCEDURE insert_customer(cID IN NUMBER, fn IN VARCHAR2, ln IN VARCHAR2, ph IN VARCHAR2, eml IN VARCHAR2)
IS
    numRowsBefore NUMBER;
    numRowsAfter NUMBER;
BEGIN
    SET TRANSACTION NAME 'insert_customer'; 
    select count(customerid) into numRowsBefore from customer; 
    DBMS_Output.put_line('Before: ' || numRowsBefore);
    SAVEPOINT before_insert_customer;
    
    insert into customer(customerid, firstname, lastname, phone, email) 
        values(cID, fn, ln, ph, eml);
    
    select count(customerid) into numRowsAfter from customer;
    DBMS_Output.put_line('After: ' || numRowsAfter);
    if (numRowsBefore + 1) != numRowsAfter then
        rollback; 
    end if; 
    SAVEPOINT after_insert_customer;
    
END; 
/
call insert_customer(63, 'Hafferly', 'Fafferson', '+1 (123)456-7890', 'email@email.com'); 

--6.1 TRIGGERS----------------

CREATE OR REPLACE TRIGGER after_insert_emp_tr
AFTER INSERT ON Employee
BEGIN
     DBMS_OUTPUT.put_line('NEW EMPLOYEE!'); 
END;
/
insert into employee(employeeid, firstname, lastname) values(68,'wadsworth', 'codson'); 

CREATE OR REPLACE TRIGGER after_update_album_tr
AFTER UPDATE ON Album
FOR EACH ROW
BEGIN
    dual;
END;
/
CREATE OR REPLACE TRIGGER after_delete_cust_tr
AFTER DELETE ON Customer
FOR EACH ROW
BEGIN
    dual;
END;
/


--7.0 JOINS----------------

select lastname, firstname, invoiceid from 
    (select * from customer C inner join invoice I on C.customerid = I.customerid)
    order by lastname asc;
select I.invoiceid, C.customerid, firstname, lastname from 
    customer C full outer join Invoice I on I.customerid=C.customerid
    order by I.invoiceid; 
select AR.name, AL.title from Artist AR  right join Album AL 
    on AR.artistid = AL.artistid
    order by AR.name;
select * from Album AL cross join Artist AR order by AR.name asc; 
select E1.firstname, E1.lastname, E2.firstname, E2.lastname, E1.reportsto from
    Employee E1, Employee E2 
    where E1.employeeid <> E2.employeeid and
    E1.reportsto = E2.reportsto
    order by E1.lastname; 

--9.0 ADMINISTRATION ----------------


