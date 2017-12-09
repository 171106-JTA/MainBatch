DROP TABLE USER_INFO;
DROP TABLE ACCOUNT;

--Create the Tables for the application
--Need to split off user info from account into 
--(i.e. a change in firstname will cause a change in lastname and middleinitial
--even though firstname is not a primary key)
CREATE TABLE ACCOUNT(
    USERNAME VARCHAR2(100),
    USER_PASSWORD VARCHAR(100) NOT NULL,
    PERMISSION NUMBER(1) NOT NULL, 
    STATUS NUMBER(1) NOT NULL,
    ACCOUNTAMOUNT NUMBER(38, 2) NOT NULL, 
    CONSTRAINT PK_a_user PRIMARY KEY(USERNAME)
);

CREATE TABLE USER_INFO(
    USERNAME VARCHAR2(100), 
    FIRSTNAME VARCHAR2(100) NOT NULL,
    LASTNAME VARCHAR2(100) NOT NULL,
    MIDDLEINITIAL VARCHAR2(100) NOT NULL,
    CONSTRAINT PK_username PRIMARY KEY(USERNAME), 
    CONSTRAINT FK_username FOREIGN KEY(USERNAME) REFERENCES ACCOUNT(USERNAME)
);
/
--Sample users for unit testing
--This is tempory fix. Eventually will mock up the database for unit testing
INSERT INTO ACCOUNT
VALUES('A','A',0,0,0);
INSERT INTO USER_INFO
VALUES('A','A','A','A');

INSERT INTO ACCOUNT 
VALUES('B', 'B',1,1,0);
INSERT INTO USER_INFO
VALUES('B', 'B','B', 'B');

INSERT INTO ACCOUNT 
VALUES('C','C',0,1,0);
INSERT INTO USER_INFO
VALUES('C', 'C', 'C','C');

INSERT INTO ACCOUNT 
VALUES('D','D',0,2,0);
INSERT INTO USER_INFO
VALUES('D', 'D', 'D', 'D');

INSERT INTO ACCOUNT 
VALUES('E','E',0,0,0);
INSERT INTO USER_INFO
VALUES('E', 'E', 'E', 'E');

INSERT INTO ACCOUNT 
VALUES('G','G',0,0,0);
INSERT INTO USER_INFO
VALUES('G', 'G', 'G', 'G');

SELECT * FROM ACCOUNT;
SELECT * FROM USER_INFO;

CREATE OR REPLACE PROCEDURE insert_user(
    new_username IN VARCHAR2, 
    new_firstname IN VARCHAR2, 
    new_lastname IN VARCHAR2, 
    new_middleinitial IN VARCHAR2,
    new_user_password IN VARCHAR2, 
    new_status IN NUMBER,
    new_permission IN NUMBER,
    new_accountamount IN NUMBER)
IS
BEGIN
    INSERT INTO ACCOUNT (USERNAME, USER_PASSWORD, STATUS, PERMISSION, ACCOUNTAMOUNT)
        VALUES(new_username, new_user_password, new_status, new_permission, new_accountamount);
        
    INSERT INTO USER_INFO (USERNAME, FIRSTNAME, LASTNAME, MIDDLEINITIAL)
        VALUES(new_username, new_firstname, new_lastname, new_middleinitial);
END;
/

--CREATE OR REPLACE PROCEDURE alter_user(
--    cur_username IN VARCHAR,
--    cur_status IN NUMBER,
--    cur_permission IN NUMBER,
--    new_status IN NUMBER,
--    new_permission IN NUMBER)
--IS
--BEGIN
--    UPDATE a_user
--    SET STATUS = new_status, PERMISSION=new_permission
--    WHERE USERNAME=cur_username AND STATUS=cur_status AND PERMISSION=cur_permission; 
--END;
--
----Testing the insert_user procedure!
--DECLARE
--    TMP VARCHAR(5);
--BEGIN
--    TMP := 'Z';
--    insert_user(TMP, TMP, TMP, TMP, TMP, 0, 0, 0);
--END;
--/
----Testing the alter_user procedure!
--DECLARE
--    CUR_USRNM VARCHAR(50);
--    CUR_STATUS NUMBER;
--    CUR_PERMISSION NUMBER;
--    NEW_STATUS NUMBER;
--    NEW_PERMISSION NUMBER;
--BEGIN
--    CUR_USRNM := 'E';
--    CUR_STATUS := 1;
--    CUR_PERMISSION := 1;
--    NEW_STATUS := 1;
--    NEW_PERMISSION := 1;
--    alter_user(CUR_USRNM, CUR_STATUS, CUR_PERMISSION, NEW_STATUS, NEW_PERMISSION);
--END;
--/
--
----SELECT ACCOUNT.USERNAME, ACCOUNT.USER_PASSWORD, ACCOUNT.STATUS, ACCOUNT.PERMISSION, 
----        ACCOUNT.ACCOUNTAMOUNT, USER_INFO.FIRSTNAME, USER_INFO.LASTNAME, 
----        USER_INFO.MIDDLEINITIAL 
----        FROM ACCOUNT JOIN USER_INFO
----        ON ACCOUNT.USERNAME = USER_INFO.USERNAME
----        WHERE ACCOUNT.USERNAME='evan' AND ACCOUNT.USER_PASSWORD='password';
--SELECT * FROM ACCOUNT NATURAL JOIN USER_INFO
--        WHERE USERNAME='evan' AND USER_PASSWORD='password';
--        
--SELECT * FROM ACCOUNT NATURAL JOIN USER_INFO
--    WHERE USERNAME = 'evan';
--SELECT * FROM USER_INFO;
--
--SELECT * FROM ACCOUNT JOIN USER_INFO ON USERNAME;
--
--UPDATE a_user 
--SET STATUS = 1, PERMISSION=1
--WHERE USERNAME= 'E' AND STATUS=0 AND PERMISSION=0;