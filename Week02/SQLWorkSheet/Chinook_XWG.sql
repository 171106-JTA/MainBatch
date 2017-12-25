--Xavier Garibay - Chinook exercises
select * from Employee;
select * from Employee where LASTNAME='King';
select * from employee where FIRSTNAME='Andrew' and REPORTSTO is null;
select * from album order by TITLE desc;
select firstname from employee order by city asc;
select * from Genre;
insert into genre
values(26, 'K-Pop');
insert into genre
values(27, 'Dubstep');
select * from employee;
insert into employee
values(9,'Garibay','Xavier', 'King', null, '23-FEB-42', '14-MAY-05', '648 Dont Rd', 'Somewhere', 'PA', 'USA', '20170', '610949494994','5464646445','xking@email.com');
insert into employee
values(10,'Doe','John', 'Servant', null, '02-JAN-01', '03-AUG-06', '676 Yeah Rd', 'Someplace', 'MO', 'USA', '45645', '7894561324','152615482','jserve@email.com');
select * from customer;
insert into customer
values(60,'Silverman','Sarah','Funny inc', '123 hollywood rd', 'West Covina', 'Oregon', 'USA', '12321', '1234567894', '9876543210','ssilv@email.org',3);
insert into customer
values(60,'Seinfeld','Jerry','Dour inc', '789 hollywood rd', 'PlaceTown', 'Florida', 'USA', '14321', '1534567894', '9176543210','jSein@email.org',5);
update customer
set FIRSTNAME='Robert',LASTNAME='Walter'
where FIRSTNAME='Aaron' and LASTNAME='Mitchell';
select * from artist;
update artist
set NAME='CCR' where NAME='Creedence Clearwater Revival';
select * from invoice;
select * from INVOICE
where BILLINGADDRESS like 'T%';
select * from invoice
where total between 15 and 50;
select * from employee
where HIREDATE between '01-JUN-03' and '01-MAR-04';

alter table invoice
drop constraint FK_INVOICECUSTOMERID;
alter table invoice
add constraint fk_invoicecustomerid
FOREIGN KEY (CUSTOMERID) REFERENCES customer(CUSTOMERID) ON DELETE CASCADE;

alter table invoiceline
drop constraint FK_INVOICELINEINVOICEID;
alter table invoiceline
add constraint FK_INVOICELINECUSTOMERID
FOREIGN KEY (invoiceid) REFERENCES invoice(invoiceid) ON DELETE CASCADE;

delete from Customer
where FIRSTNAME='Robert' and LASTNAME='Walter';

create or replace function get_time return varchar2
is
    cur_time varchar2(4000);
begin
    cur_time := TO_CHAR(SYSDATE, 'HH24:MI:SS');
    return cur_time;
end;
/
declare
    cur_time varchar(4000);
begin
    cur_time:=get_time();
    dbms_output.put_line(cur_time);
end;
/
create or replace function media_length return number
is
    text varchar2(4000);
begin
    select name into text from mediatype where MEDIATYPEID=1;
    return length(text);
end;
/
create or replace function avg_invoice return number
is
    average number;
begin
    select avg(total) into average from invoice;
    return average;
end;
/
declare
    average number;
begin
    average:=avg_invoice();
    dbms_output.put_line(average);
end;
/
create or replace function expensive return SYS_REFCURSOR
is
    etrack SYS_REFCURSOR;
    price number;
begin
    select max(unitprice) into price from track;
    open etrack for select * from track where unitprice=price;
    return etrack;
end;
/
select max(unitprice) from track;
select * from mediatype;
declare
    price number;
begin
    select max(unitprice) into price from track;
    dbms_output.put_line(price);
end;
/
create or replace function avg_line return number 
is
    average number;
begin
    select avg(unitprice) into average from invoiceline;
    return average;
end;
/
declare
    average number;
begin
    average:=avg_line();
    dbms_output.put_line(average);
end;
/
create or replace function emp_age return SYS_REFCURSOR
is
    emps SYS_REFCURSOR;
begin
    open emps for select * from employee where birthdate<'01-JAN-68';
    return emps;
end;
/

create or replace procedure emp_name(names out SYS_REFCURSOR)
is
begin
    open names for select firstname, lastname from employee;
end;
/

--check emp_name
declare
    firstname varchar2(20);
    lastname varchar2(20);
    emps SYS_REFCURSOR;
begin
    emp_name(emps);
    loop  
        fetch emps into firstname, lastname;
        dbms_output.put_line(firstname || ' ' || lastname);
        exit when emps%NOTFOUND;
    end loop;
end;
/
create or replace procedure update_emp(fname in varchar2, lname in varchar2, new_address in varchar2)
is
begin
    update employee set address=new_address where fname=firstname and lname=lastname;
end;
/
call update_emp('Xavier', 'Garibay', 'Detroit');
select * from employee where firstname='Xavier' and lastname='Garibay';

create or replace procedure manager(fname in varchar2, lname in varchar2, mfname out varchar2, mlname out varchar2)
is
begin
    select firstname, lastname into mfname, mlname from employee where employeeid=(select reportsto from employee where firstname=fname and lastname=lname); 
end;
/
declare
    fname varchar2(20);
    lname varchar2(20);
begin
    manager('Laura','Callahan', fname, lname);
    dbms_output.put_line(fname || ' ' || lname);
end;
/

create or replace procedure cust_comp(cust_id in number, fname out varchar2, lname out varchar2, comp out varchar2)
is
begin
    select firstname, lastname, company into fname, lname, comp from customer where cust_id=customerid;
end;
/
declare
    fname varchar2(20);
    lname varchar2(20);
    comp varchar2(20);
begin
    cust_comp(5, fname, lname, comp);
    dbms_output.put_line(fname || ' ' || lname|| ' - ' || comp);
end;
/
create or replace procedure delete_invoice(inv_ID in number)
is
begin
    delete from invoice where invoiceid=inv_id;
end;
/
create or replace procedure new_cust(cust_id in number, fname in varchar2, lname in varchar2, comp in varchar2, addr in varchar2, city in varchar2, state in varchar2,
                                    coun in varchar2, post in varchar2, phone in varchar2, fax in varchar2, email in varchar2, sup_id in number)
is
begin
    insert into customer
    values(cust_id, fname, lname, comp, addr, city, state, coun, post, phone, fax, email, sup_id);
end;
/
create or replace trigger emp_trigger
after insert on employee
for each row
begin
    DBMS_OUTPUT.PUT_LINE('New employee');
end;
/
create or replace trigger album_trigger
after update of title, artistid on album
for each row
begin
    dbms_output.put_line('New album');
end;
/
create or replace trigger customer_trigger
after delete on customer
for each row
begin
    dbms_output.put_line('Gone');
end;
/
--other trigger
select c.firstname, c.lastname, i.invoiceid, i.total from customer c
inner join invoice i
on c.customerid=i.customerid; 

select c.customerid, c.firstname, c.lastname, i.invoiceid, i.total from customer c
full outer join invoice i
on c.customerid=i.customerid; 

select ar.name, al.TITLE from album al
right join artist ar
on al.ARTISTID=ar.ARTISTID;

select * from artist cross join album
order by artist.name asc;

select emp.firstname, emp.lastname, emp.reportsto, mana.firstname, mana.lastname from employee emp
inner join employee mana
on emp.REPORTSTO=mana.employeeID;

commit;
