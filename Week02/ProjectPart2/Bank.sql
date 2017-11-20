--ALTER TABLE Customer DROP CONSTRAINT FK_AccId ;
--ALTER TABLE Customer DROP CONSTRAINT FK_LoanId;

-------------------------------------------------------------------------------------------------------
--ALTER TABLE Bank_Account DROP CONSTRAINT FK_Username_Account;
--ALTER TABLE Loan DROP CONSTRAINT FK_Username_Loan;
--DROP TABLE Customer;
--DROP TABLE Bank_Account;
--DROP TABLE Loan;

CREATE TABLE Customer(
    Username VARCHAR2(200) NOT NULL,
    Pass VARCHAR2(200) NOT NULL,
    FName VARCHAR2(1000),
    LName VARCHAR2(1000),
    C_AccId NUMBER,
    C_LoanId NUMBER,
    LockStatus NUMBER DEFAULT 0,
    ApprovalStatus NUMBER DEFAULT 0,
    AccessLevel VARCHAR2(50) DEFAULT 'REG',
    CONSTRAINT PK_Username PRIMARY KEY (Username)
);

CREATE TABLE Bank_Account(
    AccId NUMBER NOT NULL,
    Username VARCHAR2(200) NOT NULL,
    Amount NUMBER DEFAULT 0,
    CONSTRAINT PK_Accid PRIMARY KEY (AccId)
);

CREATE TABLE Loan(
    LoanId NUMBER NOT NULL,
    Username VARCHAR2(200) NOT NULL,
    Loan_Amount NUMBER NOT NULL,
    RequestDate VARCHAR2(200) NOT NULL,
    ApprovalDate VARCHAR2(200),
    Status VARCHAR2(200) DEFAULT 'PENDING',
    PaidOff VARCHAR2 (200) DEFAULT 'UNPAID',
    CONSTRAINT PK_LoanId PRIMARY KEY (LoanId)
);

--ALTER TABLE Customer ADD CONSTRAINT FK_AccId FOREIGN KEY (C_AccId) REFERENCES Bank_Account (AccId);

--ALTER TABLE Customer ADD CONSTRAINT FK_LoanId FOREIGN KEY(C_LoanId) REFERENCES Loan (LoanId);

ALTER TABLE Bank_Account ADD CONSTRAINT FK_Username_Account FOREIGN KEY(Username) REFERENCES Customer (Username) ON DELETE CASCADE;

ALTER TABLE Loan ADD CONSTRAINT FK_Username_Loan FOREIGN KEY (Username) REFERENCES Customer (Username) ON DELETE CASCADE;

CREATE OR REPLACE PROCEDURE update_account_amount(accountId IN NUMBER, new_amount IN NUMBER)
IS
BEGIN
    UPDATE Bank_Account SET AMOUNT = new_amount WHERE ACCID = accountId;
    COMMIT;
END;

--DROP SEQUENCE account_seq;

CREATE SEQUENCE account_seq
    START WITH 2
    INCREMENT BY 1;
    
CREATE SEQUENCE loan_seq
    START WITH 1
    INCREMENT BY 1;

CREATE OR REPLACE TRIGGER new_loan
BEFORE INSERT ON Loan
FOR EACH ROW
BEGIN
    IF :new.loanId IS NULL THEN
    SELECT loan_seq.NEXTVAL INTO :new.loanId FROM Dual;
    END IF;
END;

CREATE OR REPLACE TRIGGER new_account
BEFORE INSERT ON Customer
FOR EACH ROW
BEGIN
    IF :new.C_accId IS NULL THEN
    SELECT account_seq.NEXTVAL INTO :new.C_accId FROM Dual;
    END IF;
END;

CREATE OR REPLACE TRIGGER new_bank_account
AFTER INSERT ON Customer
FOR EACH ROW
BEGIN
    INSERT INTO Bank_Account (ACCID, USERNAME) VALUES(:new.C_accid, :new.username);
END;

CREATE OR REPLACE PROCEDURE update_approval_lock(uname IN VARCHAR2, approve IN NUMBER, lockstat IN NUMBER)
IS
BEGIN
    UPDATE Customer SET APPROVALSTATUS = approve, LOCKSTATUS = lockstat WHERE USERNAME = uname;
    COMMIT;
END;

CREATE OR REPLACE PROCEDURE promote_user(uname IN VARCHAR2, new_status IN VARCHAR2)
IS 
BEGIN
    UPDATE Customer SET ACCESSLEVEL = new_status WHERE USERNAME = uname;
    COMMIT;
END;

COMMIT;