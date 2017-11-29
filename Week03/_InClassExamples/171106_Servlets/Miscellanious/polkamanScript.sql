DROP TABLE trainer;
DROP SEQUENCE trainer_seq;
DROP TRIGGER trainer_trigger;

CREATE TABLE trainer (
    trainer_id number(6) primary key,
    trainer_username varchar(100) unique not null,
    trainer_password varchar(100) not null,
    trainer_email varchar(100) not null,
    trainer_fname varchar(100) not null,
    trainer_mname varchar(100) null,
    trainer_lname varchar(100) null,
    date_created timestamp not null
);


CREATE SEQUENCE trainer_seq
    start with 3
    increment by 1;
/
CREATE OR REPLACE TRIGGER trainer_trigger --auto increment
BEFORE INSERT ON trainer
FOR EACH ROW
BEGIN
    IF :new.trainer_id IS NULL THEN 
    SELECT trainer_seq.nextval INTO :new.trainer_id from dual;    
    END IF;
    IF :new.date_created IS NULL THEN 
    :new.date_created := current_timestamp;    
    END IF;
END;    
/

insert into trainer (trainer_username, trainer_password, trainer_email, trainer_fname, trainer_mname, trainer_lname)
VALUES('test','test','test','test','test','test');

select * from trainer;