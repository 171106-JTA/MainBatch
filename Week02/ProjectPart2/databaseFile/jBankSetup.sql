CREATE TABLE jbank_users
(
    user_id number(6),
    username varchar2(30),
    firstName varchar2(50),
    lastName varchar2(50),
    userpwd varchar2(50),
        userlevel varchar2(10),
    pin number(4),
    locked_tf varchar(5),
    approved_tf varchar(5),
    balance number(20,2),
    CONSTRAINT pk_uid primary key(user_id)
);

INSERT into jbank_userS
VALUES (000000, 'admin', 'Han', 'Jung', 
'passwd', 'ADMIN', '1234', 'FALSE', 'FALSE', 50.00);

DROP SEQUENCE jb_seq;
CREATE SEQUENCE jb_seq
    start with 3
    increment by 1;

CREATE OR REPLACE TRIGGER jb_seq_trigger --auto increment
BEFORE INSERT ON jbank_users
FOR EACH ROW
BEGIN --This keyword signifies a block for a transaction. 
    IF :new.user_id IS NULL THEN 
    SELECT jb_seq.nextval INTO :new.user_id from dual;    
    END IF;
END;    
/

SELECT * FROM jbank_users;


DROP SEQUENCE tr_seq;
CREATE SEQUENCE tr_seq
    start with 1001
    increment by 3;

CREATE OR REPLACE TRIGGER tr_seq_trigger --auto increment
BEFORE INSERT ON jbank_trans
FOR EACH ROW
BEGIN --This keyword signifies a block for a transaction. 
    IF :new.trans_id IS NULL THEN 
    SELECT tr_seq.nextval INTO :new.trans_id from dual;    
    END IF;
    
END;    
/

ALTER TABLE jbank_trans MODIFY TRAN_DATE default sysdate not null;