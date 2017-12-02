--DROP TABLE riembursment;
--DROP TABLE employee_info; 
--DROP TABLE direct_supervisor; 
--DROP TABLE department_head; 
--DROP TABLE benefits_coordinator;
DROP TABLE Login;

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
)
/


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
