DROP TABLE p1_employees CASCADE CONSTRAINTS;
DROP TABLE p1_departments CASCADE CONSTRAINTS;
DROP TABLE p1_department_roles CASCADE CONSTRAINTS;
DROP TABLE p1_reimbursable_expense_types CASCADE CONSTRAINTS;
DROP TABLE p1_reimbursment_requests CASCADE CONSTRAINTS;
DROP TABLE p1_reimbursable_events CASCADE CONSTRAINTS;
DROP TABLE p1_locations CASCADE CONSTRAINTS;
DROP TABLE p1_states CASCADE CONSTRAINTS;
DROP TABLE p1_event_grade_criteria CASCADE CONSTRAINTS;

--creating all the tables
CREATE TABLE p1_departments (
    department_id NUMBER PRIMARY KEY,
    department_name VARCHAR2(50)
);

CREATE TABLE p1_department_roles (
    department_role_id NUMBER PRIMARY KEY,
    department_id NUMBER,
    department_role_name VARCHAR2(40),
    
    CONSTRAINT FK_dept_id FOREIGN KEY (department_id) REFERENCES p1_departments(department_id)
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
    department_role_id NUMBER NOT NULL,
    manager_id NUMBER,
    employee_salary NUMBER(8,2),
    employee_home_location_id NUMBER,
    employee_email VARCHAR2(254), 
	employee_pass VARCHAR2(64), 
    employee_is_locked_out NUMBER(1,0) DEFAULT 0 NOT NULL,
    
    CONSTRAINT FK_department_role FOREIGN KEY (department_role_id) REFERENCES p1_department_roles(department_role_id),
    CONSTRAINT FK_manager FOREIGN KEY (manager_id) REFERENCES p1_employees(employee_id),
    CONSTRAINT FK_home_location FOREIGN KEY (employee_home_location_id) REFERENCES p1_locations(location_id)
);

CREATE TABLE p1_reimbursable_expense_types (
    ret_id NUMBER PRIMARY KEY,
    ret_type VARCHAR(33),
    ret_coverage NUMBER(3,2)
);

CREATE TABLE p1_event_grade_criteria (
    gs_id NUMBER PRIMARY KEY,
    event_id NUMBER,
    gs_letter_grade VARCHAR(4),  -- would normally be 2 characters max but let's allow 'pass','fail' to be letter grades
    gs_percentage_low NUMBER(5,2),   -- allowing for 100%
    gs_percentage_high NUMBER(5,2), -- also allowing for 100%. Also maintaining consistency
    
    CONSTRAINT FK_reim_event FOREIGN KEY (event_id) REFERENCES p1_reimbursable_events(event_id)
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
    re_numeric_grade NUMBER(5,2),
    re_presentation_passing NUMBER(1,0) DEFAULT 0,
    
    CONSTRAINT FK_event_type FOREIGN KEY (re_ret) REFERENCES p1_reimbursable_expense_types(ret_id),
    CONSTRAINT FK_event_location FOREIGN KEY (re_location) REFERENCES p1_locations(location_id)
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
DROP SEQUENCE p1_department_roles_seq;
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
CREATE SEQUENCE p1_department_roles_seq
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
    

create or replace TRIGGER p1_locations_trigger
	BEFORE INSERT ON p1_locations
	FOR EACH ROW
        BEGIN
		IF :new.location_id IS NULL THEN
			SELECT p1_locations_seq.nextval INTO :new.location_id FROM DUAL;
		END IF;
END;
    /

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
CREATE OR REPLACE TRIGGER p1_department_roles_trigger
	BEFORE INSERT ON p1_department_roles
	FOR EACH ROW
	BEGIN
		IF :new.department_role_id IS NULL THEN
			SELECT p1_department_roles_seq.nextval INTO :new.department_role_id FROM DUAL;
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
	BEFORE INSERT ON p1_event_grade_criteria
	FOR EACH ROW
	BEGIN
		IF :new.gs_id IS NULL THEN
			SELECT p1_gs_seq.nextval INTO :new.gs_id FROM DUAL;
		END IF;
	END;
/	

--create other triggers here
--a trigger to make sure that passwords that hit here are hashed
CREATE OR REPLACE TRIGGER p1_auto_hash_passwords_trigger
    BEFORE INSERT ON p1_employees
    FOR EACH ROW
    BEGIN
        IF length(:new.employee_pass) <> 64 THEN
            :new.employee_pass := sha256.encrypt(:new.employee_pass);
        END IF;
    END;
/

--define our views here
--a view for getting everything about the employee
CREATE OR REPLACE VIEW p1_employees_view AS 
	SELECT e.Employee_ID, 
	concat(e.Employee_First_Name, ' ' || e.Employee_Last_Name) AS employee_name,
    e.Employee_email,
	d.department_name,
    dr.department_role_name,
	concat(m.Employee_First_Name, ' ' || m.Employee_Last_Name) AS manager_name,
	e.Employee_Salary,
	l.location_Address,
	l.location_Suite,
	l.Location_City, 
	l.Location_State_Abbr,
	l.Location_Zip
		FROM p1_employees e
            INNER JOIN p1_department_roles dr USING (department_role_id)
			INNER JOIN p1_departments d ON (d.department_id = dr.department_id)
			LEFT JOIN p1_locations l ON e.Employee_Home_Location_ID = l.Location_ID
            LEFT JOIN p1_employees m ON e.Manager_ID = m.Employee_ID 
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

CREATE OR REPLACE VIEW p1_department_roles_view AS
    SELECT dr.department_role_id,
        d.department_name,
        dr.department_role_name
        
        FROM p1_department_roles dr
            INNER JOIN p1_departments d USING (department_id)
;

CREATE OR REPLACE VIEW p1_department_managers_view AS
    SELECT dr.department_role_name,
        d.department_name,
        m.employee_id AS manager_id,
        (m.employee_first_name || ' ' || m.employee_last_name) AS manager_name
        
        
        FROM p1_department_roles dr
            INNER JOIN p1_employees m USING (department_role_id)
            INNER JOIN p1_departments d USING (department_id)
            JOIN p1_employees e ON m.employee_id = e.manager_id
;

CREATE OR REPLACE VIEW p1_events_and_grade_criteria_view AS
    SELECT re.*, gc.gs_letter_grade, gc.gs_percentage_low
        FROM p1_reimbursable_events re
            INNER JOIN p1_event_grade_criteria gc USING (event_id)
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
INSERT INTO p1_department_roles(department_id, department_role_name) VALUES (5, 'Customer Service Representative');
INSERT INTO p1_department_roles(department_id, department_role_name) VALUES (5, 'Customer Service Lead');
INSERT INTO p1_department_roles(department_id, department_role_name) VALUES (5, 'Cashier');
INSERT INTO p1_department_roles(department_id, department_role_name) VALUES (6, 'Benefits Coordinator');
INSERT INTO p1_department_roles(department_id, department_role_name) VALUES (6, 'HR Specialist');
INSERT INTO p1_department_roles(department_id, department_role_name) VALUES (7, 'Sales Representative');
INSERT INTO p1_department_roles(department_id, department_role_name) VALUES (7, 'Sales Lead'); 
INSERT INTO p1_department_roles(department_id, department_role_name) VALUES (8, 'IT Support Specialist'); 
INSERT INTO p1_department_roles(department_id, department_role_name) VALUES (8, 'Web Developer');
INSERT INTO p1_department_roles(department_id, department_role_name) VALUES (8, 'Software Engineer');
INSERT INTO p1_department_roles(department_id, department_role_name) VALUES (8, 'Web Administrator');
INSERT INTO p1_department_roles(department_id, department_role_name) VALUES (8, 'Senior Software Engineer');


INSERT INTO p1_employees(employee_first_name, employee_last_name, department_role_id, manager_id, employee_salary, employee_email,
    employee_pass) VALUES (
    'Michael',
    'Warren',
    14, -- the department id of 'Software Engineer'
    NULL,
    NULL,
    'mwarren04011990@gmail.com',
    'P2ss3o45');
/
--create our functions and procedures here
--the procedure to be used by the user that initializes the accounts.
CREATE OR REPLACE PROCEDURE p1_init_employee(id IN p1_employees.Employee_ID%TYPE DEFAULT p1_employees_seq.nextval,
	first_name IN p1_employees.Employee_First_Name%TYPE,
	last_name IN p1_employees.Employee_Last_Name%TYPE,
	email IN p1_employees.Employee_Email%TYPE,
	department IN p1_departments.Department_Name%TYPE,
    department_role IN p1_department_roles.Department_Role_Name%TYPE
)
IS
BEGIN
--    prob should do some exception handling here
	INSERT INTO p1_employees(employee_id, employee_first_name, employee_last_name, employee_email, department_role_id, employee_role)
		VALUES (
			id, 
			first_name,
			last_name, 
			email,
			(SELECT dr.department_role_id FROM p1_department_roles dr WHERE dr.department_id = (SELECT d.department_id FROM p1_departments d WHERE d.department_name = department) 
                AND dr.department_role_name = department_role)
        );
	commit;
END;
/

--to be used by the user that registers accounts
CREATE OR REPLACE PROCEDURE register_new_user(first_name IN p1_employees.Employee_First_Name%TYPE,
	last_name IN p1_employees.Employee_Last_Name%TYPE,	
	department IN p1_departments.Department_Name%TYPE,
    department_role IN p1_department_roles.Department_Role_Name%TYPE,
    manager_id IN p1_employees.Manager_ID%TYPE DEFAULT NULL, -- change to 
    email IN p1_employees.Employee_Email%TYPE,
    pass IN p1_employees.Employee_Pass%TYPE)
IS
BEGIN
--    prob should do some exception handling here
	INSERT INTO p1_employees(employee_first_name, employee_last_name,  department_role_id, manager_id, employee_email, employee_pass)
		VALUES (
			first_name,
			last_name, 
			(SELECT dr.department_role_id FROM p1_department_roles dr WHERE dr.department_id = (SELECT d.department_id FROM p1_departments d WHERE d.department_name = department) 
                AND dr.department_role_name = department_role),
            manager_id,
            email,
            pass
        );
	commit;
END;
/
--for employees that access their accounts to create login credentials
--can't prefix this with "p1_" because that would violate the 30-character limit on procedure names :'(
CREATE OR REPLACE PROCEDURE create_login_credentials_for(id IN p1_employees.Employee_ID%TYPE,
    user IN p1_employees.Employee_Email%TYPE,   -- usernames are simply the email addresses of the users
    pass IN p1_employees.Employee_Pass%TYPE
)
IS
BEGIN
    UPDATE p1_employees
        SET employee_email = user,
            employee_pass  = sha256.encrypt(pass)
        WHERE employee_id  = id;
END;
/

--for giving a location to an employee
CREATE OR REPLACE PROCEDURE p1_set_location_for_employee(id IN p1_employees.Employee_ID%TYPE,
    address IN p1_locations.location_address%TYPE,
    city IN p1_locations.location_city%TYPE,
    state IN p1_locations.location_state_abbr%TYPE,
    zipcode IN p1_locations.location_zip%TYPE)
IS 
    locationID NUMBER;
BEGIN
    -- insert into the locations table and get its id
    INSERT INTO p1_locations(location_address, location_city, location_state_abbr, location_zip)
        VALUES (address, city, state, zipcode)
        RETURNING location_id INTO locationID;
    -- set the location id of that employee to the location id of the record we ust inserted
    UPDATE p1_employees
        SET Employee_Home_Location_ID = locationID
        WHERE Employee_ID = id;
END;
/
CREATE OR REPLACE FUNCTION user_is_manager(id IN p1_Employees.Employee_ID%TYPE)
RETURN NUMBER
IS 
    manageeCount NUMBER;
    isManager NUMBER(1,0) := 0;
BEGIN
    SELECT decode(count(employee_id), 0, 0, 1) INTO isManager FROM p1_Employees WHERE manager_id = id;
--    isManager := (manageeCount > 0);
    
    return isManager;   
END;
/

CREATE OR REPLACE FUNCTION p1_authenticate_user(user IN p1_employees.Employee_Email%TYPE,
    pass IN p1_employees.Employee_Pass%TYPE)
RETURN NUMBER
IS 
    user_exists NUMBER(1,0) := 0;
BEGIN
    SELECT count(*) INTO user_exists FROM p1_employees WHERE employee_email = user AND employee_pass = sha256.encrypt(pass);
    return user_exists;
END;
/

COMMIT;




