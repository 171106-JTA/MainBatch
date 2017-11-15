--This is an SQL Developer comment

/*
DBL
- CREATE
- DROP --> Deletes table
- ALTER
- TRUNCATE --> empties table
DQL 
- SELECT
DML
- INSERT, SELECT, UPDATE, DELETE
TCL
- ROLLABACK, SAVEPOINT, COMMIT, SET_TRANSACTION
DCL
- GRANT
- REVODE
*/

DROP TABLE  my_polkamans CASCADE CONSTRAINTS; 
DROP TABLE Trainer;
DROP TABLE moves; 
DROP TABLE polkamans;
DROP TABLE polkaman;
/*
CASCADE CONSTRAINTS - Optional. 
Allows tables to be dropped in any order. 
Only use this if you are goind to drop all tables. 
It will delete constraints that point to the table to be 
dropped instead of throwing an error
preventing you from dropping to begin with
*/
/
CREATE TABLE polkamans (
    pkmn_id number(3), --you can add: primary key
    pkmn_name varchar2(100) NOT NULL, --we don't want a pliaman without a name
    type1 varchar2(50),
    type2 varchar2(50),
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
    trainer_name varchar(100) not null
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
    Referencial integraity: 
    The enforcement of foriegn key relations. It prevents a user from inserting
    a record with an FK that points to a non-existent element. It also prevents
    a user from deleting a record that another table points at. 
    In additoin, we can's drop a table that another table referecnes
    Prevents risk of 'Orphan records'
*/
--DROP TABLE polkamans; --This gives an error because of Referencial Integrity

--Inserting data into our tables
--INSERT INTO POLKAMANS (PKMN_ID, PKMN_NAME, TYPE1, TYPE2)
--VALUES(4, "Firemander", "fire", null);
--Because TYPE2 = null, we don't need to write it out
INSERT INTO POLKAMANS (PKMN_ID, PKMN_NAME, TYPE1)
VALUES(4, 'Firemander', 'fire');

--If you insert into all columns, you need not specify them all. 
--However, the number of columns you insert must be equal to the total number of columns
INSERT INTO POLKAMANS
VALUES(1, 'PLANTESSAURUS', 'Plant', null);

INSERT INTO POLKAMANS
VALUES (7,'Little Turtle Dude', 'Agua', null);

SELECT * FROM POLKAMANS;

INSERT INTO TRAINER
VALUES (1, 'Bobbert'); 

INSERT INTO TRAINER
VALUES (2, 'Bobby'); 
INSERT INTO TRAINER
VALUES (3, 'Slagathor'); 

INSERT INTO MOVES
VALUES (1, 'Tackle', 'Worthless');

INSERT INTO MOVES
VALUES (2, 'Spla sh', 'Overpowered'); 

INSERT INTO MOVES
VALUES (3, 'Flamethrower', 'Fire');

INSERT INTO my_polkamans
VALUES (1, 4, 300, 3, 1);
INSERT INTO my_polkamans
VALUES (2, 4, 219, 2, 1);
INSERT INTO my_polkamans
VALUES (3, 4, 615, 1, 1);
INSERT INTO my_polkamans
VALUES (4, 1, 746, 3, 2);
INSERT INTO my_polkamans
VALUES (5, 1, 313, 3, 2);
INSERT INTO my_polkamans
VALUES (6, 7, 271, 1, 2);

SELECT * FROM MY_POLKAMANS;

--Querying
--You can also grab specific columns
select trainer_name from trainer;

/*
You can apply conditions for specific columns to filter your data
*/
select * from polkamans;
select * from polkamans WHERE pkmn_id > 3;
--User the WHERE clause to apply conditions to the columns. 
--You can user AND and OR  to do multiple conditions.
select * from POLKAMANS WHERE PKMN_ID > 3 AND TYPE1 = 'fire';

--UPDATE DATA EXAMPLE
UPDATE moves
set MOVE_TYPE =  'Aqua',
MOVE_NAME = 'splashes' --you can do multiple updates in one transaction
where MOVE_TYPE = 'Overpowered'; 

select * from moves;


--ALTER EXMAPLE
ALTER TABLE polkamans
RENAME TO polkaman;

select * from polkaman;

--TRUNCATE EXAMPLE
--TRUNCATE TABLE my_polkamans; 
--select * from my_polkamans;
--gets rid of all data in my_polkamans

--DELETE EXAMPLE
DELETE FROM MY_POLKAMANS
WHERE MY_PKMN_ID = 3;

SELECT * FROM MY_POLKAMANS;

/*
AGGREGATE FUNCTIONS
- An aggregate function is a function applied to a column and processes all the data
- returning a single value back. 
- Examples: AVG(), SUM(), MAX(), MIN(), STDDEV(), VARIANCE(), LAST(), FIRST(), COUNT()
*/
select MAX_HP from my_polkamans;
select max(MAX_HP) from my_polkamans;
select avg(MAX_HP) from MY_POLKAMANS;
select stddev(MAX_HP) from MY_POLKAMANs;

/*
SCALAR FUNCTION
A function that applies to each cell of a column. 
e.g. alters the value within each cell of a column
when in doubt, think STring manipulation functions:
lower()
upper()
ABS()
cos,sin,tan()
ROUND()
TRUNC()
CONCAT()
LENGTH()
LTRIM() --TAKE AWAY WHITE SPACE FROM LEFT
RTRIM() --TAKE AWAY WHITE SPACE FROM RIGHT
TRIM() -- BOTH SIDES
*/

select upper(pkmn_name) from polkaman
where upper(pkmn_name) = 'FIREMANDER';


/*
Suppose we want to find which group of owned polkamans that have the highest cumulative hp
*/
select * from my_polkamans;

select pkmn_id, sum(MAX_HP) as "SUM"
-- AS  is an alias, they use double quotes, and are used for convenience
from MY_POLKAMANS
WHERE TRAINER_ID != 1
group by pkmn_id having sum(MAX_HP) > 300;
--HAVING applies to groups of things while WHERE applies to all records in a table
/*
A common question is WHERE vs HAVING. 
WHERE is a conditional that applies to every record individually and will apply 
before a HAVING clause
HAVING is a conditional that aplies to groups of records and take place after filtering
out individual records with WHERE
Since it works on groups of records, it is safe to say, HAVING requires the use of GROUP BY
as well. 
HAVING is also the only way to apply conditions to aggregate
*/

select pkmn_id from (select * from polkaman); -- Nested select example. Albeit a bad one
--nested selects are usually between different tables

/*
IN keyword
IN can be used to replace '=' in a conditional. 
Serves as a convenient OR clause
*/

select * from polkaman
where pkmn_id in (1,4, 7);

select * from polkaman
where pkmn_id between 1 and 4; --inclusive


/*
LIKE is used to grab results that match a regular expression.
You must use the following wildcards
_   - underscore means any ONE character
%   - 0+ of any character 
*/
select * from polkaman
where pkmn_name  LIKE '__________'; --things with 10 letters (i.e. there are 10 underscores)

select * from polkaman
where pkmn_name  LIKE '%a__e%'; --grab all names where at some point, there is an 'a' with 2 letters after it then an 'e'