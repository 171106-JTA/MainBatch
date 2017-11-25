DROP TABLE p1_employees CASCADE CONSTRAINTS;
DROP TABLE p1_departments CASCADE CONSTRAINTS;
DROP TABLE p1_reimbursable_expense_types CASCADE CONSTRAINTS;
DROP TABLE p1_reimbursment_requests CASCADE CONSTRAINTS;
DROP TABLE p1_reimbursable_events CASCADE CONSTRAINTS;
DROP TABLE p1_locations CASCADE CONSTRAINTS;
DROP TABLE p1_states CASCADE CONSTRAINTS;
DROP TABLE p1_grade_scales CASCADE CONSTRAINTS;

--creating all the tables
CREATE TABLE p1_departents (
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
    employee_first_name VARCHAR2(35),
    employee_last_name VARCHAR2(35),
    department_id NUMBER NOT NULL,
    manager_id NUMBER,
    employee_salary NUMBER(8,2),
    employee_home_location_id NUMBER,
    
    CONSTRAINT FK_department FOREIGN KEY (department_id) REFERENCES p1_departments(department_id),
    CONSTRAINT FK_manager FOREIGN KEY (manager_id) REFERENCES p1_employees(employee_id),
    CONSTRAINT FK_home_location FOREIGN KEY (employee_home_location_id) REFERENCES p1_locations(location_id)
);

CREATE TABLE p1_reimbursable_expense_types (
    ret_id NUMBER PRIMARY KEY,
    ret_type VARCHAR(33)
);

CREATE TABLE p1_grade_scales (
    gs_id NUMBER PRIMARY KEY,
    gs_letter_grade VARCHAR(4),  -- would normally be 2 characters max but let's allow 'pass','fail' to be letter grades
    gs_percentage_low NUMBER(3,2),   -- allowing for 100%
    gs_percentage_high NUMBER(3,2)  -- also allowing for 100%. Also maintaining consistency

);

CREATE TABLE p1_reimbursable_events (
    event_id NUMBER PRIMARY KEY,
    re_ret NUMBER,
    re_date_start DATE,
    re_date_end DATE,
    re_location NUMBER NOT NULL,
    re_description VARCHAR(255),    -- let's allow for full sentence description of the reimbursable event.
    re_cost NUMBER(6,2),
    re_is_satisfactory NUMBER(1) DEFAULT 0, -- can't use boolean type here, so let's take a C++ approach here.
    re_presentation_needed NUMBER(1),   -- here, too. 
    re_grade_scale NUMBER,
    re_numeric_grade NUMBER(3,2),
    
    CONSTRAINT FK_event_type FOREIGN KEY (re_ret) REFERENCES p1_reimbursable_expense_types(ret_id),
    CONSTRAINT FK_event_location FOREIGN KEY (re_location) REFERENCES p1_locations(location_id),
    CONSTRAINT FK_event_grade_scale FOREIGN KEY (re_grade_scale) REFERENCES p1_grade_scales(gs_id)
);

CREATE TABLE p1_reimbursement_requests (
    rr_id NUMBER PRIMARY KEY,
    requester_id NUMBER NOT NULL,
    rr_amount NUMBER(4,2),
    rr_status VARCHAR2(140), -- haven't figured out my use cases for this column, so let's assume a Twitter-style sentence goes here. 
    rr_re NUMBER NOT NULL,
    
    CONSTRAINT FK_requester FOREIGN KEY (requester_id) REFERENCES p1_employees(employee_id),
    CONSTRAINT FK_request_for_event FOREIGN KEY (rr_re) REFERENCES p1_reimbursable_events(event_id)
);

--create our sequences here

--create our auto-increments here

--define our views here

--create our functions and procedures here