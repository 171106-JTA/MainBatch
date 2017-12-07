INSERT INTO GradingFormats Values('LG', 'Letter Grade'); 
INSERT INTO GradingFormats Values('Pres', 'Presentation'); 
INSERT INTO GradingFormats Values('Exm', 'Examination'); 
/
INSERT INTO Grades Values('LG', 'A', 'Y');
INSERT INTO Grades Values('LG', 'B', 'Y');
INSERT INTO Grades Values('LG', 'C', 'Y');
INSERT INTO Grades Values('LG', 'D', 'Y');
INSERT INTO Grades Values('LG', 'F', 'N');

INSERT INTO Grades Values('Pres', 'Presented', 'Y');
INSERT INTO Grades Values('Pres', 'No Pres. Given', 'N');

INSERT INTO Grades Values('Exm', 'Superb', 'Y');
INSERT INTO Grades Values('Exm', 'Satisfactory', 'Y');
INSERT INTO Grades Values('Exm', 'Unsatis.; Remedial', 'N');
INSERT INTO Grades Values('Exm', 'Unsatisfactory', 'N');

select * from grades order by gradingformat;
/
INSERT INTO EventTypes Values( 'University Course', 0.80, 'LG');
INSERT INTO EventTypes Values( 'Seminar', 0.60, 'LG');
INSERT INTO EventTypes Values( 'Certification Prep Class', 0.75, 'LG');
INSERT INTO EventTypes Values( 'Certification Exam', 1.00, 'LG');
INSERT INTO EventTypes Values( 'Technical Training', 0.90, 'LG');
INSERT INTO EventTypes Values( 'Other', 0.30, 'LG');
/
INSERT INTO Department(deptId, name) Values(0, 'N/A'); 
INSERT INTO Department(deptId, name) Values(1, 'Executive Management'); 
INSERT INTO Department(deptId, name) Values(2, 'Human Resources'); 
INSERT INTO Department(deptId, name) Values(3, 'Sales'); 
INSERT INTO Department(deptId, name) Values(4, 'Public Relations'); 
INSERT INTO Department(deptId, name) Values(5, 'Customer Service'); 
INSERT INTO Department(deptId, name) Values(6, 'IT'); 
INSERT INTO Department(deptId, name) Values(7, 'Training'); 
INSERT INTO Department(deptId, name) Values(8, 'Consulting'); 

select * from department d join positions p on d.deptid = p.deptid;
/
call Create_Position('CEO', 1); 
call Create_Position('COO', 1); 
call Create_Position('CFO', 1); 

call Create_Position('Manager, Human Resources', 2); 
call Create_Position('HR Coordinator', 2); 

call Create_Position('Manager, Sales', 3); 
call Create_Position('Salesman', 3); 

call Create_Position('Manager, Public Relations', 4); 
call Create_Position('Representative', 4); 

call Create_Position('Manager, Customer Service', 5); 
call Create_Position('Representative', 5); 


call Create_Position('Manager, IT', 6); 
call Create_Position('Network Administrator', 6); 
call Create_Position('Software Engineer', 6); 

call Create_Position('Manager, Training', 7); 
call Create_Position('Trainer', 7); 
call Create_Position('Associate', 7); 

call Create_Position('Manager, Consulting Division', 8); 
call Create_Position('Technical Consultant', 8); 
/

call Create_Employee_Full('Bob', 'Fatcatts', 'MrBigg', 'passwordbigg',
    'Executive Management', 'CEO', 'N/A', 
    'bigg@gmail.com', '+1-551-555-4236', '443 Santiago Terrace, no. 1', 'Reston', 'VA', '20193');
call Create_Employee_Full('Sal', 'Mann', 'salarymann', 'passwordsalary',
    'Executive Management', 'COO', 'N/A',
    'salarymann@gmail.com', '+1-551-555-7536', '443 Santiago Terrace, no.3', 'Reston', 'VA', '20193'); 
call Create_Employee_Full('Brianna', 'Gibson', 'BGibs', 'passwordgibs',
    'Human Resources', 'Manager, Human Resources', 'MrBigg',
    'bngibs@gmail.com', '+1-551-555-4817', '291 Evergreen Terrace', 'Herndon', 'VA', '20192'); 
call Create_Employee_Full('Wilford', 'Wilson', 'wilford', 'passwordw',
    'Human Resources', 'HR Coordinator', 'BGibs',
    'wilford@gmail.com', '+1-481-555-4117', '211 Homes Str', 'Herndon', 'VA', '20192');
call Create_Employee_Full('Robert','Smucker', 'bobbert', 'passwordsmucker',
    'Training', 'Manager, Training', 'salarymann',
    'nonspreadablesmuckers@gmail.com', '+1-481-555-7528', '623 Pioneer Blv', 'Herndon', 'VA', '20192'); 
call Create_Employee_Full('Renaldo', 'Lessley', 'rlessley', 'watermelon',
    'Training', 'Trainer', 'bobbert',
    'rlessley@gmail.com', '+1-422-555-9682', '664 Pioneer Blv', 'Herndon', 'VA', '20192'); 
call Create_Employee_Full('John', 'Doe', 'jDoeOne', 'password1',
    'Training', 'Associate', 'rlessley', 
    'johndoe@gmail.com', '+1-481-555-4789', '212 Homes Str', 'Herndon', 'VA', '20192'); 
call Create_Employee_Full('Jane', 'Doe', 'jDoeTwo', 'password2',
    'Training', 'Associate', 'rlessley', 
    'janedoe@gmail.com', '+1-481-555-4790', '212 Homes Str', 'Herndon', 'VA', '20192');
call Create_Employee_Full('June', 'Doe', 'jDoeThree', 'password3',
    'Training', 'Associate', 'rlessley', 
    'junedoe@gmail.com', '+1-481-555-4791', '212 Homes Str', 'Herndon', 'VA', '20192'); 
call Create_Employee_Full('Jack', 'Doe', 'jackadoe', 'passwordjack',
    'Training', 'Associate', 'rlessley', 
    'jackdoe@gmail.com', '+1-481-555-4792', '212 Homes Str', 'Herndon', 'VA', '20192'); 
call Create_Employee_Full('Billy', 'Bobson', 'billybob', 'passwordbob',
    'Sales', 'Manager, Sales', 'MrBigg',
    'billybobson@gmail.com', '+1-764-451-5555', '123 Street str', 'Herndon', 'VA', '20192');  
    
    
/
select * from employee e join contactinformation c on e.contactinformationkey = c.contact_index;
select * from contactInformation;

select * from department natural join positions;

call Create_Employee_Full('Hafferly', 'Fafferson', 'luckylucky', 'passwordlucky',
    'Training', 'Associate', 'rlessley', 
    'thereallyluckycharms@gmail.com', '+1-777-777-7777', '101 Washer Ln', 'Herndon', 'VA', '20192'); 

--delete from employee where loginuserid = 'sjgillet';
