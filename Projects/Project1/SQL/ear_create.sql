-- Author: Antony Lulciuc
-- FREE RESOURCES
DROP SEQUENCE EAR_USER_SEQ;
DROP SEQUENCE EAR_USER_INFO_SEQ;
DROP SEQUENCE EAR_BENCO_SEQ;
DROP SEQUENCE EAR_SUPERVISOR_SEQ;
DROP SEQUENCE EAR_MESSAGE_SEQ;
DROP SEQUENCE EAR_MESSAGE_ATTACHMENT_SEQ;
DROP SEQUENCE EAR_RECIPIENT_SEQ;
DROP SEQUENCE EAR_FORM_SEQ;
DROP SEQUENCE EAR_FORM_ATTACHMENT_SEQ;
DROP SEQUENCE EAR_FORM_STATUS_SEQ;
DROP SEQUENCE EAR_FORM_ASSIGNEE_SEQ;
DROP SEQUENCE COMPANY_EMPLOYEE_SEQ;

DROP TRIGGER EAR_USER_COUNTER;
DROP TRIGGER EAR_USER_INFO_COUNTER;
DROP TRIGGER EAR_BENCO_COUNTER;
DROP TRIGGER EAR_SUPERVISOR_COUNTER;
DROP TRIGGER EAR_MESSAGE_COUNTER;
DROP TRIGGER EAR_MESSAGE_ATTACHMENT_COUNTER;
DROP TRIGGER EAR_RECIPIENT_COUNTER;
DROP TRIGGER EAR_FORM_COUNTER;
DROP TRIGGER EAR_FORM_ATTACHMENT_COUNTER;
DROP TRIGGER EAR_FORM_STATUS_COUNTER;
DROP TRIGGER EAR_FORM_ASSIGNEE_COUNTER;
DROP TRIGGER COMPANY_EMPLOYEE_COUNTER;

DROP TABLE EAR_USER CASCADE CONSTRAINTS;
DROP TABLE EAR_USER_INFO CASCADE CONSTRAINTS;
DROP TABLE EAR_BENEFIT_COORDINATOR CASCADE CONSTRAINTS;
DROP TABLE EAR_SUPERVISOR CASCADE CONSTRAINTS;
DROP TABLE EAR_MESSAGE CASCADE CONSTRAINTS;
DROP TABLE EAR_MESSAGE_ATTACHMENT CASCADE CONSTRAINTS;
DROP TABLE EAR_RECIPIENT CASCADE CONSTRAINTS;
DROP TABLE EAR_FORM CASCADE CONSTRAINTS;
DROP TABLE EAR_FORM_ATTACHMENT CASCADE CONSTRAINTS;
DROP TABLE EAR_FORM_STATUS CASCADE CONSTRAINTS;
DROP TABLE EAR_FORM_ASSIGNEE CASCADE CONSTRAINTS;
DROP TABLE COMPANY_EMPLOYEE CASCADE CONSTRAINTS;


-- CREATE TABLES 

CREATE TABLE EAR_USER (
    id INTEGER,
    username VARCHAR2(32) UNIQUE NOT NULL,
    password VARCHAR2(32) NOT NULL,
    roleid INTEGER,
    CONSTRAINT pk_user_id PRIMARY KEY (id),
    CONSTRAINT fk_role_id FOREIGN KEY (roleid) REFERENCES CODE_LIST(id)
);

CREATE TABLE EAR_USER_INFO (
    id INTEGER,
    userid INTEGER,
    statecityid INTEGER,
    firstname VARCHAR2(32) NOT NULL,
    lastname VARCHAR2(32) NOT NULL,
    phonenumber VARCHAR2(24) NOT NULL,
    address VARCHAR2(64) NOT NULL,
    email VARCHAR2(50) NOT NULL,
    created Date NOT NULL,
    CONSTRAINT pk_info_id PRIMARY KEY (id), 
    CONSTRAINT fk_user_id FOREIGN KEY (userid) REFERENCES EAR_USER(id),
    CONSTRAINT fk_statecity_id FOREIGN KEY (statecityid) REFERENCES CODE_LIST(id)
);

CREATE TABLE EAR_SUPERVISOR (
    id INTEGER,
    bencoid INTEGER,
    supervisorid INTEGER,
    CONSTRAINT pk_supervisor_index_id PRIMARY KEY (id),
    CONSTRAINT fk_sub_benco_id FOREIGN KEY (bencoid) REFERENCES EAR_USER(id),
    CONSTRAINT fk_supervisor_id FOREIGN KEY (supervisorid) REFERENCES EAR_USER(id)
);

CREATE TABLE EAR_BENEFIT_COORDINATOR (
    id INTEGER,
    bencoid INTEGER,
    departmentid INTEGER,
    CONSTRAINT pk_benco_index_id PRIMARY KEY (id),
    CONSTRAINT fk_benco_id FOREIGN KEY (bencoid) REFERENCES EAR_USER(id),
    CONSTRAINT fk_department_id FOREIGN KEY (departmentid) REFERENCES CODE_LIST(id)
);

CREATE TABLE EAR_MESSAGE (
    id INTEGER,
    fromid INTEGER,
    messagepriorityid INTEGER,
    statusid INTEGER,
    title VARCHAR2(64),
    message VARCHAR2(4000) NULL,
    CONSTRAINT pk_message_id PRIMARY KEY (id),
    CONSTRAINT fk_sender_id FOREIGN KEY (fromid) REFERENCES EAR_USER(id),
    CONSTRAINT fk_messagepriority_id FOREIGN KEY (messagepriorityid) REFERENCES CODE_LIST(id),
    CONSTRAINT fk_message_status_id FOREIGN KEY (statusid) REFERENCES CODE_LIST(id)
);

CREATE TABLE EAR_MESSAGE_ATTACHMENT (
    id INTEGER,
    messageid INTEGER,
    blobattachment BLOB NULL,
    clobattachment CLOB NULL,
    CONSTRAINT pk_message_attachment_id PRIMARY KEY (id),
    CONSTRAINT fk_attach_message_id FOREIGN KEY (messageid) REFERENCES EAR_MESSAGE(id)
);

CREATE TABLE EAR_RECIPIENT (
    id INTEGER,
    messageid INTEGER,
    recipientid INTEGER,
    CONSTRAINT pk_recipient_id PRIMARY KEY (id),
    CONSTRAINT fk_recip_message_id FOREIGN KEY (messageid) REFERENCES EAR_MESSAGE(id),
    CONSTRAINT fk_recip_user_id FOREIGN KEY (recipientid) REFERENCES EAR_USER(id)
);

CREATE TABLE EAR_FORM (
    id INTEGER,
    userid INTEGER,
    departmentid INTEGER,
    eventstatecityid INTEGER,
    firstname VARCHAR2(32) NOT NULL,
    lastname VARCHAR2(32) NOT NULL,
    phonenumber VARCHAR2(24) NOT NULL,
    address VARCHAR2(64) NOT NULL,
    email VARCHAR2(50) NOT NULL,
    eventdatetimestart TIMESTAMP NOT NULL,
    eventdatetimeend TIMESTAMP NOT NULL,
    eventcost NUMBER(8,2) NOT NULL,
    description VARCHAR2(1024) NOT NULL,
    timeoffbegin DATE NOT NULL,
    timeoffend DATE NOT NULL,
    CONSTRAINT pk_form_id PRIMARY KEY (id),
    CONSTRAINT fk_form_user_id FOREIGN KEY (userid) REFERENCES EAR_USER(id),
    CONSTRAINT fk_form_department_id FOREIGN KEY (departmentid) REFERENCES CODE_LIST(id),
    CONSTRAINT fk_form_eventstatecity_id FOREIGN KEY (eventstatecityid) REFERENCES CODE_LIST(id)
);

CREATE TABLE EAR_FORM_ATTACHMENT (
    id INTEGER,
    formid INTEGER,
    typeid INTEGER,
    blobattachment BLOB NULL,
    clobattachment CLOB NULL,
    CONSTRAINT pk_form_attachment_id PRIMARY KEY (id),
    CONSTRAINT fk_form_id FOREIGN KEY (formid) REFERENCES EAR_FORM(id),
    CONSTRAINT fk_form_type_id FOREIGN KEY (typeid) REFERENCES CODE_LIST(id)
);

CREATE TABLE EAR_FORM_ASSIGNEE (
    id INTEGER,
    assigneeid INTEGER,
    formid INTEGER,
    CONSTRAINT pk_form_assignee_id PRIMARY KEY (id),
    CONSTRAINT fk_assignee_id FOREIGN KEY (assigneeid) REFERENCES EAR_USER(id),
    CONSTRAINT fk_application_form_id FOREIGN KEY (formid) REFERENCES EAR_FORM(id)
);

CREATE TABLE EAR_FORM_STATUS (
    id INTEGER,
    formid INTEGER, 
    processedby INTEGER,
    statusid INTEGER,
    originalestimate NUMBER(8,2) NOT NULL,
    reimbursement NUMBER(8,2) NULL,
    description VARCHAR(1024) NULL,
    timestamp DATE NOT NULL,
    CONSTRAINT pk_form_status_id PRIMARY KEY (id),
    CONSTRAINT fk_form_processedby_id FOREIGN KEY (processedby) REFERENCES EAR_USER(id),
    CONSTRAINT fk_form_status_id FOREIGN KEY (statusid) REFERENCES CODE_LIST(id),
    CONSTRAINT fk_form_status_form_id FOREIGN KEY (formid) REFERENCES EAR_FORM(id)
);

CREATE TABLE COMPANY_EMPLOYEE (
    id INTEGER,
    statecityid INTEGER,
    departmentid INTEGER,
    employeeid INTEGER UNIQUE NOT NULL,
    firstname VARCHAR2(32) NOT NULL,
    lastname VARCHAR2(32) NOT NULL,
    phonenumber VARCHAR2(24) NOT NULL,
    address VARCHAR2(64) NOT NULL,
    email VARCHAR2(50) NOT NULL,
    CONSTRAINT pk_company_emp_id PRIMARY KEY (id),
    CONSTRAINT fk_emp_statecity_id FOREIGN KEY (statecityid) REFERENCES CODE_LIST(id),
    CONSTRAINT fk_emp_department_id FOREIGN KEY (departmentid) REFERENCES CODE_LIST(id)
);

-- SEQUENCES 

CREATE SEQUENCE EAR_USER_SEQ 
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE EAR_USER_INFO_SEQ
    START WITH 1
    INCREMENT BY 1;
    
CREATE SEQUENCE EAR_BENCO_SEQ
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE EAR_SUPERVISOR_SEQ
    START WITH 1
    INCREMENT BY 1;
    
CREATE SEQUENCE EAR_MESSAGE_SEQ 
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE EAR_MESSAGE_ATTACHMENT_SEQ
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE EAR_RECIPIENT_SEQ 
    START WITH 1
    INCREMENT BY 1;
    
CREATE SEQUENCE EAR_FORM_SEQ 
    START WITH 1
    INCREMENT BY 1;
    
CREATE SEQUENCE EAR_FORM_ATTACHMENT_SEQ 
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE EAR_FORM_ASSIGNEE_SEQ 
    START WITH 1
    INCREMENT BY 1;    
    
 CREATE SEQUENCE EAR_FORM_STATUS_SEQ 
    START WITH 1
    INCREMENT BY 1;   
    
CREATE SEQUENCE COMPANY_EMPLOYEE_SEQ
    START WITH 1
    INCREMENT BY 1;   
    
-- TRIGGERS 


CREATE OR REPLACE TRIGGER EAR_USER_COUNTER
    BEFORE INSERT ON EAR_USER
    FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN 
        SELECT EAR_USER_SEQ.nextval INTO :new.id FROM DUAL;
    END IF;
END;
/  

CREATE OR REPLACE TRIGGER EAR_USER_INFO_COUNTER
    BEFORE INSERT ON EAR_USER_INFO
    FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN 
        SELECT EAR_USER_INFO_SEQ.nextval INTO :new.id FROM DUAL;
    END IF;
    
    IF :new.created IS NULL THEN
        SELECT TO_DATE(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') INTO :new.created FROM DUAL;
    END IF;
END;
/  

CREATE OR REPLACE TRIGGER EAR_BENCO_COUNTER
    BEFORE INSERT ON EAR_BENEFIT_COORDINATOR
    FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN 
        SELECT EAR_BENCO_SEQ.nextval INTO :new.id FROM DUAL;
    END IF;
END;
/  

CREATE OR REPLACE TRIGGER EAR_SUPERVISOR_COUNTER
    BEFORE INSERT ON EAR_SUPERVISOR
    FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN 
        SELECT EAR_SUPERVISOR_SEQ.nextval INTO :new.id FROM DUAL;
    END IF;
END;
/  

CREATE OR REPLACE TRIGGER EAR_MESSAGE_COUNTER
    BEFORE INSERT ON EAR_MESSAGE
    FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN 
        SELECT EAR_MESSAGE_SEQ.nextval INTO :new.id FROM DUAL;
    END IF;
END;
/  

CREATE OR REPLACE TRIGGER EAR_MESSAGE_ATTACHMENT_COUNTER
    BEFORE INSERT ON EAR_MESSAGE_ATTACHMENT
    FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN 
        SELECT EAR_MESSAGE_ATTACHMENT_SEQ.nextval INTO :new.id FROM DUAL;
    END IF;
END;
/  

CREATE OR REPLACE TRIGGER EAR_RECIPIENT_COUNTER
    BEFORE INSERT ON EAR_RECIPIENT
    FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN 
        SELECT EAR_RECIPIENT_SEQ.nextval INTO :new.id FROM DUAL;
    END IF;
END;
/  

CREATE OR REPLACE TRIGGER EAR_FORM_COUNTER
    BEFORE INSERT ON EAR_FORM
    FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN 
        SELECT EAR_FORM_SEQ.nextval INTO :new.id FROM DUAL;
    END IF;
END;
/  

CREATE OR REPLACE TRIGGER EAR_FORM_ASSIGNEE_COUNTER
    BEFORE INSERT ON EAR_FORM_ASSIGNEE
    FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN 
        SELECT EAR_FORM_ASSIGNEE_SEQ.nextval INTO :new.id FROM DUAL;
    END IF;
END;
/  

CREATE OR REPLACE TRIGGER EAR_FORM_ATTACHMENT_COUNTER
    BEFORE INSERT ON EAR_FORM_ATTACHMENT
    FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN 
        SELECT EAR_FORM_ATTACHMENT_SEQ.nextval INTO :new.id FROM DUAL;
    END IF;
END;
/  

CREATE OR REPLACE TRIGGER EAR_FORM_STATUS_COUNTER
    BEFORE INSERT ON EAR_FORM_STATUS
    FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN 
        SELECT EAR_FORM_STATUS_SEQ.nextval INTO :new.id FROM DUAL;
    END IF;
END;
/ 

CREATE OR REPLACE TRIGGER COMPANY_EMPLOYEE_COUNTER
    BEFORE INSERT ON COMPANY_EMPLOYEE
    FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN 
        SELECT COMPANY_EMPLOYEE_SEQ.nextval INTO :new.id FROM DUAL;
    END IF;
END;
/  















