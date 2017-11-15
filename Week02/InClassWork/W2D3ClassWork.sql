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