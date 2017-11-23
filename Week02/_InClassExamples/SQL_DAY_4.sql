DROP TABLE flash_cards;
CREATE TABLE flash_cards
(
    fc_id number(6),
    fc_question varchar2(4000),
    fc_answer varchar2(4000),
    constraint pk_fc primary key(fc_id)
);

insert into flash_cards
Values(1, 'What is Java', 'A beverage');
insert into flash_cards
VALUES(2, 'Are we human?', 'Yes... what? Yes.');


/*
    ORACLE has an extension called PL/SQL. (Procedural Language/Stuctured Query Language.
    -Provides tools that we can use with SQL
    --Such as, functions, stored procedures, sequences, and triggers.
    --Also provides a means to write actual code inside of our SQL.

*/

--SEQUENCE
/*
    A sequence is an object that will maintain a counter for you.
*/
DROP SEQUENCE fc_seq;
CREATE SEQUENCE fc_seq
    start with 3
    increment by 1;
    --This sequence will be used to handle the ID field of our flash_cards table.
    --In order to actually utilize this sequence, we will need to utilize, ourselves,
    --a trigger.

--TRIGGER
/*
    A trigger is an object that we can create that waits for actions to occur
    on the specific *table* the trigger was made for. (don't need multiple per table)
    A trigger can be coded to react to most CRUD operations. (Excluding select)
*/


/*
    SELECT INTO statement
    -A select into statement will grab data and place it into a variable.
    IN PL/SQL there exist implicit objects that we can use to access data.
    These objects all are preceeded with a ':'.
    In our example, we accessed :new, which points to the current record that is about
    to be inserted. This lets us manipulate data before it actually gets inserted.
    You can compare :new to a staging platform.
    -dual is a dummy table that is always available. It's only purpose is to allow
    a developer to have proper syntax in statements where the table does not matter.
*/

INSERT INTO flash_cards (FC_QUESTION, FC_ANSWER)
VALUES('Did my trigger work?', 'Sure hope so...');


--Stored Procedures
/*
    A named transaction that can be invoked when called.

    CREATE [OR REPLACE] PROCEDURE proc-name
    IS
        This section is where you can DECLARE variables
    BEGIN
        This section is where you can write the execution
    [EXCEPTION]
        You can perform exception handling here.
    END;

    You can invoke a stored procedure in two ways:
    BEGIN
        proc-name();
    END;

    ---OR

    call proc-name();
*/

CREATE OR REPLACE PROCEDURE hello_world_procedure
IS
BEGIN
    DBMS_OUTPUT.PUT_LINE('Hello World'); --SQL DEVELOPER equivalent to a SYSO
END;
/
/*
To view teh sql developer console, navigate to:
View>dbms output> click the '+' symbol, and add the database whose console you to view
*/
BEGIN
    HELLO_WORLD_PROCEDURE();
END;
/
/*
    Procedures can allow us to define executions that can change the data.
    Most actions can be taken agasint a table using procedures.
*/

/*
    Things of note:
        Parameters in stored procedures are a bit unique.
        You have use the follow keywords: IN, OUT, IN OUT.
        IN parameters, whatever is passed as input during the procedure call, cannot be accessed/changed upon 2nd call
        OUT parameters, whatever is returned from the procedure.
        IN OUT parameters, a parameter that gets passed in, then changed.
*/

CREATE OR REPLACE PROCEDURE insert_fc_procedure(some_q IN VARCHAR2, some_a IN flash_cards.fc_answer%TYPE)
IS
BEGIN
    INSERT INTO flash_cards(fc_question, fc_answer)
    VALUES(some_q, some_a);
    commit;
END;
/

--Call example
call insert_fc_procedure('A procedure question!?', 'You are darn right');


CREATE OR REPLACE PROCEDURE get_answer(some_q IN VARCHAR2, my_answer OUT varchar2)
IS
BEGIN
    select fc_answer INTO my_answer from flash_cards WHERE lower(fc_question) = lower(some_q);
END;
/
--THINGS TO NOTE: Use DECLARE to set up a block where variables can be declared
--then used within the following BEGIN block.
DECLARE
    inputs varchar2(2000);
    answer varchar2(2000);
BEGIN
    inputs := 'What is Java';
    get_answer(inputs, answer);
    DBMS_OUTPUT.PUT_LINE(answer);
END;
/

/*
    CURSORS!
    -A cursor is essentially like a pointer to a table or view.
    -We can use them to iterate through entire queries from the database.
    -When we want to pass entire tables or queries, we need to use cursors.

    NOTE: PL/SQL offers a CURSOR and a SYS_REFCURSOR
    The SYS_REFCURSOR is a stronger cursor (Therefore more costly) that is allowed
    to be passed outside of the scope of the procedure it's in. A normal CURSOR must be
    created and die within the scope of the procedure that it was created in.
*/

CREATE OR REPLACE PROCEDURE get_all_fc(cursorParam OUT SYS_REFCURSOR)
IS
BEGIN
    open cursorParam FOR
    SELECT * FROM flash_cards;
END;

DECLARE
    fc_cursor SYS_REFCURSOR;
    someID flash_cards.fc_id%TYPE;
    someQ varchar2(4000);
    someA varchar2(4000);
BEGIN
    get_all_fc(fc_cursor); --Have our cursor represent the cursor for flash_cards

    LOOP --Begin loop block
        FETCH fc_cursor INTO someID,someQ,someA; --Grab the current its pointing at.
        EXIT WHEN fc_cursor%NOTFOUND; --%NOTFOUND does not exist until there are no records left
        DBMS_OUTPUT.PUT_LINE(someID || ' ' || someQ || ' ' || someA);
    END LOOP; --End of loop block
END;

/*
    A function differs from a stored procedure in the following ways:
    A stored procedure does NOT have to return anything
    A stored procedure CAN have as many IN/OUT parameters as it wants.
    A stored procedure can alter the database such as insert, delte, etc.
    A stored procedure can NOT be called mid query. why?
    A stored procedure can call other stored procedures within it.
    A stored procedure can call functions.

    A function MUST return ONE and only ONE thing.
    A function CAN use OUT parameters, but this is highly advised against.
    A function can NOT perform database operations.
    A function CAN be called mid query.
    A function can call other functions.
    A function cannot call a stored procedure.
*/

CREATE OR REPLACE FUNCTION get_max_id
RETURN NUMBER
IS
    max_id NUMBER;
BEGIN 
    SELECT max(fc_id) INTO max_id from flash_cards;
    RETURN max_id;
END;


DECLARE
    max_id number;
BEGIN
    max_id := get_max_id();
    DBMS_OUTPUT.PUT_LINE(max_id);
END;


DECLARE
    firstNum number;
    secondNum number;
    maxNum number;

    FUNCTION findMax(x IN NUMBER, y IN NUMBER)
    RETURN NUMBER
    IS
        z NUMBER;
    BEGIN
        IF x > y THEN
            z := x;
        ELSE
            z := y;
        END IF;

        RETURN z;
    END;
BEGIN
    firstNum := 87;
    secondNum := 23;
    maxNum := findMax(firstNum, secondNum);
    DBMS_OUTPUT.PUT_LINE(maxNum);
END;


--EXCEPTION HANDLING EXAMPLE
CREATE OR REPLACE PROCEDURE exceptionExample
IS
    CURSOR badCurse IS
        select * from flash_cards;
    fid flash_cards.fc_id%TYPE;
    fquestion flash_cards.fc_question%TYPE;
    fanswer flash_cards.fc_answer%TYPE;
BEGIN
    OPEN badCURSE;
    LOOP
        FETCH badCurse into fid, fquestion, fanswer;
        EXIT WHEN badCurse%NOTFOUND;
    END LOOP;
    CLOSE badCurse;
    fid := 1/0;

    EXCEPTION
        WHEN invalid_cursor THEN
            dbms_output.put_line('CURSOR ERROR');
        WHEN ZERO_DIVIDE THEN
            dbms_output.put_line('DIVISION BY ZERO ERROR');
END;

call exceptionExample();
