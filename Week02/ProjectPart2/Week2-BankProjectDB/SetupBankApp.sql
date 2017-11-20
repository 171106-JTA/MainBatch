
/*******************************************************************************
   Bank App Database - Version 1.0
   Script: SetupBankApp.sql
   Description: Creates and populates the BankApp database.
   DB Server: Oracle
   Author: Matt Snee
********************************************************************************/

/*******************************************************************************
   Drop database if it exists
********************************************************************************/
--DROP USER mjsnee CASCADE;

/*******************************************************************************
   Create database
********************************************************************************/
--CREATE USER mjsnee 
--IDENTIFIED BY p4ssw0rd
--DEFAULT TABLESPACE users 
--TEMPORARY TABLESPACE temp
--QUOTA 10M ON users;

--GRANT connect to mjsnee;
--GRANT resource to mjsnee;
--GRANT create session TO mjsnee;
--GRANT create table TO mjsnee;
--GRANT create view TO mjsnee;
--
--conn mjsnee/p4ssw0rd


/*******************************************************************************
   Drop Tables if they exist 
********************************************************************************/

DROP TABLE Account CASCADE CONSTRAINTS PURGE;
DROP TABLE Administrator CASCADE CONSTRAINTS PURGE;
DROP TABLE Customer CASCADE CONSTRAINTS PURGE;
/

/*******************************************************************************
   Create Tables
********************************************************************************/
CREATE TABLE Account 
(
    acctNumber NUMBER NOT NULL,
    balance NUMBER(9,2) NOT NULL,
    CONSTRAINT PK_acct PRIMARY KEY (acctNumber)
);
/

CREATE TABLE Administrator
(
    adminName VARCHAR2(50) NOT NULL,
    password VARCHAR2(50) NOT NULL,
    CONSTRAINT PK_admin PRIMARY KEY (adminName)
);
/

CREATE TABLE Customer
(
    custId VARCHAR2(50) NOT NULL,
    username VARCHAR2(50) NOT NULL,
    password VARCHAR2(50) NOT NULL,
    firstName VARCHAR2(50) NOT NULL,
    lastName VARCHAR2(50) NOT NULL,
    ssn VARCHAR2(50) NOT NULL,
    acctNumber NUMBER NOT NULL,
    isBlocked NUMBER NOT NULL,
    CONSTRAINT PK_cust PRIMARY KEY (custId)
);
/

/*******************************************************************************
   Create Foreign Keys
********************************************************************************/
ALTER TABLE Customer ADD CONSTRAINT FK_acct
    FOREIGN KEY (acctNumber) REFERENCES Account (acctNumber)  ;
/

/*******************************************************************************
   Populate Tables
********************************************************************************/

INSERT INTO Account VALUES ( 1000000, 0.0);
INSERT INTO Account VALUES ( 1000001, 0.0);
INSERT INTO Account VALUES ( 1000002, 0.0);
INSERT INTO Account VALUES ( 1000003, 0.0);
INSERT INTO Account VALUES ( 1000004, 0.0);
INSERT INTO Account VALUES ( 1000005, 0.0);
INSERT INTO Account VALUES ( 1000006, 0.0);
INSERT INTO Account VALUES ( 1000007, 0.0);
INSERT INTO Account VALUES ( 1000008, 0.0);
INSERT INTO Account VALUES ( 1000009, 0.0);
/

INSERT INTO Administrator VALUES ( 'admin0', 'bankAppPW');
INSERT INTO Administrator VALUES ( 'admin1', 'bankAppPW');
INSERT INTO Administrator VALUES ( 'admin2', 'bankAppPW');
/

INSERT INTO Customer VALUES ( 0001, 'jsmith', '1234', 'John', 'Smith', '111-11-1111', 1000000, 1);
INSERT INTO Customer VALUES ( 0002, 'msmith', '1234', 'Mary', 'Smith', '111-11-1112', 1000001, 1);
INSERT INTO Customer VALUES ( 0003, 'jdoe', '1234', 'Jane', 'Doe', '111-11-1113', 1000002, 1);
INSERT INTO Customer VALUES ( 0004, 'ydoe', '1234', 'Yvette', 'Doe', '111-11-1114', 1000003, 1);
INSERT INTO Customer VALUES ( 0005, 'sstone', '1234', 'Sam', 'Stone', '111-11-1115', 1000004, 1);
INSERT INTO Customer VALUES ( 0006, 'hstone', '1234', 'Henry', 'Stone', '111-11-1116', 1000005, 1);
/

COMMIT;


