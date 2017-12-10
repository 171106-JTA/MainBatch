select emp_id from employees where authority != 1;

select * from employees;
update employees set authority = 3 where emp_id = 'jmanderley';
update employees set authority = 4 where emp_id = 'mchow';
update employees set authority = 2 where emp_id = 'anavarre';

select * from req_status;

select * from messages;
delete from messages where sender is null;
commit;

insert into EMPLOYEES values('pdenton', 'chameleon', 1);
insert into person(emp_id, firstname, lastname) values('pdenton', 'Paul', 'Denton');

delete from REQ_STATUS;
delete from REQUEST;
delete from PERSON;
delete from EMP_AMOUNT;
delete from EMPLOYEES;

drop table req_status;
drop table request;
drop table person;
drop table emp_amount;
drop table employees;

create table employees(
    emp_id varchar2(25) primary key,
    pass varchar2(25),
    authority number(1) --1 is basic employee, 2 is direct supervisor, 3 is dept head, 4 is BenCo
);

create table person(
    emp_id varchar2(25) primary key,
    firstname varchar2(20),
    lastname varchar2(20),
    ssn number(8),
    address varchar2(50),
    phone number(9),
    email varchar2(35),
    constraint fk_empl_info foreign key(emp_id) references employees(emp_id)
);

create table request(
    req_id number(9,2) primary key,
    emp_id varchar2(25),
    money number(9,2),
    req_date date,
    constraint fk_emp_req foreign key(emp_id) references employees(emp_id)
);

create table req_status(
    req_id number(9,2) primary key,
    pending number(1),
    grade1 float,
    grade2 float,
    grade3 float,
    --message varchar2(300),
    constraint fk_req_id foreign key(req_id) references request(req_id)
);

create table emp_amount(
    emp_id varchar2(25) primary key,
    reimb_avail number(9,2),
    constraint fk_reim_emp foreign key(emp_id) references employees(emp_id)
);

commit;

create table messages(
    datesent date,
    sender varchar2(25),
    receiver varchar(25),
    message varchar2(1000)
);

create or replace procedure send_message(s in messages.sender%type,r in messages.receiver%type, m in messages.message%type)
is
begin
    insert into messages values(current_date, s, r, m);
end;

/
create or replace procedure new_employee(fn in PERSON.FIRSTNAME%type, ln in PERSON.LASTNAME%type, eid in PERSON.EMP_ID%type, p in EMPLOYEES.PASS%type, em in person.email%type,
ad in person.address%type, sn in person.ssn%type, ph in person.phone%type)
is 
begin
    insert into employees(emp_id, pass, authority) values(eid, p, 1);
    insert into person(firstname, lastname, emp_id, email, address, ssn, phone) values(fn, ln, eid, em, ad, sn, ph);
    insert into emp_amount values(eid, 1000);
end;
/

begin
    NEW_EMPLOYEE('Paul', 'Denton', 'pdenton', 'chameleon');
end;
/

select * from employees;
select * from person;
select * from EMP_AMOUNT;

select a.firstname, a.lastname, b.REIMB_AVAIL from person a
inner join emp_amount b
on a.emp_id = b.emp_id;

select a.firstname, a.lastname, a.EMP_ID, b.PASS from person a
inner join employees b
on a.emp_id = b.EMP_ID;
/
rollback;
drop trigger req_id_seq_trigger;
drop sequence req_id_seq;
/
create sequence req_id_seq
    start with 1
    increment by 1;
/
create or replace trigger req_id_seq_trigger
before insert on request
for each row
begin
    if :new.req_id is null then
    select req_id_seq.nextval into :new.req_id from dual;
    end if;
end;
/
/*was working on this procedure to apply for a reimbursement*/
create or replace procedure make_request(eid in REQUEST.EMP_ID%type, mon in REQUEST.MONEY%type)
is
    avail EMP_AMOUNT.REIMB_AVAIL%type;
    rid REQUEST.REQ_ID%type;
begin
    select reimb_avail into avail from emp_amount where emp_id = eid;
    if avail - mon > 0 then
        insert into request(emp_id, money, req_date) values(eid, mon, current_date);
        select req_id into rid from request where emp_id = eid and req_date = current_date;
        insert into req_status(req_id, pending) values(rid, 1);
    end if;
end;
/
--test to insert request
declare
    emp_id REQUEST.EMP_ID%type;
    money REQUEST.MONEY%type;
begin
    emp_id:='pdenton';
    money:=400;
    MAKE_REQUEST(emp_id, money);
end;
/
select * from request;
select * from req_status;


--attempt to triple join
select a.FIRSTNAME, a.LASTNAME, b.MONEY, b.REQ_ID, c.PENDING from person a
inner join request b
on a.emp_id = b.emp_id
inner join req_status c
on c.REQ_ID = b.REQ_ID;

/*
    the following are a procedures for corporate to approve/grade reimbursemnet requests.
    we will start by first making one for the supervisor.
*/

--supervisor grading
/
create or replace procedure supervisor_approval(grade in REQ_STATUS.GRADE1%type, rid in REQ_STATUS.REQ_ID%type)
is
begin
    update REQ_STATUS set grade1 = grade where req_id = rid;
end;
/

call SUPERVISOR_APPROVAL(43.3,9);--have java put a constraint on value being between 0 and 100%
/
select * from req_status;


--making procedure for the dept heads approval
/
create or replace procedure dept_head_approval(grade in REQ_STATUS.GRADE2%type, rid in REQ_STATUS.REQ_ID%type)
is
begin
    update REQ_STATUS set grade2 = grade where req_id = rid;
end;
/

call dept_head_APPROVAL(89.23,9);--have java put a constraint on value being between 0 and 100%
/
select * from req_status;


--making benco approval procedure
/
create or replace procedure benco_approval(grade in REQ_STATUS.GRADE3%type, rid in REQ_STATUS.REQ_ID%type)
is
begin
    update REQ_STATUS set grade3 = grade where req_id = rid;
    
end;
/

call benco_APPROVAL(75.3,9);--have java put a constraint on value being between 0 and 100%
/
select * from req_status;
/
update REQ_STATUS set grade1 = 90, grade2 = 89, grade3 = 78 where REQ_ID = 2;
commit;
rollback;
/

create or replace function get_result(rid in REQ_STATUS.REQ_ID%type)
return number
is
    score1 number;
    score2 number;
    --score3 number;
    
begin
    select grade1, grade2 into score1, score2 from req_status where req_id = rid;
    
    return (score1 + score2)/2;
end;

/
begin
    dbms_output.put_line(get_result(2));
end;
/
create or replace procedure approve(rid in req_status.req_id%type)
is
    r request.money%type;
    eid emp_amount.emp_id%type;
begin
    select emp_id, money into eid, r from request where REQ_ID = rid;
    update REQ_STATUS set PENDING = 2 where REQ_ID = rid;
    update EMP_AMOUNT set REIMB_AVAIL = reimb_avail - r where emp_id = eid;
end;
/
create or replace procedure deny(rid in req_status.req_id%type)
is
begin
    update REQ_STATUS set PENDING = 0 where REQ_ID = rid;
end;
/
drop procedure approve;
drop procedure deny;
/
call approve(2);
call deny(3);
/
select * from emp_amount;
select * from req_status;
/
select * from request;
drop procedure check_difference_of_dates;
/
create or replace function check_difference_of_dates
return integer
is
    rdate REQUEST.REQ_DATE%type;
begin
    select req_date into rdate from request where EMP_ID = 'jmanderley';
    return current_date - rdate;
end;
/
declare
    --rdate request.req_date%type;
    ans integer;
begin
    ans := check_difference_of_dates;
    dbms_output.put_line(ans);
end;
/
create or replace procedure renew_reimb(eid in employees.emp_id%type)
is
begin
    update emp_amount set reimb_avail = 1000 where emp_id = eid;
end;