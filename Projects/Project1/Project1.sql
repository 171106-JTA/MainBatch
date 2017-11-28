DROP TABLE p1_employees CASCADE CONSTRAINTS;
DROP TABLE p1_departments CASCADE CONSTRAINTS;
DROP TABLE p1_reimbursable_expense_types CASCADE CONSTRAINTS;
DROP TABLE p1_reimbursment_requests CASCADE CONSTRAINTS;
DROP TABLE p1_reimbursable_events CASCADE CONSTRAINTS;
DROP TABLE p1_locations CASCADE CONSTRAINTS;
DROP TABLE p1_states CASCADE CONSTRAINTS;
DROP TABLE p1_grade_scales CASCADE CONSTRAINTS;

--creating all the tables
CREATE TABLE p1_departments (
    department_id NUMBER PRIMARY KEY,
    department_name VARCHAR2(50)
);

CREATE TABLE p1_states ( 
    state_abbr VARCHAR(2) PRIMARY KEY,
    state_name VARCHAR(30)
);

CREATE TABLE p1_locations (
    location_id NUMBER PRIMARY KEY,
    location_city VARCHAR2(50),
    location_state_abbr VARCHAR(2),
    location_address VARCHAR(75),
    location_zip NUMBER(5),
    location_suite NUMBER(4),
    CONSTRAINT FK_location_state FOREIGN KEY (location_state_abbr) REFERENCES p1_states(state_abbr)
);

CREATE TABLE p1_employees (
    employee_id NUMBER PRIMARY KEY,
    employee_first_name VARCHAR2(35) NOT NULL,
    employee_last_name VARCHAR2(35) NOT NULL,
    department_id NUMBER NOT NULL,
    manager_id NUMBER,
    employee_salary NUMBER(8,2),
    employee_home_location_id NUMBER NOT NULL,
    employee_email VARCHAR2(254), 
	employee_pass VARCHAR2(64), 
    employee_is_locked_out NUMBER(1,0) DEFAULT 0 NOT NULL,
    
    CONSTRAINT FK_department FOREIGN KEY (department_id) REFERENCES p1_departments(department_id),
    CONSTRAINT FK_manager FOREIGN KEY (manager_id) REFERENCES p1_employees(employee_id),
    CONSTRAINT FK_home_location FOREIGN KEY (employee_home_location_id) REFERENCES p1_locations(location_id)
);

CREATE TABLE p1_reimbursable_expense_types (
    ret_id NUMBER PRIMARY KEY,
    ret_type VARCHAR(33),
    ret_coverage NUMBER(3,2)
);

CREATE TABLE p1_grade_scales (
    gs_id NUMBER PRIMARY KEY,
    gs_letter_grade VARCHAR(4),  -- would normally be 2 characters max but let's allow 'pass','fail' to be letter grades
    gs_percentage_low NUMBER(5,2),   -- allowing for 100%
    gs_percentage_high NUMBER(5,2)  -- also allowing for 100%. Also maintaining consistency

);

CREATE TABLE p1_reimbursable_events (
    event_id NUMBER PRIMARY KEY,
    re_ret NUMBER,
    re_date_start DATE,
    re_date_end DATE,
    re_location NUMBER NOT NULL,
    re_description VARCHAR(255),    -- let's allow for full sentence description of the reimbursable event.
    re_cost NUMBER(7,2),
    re_is_satisfactory NUMBER(1) DEFAULT 0, -- can't use boolean type here, so let's take a C++ approach here.
    re_presentation_needed NUMBER(1),   -- here, too. 
    re_grade_scale NUMBER,
    re_numeric_grade NUMBER(5,2),
    re_presentation_passing NUMBER(1,0) DEFAULT 0,
    
    CONSTRAINT FK_event_type FOREIGN KEY (re_ret) REFERENCES p1_reimbursable_expense_types(ret_id),
    CONSTRAINT FK_event_location FOREIGN KEY (re_location) REFERENCES p1_locations(location_id),
    CONSTRAINT FK_event_grade_scale FOREIGN KEY (re_grade_scale) REFERENCES p1_grade_scales(gs_id)
);

CREATE TABLE p1_reimbursement_requests (
    rr_id NUMBER PRIMARY KEY,
    requester_id NUMBER NOT NULL,
    rr_amount NUMBER(6,2),
    rr_status VARCHAR2(140), -- haven't figured out my use cases for this column, so let's assume a Twitter-style sentence goes here. 
    rr_re NUMBER NOT NULL,
    
    CONSTRAINT FK_requester FOREIGN KEY (requester_id) REFERENCES p1_employees(employee_id),
    CONSTRAINT FK_request_for_event FOREIGN KEY (rr_re) REFERENCES p1_reimbursable_events(event_id)
);

--create our sequences here
DROP SEQUENCE p1_employees_seq;
DROP SEQUENCE p1_departments_seq;
DROP SEQUENCE p1_ret_seq;
DROP SEQUENCE p1_rr_seq;
DROP SEQUENCE p1_re_seq;
DROP SEQUENCE p1_locations_seq;
DROP SEQUENCE p1_states_seq;
DROP SEQUENCE p1_gs_seq;


CREATE SEQUENCE p1_employees_seq
	START WITH 1
	INCREMENT BY 1;
CREATE SEQUENCE p1_departments_seq
	START WITH 1
	INCREMENT BY 1;
CREATE SEQUENCE p1_ret_seq
	START WITH 1
	INCREMENT BY 1;
CREATE SEQUENCE p1_rr_seq
	START WITH 1
	INCREMENT BY 1;
CREATE SEQUENCE p1_re_seq
	START WITH 1
	INCREMENT BY 1;
CREATE SEQUENCE p1_locations_seq
	START WITH 1
	INCREMENT BY 1;
CREATE SEQUENCE p1_states_seq
	START WITH 1
	INCREMENT BY 1;
CREATE SEQUENCE p1_gs_seq
	START WITH 1
	INCREMENT BY 1;

--create our auto-increments here
CREATE OR REPLACE TRIGGER p1_employees_trigger
	BEFORE INSERT ON p1_employees
	FOR EACH ROW
	BEGIN
		IF :new.employee_id IS NULL THEN
			SELECT p1_employees_seq.nextval INTO :new.employee_id FROM DUAL;
		END IF;
	END;
/
CREATE OR REPLACE TRIGGER p1_departments_trigger
	BEFORE INSERT ON p1_departments
	FOR EACH ROW
	BEGIN
		IF :new.department_id IS NULL THEN
			SELECT p1_departments_seq.nextval INTO :new.department_id FROM DUAL;
		END IF;
	END;
/
CREATE OR REPLACE TRIGGER p1_ret_trigger
	BEFORE INSERT ON p1_reimbursable_expense_types
	FOR EACH ROW
	BEGIN
		IF :new.ret_id IS NULL THEN
			SELECT p1_ret_seq.nextval INTO :new.ret_id FROM DUAL;
		END IF;
	END;
/
CREATE OR REPLACE TRIGGER p1_rr_trigger
	BEFORE INSERT ON p1_reimbursement_requests
	FOR EACH ROW
	BEGIN
		IF :new.rr_id IS NULL THEN
			SELECT p1_rr_seq.nextval INTO :new.rr_id FROM DUAL;
		END IF;
	END;
/
CREATE OR REPLACE TRIGGER p1_re_trigger
	BEFORE INSERT ON p1_reimbursable_events
	FOR EACH ROW
	BEGIN
		IF :new.event_id IS NULL THEN
			SELECT p1_re_seq.nextval INTO :new.event_id FROM DUAL;
		END IF;
	END;
/
CREATE OR REPLACE TRIGGER p1_locations_trigger
	BEFORE INSERT ON p1_locations
	FOR EACH ROW
	BEGIN
		IF :new.location_id IS NULL THEN
			SELECT p1_locations_seq.nextval INTO :new.location_id FROM DUAL;
		END IF;
	END;
/
CREATE OR REPLACE TRIGGER p1_states_trigger
	BEFORE INSERT ON p1_states
	FOR EACH ROW
	BEGIN
		IF :new.state_abbr IS NULL THEN
			SELECT p1_states_seq.nextval INTO :new.state_abbr FROM DUAL;
		END IF;
	END;
/
CREATE OR REPLACE TRIGGER p1_gs_trigger
	BEFORE INSERT ON p1_grade_scales
	FOR EACH ROW
	BEGIN
		IF :new.gs_id IS NULL THEN
			SELECT p1_gs_seq.nextval INTO :new.gs_id FROM DUAL;
		END IF;
	END;
/	

--create other triggers here


--define our views here
--a view for getting everything about the employee
CREATE OR REPLACE VIEW p1_employees_view AS 
	SELECT e.Employee_ID, 
	concat(e.Employee_First_Name, ' ' || e.Employee_Last_Name) AS employee_name,
    e.Employee_email,
	d.department_name,
	concat(m.Employee_First_Name, ' ' || m.Employee_Last_Name) AS manager_name,
	e.Employee_Salary,
	l.location_Address,
	l.location_Suite,
	l.Location_City, 
	l.Location_State_Abbr,
	l.Location_Zip
		FROM p1_employees e
			INNER JOIN p1_departments d USING (department_id)
			INNER JOIN p1_locations l ON e.Employee_Home_Location_ID = l.Location_ID
            JOIN p1_employees m ON e.Manager_ID = m.Employee_ID
;

CREATE OR REPLACE VIEW p1_re_view AS
	SELECT re.event_id,
		ret.ret_type,
		ret.ret_coverage,
		re.re_date_start,
		re.re_date_end,
		l.location_address,
		l.location_city,
		l.location_state_abbr,
		re.re_description,
		re.re_cost,
		re.re_is_satisfactory,
		re.re_presentation_needed,
		re.re_numeric_grade,
		re.re_presentation_passing
	
	FROM p1_reimbursable_events re
		INNER JOIN p1_reimbursable_expense_types ret ON re.re_ret = ret.ret_id
		INNER JOIN p1_locations l ON re.re_location = l.location_id
;

CREATE OR REPLACE VIEW p1_rr_view AS
	SELECT rr.rr_id, 
		e.Employee_ID, 
		e.Employee_Name,
		e.Employee_Email,
		rr.rr_amount,
		rr.rr_status,
		re.*
		
		FROM p1_reimbursement_requests rr
			INNER JOIN p1_employees_view e ON rr.requester_id = e.employee_ID
			INNER JOIN p1_reimbursable_events re ON rr.rr_re = re.Event_ID
			
;

--do default inserts here
INSERT INTO p1_states values ('AL', 'Alabama');
INSERT INTO p1_states VALUES ('AK', 'Alaska');
INSERT INTO p1_states VALUES ('AZ', 'Arizona');
INSERT INTO p1_states VALUES ('AR', 'Arkansas');
INSERT INTO p1_states VALUES ('CA', 'California');
INSERT INTO p1_states VALUES ('CO', 'Colorado');
INSERT INTO p1_states VALUES ('CT', 'Connecticut');
INSERT INTO p1_states VALUES ('DE', 'Delaware');
INSERT INTO p1_states VALUES ('DC', 'District of Columbia');
INSERT INTO p1_states VALUES ('FL', 'Florida');
INSERT INTO p1_states VALUES ('GA', 'Georgia');
INSERT INTO p1_states VALUES ('HI', 'Hawaii');
INSERT INTO p1_states VALUES ('ID', 'Idaho');
INSERT INTO p1_states VALUES ('IL', 'Illinois');
INSERT INTO p1_states VALUES ('IN', 'Indiana');
INSERT INTO p1_states VALUES ('IA', 'Iowa');
INSERT INTO p1_states VALUES ('KS', 'Kansas');
INSERT INTO p1_states VALUES ('KY', 'Kentucky');
INSERT INTO p1_states VALUES ('LA', 'Louisiana');
INSERT INTO p1_states VALUES ('ME', 'Maine');
INSERT INTO p1_states VALUES ('MD', 'Maryland');
INSERT INTO p1_states VALUES ('MA', 'Massachusetts');
INSERT INTO p1_states VALUES ('MI', 'Michigan');
INSERT INTO p1_states VALUES ('MN', 'Minnesota');
INSERT INTO p1_states VALUES ('MS', 'Mississippi');
INSERT INTO p1_states VALUES ('MO', 'Missouri');
INSERT INTO p1_states VALUES ('MT', 'Montana');
INSERT INTO p1_states VALUES ('NE', 'Nebraska');
INSERT INTO p1_states VALUES ('NV', 'Nevada');
INSERT INTO p1_states VALUES ('NH', 'New Hampshire');
INSERT INTO p1_states VALUES ('NJ', 'New Jersey');
INSERT INTO p1_states VALUES ('NM', 'New Mexico');
INSERT INTO p1_states VALUES ('NY', 'New York');
INSERT INTO p1_states VALUES ('NC', 'North Carolina');
INSERT INTO p1_states VALUES ('ND', 'North Dakota');
INSERT INTO p1_states VALUES ('OH', 'Ohio');
INSERT INTO p1_states VALUES ('OK', 'Oklahoma');
INSERT INTO p1_states VALUES ('OR', 'Oregon');
INSERT INTO p1_states VALUES ('PA', 'Pennsylvania');
INSERT INTO p1_states VALUES ('PR', 'Puerto Rico');
INSERT INTO p1_states VALUES ('RI', 'Rhode Island');
INSERT INTO p1_states VALUES ('SC', 'South Carolina');
INSERT INTO p1_states VALUES ('SD', 'South Dakota');
INSERT INTO p1_states VALUES ('TN', 'Tennessee');
INSERT INTO p1_states VALUES ('TX', 'Texas');
INSERT INTO p1_states VALUES ('UT', 'Utah');
INSERT INTO p1_states VALUES ('VT', 'Vermont');
INSERT INTO p1_states VALUES ('VA', 'Virginia');
INSERT INTO p1_states VALUES ('WA', 'Washington');
INSERT INTO p1_states VALUES ('WV', 'West Virginia');
INSERT INTO p1_states VALUES ('WI', 'Wisconsin');
INSERT INTO p1_states VALUES ('WY', 'Wyoming');
/
INSERT INTO p1_reimbursable_expense_types(ret_type, ret_coverage) VALUES ('University Course', 0.80);
INSERT INTO p1_reimbursable_expense_types(ret_type, ret_coverage) VALUES ('Seminar', 0.60);
INSERT INTO p1_reimbursable_expense_types(ret_type, ret_coverage) VALUES ('Certification Preparation Class', 0.75);
INSERT INTO p1_reimbursable_expense_types(ret_type, ret_coverage) VALUES ('Certification', 1);
INSERT INTO p1_reimbursable_expense_types(ret_type, ret_coverage) VALUES ('Technical Training', 0.90);
INSERT INTO p1_reimbursable_expense_types(ret_type, ret_coverage) VALUES ('Other', 0.30);
/
INSERT INTO p1_departments(department_name) VALUES ('Customer Service');
INSERT INTO p1_departments(department_name) VALUES ('Human Resources');
INSERT INTO p1_departments(department_name) VALUES ('Sales');
INSERT INTO p1_departments(department_name) VALUES ('Information Technology');
/

--create our functions and procedures here


COMMIT;