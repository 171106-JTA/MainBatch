BEGIN
    create_user(
        'BENEFIT-COORDINATOR', 'big.boss', 'abc123',
        'Luie', 'Lulciuc', 'tonylulciuc@gmail.com',
        '2678938655','Luie St.','PA', 'PHL');
END;
/

BEGIN
 -- create employees
    insert into COMPANY_EMPLOYEE (statecityid, 
        departmentid, employeeid,firstname,lastname,phonenumber, address, email)
        values (384, 427, 7812, 'Billy', 'Bob', '1234567890', 'Billy Bob St.', 'billy.bob@gmail.com');
    
    insert into COMPANY_EMPLOYEE (statecityid, 
                departmentid, employeeid,firstname,lastname,phonenumber, address, email)
                values (384, 427, 98522, 'Paper', 'Bob', '4444444444', 'Paper Bob St.', 'paper.bob@gmail.com');
        
    insert into COMPANY_EMPLOYEE (statecityid, 
            departmentid, employeeid,firstname,lastname,phonenumber, address, email)
            values (384, 427, 512, 'Billy', 'Paper', '0010020003', 'Billy Paper St.', 'billy.paper@gmail.com');

END;
/

select * from ear_user e join ear_user_info i
    on e.id = i.userid;
    
select * from company_employee;