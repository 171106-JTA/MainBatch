/*Database creation for Banking 1.2*/
--------------------------------------------------------------------------------
--Tables
--------------------------------------------------------------------------------
/*
ALTER TABLE BANK_CUSTOMER DROP CONSTRAINT FK_CUSTOMER_PERSON;

ALTER TABLE BANK_CUSTOMER DROP CONSTRAINT FK_CUSTOMER_ROLE;

ALTER TABLE BANK_ACCOUNT DROP CONSTRAINT FK_ACCOUNT_CUSTOMER;

ALTER TABLE BANK_ACCOUNT DROP CONSTRAINT FK_ACCOUNT_ACCTYPE;

ALTER TABLE LOAN DROP CONSTRAINT FK_LOAN_ACCOUNT;

ALTER TABLE BANK_TRANSACTION DROP CONSTRAINT FK_TRANSACTION_ACCOUNT;
*/


DROP TABLE BANK_TRANSACTION;
DROP TABLE LOAN;
DROP TABLE BANK_ACCOUNT;
DROP TABLE BANK_CUSTOMER;
DROP TABLE ACCTYPE;
DROP TABLE USER_ROLE;
DROP TABLE PERSON;

CREATE TABLE PERSON(
	PERSONID    NUMERIC NOT NULL,			        --Auto increment
	FNAME       VARCHAR2(25) NOT NULL,
	LNAME       VARCHAR2(25) NOT NULL,
	ADDRESS     VARCHAR2(100) NOT NULL,
	CITY        VARCHAR2(20),
	STATE       VARCHAR2(20),
    AREACODE    VARCHAR(5),
    COUNTRY     VARCHAR2(20) DEFAULT 'USA',
	EMAIL       VARCHAR2(25) NOT NULL,
	PHONE       VARCHAR2(10) NOT NULL,
	SSN         VARCHAR2(9) NOT NULL,
	DOB         DATE NOT NULL,
    CONSTRAINT PK_PERSON PRIMARY KEY (PERSONID)
);

CREATE TABLE BANK_CUSTOMER(
    CUSTOMERID  NUMERIC NOT NULL,			        --Auto increment
	SINCE       DATE NOT NULL,			            --Customer since
	USERNAME    VARCHAR2(20) NOT NULL,		        --User credentials
	PASS        VARCHAR2(20) NOT NULL,
	ROLEID      NUMERIC DEFAULT 1 NOT NULL,		    --Default value set to 1 for simple user
	PERSONID    NUMERIC NOT NULL,			        
	ISACTIVE    NUMERIC DEFAULT 0 NOT NULL,
    CONSTRAINT PK_BANK_CUSTOMER PRIMARY KEY (CUSTOMERID)
);

--SELECT * FROM USER_CONSTRAINTS WHERE TABLE_NAME = 'BANK_ACCOUNT';

CREATE TABLE ACCTYPE(
	ACCTYPEID      NUMERIC DEFAULT 1 NOT NULL,		    --Default value set to 1 for Checking account
    ACCTYPENAME    VARCHAR2(20) NOT NULL,
    CONSTRAINT PK_ACCTYPE PRIMARY KEY (ACCTYPEID)
);

CREATE TABLE BANK_ACCOUNT(
	ACCOUNTNBER     VARCHAR2(10) NOT NULL,
	CREATIONDATE    DATE NOT NULL,
	ACCTYPEID       NUMERIC DEFAULT 1 NOT NULL,
	ISACTIVE        NUMERIC DEFAULT 1 NOT NULL,	    --Turns to false when the account is closed or the loan paid off.
	CUSTOMERID      NUMERIC NOT NULL,
	BALANCE         NUMERIC DEFAULT 0.0 NOT NULL,		        --Will eventually disappear since it can be computed.
    CONSTRAINT PK_BANK_ACCOUNT PRIMARY KEY (ACCOUNTNBER)
);


CREATE TABLE LOAN(
    LOANNUMBER      NUMERIC NOT NULL,
	PRINCIPAL       NUMERIC DEFAULT 0.0 NOT NULL,
	TERM            NUMERIC DEFAULT 60 NOT NULL,	--Period of the loan in months
	INTERESTRATE    NUMERIC NOT NULL,
	APPROVED        NUMERIC DEFAULT 0 NOT NULL,     --Waiting for an Admin to approve
	ISPASTDUE       NUMERIC DEFAULT 0 NOT NULL,
	ACCOUNTNBER     VARCHAR2(10) NOT NULL,
    CONSTRAINT PK_LOAN PRIMARY KEY (LOANNUMBER)
   
);

CREATE TABLE BANK_TRANSACTION(
	TRANSACTID      NUMERIC NOT NULL,               --Auto increment
	TRANSACTDATE    DATE NOT NULL,
	DETAILS         VARCHAR(25),
	AMOUNT          NUMERIC NOT NULL,			    -->0 for deposits and <0 for withdrawals
	ACCOUNTNBER     VARCHAR2(10) NOT NULL,		
    CONSTRAINT PK_TRANSACTION PRIMARY KEY (TRANSACTID)
);

CREATE TABLE USER_ROLE(
	ROLEID      NUMERIC DEFAULT 1 NOT NULL,		    --Default value set to 1 for simple user
    ROLENAME    VARCHAR2(20) NOT NULL,
    CONSTRAINT PK_ROLE PRIMARY KEY (ROLEID)
);

--------------------------------------------------------------------------------
--Constraints
--------------------------------------------------------------------------------
ALTER TABLE BANK_CUSTOMER ADD CONSTRAINT FK_CUSTOMER_PERSON
    FOREIGN KEY (PERSONID) REFERENCES PERSON (PERSONID);

ALTER TABLE BANK_CUSTOMER ADD CONSTRAINT FK_CUSTOMER_ROLE
    FOREIGN KEY (ROLEID) REFERENCES USER_ROLE (ROLEID);

ALTER TABLE BANK_ACCOUNT ADD CONSTRAINT FK_ACCOUNT_CUSTOMER
    FOREIGN KEY (CUSTOMERID) REFERENCES BANK_CUSTOMER(CUSTOMERID);

ALTER TABLE BANK_ACCOUNT ADD CONSTRAINT FK_ACCOUNT_ACCTYPE
    FOREIGN KEY (ACCTYPEID) REFERENCES ACCTYPE(ACCTYPEID);

ALTER TABLE LOAN ADD CONSTRAINT FK_LOAN_ACCOUNT
    FOREIGN KEY (ACCOUNTNBER) REFERENCES BANK_ACCOUNT (ACCOUNTNBER);

ALTER TABLE BANK_TRANSACTION ADD CONSTRAINT FK_TRANSACTION_ACCOUNT
    FOREIGN KEY (ACCOUNTNBER) REFERENCES BANK_ACCOUNT (ACCOUNTNBER);


--------------------------------------------------------------------------------
--Triggers
--------------------------------------------------------------------------------
--Auto increment for PERSONID
DROP SEQUENCE PERS_SEQ;
CREATE SEQUENCE PERS_SEQ
    start with 1
    increment by 1;

CREATE OR REPLACE TRIGGER PERS_SEQ_TRIGGER --auto increment
BEFORE INSERT ON PERSON
FOR EACH ROW
BEGIN --This keyword signifies a block for a transaction. 
    IF :new.PERSONID IS NULL THEN 
    SELECT PERS_SEQ.nextval INTO :new.PERSONID FROM dual;    
    END IF;
END;    
/

--Auto increment for CUSTOMERID
DROP SEQUENCE CUST_SEQ;
CREATE SEQUENCE CUST_SEQ
    start with 1
    increment by 1;

CREATE OR REPLACE TRIGGER CUST_SEQ_TRIGGER --auto increment
BEFORE INSERT ON BANK_CUSTOMER
FOR EACH ROW
BEGIN --This keyword signifies a block for a transaction. 
    IF :new.CUSTOMERID IS NULL THEN 
    SELECT CUST_SEQ.nextval INTO :new.CUSTOMERID FROM dual;    
    END IF;
END;
/

--Auto increment for TRANSACTID
DROP SEQUENCE TRANSACT_SEQ;
CREATE SEQUENCE TRANSACT_SEQ
    start with 1
    increment by 1;

CREATE OR REPLACE TRIGGER TRANSACT_SEQ_TRIGGER --auto increment
BEFORE INSERT ON BANK_TRANSACTION
FOR EACH ROW
BEGIN --This keyword signifies a block for a transaction. 
    IF :new.TRANSACTID IS NULL THEN 
    SELECT TRANSACT_SEQ.nextval INTO :new.TRANSACTID FROM dual;    
    END IF;
END;
/

--------------------------------------------------------------------------------
--Super USER information inserted
--------------------------------------------------------------------------------
INSERT INTO PERSON(FNAME, LNAME, ADDRESS, CITY, STATE, AREACODE,
    COUNTRY, EMAIL, PHONE, SSN, DOB) VALUES(
    'MAHAMADOU', 'TRAORE', '11730 PLAZA AMERICA', 'HENDON', 'VA', '75220', 'USA',
	'mahammadou@rev.com', '4445862000', '000000000', TO_DATE('01/01/1986', 'mm/dd/yyyy')
    );
        
INSERT INTO USER_ROLE VALUES(
    0, 'ADMIN');

INSERT INTO USER_ROLE VALUES(
    1, 'SIMPLE USER');
    
INSERT INTO BANK_CUSTOMER(SINCE, USERNAME, PASS, ROLEID, PERSONID, ISACTIVE) VALUES(
	SYSDATE, 'admin', 'password', 0, 1, 1
    ); 
    
INSERT INTO ACCTYPE VALUES(
    1, 'CHECKING ACCOUNT');

INSERT INTO ACCTYPE VALUES(
    2, 'SAVING ACCOUNT');
    
INSERT INTO BANK_ACCOUNT VALUES(
	'0000000000', SYSDATE, 1, 1, 1, 0.0
    );


/*--For test purpose
DBMS_OUTPUT.PUT_LINE(TO_DATE('HI'));
*/
INSERT INTO BANK_CUSTOMER(SINCE, USERNAME, PASS, ROLEID, PERSONID, ISACTIVE) VALUES(
SYSDATE , 'User1', 'password', 1, 1, 0);



--------------------------------------------------------------------------------
--Stored procedures

    CREATE OR REPLACE PROCEDURE new_Bank_Customer(customerid_in IN CUSTOMER.CUSTOMERID%TYPE, 
                    username_in IN BANK_CUSTOMER.USERNAME%TYPE, 
                    password_in IN BANK_CUSTOMER.USERNAME%TYPE, roleid_in IN BANK_CUSTOMER.ROLEID%TYPE,
                    personid_in IN BANK_CUSTOMER.PERSONID%TYPE, isactive_in IN BANK_CUSTOMER.ISACTIVE%TYPE)
    IS
    BEGIN
        INSERT INTO BANK_CUSTOMER(USERNAME, PASS, ROLEID, PERSONID, ISACTIVE) VALUES(
        username_in, password_in, roleid_in, personid_in, isactive_in);
         
        COMMIT;
    END;
    
    CREATE OR REPLACE PROCEDURE new_Transaction(transactiondate_in IN BANK_TRANSACTION.TRANSACTDATE%TYPE, 
                    details_in IN BANK_TRANSACTION.DETAILS%TYPE, 
                    amount_in IN BANK_TRANSACTION.AMOUNT%TYPE, accountnber_in IN BANK_TRANSACTION.ACCOUNTNBER%TYPE)
    IS
    BEGIN
        INSERT INTO BANK_TRANSACTION(TRANSACTDATE, DETAILS, AMOUNT, ACCOUNTNBER) VALUES(
        transactiondate_in, details_in, amount_in, accountnber_in);
         
        COMMIT;
    END;
    
    CREATE OR REPLACE PROCEDURE bank_login(username_in IN BANK_CUSTOMER.USERNAME%TYPE, pass_in IN BANK_CUSTOMER.PASS%TYPE, customer_out OUT SYS_REFCURSOR)
    IS
    BEGIN
        OPEN customer_out FOR
        SELECT customerid, fname, lname FROM bank_customer cross join person 
        WHERE username = username_in and pass = pass_in;
    END;
    /
    DECLARE
        myIterator SYS_REFCURSOR;
        someID bank_customer.CUSTOMERID%TYPE;
        someLastname Person.LNAME%TYPE;
        someFirstname person.FNAME%TYPE;
        someUsername bank_customer.username%type;
        somepass bank_customer.pass%type;
      BEGIN
        bank_login(someUsername, somepass, myIterator); --Have our cursor represent the cursor for flash_cards
        
        LOOP --Begin loop block
            FETCH myIterator INTO someID, someLastname, someFirstname, someUsername; --Grab the current its pointing at.
            EXIT WHEN myIterator%NOTFOUND; --%NOTFOUND does not exist until there are no records left
            DBMS_OUTPUT.PUT_LINE(someID || ' ' || someLastname || ' ' || someFirstname || ' ' || someUsername);
        END LOOP; --End of loop block
    END;   
/
