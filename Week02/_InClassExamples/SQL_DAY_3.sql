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

