DROP TABLE usertransaction;
DROP TABLE userinfo;
DROP TABLE bankaccount;
DROP TABLE request;
DROP TABLE bankuser;


CREATE TABLE bankuser (
    useremail VARCHAR2(256),
    passhash VARCHAR2(256),
    isadmin NUMBER(1),
    islocked NUMBER(1),
    CONSTRAINT pk_user PRIMARY KEY (useremail)
);
/


CREATE TABLE bankaccount (
    accountnumber NUMBER,
    useremail VARCHAR2(256),
    balance NUMBER,
    CONSTRAINT pk_account PRIMARY KEY (accountnumber),
    CONSTRAINT fk_accountuser FOREIGN KEY (useremail) REFERENCES bankuser(useremail) ON DELETE CASCADE
);
/

DROP SEQUENCE seq_account;
CREATE SEQUENCE seq_account
    START WITH 1
    INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trig_seq_account
BEFORE INSERT ON bankaccount
FOR EACH ROW
BEGIN
    IF :NEW.accountnumber IS NULL THEN
        SELECT seq_account.nextval INTO :NEW.accountnumber FROM dual;
    END IF;
END;
/


CREATE TABLE request (
    requestid NUMBER,
    fileremail VARCHAR2(256),
    filedate TIMESTAMP,
    requesttype NUMBER(2),
    CONSTRAINT pk_request PRIMARY KEY(requestid),
    CONSTRAINT fk_requestuser FOREIGN KEY (fileremail) REFERENCES bankuser(useremail) ON DELETE CASCADE
);
COMMIT;
/

DROP SEQUENCE seq_request;
CREATE SEQUENCE seq_request
    START WITH 1
    INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trig_seq_request
BEFORE INSERT ON request
FOR EACH ROW
BEGIN
    IF :NEW.requestid IS NULL THEN
        SELECT seq_request.nextval INTO :NEW.requestid FROM dual;
    END IF;
END;
/


CREATE TABLE userinfo (
    useremail VARCHAR2(256),
    firstname VARCHAR2(256),
    lastname VARCHAR2(256),
    address VARCHAR2(256),
    city VARCHAR2(256),
    userstate VARCHAR2(256),
    zipcode NUMBER(10),
    phone VARCHAR2(12),
    CONSTRAINT pk_userinfo PRIMARY KEY (useremail),
    CONSTRAINT fk_userinfouser FOREIGN KEY (useremail) REFERENCES bankuser(useremail) ON DELETE CASCADE
);
/


CREATE TABLE usertransaction (
    txid NUMBER,
    useremail VARCHAR(256),
    prebalance NUMBER,
    postbalance NUMBER,
    txtype NUMBER(2),
    CONSTRAINT pk_transaction PRIMARY KEY (txid),
    CONSTRAINT fk_transactionuser FOREIGN KEY (useremail) REFERENCES bankuser(useremail) ON DELETE CASCADE
);
DROP SEQUENCE seq_transaction;
CREATE SEQUENCE seq_transaction
    START WITH 1
    INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trig_seq_transaction
BEFORE INSERT ON usertransaction
FOR EACH ROW
BEGIN
    IF :NEW.txid IS NULL THEN
        SELECT seq_transaction.nextval INTO :NEW.txid FROM dual;
    END IF;
END;
/

COMMIT;