SELECT * FROM reimbursement_case;

CREATE TABLE employee (
    employee_id number(6) primary key,
    employee_username varchar2(100) unique not null,
    employee_password varchar2(100) not null,
    employee_email varchar2(100) not null,
    employee_fname varchar2(100) not null,
    employee_lname varchar2(100) not null,
    date_created TIMESTAMP(6)
);
/

CREATE SEQUENCE employee_seq
    START WITH 1
    INCREMENT BY 1;
/
CREATE OR REPLACE TRIGGER employee_trigger 

BEFORE INSERT ON employee

FOR EACH ROW

BEGIN

   IF :new.employee_id IS NULL THEN

   SELECT employee_seq.nextval INTO :new.employee_id from dual;    

   END IF;

   IF :new.date_created IS NULL THEN

   :new.date_created := CURRENT_DATE;    

   END IF;
   
   IF :new.emp_category IS NULL THEN 
   :new.emp_category := 4;
   END IF;

END;    
/


CREATE SEQUENCE Reimbursement_case_seq
    START WITH 1
    INCREMENT BY 1;
/

CREATE OR REPLACE TRIGGER reim_case_trigger
before insert on reimbursement_case
for each row 
BEGIN
    IF :new.case_id IS NULL THEN

     SELECT Reimbursement_case_seq.nextval INTO :new.case_id from dual;    

    END IF;
    IF :new.request_date IS NULL THEN
    
    :new.request_date := CURRENT_DATE;    
    
    END IF;
    IF :new.CASE_STATUS is NULL THEN
    :new.case_status := 1;
    END IF;
    
END;
/

CREATE TABLE Reimbursement_case (
    case_id number(6) primary key,
    employee_id number(6) NOT NULL,
    event_date DATE NOT NULL,
    request_date DATE,
    case_duration NUMBER(6) NOT NULL,
    case_location VARCHAR2(100) NOT NULL,
    case_description VARCHAR2(400), 
    case_cost decimal(6,2) NOT NULL,
    case_grading_format varchar2 (100) NOT NULL,
    case_event_type varchar2 (100) NOT NULL,
    case_attachment BLOB,
    CONSTRAINT FK_EmployeeCase Foreign KEY (employee_id)
    REFERENCES EMPLOYEE(employee_id)
);
/

select * FROM reimbursement_case;
select * FROM employee;
insert into REIMBURSEMENT_CASE (
case_id,
    employee_id,
    event_date,
    request_date,
    case_duration,
    case_location,
    case_description ,
    case_cost,
    case_grading_format,
    case_event_type
    ) values (
    4,
    23,
    TO_DATE('17/12/2015', 'DD/MM/YYYY'),
    TO_DATE('17/12/2015', 'DD/MM/YYYY'),
    7,
    'Revature',
    'Its good',
    183.42,
    'number',
    'training'
);
/
SELECT * FROM Employee;

CREATE TABLE EMP_category (
    category_ID NUMBER (2) NOT NULL PRIMARY KEY,
    category_type VARCHAR(100)
);

INSERT INTO EMP_CATEGORY 
VALUES(1, 'ADMIN');
INSERT INTO EMP_CATEGORY 
VALUES(2, 'DEPT_HEAD');
INSERT INTO EMP_CATEGORY 
VALUES(3, 'SUPERVISOR');
INSERT INTO EMP_CATEGORY 
VALUES(4, 'DEFAULT');

SELECT * FROM EMP_CATEGORY;
commit;
ALTER TABLE EMPLOYEE ADD emp_category number;
ALTER TABLE EMPLOYEE ADD FOREIGN KEY (emp_category) references EMP_CATEGORY(category_id);

SELECT * FROM REIMBURSEMENT_CASE;
SELECT * FROM EMPLOYEE;
INSERT INTO REIMBURSEMENT_CASE (EMPLOYEE_ID, EVENT_DATE, CASE_DURATION, CASE_LOCATION, CASE_DESCRIPTION, CASE_GRADING_FORMAT, CASE_EVENT_TYPE, Case_cost) 
VALUES(23,TO_DATE('17/12/2015', 'DD/MM/YYYY'), 2, 'Reston, VA' , 'Studying stuff', 'Presentation', 'Seminar', 200.50);
commit;

DELETE FROM REIMBURSEMENT_CASE
WHERE CASE_DURATION= 2;

UPDATE EMPLOYEE SET EMP_CATEGORY=1
WHERE EMPLOYEE_USERNAME='tester1';

ALTER TABLE REIMBURSEMENT_CASE
ADD case_status number(3);

CREATE TABLE CASE_STATUS (
    STATUS_ID NUMBER (2) NOT NULL PRIMARY KEY,
    STATUS_type VARCHAR(100)
);


INSERT INTO CASE_STATUS
VALUES(1, 'IN PROCESS');
INSERT INTO CASE_STATUS
VALUES(2, 'SUPERVISOR APPROVED');
INSERT INTO CASE_STATUS
VALUES(3, 'DEPT_HEAD APPROVED');
INSERT INTO CASE_STATUS
VALUES(4, 'FULLY APPROVED');
INSERT INTO CASE_STATUS
VALUES(5, 'DENIED');

SELECT * FROM CASE_STATUS;

SELECT * FROM REIMBURSEMENT_CASE;

SELECT * FROM EMPLOYEE;

SELECT * FROM EMP_CATEGORY;
UPDATE REIMBURSEMENT_CASE SET CASE_STATUS = 3  WHERE CASE_ID= 4;
commit;