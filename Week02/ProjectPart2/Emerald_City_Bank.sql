--DROP ALL TABLES AND OBJECTS
drop sequence userID_incr_seq; 
drop trigger user_default_data_insert_tr; 
drop trigger acct_default_data_insert_tr; 

drop table BankAccounts;
drop table users; 
drop table Status_Lookup;
/

--Create status lookup table to get consistent status messages for users/accts
create table Status_Lookup (
    statuscode number(1) primary key,
    string varchar2(10)
); 
/ --populate the status lookup table
insert into status_lookup values(1, 'ACTIVE'); 
insert into status_lookup values(0, 'INACTIVE'); 
insert into status_lookup values(-1, 'LOCKED'); 
/

--create users table, for bank users that accounts are linked to
create table Users (
    userID NUMBER(5) NOT NULL,
    loginID varchar2(16) NOT NULL unique,
    loginpassword varchar2(32) NOT NULL, 
    firstname varchar2(32) NOT NULL,
    lastname varchar2(32) NOT NULL,
    isAdmin number(1),
    statuscode number(1),
    statusstr varchar2(10),
    
    CONSTRAINT PK_userID PRIMARY KEY(userID),
    CONSTRAINT FK_user_statuscode_lookup FOREIGN KEY(statuscode) 
        REFERENCES Status_Lookup(statuscode)
); 
/
insert into users values(1, 'Oz', 'curtain', 'Andrew', 'Oz', 1, 1, 'ACTIVE'); 

/

--create bank account table, where balances of money are saved
CREATE TABLE BankAccounts (
    accountNumber NUMBER(9) NOT NULL,
    balance NUMBER(12,2),
    ownerID NUMBER(5), 
    ownerLoginID VARCHAR(16) not null,
    statuscode number(1),
    statusstr varchar2(10),
    
    CONSTRAINT PK_acctNum PRIMARY KEY (accountnumber),
    CONSTRAINT FK_ownerID FOREIGN KEY(ownerid) REFERENCES Users(userid),
    CONSTRAINT FK_bank_acct_statuscode_lookup FOREIGN KEY(statuscode) 
        REFERENCES Status_Lookup(statuscode)
);
/
--creates sequence used to automatically increment the userid of new users
CREATE SEQUENCE userID_incr_seq start with 2 increment by 1; 
/
--trigger to insert default data into new user accounts
CREATE OR REPLACE TRIGGER user_default_data_insert_tr
BEFORE INSERT ON Users
FOR EACH ROW
BEGIN
    if :new.userID is null then
        select userID_incr_seq.neXtval into :new.userID from dual; 
    end if; 
    if :new.isAdmin is null then
        select 0 into :new.isAdmin from dual; 
    end if; 
    if :new.statuscode is null then
        select 0 into :new.statuscode from dual; 
        select string into :new.statusstr from status_lookup where :new.statuscode = status_lookup.statuscode;
    end if; 
END; 
/

--trigger to insert default data into new bank accounts
CREATE OR REPLACE TRIGGER acct_default_data_insert_tr
BEFORE INSERT ON BankAccounts
FOR EACH ROW
BEGIN
    if :new.ownerId is null then
        select userID into :new.ownerId from Users where :new.ownerloginId = Users.loginId;
    end if;
    if :new.balance is null then 
        select 0 into :new.balance from dual; 
    end if; 
    if :new.statuscode is null then
        select 0 into :new.statuscode from dual; 
        select S.string into :new.statusstr from status_lookup S where :new.statuscode = S.statuscode;
    end if; 
    
END; 
/

CREATE OR REPLACE TRIGGER user_status_update_tr
BEFORE UPDATE of statuscode ON Users
FOR EACH ROW
BEGIN
    select string into :new.statusstr from status_lookup where statuscode = :new.statuscode;
END; 
/

CREATE OR REPLACE TRIGGER acct_status_updat_tr
BEFORE UPDATE OF statuscode ON BankAccounts
FOR EACH ROW
BEGIN
    select string into :new.statusstr from status_lookup where statuscode = :new.statuscode; 
END;
/

CREATE OR REPLACE TRIGGER lock_user_lock_all_accts_tr
AFTER UPDATE OF statuscode ON Users 
FOR EACH ROW
WHEN(new.statuscode = -1)
BEGIN
    update BankAccounts set statuscode = -1 where ownerloginId = :new.loginId;
END;
/


CREATE OR REPLACE PROCEDURE Deposit_Into_Acct(acctNum IN NUMBER, amt IN NUMBER, bal OUT NUMBER) 
IS
    oldBalance NUMBER;
    newBalance NUMBER; 
BEGIN
    commit;
    SET TRANSACTION NAME 'Deposit'; 
    
    select balance into oldbalance from BankAccounts B where acctNum = B.accountNumber; 
    SAVEPOINT after_get_old_balance;
    
    UPDATE BankAccounts SET balance = oldBalance + amt where accountNumber = acctNum; 
    
    select B.balance into newBalance from BankAccounts B where acctNum = B.accountNumber; 
    if newbalance != oldBalance + amt then
        dbms_output.put_line('ROLLING BACK ON DEPOSIT'); 
        rollback; 
    end if;
    bal := newBalance;
    commit; 
END; 
/

CREATE OR REPLACE PROCEDURE Withdraw_From_Acct(acctNum IN NUMBER, amt IN NUMBER, bal OUT NUMBER) 
IS 
    oldBalance NUMBER;
    newBalance NUMBER; 
BEGIN
    commit;
    SET TRANSACTION NAME 'Withdrawal';
    
    select balance into oldBalance from bankAccounts where acctNum = accountNumber; 
    SAVEPOINT after_get_old_balance;
    
    UPDATE BankAccounts SET BankAccounts.balance = oldBalance - amt where accountNumber = acctNum;
    
    select balance into newBalance from BankAccounts where acctNum = AccountNumber; 
    if newBalance != oldBalance - amt then
        dbms_output.put_line('ROLLING BACK ON WITHDRAW'); 
        rollback; 
    end if; 
    bal := newBalance;
    commit; 
END;
/

CREATE OR REPLACE PROCEDURE Set_And_Get_Status_accts(acctNum IN NUMBER, st IN OUT NUMBER)
IS

BEGIN
    update bankaccounts set statuscode = st where accountnumber = acctnum; 
    select statuscode into st from BankAccounts where accountnumber = acctnum; 
END; 
/
CREATE OR REPLACE PROCEDURE Set_And_Get_Status_users(user IN VARCHAR2, st IN OUT NUMBER)
IS

BEGIN
    update users set statuscode = st where loginid = user; 
    select statuscode into st from Users where loginid = user; 
END; 
/

DECLARE
    bala NUMBER;
BEGIN
    deposit_into_acct(1234567, 20.52, bala); 
    withdraw_from_acct(1234567, 10.00, bala); 
END;
/

insert into users(loginid, loginpassword, firstname, lastname)
    values('sjgillet', 'passwords', 'Stuart', 'Gillette'); 
insert into users(loginid, loginpassword, firstname, lastname)
    values('wilford','passwordw', 'Wilford','Wilson'); 
insert into BankAccounts values(12345678, 10.00,2,'sjgillet', 1,'ACTIVE'); 
insert into BankAccounts values(23456789, 1000.00, 2, 'sjgillet', 1, 'ACTIVE'); 
update users set statuscode = 1 where loginid = 'sjgillet';

select U.loginid as "Username", U.loginpassword as "Password", 
    B.accountnumber as "Account #", B.balance as "Balance", 
    U.statusstr as "User Status", B.statusstr as "Account Status"    
    from users U inner join bankAccounts B on U.userID = B.ownerID;

select * from users;


