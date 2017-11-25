--This is a SQL Developer comment!
/*
DDL
-CREATE
-DROP - Deletes table
-ALTER
-TRUNCATE - empties table
DML
-INSERT, SELECT, UPDATE, DELETE
DQL
-SELECT
TCL
-ROLLBACK, SAVEPOINT, COMMIT, SET_TRANSACTION
DCL
-GRANT
-REVOKE
*/
DROP TABLE my_polkamans;
DROP TABLE trainer;
DROP TABLE moves;
DROP TABLE polkaman;
DROP TABLE polkamans;
/*
    CASCADE CONTRAINTS is optional, not really needed. But it will delete
    constraints that point to the table 
    to be dropped instead of throwing an error
    preventing you from dropping to begin with.
*/

/

CREATE TABLE polkamans (
    pkmn_id number(3), --you can add: primary key,
    pkmn_name varchar2(100) NOT NULL, --We dont want a polkaman without a name
    type1 varchar2(50) NOT NULL,
    type2 varchar2(50) NULL,    --A polkaman might not have two types
    CONSTRAINT PK_polkamans PRIMARY KEY(pkmn_id)
);
/ --Separate executions with this forward slash

SELECT * FROM polkamans; --Grab all data from polkamans

CREATE TABLE Moves (
    move_id number(3) primary key,
    move_name varchar2(100) not null,
    move_type varchar2(100) not null
);

CREATE TABLE Trainer (
    trainer_id number(3) primary key,
    trainer_name varchar2(100) not null
);

CREATE TABLE my_polkamans (
    my_pkmn_id number(3) primary key,
    pkmn_id number(3) not null,
    max_hp number(3) not null,
    move_id number(3) not null,
    trainer_id number(3) not null,
    constraint FK_pkmn_id FOREIGN KEY (pkmn_id) REFERENCES POLKAMANS(pkmn_id),
    constraint FK_move_id FOREIGN KEY (move_id) REFERENCES Moves(move_id),
    constraint FK_trainer_id FOREIGN KEY (trainer_id) REFERENCES Trainer(trainer_id)
);

/*
    Referential Integrity:
    -The enforcement of foreign key relations. It prevents a user from inserting a record
    with a FK that points to a non existent element. It also prevents a user from deleting a
    record that another table points at.
    On top of this, we cant drop a table, that another table references.
    Prevents risk of 'Orphan records'
*/
--Inserting data into our tables
INSERT INTO POLKAMANS (PKMN_ID, PKMN_NAME, TYPE1) --Write which columns you insert into to
VALUES (4, 'Firemander', 'fire'); --the actual values to be inserted
--If you insert into all columns, you need not specify them all.
INSERT INTO POLKAMANS 
VALUES (1, 'Plantesaurus', 'Plant', null);

INSERT INTO POLKAMANS
VALUES (7, 'Little Turtle Dude', 'Agua', null);

INSERT INTO TRAINER
VALUES (1, 'Bobbert');

INSERT INTO TRAINER
VALUES (2, 'Bobby');

INSERT INTO TRAINER
VALUES (3, 'Slagathor');

INSERT INTO MOVES
VALUES(1, 'Tackle', 'Worthless');

INSERT INTO MOVES
VALUES (2, 'Splash', 'Overpowered');

INSERT INTO MOVES
VALUES (3, 'Flamethrower', 'Fire');

INSERT INTO MY_Polkamans
VALUES (1, 4, 300, 3, 1);

INSERT INTO MY_Polkamans
VALUES (2, 4, 219, 2, 1);

INSERT INTO MY_Polkamans
VALUES (3, 4, 131, 1, 1);

INSERT INTO MY_Polkamans
VALUES (4, 1, 271, 2, 2);

INSERT INTO MY_Polkamans
VALUES (5, 7, 131, 1, 2);

INSERT INTO MY_Polkamans
VALUES (6, 7, 271, 3, 3);

select * from my_polkamans;

--Querying
--You can also grab specific columns
select trainer_name from trainer;

--You can apply conditions for specific columns to filter your data.
select * from polkamans;
select * from polkamans WHERE pkmn_id > 3;
--use the WHERE clause to apply conditions to the columns.
--You can AND n' OR to do multiple conditions:
select * from polkamans WHERE pkmn_id >3
AND type1 = 'fire';

--UPDATE DATA EXAMPLE
UPDATE moves 
set MOVE_TYPE = 'Agua',
MOVE_NAME = 'splashes' --you can do multiple updates in one transaction
where MOVE_TYPE = 'Overpowered';
select * from moves;

--ALTER EXAMPLE
ALTER TABLE polkamans 
RENAME TO polkaman;

--TRUNCATE TABLE my_polkamans;
--select * from my_polkamans;

--DELETE EXAMPLE
DELETE FROM my_polkamans 
WHERE my_pkmn_id = 3;

select * from my_polkamans;

/*
AGGREGATE FUNCTIONS
-An aggregate function is a function you apply to a column that will
process all the data and return a single value back. Examples of this are:
AVG() - average
SUM()
MAX()
MIN()
STDDEV()
VARIANCE()
LAST()
FIRST()
COUNT()
*/

select MAX_HP from my_polkamans;
select max(MAX_HP) from my_polkamans;
select avg(MAX_HP) from my_polkamans;
select stddev(MAX_HP) from my_polkamans;

/*
SCALAR FUNCTION
-A function that applies to each cell of a column.
-e.g. alters the value within each cell of a column.
-When in doubt, think String manipulation functions:
lower()
upper()
ABS()
cos,tin,tan()
ROUND()
TRUNC()
CONCAT()
LENGTH()
LTRIM()
RTRIM()
TRIM()
*/

select upper(pkmn_name) from polkaman
where upper(pkmn_name) = 'FIREMANDER';

/*
I want to find, out of all owned polkamans, which group of them, has the highest cumalitive
HP.
*/

select * from my_polkamans;
--AS is an alias, they use double quotes, and are used for convenience.
select pkmn_id AS "Polkaman ID", sum(MAX_HP) AS "SUM"  
from my_polkamans
where Trainer_ID != 3 
group by pkmn_id HAVING sum(Max_HP) > 300;
/*
    A common question to be asked is, where vs having.
    -WHERE is a conditional that applies to every record individually and will apply
    before a HAVING clause.
    -HAVING is a conditional that applies to groups of records. It takes place after you
    filtered out individual records with WHERE. Since it works on groups of records,
    it is safe to say, HAVING requires the use of GROUP BY as well.
    HAVING is also the only way to apply conditions to aggregates.
*/

select pkmn_id from (select * from polkaman); --Nested select example, albeit a bad one.

/*
 IN keyword
 -IN can be used to replace the '=' in a conditional. 
 -Basically serves as a convenient OR clause.
 
 
*/
select * from polkaman
WHERE pkmn_id in (1,4,7); --1 OR 4 OR 7

select * from polkaman
where pkmn_ID BETWEEN 1 AND 4; --inclusive

/*
LIKE is used to grab results that match a regular expression
You must use the following wildcards:
 _   - any ONE character
 %   - 0+ of ANY character
*/

select * from polkaman
where pkmn_name LIKE '%a__e%'; --Grab all names where at some point, there is an 'a' with 2 letters after it, THEN an 'e'


/*
VIEWS
-A view is a virtual table. It is encompassed of a custom query that can then be called on
by name. Especially useful for more complex queries.
*/
CREATE VIEW Trainer_PKMN AS
select * from trainer A --Also aliasing
inner join my_polkamans B
on A.Trainer_ID = B.Trainer_id
inner join polkaman C
on b.pkmn_id = c.pkmn_id
order by Trainer_Name ASC;

--Cross join
select * from polkaman
cross join my_polkamans;
--You can shorthand cross join:
select * from polkaman, my_polkamans;


insert into polkaman
values(23, 'TwentyThreeMon', 'Generic', 'Dragon');

select * from polkaman A --LEFT TABLE
left join my_polkamans B --RIGHT TABLE
on a.pkmn_id = b.pkmn_id;

select * from polkaman A --LEFT TABLE
right join my_polkamans B --RIGHT TABLE
on a.pkmn_id = b.pkmn_id;

select * from polkaman A --LEFT TABLE
full outer join my_polkamans B --RIGHT TABLE
on B.pkmn_id = A.pkmn_id
full outer join trainer C
on B.trainer_id = C.trainer_id
full outer join moves D
on B.move_id = d.move_id;

--Natural joins
select * from polkaman
natural inner join my_polkamans;
/*
Adding the 'natural' clause to any join will require you to omit the 'ON' clause.
This is because a natural join will join automatically based on matching "column name"
and then omit any duplicate column names from the final view.
*/
drop table garbageA;
drop table garbageB;

create table garbageA(
    col1 number(2),
    col2 number(2)
    );

create table garbageB(
    col1 number(2),
    col2 number(2)
    );
    
INSERT INTO garbageA
Values(1,2);
INSERT INTO garbageA
Values(3,5);
INSERT INTO garbageB
Values(1,2);
INSERT INTO garbageB
Values(3,5);

select * from garbageA
natural inner join garbageB;

select a.pkmn_id, b.pkmn_id from my_polkamans A
inner join polkaman B
on a.pkmn_id > b.pkmn_id;
/*
Any time, you perform a join where you do:
tableA.column = tableB.column you are performing, an EQUIJOIN
Any joins you perform that do NOT use '=', like above, is called
a THETAJOIN
*/

drop TABLE Employee;
CREATE TABLE Employee (
    emp_ID number(3),
    emp_name varchar(200),
    sup_id number(3)
);

INSERT INTO EMPLOYEE
VALUES(1,'Bobbert',1);
INSERT INTO EMPLOYEE
VALUES(2,'Betty',2);
INSERT INTO EMPLOYEE
VALUES(3,'Todd',1);
INSERT INTO EMPLOYEE
VALUES(4,'Gabriel',2);

select * from Employee A
inner join Employee B
on A.EMP_ID = B.SUP_ID;
/*
Our self join brings us a view with each supervisor, and 
the employees that work below them.
Note: Self join is not an actual keyword, but a conc
*/

--Set operations

create table setA(
    c1 number(3),
    c2 varchar2(3)
);

create table setB(
    c3 number(3),
    c4 varchar2(3)
);

insert into SetA
VALUES(1,'A');
insert into SetA
VALUES(2,'B');
insert into SetA
VALUES(3,'C');
insert into SetA
VALUES(4,'D');
insert into SetB
VALUES(3,'C');
insert into SetB
VALUES(4,'D');
insert into SetB
VALUES(5,'E');
insert into SetB
VALUES(6,'F');


--UNION
--Combines both queries, filters out duplicates
select * from setA
UNION
select * from setB;

--UNION ALL
--Same as above, includes duplicates though
select * from setA
UNION ALL
select * from setB;

--MINUS
--Filter out records that are in both queries, from the Query 
select * from setA
Minus
select * from setB;

--INTERSECT
--Show only records that appear in both queries
select * from setA
INTERSECT
select * from setB;

/*
Rules for set operators
-The two queries must have matching column counts,
as well as the column datatypes being presented in the 
same order. column names do not have to match.
It will just take the names of the left query anyway.
*/

select pkmn_id,pkmn_name from polkaman
UNION ALL
select trainer_id,trainer_name from trainer;

--Will NOT WORK
--select * from polkaman
--union
--select * from trainer; 


/*
I am selecting from trainer table. I want only trainers that own fire type polkamans.
How can I do this without using joins.
*/
--With nested selects and 'IN' we can do it.
select * from trainer where Trainer_ID IN(
select TRAINER_ID from MY_polkamans where pkmn_id IN(
SELECT pkmn_id from polkaman where lower(type1)='fire' OR lower(type2) = 'fire'));

--Example of inner query from above
select TRAINER_ID,pkmn_id from MY_polkamans where pkmn_id IN(
SELECT pkmn_id from polkaman where lower(type1)='fire' OR lower(type2) = 'fire');

--Example of innermost query from above
SELECT pkmn_id from polkaman where lower(type1)='fire' OR lower(type2) = 'fire';



--CHECK Contraint
--Apply a condition that must be met for a column to accept insert.
ALTER TABLE polkaman
ADD CONSTRAINT check_pkmn_id Check(pkmn_id < 152);

INSERT INTO polkaman
VALUES(162,'Moo Too', 'Milk', 'Dragon');

/

/*
IN vs EXISTS
-Both of these commands can be used to perform conditional checks.
-High level overview: EXISTS is garbage.
-It is highly inefficient if the outer query is even remotely large.
*/

select * from trainer where EXISTS(
select TRAINER_ID from MY_polkamans where EXISTS(
SELECT pkmn_id from polkaman where lower(type1)='fire' OR lower(type2) = 'fire'));

/*
Every inner query will run once for every outer query.
So for the above.
trainer - 3 records
my_polkamans - 5 records
polkamans - 5 records

So therefore, we run ~16 queries in the above example using EXISTS.
This is why it behooves somebody to NOT use exists, if the outer query has a lot of data.
When in doubt, just use IN
*/
--rownum is a valid identifier you can use in your conditionals.
--However, it is finicky.
select * from (select max_hp from my_polkamans
where rownum < 4
order by max_hp desc)
minus
select * from (select max_hp from my_polkamans
where rownum < 3
order by max_hp desc);

/*
As soon as a rownum conditional fails, it stops applying the condition to the
remaining records.
*/


CREATE USER minibobbert IDENTIFIED BY p4ssw0rd;
GRANT DBA TO minibobbert;

select * from flash_cards;

commit;

/*
SQL Injection

X=';select * from polkaman where pkmn_name like '%' OR pkmn_name = '

select * from polkaman where lower(pkmn_name) = '';select * from polkaman'';
*/
select * from flash_cards;

DECLARE
    x VARCHAR(4000);
BEGIN
    get_answer('Did JDBC work?', x);
END;