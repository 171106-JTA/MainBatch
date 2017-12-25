drop table app_info;
drop table grade_format;
drop table app_status;
drop table repay_app;
drop table repay_event;
drop table emp_status;
drop table login;
drop table employee;
drop table app_rejected;
drop sequence app_info_seq;
drop sequence app_seq;
drop sequence file_seq;
drop sequence app_status_seq;


create table employee(
    emp_id number,
    fname varchar2(4000),
    lastname varchar2(4000),
    email varchar2(4000),
    repay_awarded number,
    repay_pending number,
    department number,
    sup_id number,
    constraint pk_emp_id primary key(emp_id),
    constraint fk_supemp_id foreign key(sup_id) references employee(emp_id)
    );

create table login(
    emp_id number,
    emp_username varchar2(4000),
    emp_password varchar2(4000),
    constraint pk_logemp_id primary key(emp_id),
    constraint fk_logemp_id foreign key(emp_id) references employee(emp_id)
    );

create table emp_status(
    emp_id number,
    chair number,
    benco number,
    constraint pk_statemp_id primary key(emp_id),
    constraint fk_statemp_id foreign key(emp_id) references employee(emp_id)
    );

create table repay_event(
    ev_type_id number,
    ev_descr varchar2(4000),
    ev_repay number,
    constraint pk_ev_id primary key(ev_type_id)
    );

create table repay_app(
    app_id number,
    emp_id number,
    constraint pk_app_id primary key(app_id)
);

create table app_status(
    app_id number,
    urgent number,
    sup_apr number,
    chair_apr number,
    benco_apr number,
    passed number,
    approved number,
    completed number,
    info_hold number,
    recent_date date,
    constraint pk_statusapp_id primary key(app_id),
    constraint fk_statusemp_id foreign key(app_id) references repay_app(app_id)
);

create table grade_format(
    ev_grade_id number,
    pass_grade varchar2(4000),
    presentation number,
    constraint pk_grade primary key(ev_grade_id)
);

create table app_info(
    app_id number,
    ev_date date,
    ev_descr varchar2(4000),
    ev_time varchar2(4000),
    ev_cost number,
    ev_type_id number,
    ev_grade_id number,
    constraint pk_infoapp_id primary key(app_id),
    constraint fk_grade foreign key(ev_grade_id) references grade_format(ev_grade_id),
    constraint fk_appemp_id foreign key(ev_type_id) references repay_event(ev_type_id)
);

create table app_rejected(
    app_id number,
    reason varchar2(4000),
    constraint pk_rejectedapp_id primary key(app_id),
    constraint fk_rejectedapp_id foreign key(app_id) references repay_app(app_id)
);

create table app_files(
    file_id number,
    app_id number,
    file_name varchar2(4000),
    file_data blob,
    file_description varchar2(4000),
    constraint pk_appfile_id primary key(file_id),
    constraint fk_appfile_id foreign key(app_id) references repay_app(app_id)
);
select * from app_pass;
create table app_pass(
    app_id number,
    grade_file_id number,
    grade_input_mark number,
    constraint pk_apppass_id primary key(app_id),
    constraint fk_apppass_id foreign key(app_id) references repay_app(app_id)
);

create table benco_approved(
    app_id number,
    reason varchar2(4000),
    constraint pk_bencoappr primary key(app_id),
    constraint fk_bencoappr foreign key(app_id) references repay_app(app_id)   
);

insert into app_pass values(1,0,0);
insert into app_pass values(2,0,0);
insert into app_pass values(3,0,0);

insert into employee
values(1,'Xavier', 'Garibay', 'x@gmail.com', 0, 0,1, 1);
insert into employee
values(2,'Stacy', 'Mom', 'mom@gmail.com', 200, 0, 2, 1);
insert into employee
values(3,'Jessie', 'Girl', 'girl@gmail.com', 0,500, 2, 2);
insert into employee
values(4,'Eminem', 'Shady', 'rap@gmail.com', 0,400, 2, 3);
insert into employee
values(5,'Lisa', 'Life', 'rap@gmail.com', 1,0, 2, 1);

insert into emp_status
values(1,1,0);
insert into emp_status
values(2,0,1);
insert into emp_status
values(3,0,0);
insert into emp_status
values(4,0,0);
insert into emp_status
values(5,1,0);

insert into login
values(1,'xavier','garibay');
insert into login
values(2,'stacy','mom');
insert into login
values(3,'jessie','girl');
insert into login
values(4,'slim','shady');
insert into login
values(5,'lisa','life');

insert into repay_event
values(1,'University Course', 0.8);
insert into repay_event
values(2,'Seminar', 0.6);
insert into repay_event
values(3,'Certification Prep Class', 0.75);
insert into repay_event
values(4,'Certification', 1);
insert into repay_event
values(5,'Technical Training', 0.9);
insert into repay_event
values(6,'Other', 0.3);

insert into repay_app
values(1, 2);
insert into repay_app
values(2, 4);
insert into repay_app
values(3, 4);

insert into app_status
values(1,0,0,0,0,0,0,0,0,'11-JAN-17');
insert into app_status
values(2,0,0,0,0,0,0,0,0,'11-JAN-17');
insert into app_status
values(3,0,0,0,0,0,0,0,0,'08-DEC-17');

insert into app_info
values(1,'11-JAN-17','Class Graduation','11:23:00',500,1,1);
insert into app_info
values(2,'11-JAN-17','Class Graduation','11:23:00',300,1,1);
insert into app_info
values(3,'11-JAN-17','2nd app','11:23:00',100,2,3);


insert into grade_format
values(1,'65',0);
insert into grade_format
values(2,'P',0);
insert into grade_format
values(3,'Attended',1);

create sequence app_info_seq
    start with 10
    increment by 1;
create sequence app_status_seq
    start with 10
    increment by 1;
create sequence app_seq
    start with 10
    increment by 1;
create sequence pass_seq
    start with 10
    increment by 1;
 create sequence file_seq
    start with 50
    increment by 1;

insert into APP_PASS values(pass_seq.nextval,0,0);
 /   
create or replace trigger app_info_trigger
before insert on app_info
for each row
begin --this keyword signifies a block for a transaction
    if :new.app_id is null then
    select app_info_seq.nextval into :new.app_id from dual;
    end if;
end; 

create or replace trigger app_trigger
before insert on repay_app
for each row
begin --this keyword signifies a block for a transaction
    if :new.app_id is null then
    select app_seq.nextval into :new.app_id from dual;
    end if;
end;

create or replace trigger app_status_trigger
before insert on app_status
for each row
begin --this keyword signifies a block for a transaction
    if :new.app_id is null then
    select app_status_seq.nextval into :new.app_id from dual;
    end if;
end;   
/
create or replace trigger app_file_trigger
before insert on app_files
for each row
begin --this keyword signifies a block for a transaction
    if :new.file_id is null then
    select file_seq.nextval into :new.file_id from dual;
    end if;
end; 
/
create or replace trigger app_pass_trigger
before insert on app_pass
for each row
begin --this keyword signifies a block for a transaction
    if :new.app_id is null then
    select pass_seq.nextval into :new.app_id from dual;
    end if;
end; 
/
create or replace view app_sup as
select app_id, ra.emp_id, e.department, e.sup_id from repay_app ra
inner join employee e
on ra.emp_id=e.emp_id;

create or replace view approve_app as
select ra.app_id, ra.emp_id, e.fname, e.lastname, ai.ev_descr, ai.ev_cost, ai.ev_type_id, e.repay_awarded, e.repay_pending from repay_app ra
inner join app_info ai on ra.app_id=ai.app_id
inner join employee e on e.emp_id=ra.emp_id;

create or replace view user_app as
select ra.app_id,inf.ev_descr, inf.ev_date, inf.ev_cost, inf.ev_type_id, st.sup_apr, st.chair_apr, st.benco_apr, st.passed, st.recent_date, st.info_hold from repay_app  ra
inner join app_status st on ra.app_id=st.app_id
inner join app_info inf on ra.app_id=inf.app_id;



create or replace view grade_approve as
select pass.app_id, fil.file_name, grade.pass_grade, grade.presentation, stat.passed, stat.approved from app_pass pass
inner join app_status stat on pass.app_id=stat.APP_ID
inner join app_info info on pass.app_id=info.APP_ID
inner join grade_format grade on info.ev_grade_id=grade.ev_grade_id
inner join(select  file_id,file_name from app_files) fil on pass.grade_file_id=fil.file_id; 


create or replace view grade_approve as
select pass.app_id, emp.fname, emp.lastname, fil.file_name, grade.pass_grade, grade.presentation, stat.passed, stat.approved, emp.sup_id, stat.completed, stat.info_hold from app_pass pass
inner join app_status stat on pass.app_id=stat.APP_ID
inner join app_info info on pass.app_id=info.APP_ID
inner join grade_format grade on info.ev_grade_id=grade.ev_grade_id
inner join(select  file_id,file_name from app_files) fil on pass.grade_file_id=fil.file_id
inner join(select ap.app_id, e.fname, e.lastname, e.sup_id from employee e
inner join repay_app ra on e.emp_id=ra.EMP_ID
inner join app_pass ap on ap.APP_ID=ra.app_id) emp on emp.app_id=pass.app_id; 

commit;
