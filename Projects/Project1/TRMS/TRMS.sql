DROP TABLE messages; 
DROP TABLE form_attach; 
DROP TABLE event_form; 
DROP TABLE personnel; 
DROP SEQUENCE emp_seq;

CREATE TABLE personnel (
  emp_id NUMBER(6) PRIMARY KEY,
  fname VARCHAR2(100) NOT NULL,
  lname VARCHAR2(100) NOT NULL,
  usrnm VARCHAR2(100) UNIQUE NOT NULL,
  pswrd VARCHAR2(100) NOT NULL,
  email VARCHAR2(100) NOT NULL,
  phone VARCHAR2(100) NOT NULL,
  dept VARCHAR2(100) NOT NULL,
  role1 VARCHAR2(100), 
  role2 VARCHAR2(100), 
  reportsto NUMBER(6),
  amt_avail NUMBER(6)
);

CREATE TABLE event_form (
  emp_id NUMBER(6) NOT NULL,
  form_id NUMBER(6) PRIMARY KEY NOT NULL,
  status VARCHAR2(100) NOT NULL,
  date_submit TIMESTAMP NOT NULL, 
  date_occur TIMESTAMP NOT NULL,
  time_start TIMESTAMP NOT NULL,
  time_end TIMESTAMP NOT NULL,
  event_place VARCHAR2(100) NOT NULL,
  event_type VARCHAR2(100) NOT NULL,
  description VARCHAR2(100) NOT NULL,
  grade_format VARCHAR2(100) NOT NULL,
  grade_cutoff NUMBER(6) NOT NULL,
  grade_received NUMBER(6),
  event_cost NUMBER(6) NOT NULL,
  justification VARCHAR2(100) NOT NULL,
  workhr_missed NUMBER(6),
  CONSTRAINT fk_emp_id FOREIGN KEY(emp_id) REFERENCES personnel(emp_id)
);

CREATE TABLE form_attach (
  -- presentations, .msg, media attachments
  form_id NUMBER(6) NOT NULL,
  uploadb BLOB,
  uploadc CLOB,
  CONSTRAINT fk_attch_id FOREIGN KEY(form_id) REFERENCES event_form(form_id)
);

CREATE TABLE messages (
  form_id NUMBER(6) NOT NULL,
  emp_id_to NUMBER(6),
  emp_id_from NUMBER(6),
  msg_content VARCHAR2(500),
  CONSTRAINT fk_msg_id FOREIGN KEY(form_id) REFERENCES event_form(form_id)
);


INSERT INTO personnel
VALUES (1,'Rhys','Waylan','rhwyln','master','rhys.waylan@lucid.com','202-148-2918','HR','Department Head','Direct Supervisor',NULL,1000);
INSERT INTO personnel
VALUES (2,'Zaïre','Lawson','zrlwsn','commander','zaire.lawson@lucid.com','202-194-7208','HR','Benefits Coordinator',NULL,1,1000);
INSERT INTO personnel
VALUES (3,'Munn','Santana','mnstn','slinger','munn.santana@lucid.com','202-037-1189','IT','Deptartment Head',NULL,NULL,1000);
INSERT INTO personnel
VALUES (4,'Stiles','Stilinski','ststlnk','spark','stiles.stilinski@lucid.com','202-828-0019','IT','Direct Supervisor',NULL,3,1000);
INSERT INTO personnel
VALUES (5,'Djeya','Faruq','djfrq','knives','djeya.faruq@lucid.com','202-957-2749','RD','Department Head',NULL,NULL,1000);
INSERT INTO personnel
VALUES (6,'Dmitrius','James','dmjms','sniper','dmitrius.james@lucid.com','202-551-2990','RD','Direct Supervisor',NULL,5,1000);
INSERT INTO personnel
VALUES (7,'Panna','Ondrash','pndrsh','bigbrother','panna.ondrash@lucid.com','202-483-8402','Marketing','Department Head',NULL,NULL,1000);
INSERT INTO personnel
VALUES (8,'Kakashi','Hatake','kkhtk','pakkun','kakashi.hatake@lucid.com','202-202-4113','Marketing','Direct Supervisor',NULL,7,1000);
INSERT INTO personnel
VALUES (9,'Tua','Hannover','thnvr','bruiser','tua.hannover@lucid.com','202-479-9023','Finance','Department Head',NULL,NULL,1000);
INSERT INTO personnel
VALUES (10,'Roy','Mustang','rymstg','flames','roy.mustang@lucid.com','202-799-4238','Finance','Direct Supervisor',NULL,9,1000);


CREATE SEQUENCE emp_seq
  START WITH 11
  INCREMENT BY 1;
/

CREATE OR REPLACE TRIGGER emp_trigger
BEFORE INSERT ON personnel
FOR EACH ROW
BEGIN
  IF :new.emp_id IS NULL THEN
  SELECT emp_seq.nextval INTO :new.emp_id FROM DUAL;
  END IF;
END;
/

select * from personnel;