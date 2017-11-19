drop table accounts;
drop table users;

create table users(
    username varchar2(25) primary key,
    pass varchar2(25),
    blocked number,
    promoted number
    
);

create table accounts(
    username varchar2(25) primary key,
    deposit number,
    debt number,
    loan number,
    constraint fk_username foreign key(username) references users(username)
);

commit;
select * from accounts;
select * from users;
--insert into user values()