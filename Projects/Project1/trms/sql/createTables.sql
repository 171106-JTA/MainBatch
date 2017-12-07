DROP TABLE reqhistory;
DROP TABLE grade;
DROP TABLE reqfile;
DROP TABLE request;
DROP TABLE reqstatus;

DROP TABLE filepurpose;
DROP TABLE mimetype;
DROP TABLE eventtype;

DROP TABLE empinfo;
DROP TABLE address;

DROP TABLE employee;
DROP TABLE emprank;
DROP TABLE department;
DROP TABLE empaccount;



CREATE TABLE empaccount (
    email VARCHAR2(50),
    pass VARCHAR2(50),
    CONSTRAINT pk_account PRIMARY KEY (email)
);
/

-- lookup table
CREATE TABLE department (
    dept_name VARCHAR2(50),
    CONSTRAINT pk_dept PRIMARY KEY (dept_name)
);
/

-- lookup table
CREATE TABLE emprank (
    emp_rank VARCHAR2(50),
    CONSTRAINT pk_rank PRIMARY KEY (emp_rank)
);
/

CREATE TABLE employee (
    email VARCHAR2(50),
    emp_rank VARCHAR2(50),
    dept_name VARCHAR2(50),
    supe_email VARCHAR2(50),
    first_name VARCHAR2(50),
    last_name VARCHAR2(50),
    funds FLOAT,
    CONSTRAINT pk_employee PRIMARY KEY (email),
    CONSTRAINT fk_emp_acc FOREIGN KEY (email) REFERENCES empaccount(email),
    CONSTRAINT fk_emp_supe FOREIGN KEY (supe_email) REFERENCES employee(email),
    CONSTRAINT fk_emp_dept FOREIGN KEY (dept_name) REFERENCES department(dept_name),
    CONSTRAINT fk_emp_rank FOREIGN KEY (emp_rank) REFERENCES emprank(emp_rank)
);
/


CREATE TABLE address (
    addr_id NUMBER,
    addr_address VARCHAR2(50),
    addr_city VARCHAR2(50),
    addr_state VARCHAR2(50),
    addr_zip VARCHAR2(12),
    CONSTRAINT pk_address PRIMARY KEY (addr_id)
);
/
DROP SEQUENCE seq_addr;
CREATE SEQUENCE seq_addr
    START WITH 1
    INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trig_seq_addr
BEFORE INSERT ON address
FOR EACH ROW
BEGIN
    IF :NEW.addr_id IS NULL THEN
        SELECT seq_addr.nextval INTO :NEW.addr_id FROM dual;
    END IF;
END;
/

CREATE TABLE empinfo (
    email VARCHAR2(50),
    birthday DATE,
    addr_id NUMBER,
    phone VARCHAR2(12),
    CONSTRAINT pk_empinfo PRIMARY KEY (email),
    CONSTRAINT fk_info_acc FOREIGN KEY (email) REFERENCES empaccount(email),
    CONSTRAINT fk_info_addr FOREIGN KEY (addr_id) REFERENCES address(addr_id)
);
/



CREATE TABLE eventtype (
    event_type VARCHAR2(50),
    percent_off FLOAT,
    CONSTRAINT pk_eventtype PRIMARY KEY (event_type),
    CONSTRAINT ck_percent_off CHECK (percent_off >= 0 AND percent_off <= 100)
);

-- lookup table
CREATE TABLE reqstatus (
    req_status VARCHAR2(50),
    CONSTRAINT pk_reqstatus PRIMARY KEY (req_status)
);
/

CREATE TABLE request (
    req_id NUMBER,
    filer_email VARCHAR2(50),
    event_type VARCHAR2(50),
    addr_id NUMBER,
    event_cost FLOAT,
    funding FLOAT,
    req_status VARCHAR2(50),
    is_urgent CHAR(1),
    exceeds_status CHAR(1),
    date_filed DATE,
    event_start DATE,
    event_end DATE,
    description VARCHAR2(300),
    CONSTRAINT pk_request PRIMARY KEY (req_id),
    CONSTRAINT fk_req_emp FOREIGN KEY (filer_email) REFERENCES employee(email),
    CONSTRAINT fk_req_type FOREIGN KEY (event_type) REFERENCES eventtype(event_type),
    CONSTRAINT fk_req_addr FOREIGN KEY (addr_id) REFERENCES address(addr_id),
    CONSTRAINT ck_is_urgent CHECK (is_urgent = 'Y' OR is_urgent = 'N'),
    CONSTRAINT ck_exceeds_status CHECK (exceeds_status = 'Y' OR exceeds_status = 'N'),
    CONSTRAINT ck_dates CHECK (date_filed <= (event_start - 14) AND event_start <= event_end)   /* subtracting is done by day increments */
);
/
DROP SEQUENCE seq_req;
CREATE SEQUENCE seq_req
    START WITH 1
    INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trig_seq_req
BEFORE INSERT ON request
FOR EACH ROW
BEGIN
    IF :NEW.req_id IS NULL THEN
        SELECT seq_req.nextval INTO :NEW.req_id FROM dual;
    END IF;
END;
/


-- lookup table
CREATE TABLE filepurpose (
    file_purpose VARCHAR2(50),
    CONSTRAINT pk_filepurpose PRIMARY KEY (file_purpose)
);
/

CREATE TABLE mimetype (
    mime_type VARCHAR(50),
    CONSTRAINT pk_mimetype PRIMARY KEY(mime_type)
);
/

CREATE TABLE reqfile(
    file_id NUMBER,
    req_id NUMBER,
    file_purpose VARCHAR2(50),
    mime_type VARCHAR2(50),
    file_name VARCHAR2(50),
    file_content BLOB,
    CONSTRAINT pk_file PRIMARY KEY (file_id),
    CONSTRAINT fk_file_req FOREIGN KEY (req_id) REFERENCES request(req_id),
    CONSTRAINT fk_file_purpose FOREIGN KEY (file_purpose) REFERENCES filepurpose(file_purpose),
    CONSTRAINT fk_file_mime FOREIGN KEY (mime_type) REFERENCES mimetype(mime_type)
);
/
DROP SEQUENCE seq_file;
CREATE SEQUENCE seq_file
    START WITH 1
    INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trig_seq_file
BEFORE INSERT ON reqfile
FOR EACH ROW
BEGIN
    IF :NEW.file_id IS NULL THEN
        SELECT seq_file.nextval INTO :NEW.file_id FROM dual;
    END IF;
END;
/

CREATE TABLE reqhistory (
    hist_id NUMBER,
    req_id NUMBER,
    approver_email VARCHAR2(50),
    approval_file NUMBER,
    post_status VARCHAR(50),
    approval_time TIMESTAMP,

    CONSTRAINT pk_hist PRIMARY KEY(hist_id),
    CONSTRAINT fk_hist_req FOREIGN KEY (req_id) REFERENCES request(req_id),
    CONSTRAINT fk_hist_emp FOREIGN KEY (approver_email) REFERENCES employee(email),
    CONSTRAINT fk_hist_file FOREIGN KEY (approval_file) REFERENCES reqfile(file_id),
    CONSTRAINT fk_hist_status FOREIGN KEY (post_status) REFERENCES reqstatus(req_status),
    CONSTRAINT ck_approver_or_file CHECK ((approval_file IS NULL AND approver_email IS NOT NULL) OR (approval_file IS NOT NULL AND approver_email IS NULL))
);
/
DROP SEQUENCE seq_hist;
CREATE SEQUENCE seq_hist
    START WITH 1
    INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trig_seq_hist
BEFORE INSERT ON reqhistory
FOR EACH ROW
BEGIN
    IF :NEW.hist_id IS NULL THEN
        SELECT seq_hist.nextval INTO :NEW.hist_id FROM dual;
    END IF;
END;
/

CREATE TABLE grade (
    req_id NUMBER,
    pass_fail CHAR(1),
    grade_percent FLOAT,
    cutoff_percent FLOAT,
    file_id NUMBER,
    CONSTRAINT pk_grade PRIMARY KEY (req_id),
    CONSTRAINT fk_grade_req FOREIGN KEY (req_id) REFERENCES request(req_id),
    CONSTRAINT fk_grade_file FOREIGN KEY (file_id) REFERENCES reqfile(file_id),
    CONSTRAINT ck_grade CHECK (grade_percent <= 100 AND grade_percent >= 0),
    CONSTRAINT ck_cutoff CHECK (cutoff_percent <= 100 AND cutoff_percent >= 0),
    CONSTRAINT ck_pass_fail CHECK (pass_fail = 'Y' OR pass_fail = 'N'),
    CONSTRAINT ck_pass_fail_or_percent CHECK((pass_fail IS NULL AND cutoff_percent IS NOT NULL AND grade_percent IS NOT NULL) OR 
                                            (pass_fail IS NOT NULL AND cutoff_percent IS NULL AND grade_percent IS NULL))
);
/

/*
CREATE TABLE department (
    dept_name VARCHAR2(50),
    dept_head NUMBER,
    CONSTRAINT pk_department PRIMARY KEY (dept_name),
    CONSTRAINT fk_dept_empinfo FOREIGN KEY (deptheademail) REFERENCES empinfo(email) ON DELETE SET NULL   -- creates a circular dependency
);
/
*/


COMMIT;