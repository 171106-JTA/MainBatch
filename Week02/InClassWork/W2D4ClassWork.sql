DROP TABLE FLASH_CARDS;
CREATE TABLE FLASH_CARDS 
(
    FC_ID NUMBER(6),
    FC_QUESTION VARCHAR2(4000),
    FC_ANSWER VARCHAR2(4000),
    CONSTRAINT PK_FC PRIMARY KEY (FC_ID)
);

INSERT INTO FLASH_CARDS
VALUES(1, 'WHAT IS JAVA', 'A BEVERAGE');
INSERT INTO FLASH_CARDS
VALUES(2, 'ARE WE HUMAN', 'YES...WHAT? YES.');

SELECT * FROM FLASH_CARDS;

/*
Oracle has an extension called PL/SQL
Procedural Language/Structured Query Language\
- Provided tools that we can use with SQL including
-- Functions
-- Stored Proecdures
-- Sequences
-- Triggers
-- Also provides a means to write actual code inside SQL scripts
*/

--SEQUENCE
/*
 A Sequence is an obejct that will maintain a counter for you
*/

CREATE SEQUENCE FC_SEQ
    START WITH 3
    INCREMENT BY 1; 
    --The sequence will be used to handle the ID field of the current flash_card table
    --In order to actually utilize this sequence, we will need to utilize a trigger
    
--TRIGGER
/*
    A trigger is an object we can create that waits for actions to occur on the 
    specific table the trigger was made for
    A trigger can be coded to react to most CRUD operations. (Excluding select)
*/

CREATE OR REPLACE TRIGGER FC_SEQ_TRIGGER --AUTO INCREMEING
BEFORE INSERT ON FLASH_CARDS --or AFTER
FOR EACH ROW
BEGIN  -- This keyword signifies a block for a transaction
    IF :NEW.FC_ID IS NULL THEN
    SELECT FC_SEQ.NEXTVAL INTO :NEW.FC_ID FROM DUAL;
    END IF;
END;
/
/*
    Select Into statment
    - A Select Into STatement will grab data and place it into a variable
    In PL/SQL there exists implicit objects that we can use to access data
    These object are all preceeded with a colon ':'. 
    In the above example we accessed :NEW, which points to the current record 
    that is about to be inserted. This lets us manipulate data before it gets
    inserted. :NEW can be compared to a staging platform
    
    DUAL is a dummy table that is always available. It's only purpose is to allow 
    a develop to have proper syntax in statements where the table does not matter. 
    
*/

INSERT INTO FLASH_CARDS (FC_QUESTION, FC_ANSWER)
VALUES('Did my tirgger work?', 'I sure hope so!');
/
SELECT * FROM FLASH_CARDS;


--Stored Procedure
/*
    A Stored Procedure is a named transaction that can be invoked when called. 
    
    CREATE [or REPlACE] proc-name
    IS
        [This section is where you can DECLARE variables]
    BEGIN
        This section is where you can write the execution
    [EXCEPTION]
        You can perform exception handling here. 
    END; 
    
    you can invoke a stored procedure in two ways: 
   
    BEGIN
        proc-name();
    END; 
    
    --Or 
    
    call proc-name();
    
*/

CREATE OR REPLACE PROCEDURE HELLO_WORLD_PROCEDURE
IS 
BEGIN 
    dbms_output.put_line('Hello World');
END;
/

/*
To View the SQL developer console, navigate to: 
View -> dbms output> clkci the '+' symbol, and add the database whose console you to view
*/
BEGIN
    HELLO_WORLD_PROCEDURE();
END;
/
/*
    Procedure can allow us to define executions that can change the data. 
    Most actions can be taken against a table using procedures. 
*/

/*
    Things of note: 
        Parameters in stored procedures are a bit unique. 
        YOu have to use the follwoing keywords: IN, OUT, IN OUT.
        IN parameters -> whatever is passed as input during the procedure call
        OUT parameters -> whatever is returned from the procedure. 
        IN OUT parameters -> a parameter passed in then changed. 
*/

CREATE OR REPLACE PROCEDURE INSERT_FC_PROCEDURE (SOME_Q IN VARCHAR2, SOME_A IN FLASH_CARDS.FC_ANSWER%TYPE)
IS
BEGIN
    INSERT INTO FLASH_CARDS(FC_QUESTION, FC_ANSWER)
    VALUES(SOME_Q, SOME_A); 
    COMMIT; 
END;
/

BEGIN
    INSERT_FC_PROCEDURE('A procedure question?', 'You are darn right!');
END;

call INSERT_FC_PROCEDURE('A procedure question!!!!!!?', 'You are darn right!!!!!!');


CREATE OR REPLACE PROCEDURE get_answer(SOME_Q IN VARCHAR2, MY_ANSWER OUT VARCHAR2)
IS 
BEGIN
    SELECT FC_ANSWER INTO MY_ANSWER FROM FLASH_CARDS WHERE LOWER(FC_QUESTION) = LOWER(SOME_Q);
END;

/*
    DECLARE -> usered to sup up a block where wariables can be declared, then used
    within the following BEGIN block. 
*/
DECLARE 
    INPUTS VARCHAR2(2000);
    ANSWER VARCHAR2(2000);
BEGIN
    INPUTS := 'WHAT IS JAVA';
    GET_ANSWER(INPUTS, ANSWER);
    DBMS_OUTPUT.PUT_LINE(ANSWER); --Prove to ourselves that the line was changed
END;

/*
    CURSORS: 
    - a CURSOR IS ESSENTIALLY LIKE A POINTER TO A TABLE OR VIEW
    - WE CAN USE THEM TO ITERATE THROUGH INTIRE QUERIES FORM THE DATABASE
    - When we want to pass entire tables or queries, we need to use cursosrs. 
    
    Note: PL/SQL offers a CURSOR and a SYS_REFCURSOR
    The SYS_REFCURSOR is a stronger cursor (Therefore more costly) that is allowed
    to be passed outside of the scope of the procedure it's in. A normal CURSOR must 
    be created and die within the scope of the procedure that is was created in. 
*/

CREATE OR REPLACE PROCEDURE GET_ALL_FC(CURSORPARAM OUT SYS_REFCURSOR)
IS 
BEGIN 
    OPEN CURSORPARAM FOR
    SELECT * FROM FLASH_CARDS;
END;

DECLARE
    FC_CURSOR SYS_REFCURSOR;
    SOMEID FLASH_CARDS.FC_ID%TYPE;
    SOMEQ VARCHAR2(4000); 
    SOMEA VARCHAR2(4000);
BEGIN
    GET_ALL_FC(FC_CURSOR);
    
    LOOP
        FETCH FC_CURSOR INTO SOMEID, SOMEQ, SOMEA; --Grab the current recods its pointing at
        EXIT WHEN FC_CURSOR%NOTFOUND; --%NOTFOUND does not exist until therer are no records left. 
        DBMS_OUTPUT.PUT_LINE(SOMEID || ' ' || SOMEQ || ' ' || SOMEA);
    END LOOP;
END;

/*
    A function differs from a stored procedue in the following ways: 
    A stored procedure does not have to return anything
    A stored procedure CAN have as many IN/OUT parameters as it wants
    A stored procedure CAN alter the database such as insert, delete, ect.
    A stored procedure CANNOT be called mid-query
    We can call a stored procedure from a stored procedure
    A stored procedur can call functions
    
    A function MUST return one and only one thing
    A function CAN use OUT parameters, but this is highly advised against
    A funciton CANNOT perfomr database operations. 
    A function CAN be called mid-query
    a function can call other functions
    A function cannot call a stored procedure 
    - (because functions cannot change a database and stored procedures can)
*/

CREATE OR REPLACE FUNCTION GET_MAX_ID
RETURN NUMBER
IS
    MAX_ID NUMBER; 
BEGIN
    SELECT MAX(FC_ID) INTO MAX_ID FROM FLASH_CARDS;
    RETURN MAX_ID;
END;
/
DECLARE
    MAX_ID NUMBER;
BEGIN 
    MAX_ID := GET_MAX_ID();
    DBMS_OUTPUT.PUT_LINE(MAX_ID);
END;


--EXAMPLE. MAKING FUNCTION INSIDE A DECLARATION BLOCK: A TEMPORARY FUNCTION
DECLARE
    FIRSTNUM NUMBER; 
    SECONDNUM NUMBER; 
    MAXNUM NUMBER;
    
    FUNCTION FINDMAX(X IN NUMBER, Y IN NUMBER)
    RETURN NUMBER
    IS
        Z NUMBER;
    BEGIN
        IF X > Y THEN
            Z := X;
        ELSE
            Z := Y;
        END IF;
        
        RETURN Z;
    END;
BEGIN 
    FIRSTNUM := 87; 
    SECONDNUM := 23; 
    MAXNUM := FINDMAX(FIRSTNUM,SECONDNUM);
    DBMS_OUTPUT.PUT_LINE(MAXNUM);
END;

--EXCEPTOIN Example
CREATE OR REPLACE PROCEDURE EXCEPTIONEXAMPLE
IS
    CURSOR BADCURSE IS
        SELECT * FROM FLASH_CARDS;
    FID FLASH_CARDS.FC_ID%TYPE;
    FQUESTION FLASH_CARDS.FC_QUESTION%TYPE;
    FANSWER FLASH_CARDS.FC_ANSWER%TYPE;
BEGIN
    OPEN BADCURSE;
    LOOP
        FETCH BADCURSE INTO FID, FQUESTION, FANSWER;
        EXIT WHEN BADCURSE%NOTFOUND;
    END LOOP;
    CLOSE BADCURSE;
    FID := 1/0;
    
    EXCEPTION
        WHEN INVALID_CURSOR THEN 
            DBMS_OUTPUT.PUT_LINE('CURSOR ERROR');
        WHEN ZERO_DIVIDE THEN
            DBMS_OUTPUT.PUT_LINE('DIVISION BY ZERO ERROR');
END;
/
CALL EXCEPTIONEXAMPLE();


/*
In vs exists
- both these commands can be used to perform conditional checks
- high level overview: EXISTS is garbarg...highly inefficient if the outer query is even remotely large
*/

SELECT * FROM TRAINER WHERE EXISTS (
SELECT TRAINER_ID FROM MY_POLKAMANS WHERE EXISTS (
SELECT PKMN_ID FROM POLKAMAN WHERE LOWER(TYPE1)='fire' OR LOWER(TYPE2) = 'fire'));

/*
Every inner query will run once for every outer query
Trainer - 3 records
my_polamans - 5 records (approximately)
polkmans = 5 records (approximatley)

thereforce, we run ~16 queries in the above example
This is why it behooves somebody to NOT use exists, if the outer query has a lot of data
When in doubt, just use IN. EXISTS if almost depricated. 
*/ 

--rownum is a valid identifier you can use in your conditoinals. 
--however, it is finicky
SELECT * FROM (SELECT MAX_HP FROM MY_POLKAMANS 
WHERE ROWNUM < 4
ORDER BY MAX_HP DESC)
UNION
SELECT * FROM (SELECT MAX_HP FROM MY_POLKAMANS 
WHERE ROWNUM < 3
ORDER BY MAX_HP DESC);

/*
As soon as a rownum conditional fails, it stops applying the condition to the remaining records. 
*/

CREATE USER bobbert IDENTIFIED BY password;

--COMPLETE COMMIT;