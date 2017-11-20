drop table status;
drop table money;
drop table login;
drop sequence login_seq;
drop sequence money_seq;
drop sequence status_seq;
create table login(
    ac_id number,
    ac_username varchar2(4000),
    ac_password varchar2(4000),
    constraint pk_login primary key (ac_id),
    constraint uk_username unique (ac_username)
);
create table status(
    ac_id number,
    st_admin number,
    st_locked number,
    st_accepted number,
    constraint fk_status foreign key (ac_id) references login(ac_id) on delete cascade,
    constraint pk_status primary key (ac_id)
);
create table money(
    ac_id number,
    mn_balance number,
    mn_loans number,
    constraint fk_money foreign key (ac_id) references login(ac_id) on delete cascade,
    constraint pk_money primary key (ac_id)
);

create sequence login_seq
    start with 3
    increment by 1;

create or replace trigger login_seq_trigger
before insert on login
for each row
begin --this keyword signifies a block for a transaction
    if :new.ac_id is null then
    select login_seq.nextval into :new.ac_id from dual;
    end if;
end;

create sequence status_seq
    start with 3
    increment by 1;

create or replace trigger status_seq_trigger
before insert on status
for each row
begin --this keyword signifies a block for a transaction
    if :new.ac_id is null then
    select status_seq.nextval into :new.ac_id from dual;
    end if;
end;

create sequence money_seq
    start with 3
    increment by 1;

create or replace trigger money_seq_trigger
before insert on money
for each row
begin --this keyword signifies a block for a transaction
    if :new.ac_id is null then
    select money_seq.nextval into :new.ac_id from dual;
    end if;
end;

insert into login
values(1,'Xavier.Garibay', 'wolf');
insert into login
values(2,'John.Doe', 'deer');

insert into status
values(1, 1, 0, 1);
insert into status
values(2, 0, 0, 1);


insert into money
values (1, 2000, 0);
insert into money
values (2, 600, 0);
/

commit;
