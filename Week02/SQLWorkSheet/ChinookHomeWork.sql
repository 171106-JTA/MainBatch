/*2.1 SELECT*/
select * from employee;
select * from employee where lastname = 'King';
select * from employee where firstname = 'Andrew' and reportsto is null;

/*2.2 ORDER BY*/
select * from album order by title desc;
select firstname from customer order by city asc;

/*2.3 INSERT INTO*/
select * from genre;
insert into genre values(26, 'bobbert');
insert into genre values(27, 'bobbertology');

describe employee;
select * from employee;
insert into employee values(9, 'Bobbertson', 'Bobbert', 'The Bobber', 3, '17-FEB-14', '17-FEB-14', 'Bobbert Lane', 'Bobtown', 
'Virginia', 'United States', '8394', '3456789', '34567891', 'bobbert@bobbert.com');
insert into employee values(10, 'Robbertson', 'Robbert', 'The Robber', 3, '17-FEB-14', '17-FEB-14', 'Robbert Lane', 'Robtown', 
'Virginia', 'United States', '8394', '3456789', '34567891', 'robbert@bobbert.com');

describe customer;
select * from customer;
insert into customer values(60, 'Bobbert', 'Bobbertson', 'BobCo', 'Bobbert Lane', 'Bobtown', 
'Virginia', 'United States', '8394', '3456789', '34567891', 'bobbert@bobbert.com', 5);
insert into customer values(61, 'Robbert', 'Robbertson', 'BobCo', 'Robbert Lane', 'Robtown', 
'Virginia', 'United States', '8394', '3456789', '34567891', 'robbert@bobbert.com', 5);

/*2.4 UPDATE*/
update customer set firstname = 'Robert', lastname = 'Walter' where firstname = 'Aaron' and lastname = 'Mitchell';

update artist set name = 'CCR' where name = 'Creedence Clearwater Revival';

/*2.5 LIKE*/
select invoiceid from invoice where billingaddress like 'T%';

/*2.6*/
select * from invoice where total > 15 and total < 50;
select employeeid, firstname, lastname from employee where hiredate > '01-JUN-03' and  hiredate < '01-MAR-04';

/*2.7*/
alter table invoice drop constraint fk_invoicecustomerid;
delete from customer where firstname = 'Robert' and lastname = 'Walter';

/*3.1*/
/
create or replace function get_time
return the_time
is
    the_time time;
begin
    the_time := current_time;
end;
/
declare
begin
    dbms_output.put_line(get_time());
end;
/

create or replace function getLength(n varchar2)
return number
is
    len number;
begin
    select mediatypeid into len from mediatype where name = n;
    return len;
end;
/
declare
begin
    dbms_output.put_line(getLength('MPEG audio file'));
    
end;
/

--3.2
create or replace function getAverage
return number
is 
    tot number;
begin
    select avg(total) into tot from invoice;
    return tot;
end;
/

declare
    tot number;
begin
    tot := getAverage;
    dbms_ouput.put_line(tot);
end;
/
create or replace function getMax
return number
is
    tot number;
begin
    select max(unitprice) into tot from track;
    return tot;
end;
/

declare
    x number;
begin 
    x:= getMax;
    dbms_ouptput.put_line(x);
end;
/

--3.3
create or replace function getAvg
return number
is
    t number;
begin
    select unitprice into t from invoiceline;
    return t;
end;
/

--3.4
create or replace function getPeople
return number
is
    t number;
begin
    select employeeid into t from employee where birthdate > '31-DEC-1967';
    return t;
end;
/

--4.1
create or replace procedure something1
IS
    CURSOR curse IS
        select firstname, lastname from employee;
    fn varchar2;
    ln varchar2;
BEGIN
    OPEN curse;
    LOOP
        FETCH curse into fn, ln;
        EXIT WHEN curse%NOTFOUND;
    END LOOP;
    CLOSE curse;
END;
/

--4.2
create or replace procedure something2
IS
    CURSOR curse IS
        select reportsto from employee;
    r number;
BEGIN
    OPEN curse;
    LOOP
        FETCH curse into r;
        EXIT WHEN curse%NOTFOUND;
    END LOOP;
    CLOSE curse;
END;
/

--4.3
create or replace procedure something3
IS
    CURSOR curse IS
        select firstname, lastname, title from employee;
    fn varchar2;
    ln varchar2;
    ti varchar2;
BEGIN
    OPEN curse;
    LOOP
        FETCH curse into fn, ln, ti;
        EXIT WHEN curse%NOTFOUND;
    END LOOP;
    CLOSE curse;
END;
/

--5.0
CREATE OR REPLACE PROCEDURE delete_invoice(inv IN number)
IS
BEGIN
    delete from invoiceline where INVOICEID = inv;
    delete from invoice where INVOICEID = inv;
END;
/

CREATE OR REPLACE PROCEDURE insert_invoice(inv IN number)
IS
BEGIN
    insert into invoice(invoiceid) values(inv);
    insert into invoiceline(invoiceid) values(inv);
    
END;
/

--6.1

--7.1
select a.firstname, a.lastname, b.INVOICEID from customer a
inner join invoice b
on a.customerid = b.CUSTOMERID;

--7.2

select a.firstname, a.lastname, b.INVOICEID, b.total from customer a
full outer join invoice b
on a.customerid = b.CUSTOMERID;

--7.3

select a.NAME, b.TITLE from artist a
right join album b
on a.ARTISTID = b.ARTISTID;

--7.4
select * from artist a
cross join album b
order by a.ARTISTID asc;

--7.5
select * from employee a
join employee b
on a.REPORTSTO = b.REPORTSTO;
