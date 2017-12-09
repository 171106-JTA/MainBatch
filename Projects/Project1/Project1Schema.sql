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
employee -> Logged in as standard user
benco -> Logged in as BenCo
direct_supervisor -> Logged in as Direct Supervisor
department_head -> Logged in as Department Head
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
    eventTypeName VARCHAR(100),
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
INSERT INTO eventTypes (eventTypeName, reimbursementPercentage) values('Univerisity Courses', .80);
INSERT INTO eventTypes (eventTypeName, reimbursementPercentage) values('Seminars', .60);
INSERT INTO eventTypes (eventTypeName, reimbursementPercentage) values('Certification Preparation Classes', .75);
INSERT INTO eventTypes (eventTypeName, reimbursementPercentage) values('Certification', 1.00);
INSERT INTO eventTypes (eventTypeName, reimbursementPercentage) values('Technical Training', .90);
INSERT INTO eventTypes (eventTypeName, reimbursementPercentage) values('Other', .30);
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
--DELETE FROM Address
--WHERE AddressID = 1;
--SELECT LAST_INSERT_ID FROM ADDRESS;
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
    submissionDateAndTime TIMESTAMP(0),
    eventLocation NUMBER(9),                --Foreign key to address table
    eventDescription CLOB, 
    eventCost NUMBER(11,2),                 --Allow maxmimum event cost to be $100,000,000.00
    gradingFormatID NUMBER(2),              --Foreign key to grading format table
    eventTypeID NUMBER(2),                  --Foreign key to event type table
    work_related_justification CLOB,        --Description of how the training is work related
    passing_grade NUMBER(3),                --Passing grade between 0 < X < 100. Assume passing grade is a percentage
    status NUMBER(2),                       --0 -> Awaiting Approval, 1 -> Pending, 2 -> Accapted
    exceeds_funds NUMBER(1),                --0 -> not exceeding funds, 1 -> Exceeds available funds
    reason_for_excess_funds CLOB,
    reason_for_denial CLOB, 
    approval_by_direct_supervisor NUMBER(1), -- 0 -> Awaiting approval by DS; 1 -> Approved by DS
    approval_by_department_head NUMBER(1),   -- 0 -> Awaiting approval by DH; 1 -> Approved by DH
    approval_by_benco NUMBER(1),             -- 0 -> Awaiting approval by BC; 1 -> Approved by BC
    urgency NUMBER(1),                       -- 0 -> standard urgency; 1 -> course is less than 2 weeks from beginning
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
--INSERT INTO Address (numberAndStreet, city, state, zip, aptNumber)
--VALUES('123 Not Somethign', 'Reston', 'VA', 20190, 2);
--SELECT * FROM ADDRESS;
--INSERT INTO ReimbursementForm (
--    username, 
--    eventDateAndTime, 
--    eventLocation, 
--    eventDescription, 
--    eventCost, 
--    gradingFormatID, 
--    eventTypeID)
--VALUES (
--    'A', 
--    TO_TIMESTAMP ('3-Dec-17 14:10', 'DD-Mon-RR HH24:MI'), 
--    1, --Assume test code for Address table was run
--    'This is an event description #1', 
--    50.04, 
--    1, --grading format
--    3  --event type
--    );
--SELECT * FROM ReimbursementForm;
--DELETE FROM ReimbursementForm
--WHERE REIMBURSEMENTID = 3;
/

--------------------------------
-- Stored Procedures
--------------------------------
CREATE OR REPLACE PROCEDURE newReimbursementForm(
    username IN VARCHAR,
    event_street IN VARCHAR,
    event_city IN VARCHAR,
    event_state IN VARCHAR,
    event_zip IN VARCHAR,
    event_apt IN VARCHAR,
    date_and_time IN VARCHAR,
    submit_date_and_time IN VARCHAR,
    description IN VARCHAR,
    eventcost IN VARCHAR,
    grading_format IN VARCHAR,
    event_type IN VARCHAR,
    work_related_justification IN CLOB,
    passing_grade NUMBER
    )
IS
    new_address_id NUMBER;
    grading_format_id NUMBER;
    event_type_id NUMBER;
BEGIN
    INSERT INTO Address (numberAndStreet, city, state, zip, aptNumber)
        VALUES(event_street, event_city, event_state, event_zip, event_apt);
    new_address_id := Address_ID_SEQ.CURRVAL;
    SELECT gradingFormatID INTO grading_format_id FROM gradingFormats  
    WHERE gradingFormatName = grading_format;
    SELECT eventTypeID INTO event_type_id FROM eventTypes  
    WHERE eventTypeName = event_type;
    
    INSERT INTO ReimbursementForm (
        username,
        eventDateAndTime,
        submissionDateAndTime,
        eventLocation, 
        eventDescription, 
        eventCost,
        gradingFormatID,
        eventTypeID, 
        status, 
        exceeds_funds,
        work_related_justification,
        passing_grade,
        reason_for_excess_funds,
        reason_for_denial, 
        approval_by_direct_supervisor, -- 0 -> Awaiting approval by DS; 1 -> Approved by DS
        approval_by_department_head,   -- 0 -> Awaiting approval by DH; 1 -> Approved by DH
        approval_by_benco,             -- 0 -> Awaiting approval by BC; 1 -> Approved by BC
        urgency
        )
    VALUES (
        username,
        TO_TIMESTAMP (date_and_time, 'DD-Mon-YYYY HH24:MI'),
        TO_TIMESTAMP (submit_date_and_time, 'DD-Mon-YYYY HH24:MI'),
        new_address_id,
        description, 
        eventcost,
        grading_format_id,
        event_type_id,
        0,                  --Default is pending status
        0,                  --Default exceeds_funds
        work_related_justification,
        passing_grade, 
        null,               --Defaut reason_for_excess_funds
        null,               --Defaut reason_for_denial
        0,                  --Defaut approval_by_direct_supervisor
        0,                  --Defaut approval_by_department_head
        0,                  --Defaut approval_by_benco
        0                   --Defaut urgency
        );
END;
/

--DECLARE
--  Temp DATE := TO_TIMESTAMP (CURRENT_TIMESTAMP, 'DD-Mon-YYYY HH24:MI')
--BEGIN
-- dbms_output.put_line(To_char (CURRENT_TIMESTAMP, 'DD-MON-YYYY HH24:MI');
--END;

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
