DROP TABLE A_USER;
--Create the Tables for the application
CREATE TABLE A_USER(
    USERNAME VARCHAR2(100),
    FIRSTNAME VARCHAR2(100),
    LASTNAME VARCHAR2(100),
    MIDDLEINITIAL VARCHAR2(1),
    USER_PASSWORD VARCHAR(100),
    PERMISSION NUMBER(1), 
    STATUS NUMBER(1),
    ACCOUNTAMOUNT NUMBER(38, 2), 
    CONSTRAINT PK_a_user PRIMARY KEY(USERNAME)
);
/
--Sample users, if needed
INSERT INTO A_USER 
VALUES('A', 'A', 'A','A','A',0,0,0);
INSERT INTO A_USER 
VALUES('B', 'B', 'B','B','B',1,1,0);
INSERT INTO A_USER 
VALUES('C', 'C', 'C','C','C',0,1,0);
INSERT INTO A_USER 
VALUES('D', 'D', 'D','D','D',0,2,0);
INSERT INTO A_USER 
VALUES('E', 'E', 'E','E','E',0,0,0);
INSERT INTO A_USER 
VALUES('G', 'G', 'G','G','G',0,0,0);

SELECT * FROM A_USER;

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
    INSERT INTO A_USER (USERNAME, FIRSTNAME, LASTNAME, MIDDLEINITIAL,
                        USER_PASSWORD, STATUS, PERMISSION, ACCOUNTAMOUNT)
    VALUES(new_username, new_firstname, new_lastname, new_middleinitial, 
        new_user_password, new_status, new_permission, new_accountamount);
END;
/

CREATE OR REPLACE PROCEDURE alter_user(
    cur_username IN VARCHAR2,
    cur_status IN NUMBER,
    cur_permission IN NUMBER,
    new_status IN NUMBER,
    new_permission IN NUMBER)
IS
BEGIN
    UPDATE a_user
    SET STATUS = new_status, PERMISSION=new_permission
    WHERE USERNAME=cur_username AND STATUS=cur_status AND PERMISSION=cur_permission; 
END;

--Testing the insert_user procedure!
DECLARE
    TMP VARCHAR(5);
BEGIN
    TMP := 'Z';
    insert_user(TMP, TMP, TMP, TMP, TMP, 0, 0, 0);
END;
/
--Testing the alter_user procedure!
DECLARE
    CUR_USRNM VARCHAR(50);
    CUR_STATUS NUMBER;
    CUR_PERMISSION NUMBER;
    NEW_STATUS NUMBER;
    NEW_PERMISSION NUMBER;
BEGIN
    CUR_USRNM := 'E';
    CUR_STATUS := '0';
    CUR_PERMISSION := '0';
    NEW_STATUS := '2';
    NEW_PERMISSION := '1';
    alter_user(CUR_USRNM, CUR_STATUS, CUR_PERMISSION, NEW_STATUS, NEW_PERMISSION);
END;
/

SELECT * FROM A_USER;