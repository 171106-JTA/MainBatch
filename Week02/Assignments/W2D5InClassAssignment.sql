CREATE OR REPLACE PROCEDURE fetch_all_flashcards(flashcard_cursor OUT SYS_REFCURSOR)
IS
BEGIN
    OPEN flashcard_cursor for
     SELECT * FROM flash_cardS;
END;
/

-- Procedure used befor converting to a Statment for the second part of the problem
--CREATE OR REPLACE PROCEDURE update_by_id(the_id IN NUMBER, question IN VARCHAR, answer IN VARCHAR)
--IS
--BEGIN
--    UPDATE flash_cards
--    set FC_QUESTION = question, FC_ANSWER = answer
--    where FC_ID = the_id;
--END;


SELECT * FROM FLASH_CARDS;

--Test fetch_all_flashcards
--From Ryan's Thursday code
DECLARE
    FC_CURSOR SYS_REFCURSOR;
    SOMEID FLASH_CARDS.FC_ID%TYPE;
    SOMEQ VARCHAR2(4000); 
    SOMEA VARCHAR2(4000);
BEGIN
    fetch_all_flashcards(FC_CURSOR);
    
    LOOP
        FETCH FC_CURSOR INTO SOMEID, SOMEQ, SOMEA; --Grab the current recods its pointing at
        EXIT WHEN FC_CURSOR%NOTFOUND; --%NOTFOUND does not exist until therer are no records left. 
        DBMS_OUTPUT.PUT_LINE(SOMEID || ' ' || SOMEQ || ' ' || SOMEA);
    END LOOP;
END;