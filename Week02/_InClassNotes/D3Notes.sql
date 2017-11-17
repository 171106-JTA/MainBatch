-- This is a SQL Developer comment!
/*
DDL 
• CREATE
• DROP - deletes table
• ALTER
• TRUNCATE - empties table
DML
• INSERT, SELECT, UPDATE, DELETE
DQL
• SELECT
TCL
• ROLLBACK, SAVEPOINT, COMMIT, SET_TRANSACTION
DCL
• GRANT
• REVOKE
*/

DROP TABLE garbageA;
DROP TABLE garbageB;
DROP TABLE my_pokemans CASCADE CONSTRAINTS;
DROP TABLE trainer;
DROP TABLE moves CASCADE CONSTRAINTS;
DROP TABLE pokemans CASCADE CONSTRAINTS; 
/*
    CASCADE CONSTRAINTS is optional, not realy needed. But it will delete constraints that point to the table, as well as the table itself to 
    be dropped instead of throwing an error. preventing you frrom dropping to begin with. 
*/
/

CREATE TABLE pokemans(
    pkmn_id number(3), -- you can add: primary key
    pkmn_name varchar2(100) NOT NULL, -- We don't want a pokeman without a name
    type1 varchar2(50) NOT NULL, 
    type2 varchar2(50) NULL, -- A pokeman might not have two types
    CONSTRAINT PK_pokemans PRIMARY KEY(pkmn_id)
);
/-- separate executions with this forward slash

SELECT * FROM pokemans;



CREATE TABLE MOVES (
    move_id number(3) PRIMARY KEY,
    move_name varchar2(100) NOT NULL,
    move_type varchar2(100) NOT NULL
);  

CREATE TABLE TRAINER (
    trainer_id number(3) PRIMARY KEY,
    trainer_name varchar2(100) NOT NULL
);

CREATE TABLE MY_POKEMANS (
    my_pkmn_id number(3) PRIMARY KEY,
    pkmn_id number(3) NOT NULL, 
    max_hp number(3) NOT NULL, 
    move_id number(3) NOT NULL,
    trainer_id number(3) NOT NULL,
    CONSTRAINT FK_pkmn_id FOREIGN KEY(pkmn_id) REFERENCES POKEMANS(pkmn_id),
    CONSTRAINT FK_move_id FOREIGN KEY(move_id) REFERENCES MOVES(move_id),
    CONSTRAINT FK_trainer_id FOREIGN KEY(trainer_id) REFERENCES TRAINER(trainer_id)
);

/*
    Referential integrity: 
    • The enforcement of foreign key relations. It prevents a user from inserting a record with a FK that points to a non existent element. It also 
        prevents a user from deleting a record that another table points at. 
        On top of this, we can't drop a table, that another table references.
        Prevents risk of "Orphan records"
    • 
*/


INSERT INTO POKEMANS (PKMN_ID, PKMN_NAME, TYPE1) -- write whihc columns you insert into 
VALUES (4, 'Firemander', 'fire'); -- the actual values to be inserted
-- if you insert into all columns, you need not specify them all. 
INSERT INTO POKEMANS
VALUES (1, 'Plantesaurus', 'Plant', null);

INSERT INTO POKEMANS
VALUES (7, 'Little Turtle Dude', 'Agua', null);

INSERT INTO TRAINER
VALUES (1, 'Bobbert');

INSERT INTO TRAINER
VALUES (2, 'Bobby');

INSERT INTO TRAINER
VALUES (3, 'Slagathor');

INSERT INTO MOVES
VALUES (1, 'Tackle', 'Worthless');

INSERT INTO MOVES
VALUES (2, 'Splash', 'Overpowered');

INSERT INTO MOVES 
VALUES (3, 'Flamethrower', 'Fire');

INSERT INTO MY_POKEMANS
VALUES(1, 4, 419, 3, 1);-- This is a SQL Developer comment!
/*
DDL 
• CREATE
• DROP - deletes table
• ALTER
• TRUNCATE - empties table
DML
• INSERT, SELECT, UPDATE, DELETE
DQL
• SELECT
TCL
• ROLLBACK, SAVEPOINT, COMMIT, SET_TRANSACTION
DCL
• GRANT
• REVOKE
*/

DROP TABLE my_pokemans CASCADE CONSTRAINTS;
DROP TABLE trainer;
DROP TABLE moves CASCADE CONSTRAINTS;
DROP TABLE pokemans CASCADE CONSTRAINTS; 
/*
    CASCADE CONSTRAINTS is optional, not realy needed. But it will delete constraints that point to the table, as well as the table itself to 
    be dropped instead of throwing an error. preventing you frrom dropping to begin with. 
*/
/

CREATE TABLE pokemans(
    pkmn_id number(3), -- you can add: primary key
    pkmn_name varchar2(100) NOT NULL, -- We don't want a pokeman without a name
    type1 varchar2(50) NOT NULL, 
    type2 varchar2(50) NULL, -- A pokeman might not have two types
    CONSTRAINT PK_pokemans PRIMARY KEY(pkmn_id)
);
/-- separate executions with this forward slash

SELECT * FROM pokemans;



CREATE TABLE MOVES (
    move_id number(3) PRIMARY KEY,
    move_name varchar2(100) NOT NULL,
    move_type varchar2(100) NOT NULL
);  

CREATE TABLE TRAINER (
    trainer_id number(3) PRIMARY KEY,
    trainer_name varchar2(100) NOT NULL
);

CREATE TABLE MY_POKEMANS (
    my_pkmn_id number(3) PRIMARY KEY,
    pkmn_id number(3) NOT NULL, 
    max_hp number(3) NOT NULL, 
    move_id number(3) NOT NULL,
    trainer_id number(3) NOT NULL,
    CONSTRAINT FK_pkmn_id FOREIGN KEY(pkmn_id) REFERENCES POKEMANS(pkmn_id),
    CONSTRAINT FK_move_id FOREIGN KEY(move_id) REFERENCES MOVES(move_id),
    CONSTRAINT FK_trainer_id FOREIGN KEY(trainer_id) REFERENCES TRAINER(trainer_id)
);

/*
    Referential integrity: 
    • The enforcement of foreign key relations. It prevents a user from inserting a record with a FK that points to a non existent element. It also 
        prevents a user from deleting a record that another table points at. 
        On top of this, we can't drop a table, that another table references.
        Prevents risk of "Orphan records"
    • 
*/


INSERT INTO POKEMANS (PKMN_ID, PKMN_NAME, TYPE1) -- write whihc columns you insert into 
VALUES (4, 'Firemander', 'fire'); -- the actual values to be inserted
-- if you insert into all columns, you need not specify them all. 
INSERT INTO POKEMANS
VALUES (1, 'Plantesaurus', 'Plant', null);

INSERT INTO POKEMANS
VALUES (7, 'Little Turtle Dude', 'Agua', null);

INSERT INTO TRAINER
VALUES (1, 'Bobbert');

INSERT INTO TRAINER
VALUES (2, 'Bobby');

INSERT INTO TRAINER
VALUES (3, 'Slagathor');

INSERT INTO MOVES
VALUES (1, 'Tackle', 'Worthless');

INSERT INTO MOVES
VALUES (2, 'Splash', 'Overpowered');

INSERT INTO MOVES 
VALUES (3, 'Flamethrower', 'Fire');

INSERT INTO MY_POKEMANS
VALUES(1, 3, 419, 99, 27);

INSERT INTO MY_POKEMANS
VALUES(2, 4, 219, 2, 1);

INSERT INTO MY_POKEMANS
VALUES(3, 4, 131, 1, 1);

INSERT INTO MY_POKEMANS
VALUES(4, 1, 271, 2, 2);

INSERT INTO MY_POKEMANS
VALUES(5, 7, 131, 1, 2);

INSERT INTO MY_POKEMANS
VALUES(6, 7, 271, 3, 3);

SELECT * FROM MY_POKEMANS;

--Querying
--You can also grab specific columns
SELECT trainer_name FROM trainer;

--You can apply conditions for specific columns to filter your data.
SELECT * FROM pokemans;
SELECT * FROM pokemans WHERE pkmn_id  > 3;
--use the WHERE clause to apply conditions to the columns
--you can use AND/OR to do multiple conditions: 
SELECT * FROM POKEMANS WHERE pkmn_id > 3 AND type1 = 'fire';

--UPDATE DATA example
UPDATE MOVES 
SET move_type = 'Agua',
move_name = 'splashes'  -- you can do multiple updates in one transaction
WHERE MOVE_TYPE = 'Overpowered';

SELECT * FROM MOVES;

--TRUNCATE EXAMPLE

--DELETE EXAMPLE
SELECT * FROM my_pokemans
WHERE my_pkmn_id = 3;
DELETE FROM my_pokemans WHERE my_pkmn_id = 3;

SELECT * FROM my_pokemans;

/*
AGGREGATE FUNCTIONS
• An aggregate function is a function you apply to a column that will process all the data and return a single value. Examples:
    • AVG()
    • SUM()
    • MAX()
    • MIN()
    • STDDEV()
    • VARIANCE()
    • LAST()
    • FIRST()
    • COUNT()
*/

SELECT max_hp FROM MY_POKEMANS;
SELECT max(MAX_HP) FROM MY_POKEMANS;
SELECT avg(MAX_HP) FROM MY_POKEMANS;
SELECT stddev(MAX_HP) FROM MY_POKEMANS;

/*
SCALAR FUNCTION
• A function that applies to each cell of a column
    • e.g. alters the value within each cell of a column
• When in doubt, think String manipulation functions:
lower()
upper()
abs()
cos(),sin(),tan()
round()
trunc()
concat()
length()
ltrim()
rtrim()
*/

SELECT upper(pkmn_name) FROM pokemans
WHERE upper(pkmn_name) = 'FIREMANDER';

SELECT * FROM MY_POKEMANS;

/*
I want to find, out of all owned pokemans, which group of them, has the highest cumulative max HP
*/

select pkmn_id, sum(MAX_HP) AS "SUM" FROM MY_POKEMANS
GROUP BY pkmn_id HAVING sum(MAX_HP) > 300;
/*
    A common question to be asked is, where vs having. 
    • WHERE is a conditional that applies to every record individually and will apply before a HAVING clause.
    • HAVING is a conditional that applies to groups of records. it takes place after you filtered out individual records with WHERE. Since it
        works on groups of records, it is safe to say, HAVING requires the use of GROUP BY as well.
        HAVING is also the only way to apply conditions to aggregates.
        
    
*/

SELECT pkmn_id FROM (SELECT * FROM pokemans);   -- nested SELECT works in Oracle, too! This is bad example, btw...

/*
IN keyword
• IN can be used to replace the '=' in a conditional.
• Basically serves as a convenient OR clause.


*/

SELECT * FROM pokemans
WHERE pkmn_id in (1,4,7);   -- 1 OR 4 OR 7

SELECT * FROM pokemans
WHERE pkmn_i BETWEEN 1 AND 4;   -- inclusive

/*
LIKE is used to grab results that match a regular expression
You must use the following wildcards: 
• _ - any ONE character
• % - 0+ of ANY character
*/

SELECT * FROM pokemans
WHERE pkmn_name LIKE '%a__e%';  -- get all names where at some point, there exists 'a' with 2 letters, then an 'e'

/*
VIEWS
• A view is a virtual table. It is encompased of a custom query that can then be called on by name. Especially useful for more complex queries.
*/
CREATE VIEW Trainer_PKMN AS
SELECT a.trainer_name as "Trainer" , c.pkmn_name AS  "Pokeman" FROM trainer A-- also aliasing
INNER JOIN my_pokemans B
ON A.trainer_id = B.trainer_id
INNER JOIN pokemans C
ON B.pkmn_id = C.pkmn_id
ORDER BY trainer_name ASC;

--cross join
SELECT * FROM pokemans
CROSS JOIN my_pokemans;
-- you can shorthand cross join:
select * from pokemans,my_pokemans;

INSERT INTO pokemans
VALUES (23, 'TwentyThreeMon', 'Generic', 'Dragon');

SELECT * FROM pokemans A -- left table
LEFT JOIN my_pokemans B
ON A.pkmn_id = B.pkmn_id;

SELECT * FROM pokemans A -- left table
RIGHT JOIN my_pokemans B
ON A.pkmn_id = B.pkmn_id;

SELECT * FROM pokemans A -- left table
FULL OUTER JOIN my_pokemans B
ON A.pkmn_id = B.pkmn_id;

SELECT * FROM pokemans A -- left table
FULL OUTER JOIN my_pokemans B
ON A.pkmn_id = B.pkmn_id
FULL OUTER JOIN trainer C
ON B.trainer_id = C.trainer_id
FULL OUTER JOIN moves D
ON B.move_id = D.move_id;

--Natural joins
SELECT * FROM pokemans
NATURAL INNER JOIN my_pokemans;
/*
Adding the 'natural' clause to any join will require you to omit the 'on' clause.
This is because a natural join will join automatically based onmatching "column name", and then omit any duplicate column names from the final 
view.
*/

CREATE TABLE garbageA(
    col1 number(2) NOT NULL,
    col2 number(2)
);

CREATE TABLE garbageB(
    col1 number(2) NOT NULL,
    col2 number(2)
);

INSERT INTO garbageA
values (1,2);
INSERT INTO garbageA
values (3,5);
INSERT INTO garbageB
values (1,2);
INSERT INTO garbageB
values (3,5);

SELECT * FROM garbageA
NATURAL INNER JOIN garbageB;

SELECT a.pkmn_id,b.pkmn_id FROM my_pokemans A
INNER JOIN pokemans B
ON a.pkmn_id > b.pkmn_id;
/*
Any time you perform a join where you do: 
table_a.column = table_b.column you are performing an EQUIJOIN. 
Any joins you perform that do NOT use '=' is called a THETAJOIN.
*/

DROP TABLE EMPLOYEE;

CREATE TABLE Employee (
    emp_ID number(3),
    emp_name varchar2(200),
    sup_id number(3)
);

INSERT INTO EMPLOYEE 
VALUES (1,'Bobbert',1);
INSERT INTO EMPLOYEE 
VALUES (2,'Betty',2);
INSERT INTO EMPLOYEE 
VALUES (3,'Todd',1);
INSERT INTO EMPLOYEE 
VALUES (4,'Gabriel',2);

SELECT * FROM Employee A
INNER JOIN Employee B
on A.emp_id = B.sup_id;
/*
Our self join brings us a view with each supervisor, and the employees that work below them. 
NOTE: Self join is not an actual keyword, but a concept.

*/
CREATE TABLE setA
(
    c1 number(3),
    c2 varchar2(3)
);

CREATE TABLE setB
(
    c3 number(3),
    c4 varchar2(3)
);

INSERT INTO setA
VALUES(1,'A');
INSERT INTO setA
VALUES(2,'B');
INSERT INTO setA
VALUES(3,'C');
INSERT INTO setA
VALUES(4,'D');
INSERT INTO setB
VALUES(3,'C');
INSERT INTO setB
VALUES(4,'D');
INSERT INTO setB
VALUES(5,'E');
INSERT INTO setB
VALUES(6,'F');

--UNION
-- Combines from both queries, filters out duplicates
SELECT * FROM setA
UNION 
SELECT * FROM setB;

--UNION ALL
-- Same as above, plus the duplicates
SELECT * FROM setA
UNION ALL
SELECT * FROM setB;

-- MINUS
-- Filter out records that are in both queries, from the query
SELECT * FROM setA
MINUS
SELECT * FROM setB;

--INTERSECT
--Show only records that appear in both queries
SELECT * FROM setA
INTERSECT
SELECT * FROM setB;

/*
Rules for set operators
• The two queries must have matching column counts as well as the column data types being presented in the same order. Column names do not 
    have to match. It will just take the names of the left query anyway.
*/


SELECT pkmn_id, pkmn_name FROM pokemans
UNION ALL 
SELECT trainer_id, trainer_name FROM trainer;

--won't work
--SELECT * FROM pokemans
--UNION
--SELECT * FROM trainer

/*
I am selecting from trainer table. I want only trainers that own fire type pokemans. How can I do this without using joins?
*/
--with nested selects and 'IN', we can do it
SELECT * FROM trainer WHERE trainer_id IN(
    SELECT trainer_id FROM my_pokemans WHERE pkmn_id IN (
        SELECT pkmn_id FROM pokemans WHERE lower(type1) = 'fire' OR lower(type2) = 'fire'
    )
);

--CHECK constraint
--Apply a conditio that must be met for a column to accept insert.
 ALTER TABLE pokemans
ADD CONSTRAINT check_pkmn_id CHECK (pkmn_id < 152);

INSERT INTO pokemans
VALUES(162, 'Moo Too', 'Milk', 'Dragon');
/*
IN vs EXISTS
• Both of these commands can be used to perform conditional checks
• High level overview: EXISTS is garbage.
    • It is highly inefficient if the outer query is even remotely large.

*/

SELECT * FROM trainer WHERE EXISTS(
    SELECT TRAINER_ID FROM MY_POKEMANS WHERE EXISTS (
        SELECT pkmn_id FROM POKEMANS WHERE lower(type1) = 'fire' OR lower(type2) = 'fire'
    )
);

/*
Every inner query will run once per every outer query.
So for the above.
trainer - 3 records
my_pokemans - 5 records
pokemans - 5 records

So therefore, we run about 16 queries in the above example using EXISTS.
This is why it behooves somebody to NOT use exists, if the outer query has a lot of data.
When in doubt, just use IN. 
*/
--rownum is a valid identifier you can use in your conditionals
--however, it is finicky.
SELECT * FROM (SELECT max_hp FROM my_pokemans 
WHERE rownum < 4 
ORDER BY max_hp DESC)
MINUS
SELECT * FROM (SELECT max_hp FROM my_pokemans
WHERE rownum < 3
ORDER BY max_hp desc);


/*
As soon as a rownum conditional fails, it stops applying the condition to the remaining records.
*/
commit;
/
DECLARE 
    x VARCHAR(4000);
BEGIN
    get_answer('Did JDBC work?', x);
END;