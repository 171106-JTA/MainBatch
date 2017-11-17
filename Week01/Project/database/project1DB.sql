DROP TABLE A_USER;
CREATE TABLE A_USER(
    USERNAME VARCHAR2(100),
    FIRSTNAME VARCHAR2(100),
    LASTNAME VARCHAR2(100),
    MIDDLEINITIAL VARCHAR2(1),
    USER_PASSWORD VARCHAR(100),
    STATUS NUMBER(1),
    PERMISSION NUMBER(1), 
    ACCOUNTAMOUNT NUMBER(38, 2), 
    CONSTRAINT PK_a_user PRIMARY KEY(USERNAME)
);
/
--Sample users, if needed
--INSERT INTO A_USER 
--VALUES('A', 'A', 'A','A','A',0,0,0);
--INSERT INTO A_USER 
--VALUES('B', 'B', 'B','B','B',0,0,0);
--INSERT INTO A_USER 
--VALUES('C', 'C', 'C','C','C',0,0,0);
--INSERT INTO A_USER 
--VALUES('D', 'D', 'D','D','D',0,0,0);
--
--SELECT * FROM A_USER;

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

--Testing the insert_user procedure!
DECLARE
    TMP VARCHAR(5);
BEGIN
    TMP := 'A';
    insert_user(TMP, TMP, TMP, TMP, TMP, 0, 0, 0);
END;
/
SELECT * FROM A_USER;