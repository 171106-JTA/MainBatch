DROP TABLE People; 
DROP TABLE Users;
DROP TABLE Admins;
CREATE TABLE People (
    firstname VARCHAR2(30) NOT NULL,
    lastname VARCHAR2(30) NOT NULL,
    SSN VARCHAR2(10) NOT NULL,
    Standing VARCHAR2(10) NOT NULL,
    Username VARCHAR(20) NOT NULL,
    PWord VARCHAR(20) NOT NULL,
    CONSTRAINT PPLPK_Constraint Primary key (Username)
    );
CREATE TABLE Users (
    firstname VARCHAR2(30) NOT NULL,
    lastname VARCHAR2(30) NOT NULL,
    SSN VARCHAR2(10) NOT NULL,
    Standing VARCHAR2(10) NOT NULL,
    Username VARCHAR(20) NOT NULL,
    PWord VARCHAR(20) NOT NULL,
    CONSTRAINT USERPK_Constraint Primary key (Username)
    CONSTRAINT ADFK_Constraint Foreign key (acc) REFERENCE Persons(acc)
    );
CREATE TABLE Admins(
    firstname VARCHAR2(30) NOT NULL,
    lastname VARCHAR2(30) NOT NULL,
    SSN Number(10) NOT NULL,
    Standing VARCHAR2(10) NOT NULL,
    Username VARCHAR(20) NOT NULL,
    PWord VARCHAR(20) NOT NULL,
    CONSTRAINT ADPK_Constraint Primary key (Username)
    CONSTRAINT ADFK_Constraint Foreign key (Username) REFERENCE Users(Username)
    );
INSERT INTO Admins
VALUES('Admin', 'admin', 65748392, 'Admin', 'Admin', 'istrator'); 
CREATE TABLE Accounts(
    Amount	NUMBER(10), 
    temp	timestamp,  
    );
CREATE TABLE Transactions(
    AccNum	NUMBER(10), 
    Tran Trans%TYPE,
    );
    
DROP SEQUENCE fc_seq;
CREATE SEQUENCE fc_seq
    start with 3
    increment by 1;
    
CREATE OR REPLACE TRIGGER ppl_trigger --auto increment
BEFORE INSERT ON PEOPLE
FOR EACH ROW
BEGIN --This keyword signifies a block for a transaction. 
    IF :new.acc IS NULL THEN 
    SELECT ppl.nextval INTO :new.acc from dual;    
    END IF;
END;   
/
SELECT * FROM USERS;
SELECT FROM Users
WHERE acc > ? and standing = ?;

PreparedStatement myStmt = myConn.prepareStatement("select * from Users" + " where acc > ? and standing = ?");

--DROP TYPE Accounts; 
CREATE OR REPLACE TYPE Accounts AS OBJECT(
AccNum	NUMBER(10), 
Tran	Number,  
CONSTRUCTOR FUNCTION Accounts 
RETURN SELF AS RESULT 
);
--DROP TYPE Trans
CREATE OR REPLACE TYPE Trans AS OBJECT(
Amount	NUMBER(10), 
temp	timestamp,  
CONSTRUCTOR FUNCTION Trans 
RETURN SELF AS RESULT 
);

DROP TYPE BODY Accounts; 
CREATE OR REPLACE TYPE BODY Accounts IS 
 
CONSTRUCTOR FUNCTION Accounts 
RETURN SELF AS RESULT 
IS 
BEGIN 
SELF.AccNum	:= NULL; 
SELF.Tran := NULL; 

RETURN; 
END; 
END; 
/ 

DROP TYPE BODY Trans; 
CREATE OR REPLACE TYPE BODY Trans IS 
 
CONSTRUCTOR FUNCTION Trans 
RETURN SELF AS RESULT 
IS 
BEGIN 
SELF.Amount	:= NULL; 
SELF.temp := NULL; 

RETURN; 
END; 
END; 
/ 
