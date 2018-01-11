--select 'truncate table', table_name, 'drop storage;' from user_tables;

CREATE TABLE Permissions (
    Permission_ID number(1),
    Permission_Type VarChar2(10),
    CONSTRAINT PK_Permission_ID PRIMARY KEY(Permission_ID)
);

CREATE TABLE Account_Type (
    Account_Type_ID number(1),
    Account_Type VARCHAR2(20), 
    CONSTRAINT PK_Account_Type_ID PRIMARY KEY(Account_Type_ID)
);

CREATE TABLE Accounts (
    Account_ID number(6),
    Start_Date TIMESTAMP(0),
    PERMISSION_ID number(1),
    Account_Type_ID number(1),
    CONSTRAINT PK_Account_ID PRIMARY KEY(Account_ID),
    CONSTRAINT FK_Permission_ID FOREIGN KEY (Permission_ID)
        REFERENCES Permissions (Permission_ID),
    CONSTRAINT FK_Account_Type_ID FOREIGN KEY (Account_Type_ID)
        REFERENCES Account_Type (Account_Type_ID)        
);

CREATE TABLE Credentials (
    Account_ID number(1),
    Usernames VARCHAR2(20),
    Passwords VARCHAR2(20), 
    CONSTRAINT FK_CRED_ACCT_ID FOREIGN KEY (Account_ID)
        REFERENCES Accounts (Account_ID)
);

CREATE TABLE Admin_Type (
    Admin_Type_ID number(1),
    Admin_Type VARCHAR2(20), 
    CONSTRAINT PK_Admin_Type_ID PRIMARY KEY(Admin_Type_ID)
);

CREATE TABLE Admins (
    Admin_ID number(6),
    Admin_Type_ID number(1), 
    CONSTRAINT PK_Admin_ID PRIMARY KEY(Admin_ID),
    Constraint FK_Admin_Type_ID Foreign Key (Admin_Type_ID)
        References Admin_Type (Admin_Type_ID)
);

CREATE TABLE Admin_Account_Junction (
    Account_ID number(6),
    Admin_ID number(6),
    CONSTRAINT FK_Account_ID Foreign KEY(Account_ID)
        REFERENCES Accounts (Account_ID),
    CONSTRAINT FK_Admin_ID Foreign KEY(Admin_ID)
        REFERENCES Admins (Admin_ID)
);

CREATE TABLE State (
    State_ID number(1),
    State VARCHAR2(20),
    CONSTRAINT PK_State_ID PRIMARY KEY(State_ID)
);

CREATE TABLE Status (
    Status_ID number(1),
    Status VARCHAR2(20),
    CONSTRAINT PK_Status_ID PRIMARY KEY(Status_ID)
);

CREATE TABLE User_Type (
    User_Type_ID number(1),
    User_Type VARCHAR2(20),
    CONSTRAINT PK_Type_ID PRIMARY KEY(User_Type_ID)
);

CREATE TABLE Users (
    User_ID number(6),
    Age number(3),
    User_Type_ID number(1),
    State_ID number(1),
    Status_ID number(1),
    Update_Date TIMESTAMP(0),
    Start_Date TIMESTAMP(0),
    Balance number(10,2),
    Credit_Limit number(7, 2),
    Penalty number(7, 2),
    Interest number(5,2),
    CONSTRAINT PK_USER_ID PRIMARY KEY (User_ID),
    CONSTRAINT FK_USER_TYPE_ID FOREIGN KEY (USER_TYPE_ID)
        REFERENCES USER_TYPE (USER_TYPE_ID),
    CONSTRAINT FK_USER_STATUS_ID FOREIGN KEY (Status_ID)
        REFERENCES STATUS (Status_ID),
    CONSTRAINT FK_STATE_ID FOREIGN KEY (STATE_Id)
        REFERENCES STATE (State_ID)
);

CREATE TABLE Account_Variance (
    User_ID number(6,0),
    Increasing_Limit_Rate number(6,0),
    Increasing_Penalty_Rate number(6,0),
    Increasing_Interest_Rate number(6,0),
    Decreasing_Limit_Rate number(6,0),
    Decreasing_Penalty_Rate number(6,0),
    Decreasing_Interest_Rate number(6,0),
    CONSTRAINT FK_VAR_USER_ID FOREIGN KEY (USER_ID)
        REFERENCES USERS (USER_ID)
);

CREATE TABLE SSN (
    User_ID number(6),
    SSN number(9),
    CONSTRAINT FK_User_ID FOREIGN KEY (USER_ID)
        REFERENCES Users (User_ID)
);

CREATE TABLE Locked_Accounts (
    User_ID number(6),
    SSN number(9),
    CONSTRAINT FK_Locked_User_ID FOREIGN KEY (USER_ID)
        REFERENCES Users (User_ID)
);

CREATE TABLE User_Account_Junction(
    User_ID number(6),
    Account_ID number(6),
    CONSTRAINT FK_UAJ_USER_ID FOREIGN KEY (USER_ID)
        REFERENCES Users (USER_ID),
    CONSTRAINT FK_UAJ_Account_ID FOREIGN KEY (Account_ID)
        REFERENCES Accounts (Account_ID)
);

CREATE TABLE User_Admin_Junction (
    User_ID number(6),
    Admin_ID number(6),
    CONSTRAINT FK_UAdmJ_USER_ID FOREIGN KEY (USER_ID)
        REFERENCES Users (USER_ID),
    CONSTRAINT FK_UAdmJ_Admin_ID FOREIGN KEY (Admin_ID)
        REFERENCES Admins (Admin_ID)
);

CREATE TABLE Transaction_Type (
    Transaction_Type_ID number(6),
    Tranaction_Type VARCHAR2(20),
    CONSTRAINT PK_TRANS_TYPE_ID PRIMARY KEY (Transaction_Type_ID)
);

CREATE TABLE Transactions (
    Transaction_ID number(6),
    Transaction_Type_ID number(6),
    User_ID number(6),
    Recipient_ID number(6),
    Amount number(6,2),
    Message VARCHAR(50),
    StartDate TIMESTAMP(0),
    CONSTRAINT PK_TRANS_ID PRIMARY KEY (Transaction_ID),
    CONSTRAINT FK_TRANS_TRANS_TYPE_ID FOREIGN KEY (Transaction_Type_ID)
        REFERENCES Transaction_Type (Transaction_Type_ID),
    CONSTRAINT FK_TRANS_USER_ID FOREIGN KEY (User_ID)
        REFERENCES Users (User_ID)
);

CREATE TABLE Loan_Status (
    Loan_Status_ID number(1),
    Loan_Status VARCHAR(20),
    CONSTRAINT PK_LOAN_STAT_ID PRIMARY KEY (Loan_Status_ID)
);

CREATE TABLE Loan (
    Loan_ID number(6),
    Amount number(6,2),
    Remaining number(6, 2),
    Interest number(6, 2),
    Status number(1),
    UpdateDate TIMESTAMP(0),
    CONSTRAINT PK_LOAN_ID PRIMARY KEY (Loan_ID),
    CONSTRAINT FK_LOAN_STAT_ID FOREIGN KEY (Status)
        REFERENCES LOAN_STATUS (LOAN_STATUS_ID)
);

CREATE TABLE Transaction_Loan_Junction (
    Transaction_ID number(6),
    Loan_ID number(6),
    CONSTRAINT FK_TRANSACTION_ID FOREIGN KEY (Transaction_ID)
        REFERENCES Transactions (Transaction_ID),
    CONSTRAINT FK_LOAN_ID FOREIGN KEY (Loan_ID)
        REFERENCES Loan (Loan_ID)
);

CREATE TABLE Request_Type (
    Request_Type_ID number(6),
    Request_Type VARCHAR2(20),
    CONSTRAINT PK_REQUEST_TYPE_ID PRIMARY KEY (Request_Type_ID)
);

CREATE TABLE Request_Status (
    Request_Status_ID number(1),
    Request_Status VARCHAR2(20),
    CONSTRAINT PK_REQ_STAT_STAT_ID PRIMARY KEY(Request_Status_ID)
);

CREATE TABLE Request (
    Request_ID number(6),
    User_ID number(6),
    Request_Type_ID number(6),
    Request_Status_ID number(1),
    CONSTRAINT PK_REQUEST_ID PRIMARY KEY (Request_ID),
    CONSTRAINT FK_REQUEST_USER_ID FOREIGN KEY (User_ID)
        REFERENCES Users (User_ID),
    CONSTRAINT FK_REQUEST_REQUEST_TYPE_ID FOREIGN KEY (Request_Type_ID)
        REFERENCES Request_Type (Request_Type_ID),
    CONSTRAINT FK_REQUEST_REQUEST_STAT_ID FOREIGN KEY (Request_Status_ID)
        REFERENCES Request_Status (Request_Status_ID)
);

CREATE TABLE Request_Transaction_Junction (
    Request_ID number(6),
    Transaction_ID number(6),
    CONSTRAINT FK_REQUEST_ID FOREIGN KEY (Request_ID)
        REFERENCES Request (Request_ID),
    CONSTRAINT FK_REQUEST_TRANSACTION_ID FOREIGN KEY (Transaction_ID)
        REFERENCES Transactions (Transaction_ID)
);

CREATE TABLE Loan_User_Junction (
    Loan_ID number(6),
    User_ID number(6),
    CONSTRAINT FK_LOAN_USER_ID FOREIGN KEY (USER_ID)
        REFERENCES USERS (USER_ID),
    CONSTRAINT FK_LOAN_LOAN_ID FOREIGN KEY (Loan_ID)
        REFERENCES Loan (Loan_ID)
);

CREATE TABLE Loan_Request_Junction (
    Loan_ID number(6),
    Request_ID number(6),
    CONSTRAINT FK_LOAN_Request_ID FOREIGN KEY (Request_ID)
        REFERENCES Request (Request_ID),
    CONSTRAINT FK_LRJ_LOAN_ID FOREIGN KEY (Loan_ID)
        REFERENCES Loan (Loan_ID)
);


CREATE VIEW user_credentials AS 
    SELECT usernames, passwords FROM ACCOUNT_TYPE NATURAL JOIN Accounts NATURAL JOIN CREDENTIALS  
    WHERE ACCOUNT_TYPE.ACCOUNT_TYPE = 'User';
    
CREATE VIEW admin_credentials AS
    SELECT usernames, passwords FROM ACCOUNT_TYPE NATURAL JOIN Accounts NATURAL JOIN CREDENTIALS  
    WHERE ACCOUNT_TYPE.ACCOUNT_TYPE = 'Admin';
    
--    SELECT Credentials.usernames, Credentials.passwords FROM Credentials JOIN (
--        SELECT * FROM ACCOUNTS JOIN ACCOUNT_TYPE ON Accounts.ACCOUNT_TYPE_ID = ACCOUNT_TYPE.ACCOUNT_TYPE_ID
--        )   

INSERT INTO PERMISSIONS VALUES (0, 'None');
INSERT INTO PERMISSIONS VALUES (1, 'Read only');
INSERT INTO PERMISSIONS VALUES (3, 'Read/Write');
INSERT INTO PERMISSIONS VALUES (7, 'All');

INSERT INTO USER_TYPE VALUES(0, 'Root');
INSERT INTO USER_TYPE VALUES(1, 'Basic');
INSERT INTO USER_TYPE VALUES(2, 'Senior');
INSERT INTO USER_TYPE VALUES(3, 'Premium');

INSERT INTO ADMIN_TYPE VALUES(0, 'Site Administrator');
INSERT INTO ADMIN_TYPE VALUES(1, 'DB Administrator');

INSERT INTO REQUEST_TYPE VALUES(0, 'Account Request');
INSERT INTO REQUEST_TYPE VALUES(1, 'Loan Request');
INSERT INTO REQUEST_TYPE VALUES(2, 'Rollback Request');

INSERT INTO ACCOUNT_TYPE VALUES(0, 'User');
INSERT INTO ACCOUNT_TYPE VALUES(1, 'Admin');

INSERT INTO STATUS VALUES(0, 'Good');
INSERT INTO STATUS VALUES(1, 'Locked');
INSERT INTO STATUS VALUES(2, 'Rejected');
INSERT INTO STATUS VALUES(3, 'Pending');

INSERT INTO STATE VALUES(0, 'Neutral');
INSERT INTO STATE VALUES(1, 'Positive');
INSERT INTO STATE VALUES(2, 'Negative');

INSERT INTO Transaction_Type VALUES(0, 'Deposit');
INSERT INTO Transaction_Type VALUES(1, 'Withdraw');
INSERT INTO Transaction_Type VALUES(2, 'Loan dispersal');
INSERT INTO Transaction_Type VALUES(3, 'Loan payment');

INSERT INTO Request_Status VALUES(0, 'Pending');
INSERT INTO Request_Status VALUES(1, 'Approved');
INSERT INTO Request_Status VALUES(2, 'Denied');

INSERT INTO LOAN_STATUS VALUES(0, 'Active');
INSERT INTO LOAN_STATUS VALUES(1, 'Paid');
INSERT INTO LOAN_STATUS VALUES(2, 'Removed');

--CREATE VIEW Root_Admins_View AS
SELECT ADMINS.ADMIN_ID, ADMIN_TYPE, ACCOUNT_TYPE, PERMISSION_TYPE, USERNAMES, PASSWORDS, USER_ID FROM ADMINS 
JOIN ADMIN_TYPE ON ADMINS.ADMIN_TYPE_ID = ADMIN_TYPE.ADMIN_TYPE_ID
JOIN ADMIN_ACCOUNT_JUNCTION ON ADMINS.ADMIN_ID = ADMIN_ACCOUNT_JUNCTION.ADMIN_ID 
JOIN ACCOUNTS ON ACCOUNTS.ACCOUNT_ID = ADMIN_ACCOUNT_JUNCTION.ADMIN_ID
JOIN PERMISSIONS ON PERMISSIONS.PERMISSION_ID = ACCOUNTS.PERMISSION_ID
JOIN ACCOUNT_TYPE ON ACCOUNTS.ACCOUNT_TYPE_ID = ACCOUNT_TYPE.ACCOUNT_TYPE_ID
JOIN USER_ADMIN_JUNCTION ON ADMINS.ADMIN_ID = USER_ADMIN_JUNCTION.ADMIN_ID
JOIN CREDENTIALS ON CREDENTIALS.CREDENTIALS_ID = ACCOUNTS.CREDENTIALS_ID;

DROP SEQUENCE adm_id_seq;
CREATE SEQUENCE adm_id_seq
  MINVALUE 0
  MAXVALUE 999999999999999999999999999
  START WITH 0
  INCREMENT BY 1
  CACHE 20;
  
DROP SEQUENCE us_id_seq;
  CREATE SEQUENCE us_id_seq
  MINVALUE 0
  MAXVALUE 999999999999999999999999999
  START WITH 0
  INCREMENT BY 1
  CACHE 20;
  
DROP SEQUENCE req_id_seq;
CREATE SEQUENCE req_id_seq
  MINVALUE 0
  MAXVALUE 999999999999999999999999999
  START WITH 0
  INCREMENT BY 1
  CACHE 20;
  
DROP SEQUENCE trans_id_seq;
CREATE SEQUENCE trans_id_seq
  MINVALUE 0
  MAXVALUE 999999999999999999999999999
  START WITH 0
  INCREMENT BY 1
  CACHE 20;
  
DROP SEQUENCE loan_id_seq;
CREATE SEQUENCE loan_id_seq
  MINVALUE 0
  MAXVALUE 999999999999999999999999999
  START WITH 0
  INCREMENT BY 1
  CACHE 20;
  
DROP SEQUENCE acct_id_seq;
CREATE SEQUENCE acct_id_seq
  MINVALUE 0
  MAXVALUE 999999999999999999999999999
  START WITH 0
  INCREMENT BY 1
  CACHE 20;

CREATE OR REPLACE PROCEDURE add_request
(req_TypeID in NUMBER, us_id in NUMBER, trans_id in NUMBER,
status IN NUMBER)
AS
BEGIN
    DECLARE
        req_id INT := req_id_seq.nextVal;
    BEGIN
        INSERT INTO Request VALUES (req_id, us_id, req_TypeID, status);
            IF (req_TypeID = 2) THEN
                INSERT INTO REQUEST_TRANSACTION_JUNCTION VALUES(req_id, trans_id);
            END IF;
        COMMIT;
        EXCEPTION WHEN OTHERS THEN ROLLBACK;
    END;
END;
/

CREATE OR REPLACE PROCEDURE add_loan
(us_id in NUMBER, req_id in NUMBER, amount in NUMBER)
AS
BEGIN
    DECLARE
        ts TIMESTAMP := TO_TIMESTAMP(CURRENT_TIMESTAMP + 30);
        loan_id INT := loan_id_seq.nextVal;
    BEGIN
        INSERT INTO LOAN VALUES(loan_id, amount, amount, 0.5, 0, ts);
        INSERT INTO LOAN_USER_JUNCTION VALUES(loan_id, us_id);
        INSERT INTO LOAN_REQUEST_JUNCTION VALUES(loan_id, req_id);
        
        COMMIT;
        EXCEPTION WHEN OTHERS THEN ROLLBACK;
    END;
END;
/

CREATE OR REPLACE PROCEDURE add_transaction
(trans_TypeID in NUMBER, trans_msg IN VARCHAR2, us_id IN NUMBER, recip_id IN NUMBER, amount IN NUMBER)
AS
BEGIN
    DECLARE
        trans_id INT := trans_id_seq.nextVal;
    BEGIN
        INSERT INTO TRANSACTIONS VALUES(trans_id, trans_TypeID, us_id, recip_id, amount, trans_msg, CURRENT_TIMESTAMP);
        IF trans_TypeID = 2 OR trans_TypeID = 3 THEN 
            update_loan(amount, 0);
            INSERT INTO TRANSACTION_LOAN_JUNCTION VALUES(trans_id, recip_id);
        END IF;
        COMMIT;
        EXCEPTION WHEN OTHERS THEN ROLLBACK;
    END;
END;
/
  
CREATE OR REPLACE PROCEDURE add_account
(perm_ID in NUMBER, acct_user IN VARCHAR2, acct_pass IN VARCHAR2, 
acct_Type_ID IN NUMBER, acct_id OUT NUMBER)
AS
BEGIN
    DECLARE
        acct_id INT := acct_id_seq.nextVal;    

    BEGIN
        INSERT INTO ACCOUNTS VALUES(acct_id, CURRENT_TIMESTAMP, perm_ID, acct_Type_ID);
        INSERT INTO CREDENTIALS VALUES(acct_id, acct_user, acct_pass);
        COMMIT;
        EXCEPTION WHEN OTHERS THEN ROLLBACK;
    END;
END;
/    

CREATE OR REPLACE PROCEDURE add_user
(us_typeID IN NUMBER, us_age IN NUMBER, us_user IN VARCHAR2, us_pass IN VARCHAR2, 
cred_limit in NUMBER, penalty IN NUMBER, interest IN NUMBER,  us_ssn IN NUMBER)
AS
BEGIN
    DECLARE 
        us_id INT := uniq_id_seq.nextVal;
        acct_ID INT DEFAULT 0;
        idx INT := 0;
    BEGIN
    
         -- Create user account
        INSERT INTO USERS VALUES(us_id, us_age, us_typeID, 0, 2, CURRENT_TIMESTAMP + 30, CURRENT_TIMESTAMP, 0, cred_limit, penalty, interest);
        INSERT INTO SSN VALUES (us_id, us_ssn);
        INSERT INTO ACCOUNT_VARIANCE VALUES(us_id, 400, 0, 0.5, 200, 0, 0);
            
        -- Create account
        add_account(1, us_user, us_pass, 0, acct_ID);
    
        INSERT INTO USER_ACCOUNT_JUNCTION VALUES(us_id, acct_ID);

--        exec ('CREATE VIEW user_view' || us_id ||' AS 
--            SELECT * FROM Users JOIN )
--        CREATE VIEW user_view || us_id AS
--            EXECUTE IMMEDIATE
--            SELECT * FROM Users;
        
        COMMIT;
        EXCEPTION WHEN OTHERS THEN ROLLBACK;
    END;
END;
/ 

CREATE OR REPLACE PROCEDURE add_admin 
(adm_typeID in NUMBER, adm_permID IN NUMBER,
adm_user IN VARCHAR2, adm_pass IN VARCHAR2, us_id IN number)
AS
BEGIN
    
    DECLARE 
        adm_id INT := uniq_id_seq.nextVal;
        acct_ID INT DEFAULT 0;
    
    BEGIN
    
        -- Create admin account
        INSERT INTO ADMINS VALUES(adm_id, adm_typeID);
    
        add_account(adm_permID, adm_user, adm_pass, 1, acct_ID);
    
        -- Create admin_account_junction
        INSERT INTO ADMIN_ACCOUNT_JUNCTION VALUES(acct_ID, adm_id);
        
        -- Create user_admin_junction
        INSERT INTO USER_ADMIN_JUNCTION VALUES(us_id, adm_id); 
    
        COMMIT;
        EXCEPTION WHEN OTHERS THEN ROLLBACK;
    END;
END;
/

CREATE OR REPLACE PROCEDURE update_request
(req_stat IN NUMBER, out_ref OUT SYS_REFCURSOR)
AS BEGIN
    UPDATE Request SET Request.Request_Status_ID = req_stat;
    COMMIT;
    EXCEPTION WHEN OTHERS THEN ROLLBACK;
END;
/

CREATE OR REPLACE PROCEDURE update_loan
(remaining IN NUMBER, loan_stat IN NUMBER)
AS 
BEGIN
    UPDATE LOAN SET 
        LOAN.remaining = remaining,
        LOAN.status = loan_stat;
    COMMIT;
    EXCEPTION WHEN OTHERS THEN ROLLBACK;
END;    
/

CREATE OR REPLACE PROCEDURE update_trans
(msg IN NUMBER)
AS BEGIN
    UPDATE TRANSACTIONS SET 
    TRANSACTIONS.message = msg;
    
    COMMIT;
    EXCEPTION WHEN OTHERS THEN ROLLBACK;
END;
/

CREATE OR REPLACE PROCEDURE update_user
(us_typeID IN NUMBER, us_age IN NUMBER, us_user IN VARCHAR2, us_pass IN VARCHAR2, 
cred_limit in NUMBER, penalty IN NUMBER, interest IN NUMBER,  us_ssn IN NUMBER, 
us_perm IN NUMBER, us_update_date IN TIMESTAMP, us_state IN NUMBER, us_status IN NUMBER,us_inc_lim IN NUMBER, us_inc_pen IN NUMBER, us_inc_int IN NUMBER, 
us_dec_lim IN NUMBER, us_dec_pen IN NUMBER, us_dec_int IN NUMBER, us_balance IN NUMBER)
AS BEGIN
    UPDATE SSN SET SSN.ssn = us_ssn;
    UPDATE USERS SET 
        USERS.age = us_age,
        USERS.USER_TYPE_ID = us_typeID,
        USERS.STATE_ID = us_state,
        USERS.STATUS_ID = us_status,
        USERS.UPDATE_DATE = us_update_date,
        USERS.BALANCE = us_balance,
        USERS.CREDIT_LIMIT = cred_limit,
        USERS.PENALTY = penalty,
        USERS.Interest = interest;
    UPDATE ACCOUNT_VARIANCE SET
        ACCOUNT_VARIANCE.Increasing_Limit_Rate = us_inc_lim,
        ACCOUNT_VARIANCE.Increasing_Penalty_Rate = us_inc_pen,
        ACCOUNT_VARIANCE.Increasing_Interest_Rate = us_inc_int,
        ACCOUNT_VARIANCE.Decreasing_Limit_Rate = us_dec_lim,
        ACCOUNT_VARIANCE.Decreasing_Penalty_Rate = us_dec_pen,
        ACCOUNT_VARIANCE.Decreasing_Interest_Rate = us_dec_int;

    COMMIT;
    EXCEPTION WHEN OTHERS THEN ROLLBACK;
END;
/

CREATE OR REPLACE PROCEDURE update_account
(acct_user IN VARCHAR2, acct_pass IN VARCHAR2,
acct_type_ID IN NUMBER, perm_ID IN NUMBER)
AS BEGIN
    UPDATE CREDENTIALS SET 
        Credentials.usernames = acct_user,
        Credentials.passwords = acct_pass;
    UPDATE ACCOUNTS SET
        Accounts.Account_Type_ID = acct_type_ID,
        Accounts.Permission_ID = perm_ID;
    COMMIT;
    EXCEPTION WHEN OTHERS THEN ROLLBACK;
END;
/

CREATE OR REPLACE PROCEDURE update_admin 
(adm_typeID in NUMBER, adm_permID IN NUMBER,
adm_user IN VARCHAR2, adm_pass IN VARCHAR2, us_id IN number)
AS
BEGIN
    UPDATE ADMINS SET 
        ADMINS.Admin_TYPE_ID = adm_typeID;
        update_account(adm_user, adm_pass, 1, adm_permID);
    COMMIT;
    EXCEPTION WHEN OTHERS THEN ROLLBACK;
END;
/

CREATE OR REPLACE PROCEDURE delete_request
(req_typeID IN NUMBER, protocol IN NUMBER, 
del_id IN NUMBER, out_ref OUT SYS_REFCURSOR)
AS BEGIN
    IF(protocol = 0) THEN
        IF(req_TypeID = 2) THEN
            DELETE FROM REQUEST_TRANSACTION_JUNCTION WHERE REQUEST_TRANSACTION_JUNCTION.Request_ID = del_id;
        END IF;
            DELETE FROM REQUEST WHERE REQUEST.Request_ID = del_id;
    ELSE
        IF(req_TypeID = 2) THEN
            DELETE FROM REQUEST_TRANSACTION_JUNCTION WHERE Request_TRANsaction_JUNCTION.REQUEST_ID
            IN ( SELECT Request.USER_ID FROM Users WHERE User_ID = del_id);
        END IF;   
        DELETE FROM REQUEST WHERE REQUEST.User_ID = del_id;
    END IF;
    COMMIT;
    EXCEPTION WHEN OTHERS THEN ROLLBACK;
END;
/

CREATE OR REPLACE PROCEDURE delete_trans
(protocol IN NUMBER, del_id IN NUMBER)
AS BEGIN
    DECLARE 
        idx INT := 0;
    BEGIN
    SELECT Transaction_id INTO idx FROM TRANSACTIONS;
    IF protocol = 0 THEN
        SELECT COUNT(*) INTO idx FROM Transaction_Loan_Junction WHERE TRANSACTION_LOAN_JUNCTION.TRANSAcTION_ID = del_id;
        IF(idx IS NOT NULL) THEN
            DELETE FROM TRANSACTION_LOAN_JUNCTION WHERE TRANSACTION_LOAN_JUNCTION.TRANSACTION_ID = del_id;
        END IF;
            
        SELECT COUNT(*) INTO idx FROM Request_Transaction_Junction WHERE Request_Transaction_Junction.TRANSAcTION_ID = del_id;
        IF(idx IS NOT NULL) THEN
            DELETE FROM Request_Transaction_Junction WHERE Request_Transaction_Junction.TRANSACTION_ID = del_id;
        END IF;
        DELETE FROM TRANSACTIONS WHERE TRANSACTIONS.TRAnSACTION_ID = del_id;
    ELSE    
        DELETE FROM Request_Transaction_Junction WHERE Request_Transaction_Junction.TRANSACTION_ID 
        IN ( SELECT Transactions.Transaction_ID FROM TRANSACTIONS WHERE TRANSACTIONS.USER_ID = del_id);
        DELETE FROM TRANSACTIONS WHERE TRANSACTIONS.User_ID = del_id;
    END IF;

    COMMIT;
    EXCEPTION WHEN OTHERS THEN ROLLBACK;

    END; 
END;
/
    
CREATE OR REPLACE PROCEDURE delete_loan
(del_id IN NUMBER, protocol IN NUMBER)
AS BEGIN
    IF protocol = 0 THEN
        DELETE FROM LOAN_USER_JUNCTION WHERE LOAN_USER_JUNCTION.Loan_id = del_id;
        DELETE FROM LOAN WHERE LOAN.Loan_id = del_id;
        delete_trans(2, del_id);
    ELSIF protocol = 1 THEN
        DELETE FROM LOAN_User_JUNCTION WHERE LOAN_User_JUNCTION.Loan_ID
        IN (SELECT USERS.USER_ID FROM Users WHERE Users.USER_ID = del_id);
--        IN ( SELECT LOAN.Loan_ID FROM Loan WHERE Loan.User_ID = del_id);
        delete_trans(1, del_id);
    ELSE
        DELETE FROM LOAN_REQUEST_JUNCTION WHERE LOAN_REQUEST_JUNCTION.Loan_ID = del_id;
        DELETE FROM LOAN_Request_JUNCTION WHERE LOAN_Request_JUNCTION.Loan_ID
        IN (SELECT Request.Request_ID FROM Request WHERE Request.Request_ID = del_id);
--        IN (SELECT LOAN.Load_ID FROM LOAN WHERE Loan.Request_ID = del_id );
--        DELETE FROM LOAN WHERE LOAN.Request_id = del_id;
    END IF;
    
    COMMIT;
    EXCEPTION WHEN OTHERS THEN ROLLBACK;
END;
/

CREATE OR REPLACE PROCEDURE delete_user
(del_id IN NUMBER)
AS BEGIN
    DECLARE
        idx INT := 0;
        acct_ID INT;
        
    BEGIN
        SELECT ACCOUNT_ID INTO acct_ID FROM USER_ACCOUNT_JUNCTION WHERE USER_ACCOUNT_JUNCTION.USER_ID = del_id;
        DELETE FROM USER_ACCOUNT_JUNCTION WHERE USER_ACCOUNT_JUNCTION.USER_ID = del_id;
        delete_account(del_i);
        DELETE FROM USER_ACCOUNT_JUNCTION WHERE USER_ACCOUNT_JUNCTION.USER_ID = del_id;
        DELETE FROM ACCOUNT_VARIANCE WHERE ACCOUNT_VARIANCE.USER_ID = del_id;
        DELETE FROM LOCKED_ACCOUNTS WHERE LOCKED_ACCOUNTS.USER_ID = del_id;
        DELETE FROM SSN WHERE SSN.USer_ID = del_id;
        SELECT COUNT(*) INTO idx FROM USER_ADMIN_JUNCTION WHERE USER_ADMIN_JUNCTION.USER_ID = del_id;
        IF(idx > 0) THEN
            DELETE FROM USER_ADMIN_JUNCTION WHERE USER_ADMIN_JUNCTION.USER_ID = del_id;
            idx := 0;
        END IF;
        SELECT COUNT(*) INTO idx FROM Request WHERE REQUEST.USER_ID = del_id;
        IF(idx > 0) THEN
            delete_request(0, 1, del_id, 0);
            delete_request(1, 1, del_id, 0);
            delete_request(2, 1, del_id, 0);
            idx := 0;
        END IF;
        delete_loan(del_id, 1);
        END IF;

        SELECT COUNT(*) INTO idx FROM Transactions WHERE TRANSACTIONS.USER_ID = del_id;
        IF(idx = 0) THEN
            delete_transaction();
            idx := 0;
        END IF;
        DELETE FROM USERS WHERE USERS.USER_ID = del_id;
        
        COMMIT;
        EXCEPTION WHEN OTHERS THEN ROLLBACK;
    END;
END;
/

CREATE OR REPLACE PROCEDURE delete_account
(del_id IN NUMBER)
AS BEGIN
    DELETE FROM CREDENTIALS WHERE Credentials.Account_ID = del_id;
    DELETE FROM ACCOUNTS WHERE Accounts.Account_ID = del_id;
    
    COMMIT;
    EXCEPTION WHEN OTHERS THEN ROLLBACK;
END;
/

--DROP TRIGGER NO_ROOT_ADMIN;
CREATE OR REPLACE TRIGGER NO_ROOT_ADMIN
BEFORE INSERT OR UPDATE
ON REQUEST --ADMIN_TYPE, USER_ADMIN_JUNCTION, ADMIN_ACCOUNT_JUNCTION
DECLARE
    cnt number;
BEGIN
    SELECT COUNT(*) INTO cnt FROM ADMINS WHERE ADMIN_ID = 0;
    IF cnt = 0 THEN
        write_user(0, 0, 'root', 'bobbert1', 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        write_admin(0, 7, 'root', 'bobbert1', 0);
        END IF;
END;
/

CREATE OR REPLACE TRIGGER NO_ROOT_ADMIN_DEL
BEFORE DELETE
ON USER_ADMIN_JUNCTION --ADMIN_TYPE, USER_ADMIN_JUNCTION, ADMIN_ACCOUNT_JUNCTION
FOR EACH ROW 
WHEN (NEW.ADMIN_ID = NULL)
BEGIN
        raise_application_error (-20100, 'You can not delete initial record');
END;
/