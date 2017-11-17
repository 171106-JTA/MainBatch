CREATE OR REPLACE PROCEDURE update_flashcard(update_id IN NUMBER, new_question IN VARCHAR2, new_answer IN VARCHAR2)
IS
BEGIN
    UPDATE Flash_cards SET fc_question = new_question, fc_answer = new_answer
    WHERE fc_id = update_id;
END;