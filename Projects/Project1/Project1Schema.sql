--DROP TABLE riembursment;
--DROP TABLE employee_info; 
--DROP TABLE direct_supervisor; 
--DROP TABLE department_head; 
--DROP TABLE benefits_coordinator;

DROP TABLE Login CASCADE CONSTRAINTS;
DROP TABLE EmployeeInformation CASCADE CONSTRAINTS;
DROP TABLE ReimbursementForm CASCADE CONSTRAINTS;
DROP TABLE eventTypes CASCADE CONSTRAINTS;
DROP TABLE gradingFormats CASCADE CONSTRAINTS;
DROP TABLE Address CASCADE CONSTRAINTS;

/*
permission: 
0 -> Logged in as standard user
1 -> Logged in as BenCo
2 -> Logged in as Direct Supervisor
3 -> Logged in as Department Head
*/
CREATE TABLE Login (
    username VARCHAR(100),
    password VARCHAR(100),
    permission VARCHAR(100),
    CONSTRAINT PK_username primary key (username)
);
/
--Test INSERT for Login table
--INSERT INTO Login values ('TestUser', 'TestUser','A_permission');
--SELECT * FROM Login;
/

CREATE TABLE EmployeeInformation (
    username VARCHAR(100),
    firstName VARCHAR(500),
    lastName VARCHAR(500),
    middleInitial VARCHAR(1),
    email VARCHAR(500),
    CONSTRAINT PK_Employee_Username PRIMARY KEY (username),
    CONSTRAINT FK_Employee_Username FOREIGN KEY (username) REFERENCES Login(username)
);
/
--Test INSERT for EmployeeInformation table
--INSERT INTO EmployeeInformation values('TestUser', 'Test', 'User', 'Z', 'tester@tester.com');
--SELECT * FROM EmployeeInformation;
/

--------------------------------
-- Event Types
--------------------------------
DROP SEQUENCE EventType_ID_SEQ;
--SEQUENCE for Grading Formats
CREATE SEQUENCE EventType_ID_SEQ
    START WITH 1
    INCREMENT BY 1; 
--TABLE  for Event Types
CREATE TABLE eventTypes (
    eventTypeID NUMBER(9),
    evenTypeName VARCHAR(100),
    reimbursementPercentage NUMBER(3,2), --Allow three digits for 1.00 (i.e. %100)
    CONSTRAINT PK_eventTypeID PRIMARY KEY (eventTypeID)
);
--TRIGGER for Event Types
CREATE OR REPLACE TRIGGER EventType_SEQ_TRIGGER
BEFORE INSERT ON eventTypes
FOR EACH ROW
BEGIN
    IF :NEW.eventTypeID IS NULL THEN
    SELECT EventType_ID_SEQ.NEXTVAL INTO :NEW.eventTypeID FROM DUAL;
    END IF;
END;
/
--Default values for event types.
INSERT INTO eventTypes (evenTypeName, reimbursementPercentage) values('Univerisity Courses', .80);
INSERT INTO eventTypes (evenTypeName, reimbursementPercentage) values('Seminars', .60);
INSERT INTO eventTypes (evenTypeName, reimbursementPercentage) values('Certification Preparation Classes', .75);
INSERT INTO eventTypes (evenTypeName, reimbursementPercentage) values('Certification', 1.00);
INSERT INTO eventTypes (evenTypeName, reimbursementPercentage) values('Technical Training', .90);
INSERT INTO eventTypes (evenTypeName, reimbursementPercentage) values('Other', .30);
/
SELECT * FROM eventTypes;

--------------------------------
-- Grading Format
--------------------------------
DROP SEQUENCE GradingFormat_ID_SEQ;
--SEQUENCE for Grading Formats
CREATE SEQUENCE GradingFormat_ID_SEQ
    START WITH 1
    INCREMENT BY 1; 
--TABLE  for Grading Format 
CREATE TABLE gradingFormats (
    gradingFormatID NUMBER(2), --Allow 99 unique grading formats
    gradingFormatName VARCHAR(100),
    CONSTRAINT PK_gradingFormatID PRIMARY KEY (gradingFormatID)
);
--TRIGGER for Grading Format
CREATE OR REPLACE TRIGGER GradingFormat_SEQ_TRIGGER
BEFORE INSERT ON gradingFormats
FOR EACH ROW
BEGIN
    IF :NEW.gradingFormatID IS NULL THEN
    SELECT GradingFormat_ID_SEQ.NEXTVAL INTO :NEW.gradingFormatID FROM DUAL;
    END IF;
END;
/
--Default values for grading formats.
INSERT INTO gradingFormats (gradingFormatName) values('grade');
INSERT INTO gradingFormats (gradingFormatName) values('presentation');
/

--------------------------------
-- Address
--------------------------------
DROP SEQUENCE Address_ID_SEQ; 
--SEQUENCE for Address
CREATE SEQUENCE Address_ID_SEQ
    START WITH 1
    INCREMENT BY 1; 
--Address TABLE
CREATE TABLE Address (
    addressID number(9), --Allow 100,000,000 unique addresses
    numberAndStreet VARCHAR(500),
    city VARCHAR(500),
    state VARCHAR(500),
    zip NUMBER(8),
    aptNumber VARCHAR(6), --Allow up to 999,999 for the apartment number
    CONSTRAINT PK_addressID PRIMARY KEY (addressID)
);
--TRIGGER for Address
CREATE OR REPLACE TRIGGER Address_SEQ_TRIGGER
BEFORE INSERT ON Address
FOR EACH ROW
BEGIN
    IF :NEW.addressID IS NULL THEN
    SELECT Address_ID_SEQ.NEXTVAL INTO :NEW.addressID FROM DUAL;
    END IF;
END;
/
--Test INSERT for Address TABLE
--INSERT INTO Address (numberAndStreet, city, state, zip, aptNumber)
--VALUES('11730 Plaza America Dr.', 'Reston', 'VA', 20190, 2);
--SELECT * FROM Address;
/

--------------------------------
-- Reimbursement Forms
--------------------------------
DROP SEQUENCE RF_ID_SEQ;
--SEQUENCE for ReimbursementForm
CREATE SEQUENCE RF_ID_SEQ
    START WITH 1
    INCREMENT BY 1; 
--TABLE for ReimbursementForm
CREATE TABLE ReimbursementForm (
    reimbursementID NUMBER(9),
    username VARCHAR(100), 
    eventDateAndTime TIMESTAMP(0), 
    eventLocation number(9),    --Foreign key to address table
    eventDescription CLOB, 
    eventCost NUMBER(11,2),     --Allow maxmimum event cost to be $100,000,000.00
    gradingFormatID number(2),  --Foreign key to grading format table
    eventTypeID number(2),      --Foreign key to event type table
    CONSTRAINT PK_reimbursementID PRIMARY KEY (reimbursementID),
    CONSTRAINT FK_Reimbursement_Username FOREIGN KEY (username) REFERENCES Login(username),
    CONSTRAINT FK_eventLocation FOREIGN KEY (eventLocation) REFERENCES Address(addressID),
    CONSTRAINT FK_gradingFormatID FOREIGN KEY (gradingFormatID) REFERENCES gradingFormats(gradingFormatID)
);
--TRIGGER for ReimbursementForm
CREATE OR REPLACE TRIGGER RF_SEQ_TRIGGER
BEFORE INSERT ON ReimbursementForm
FOR EACH ROW
BEGIN
    IF :NEW.reimbursementID IS NULL THEN
    SELECT RF_ID_SEQ.NEXTVAL INTO :NEW.reimbursementID FROM DUAL;
    END IF;
END;
/
--Test INSERT for ReimbursementForm
--INSERT INTO ReimbursementForm (
--    username, 
--    eventDateAndTime, 
--    eventLocation, 
--    eventDescription, 
--    eventCost, 
--    gradingFormatID, 
--    eventTypeID)
--VALUES (
--    'TestUser', 
--    TO_TIMESTAMP ('3-Dec-17 14:10', 'DD-Mon-RR HH24:MI'), 
--    1, --Assume test code for Address table was run
--    'This is an event description', 
--    50.04, 
--    1, --grading format
--    3  --event type
--    );
--SELECT * FROM ReimbursementForm;


INSERT INTO Login 
values ('evan', 'password', 'employee');
INSERT INTO Login 
values ('A', 'A', 'employee');
INSERT INTO Login 
values ('B', 'B', 'benco');
INSERT INTO Login 
values ('C', 'C', 'direct_supervisor');
INSERT INTO Login 
values ('D', 'D', 'department_head');
INSERT INTO Login 
values ('E', 'E', 'employee');


SELECT * FROM Login; 
SELECT upper(PERMISSION) FROM Login;
--CREATE TABLE Employee_Info (
--    employeeid NUMBER(7),
--    email VARCHAR2(500),
----    funds number(6,2),
--    firstname VARCHAR2(150), 
--    lastname VARCHAR2(150), 
--    CONSTRAINT PK_employeeid PRIMARY KEY(employeeid)
--);
--
--CREATE TABLE Reimbursment (
--    ReimbursementID NUMBER(7),
--    Amount number(6,2),
--    --Holds status of reimbursement. 
--    --0 = waiting approval, 1 = pending, 2 = awaiting confirmation, 3 = confirmed
--    status NUMBER(1), 
--    employeeID NUMBER(7),
--    CONSTRAINT PK_reimbursementID PRIMARY KEY(ReimbursementID),
--    constraint employeeID FOREIGN KEY (employeeID) REFERENCES Employee_Info(employeeID)
--);
