DROP TABLE A_USER;
CREATE TABLE A_USER(
    USERNAME VARCHAR2(100),
    FIRSTNAME VARCHAR2(100),
    LASTNAME VARCHAR2(100),
    MIDDLEINITIAL VARCHAR2(1),
    PASSWORD VARCHAR(100),
    STATUS NUMBER(1),
    PERMISSION NUMBER(1), 
    ACCOUNTAMOUNT NUMBER(38, 2)    
);

--INSERT INTO A_USER 
--VALUES('A', 'A', 'A','A','A',0,0,0);
--INSERT INTO A_USER 
--VALUES('B', 'B', 'B','B','B',0,0,0);
--INSERT INTO A_USER 
--VALUES('C', 'C', 'C','C','C',0,0,0);
--INSERT INTO A_USER 
--VALUES('D', 'D', 'D','D','D',0,0,0);
--
--SELECT * FROM A_USER;