--this is a SQL Developer comments!
/*
DDL
- CREATE
- DROP - deletestable
- ALTER
- TRUNCATE - empties table

DML
- INSERT, SELECT,UPDATE. DELTE
DQL
- SELECT
TCL
- ROLLBACK, SAVEPOINT, COMMIT, SET_TRANSACTION
DCL
- GRANT
- REVOKE
*/

/*
CASCADE
*/

DROP TABLE MY_POLKAMANS;
DROP TABLE TRAINER;
DROP TABLE MOVES;
DROP TABLE POLKAMANS;

CREATE TABLE POLKAMANS (
    pkmn_id number(3), -- you can add: primary key
    pkmn_name varchar2(100) NOT NULL,
    type1 varchar2(50) NOT NULL,
    type2 varchar2(50) NULL,
    constraint PK_polkamans PRIMARY KEY (pkmn_id)
    
);
CREATE TABLE MOVES(
    move_id number(3) primary key,
    move_name varchar(100) not null,
    move_type varchar2(100) not null
);

CREATE TABLE TRAINER(
    trainer_id number(3) primary key,
    trainer_name varchar2(20)
);


CREATE TABLE my_polkamans(
    my_pkmn_id number(3) primary key,
    pkmn_id number(3) not null,
    max_hp number(3) not null,
    move_id number(3) not null,
    trainer_id number(3) not null,
    CONSTRAINT FK_pkmn_id FOREIGN KEY (pkmn_id) references POLKAMANS(pkmn_id),
    CONSTRAINT fk_MOVE_ID FOReign KEY (move_id) REFEREnCES MOVES(move_id),
    CONSTRAINT FK_trainer_id  foreign key (trainer_id) references trainer(trainer_id)
);

InSERT INTO POLKAMANS (PKMN_ID, pkmn_name, type1)
values ( 4, 'FIREMANdER', 'FEUR');

InSERT INTO POLKAMANS 
values ( 1, 'plantsautr', 'gass' , NULL);

InSERT INTO POLKAMANS 
values ( 7, 'waterdude', 'aggua' , nULL);

insert INTO TRAINER
VALUES ( 1,'Bobbert');

INSERT INTO TRAINER
VALUES (2, 'Boy');

INSERT INTO TRAINER
values(4, 'PUrlPLe');


INSERT into MOVEs
values(1, 'TACKLE', 'WORTHLESS');


INSERT into MOVEs
values(2, 'FIREFTHROWER', 'FIRE');


INSERT into MOVEs
values(3, 'hyperbeam', 'normmmall');


INSERT INTO MY_POLKAMANS
VALUES ( 1, 4, 292, 1, 1);


INSERT INTO MY_POLKAMANS
VALUES ( 2, 1, 252, 3, 2);


INSERT INTO MY_POLKAMANS
VALUES ( 7, 4, 300, 2, 2);


INSERT INTO MY_POLKAMANS
VALUES ( 9, 7, 160, 1, 2);

SELECT TRainER_NAME FROM TRAINER;

SELECT * FROM POLKAMANS;
SELECT * FROM POLKAMANS WHERE PKMN_ID >2;

SELECT * FROM POLKAMANS WHERE PKMN_ID >2
AND type1 = 'FEUR';


UPDATE MoVES
set MOVE_TYPE = 'AGUA',
MOVE_NAME = 'HYdropumpa'
WHERE MOVE_TYPE = 'normmmall';


SELECT * FROM MOVES;

ALTER TABLE Polkamans
RENAME TO POLKAMAN;

SELECT * FROM POLKAMAN;

/*
aggreGATE FUNCTION
-an aggregate function is a function you apply to a colum that will process all
the data and return a single value back example of this are "

AVG(); -- avergae
SUM()
MAX()
Min()
STDDEV()
VArianCE()
LAST()
FIRST()
COUNT()
*/

SELECT Max_HP FROM my_polkamans;
SELECT max(MAX_HP) FROM my_polkamans;

/*
SCALAR FUNCTION
- a function that applies to each cell of a column.
e.g. aliters the value within each cell of a column
when in doubt think string manipulation function
lower()
upeper()
ABS
cos,sin,tand()
ROUnD
TRUnCT 
CONCAT
LENgTH
LTRIM
RTRIM
*/
--
--SELECT UPPSER(PKMN_NAME) FROM POLKAMAN
--WHERE UPPER(PKMN_NAME) = 'FIREMANDER';
--SELECT * from  my_POLKAMANs;

/*
SELECT PKMN_ID, SUM(MAX_HP) FROM MY_POLKAMANS
FROUP BY PKMN_ID HAVING SUM(MAX_HP) > 300 INSTEAD OF WHERE


WHERE VS HAVING

where is a conditional that applies to every record individally and will apply before a having clause

having is a conditional that applies to groups of records it takes place after you

filtered out individual records with where

since it works on groups of records 
it is safe to sy having requires the use of group by as well

having is also the nly way to spply condition to aggreteate

select * from pokemansmhmjk
*/

COMMIT;

