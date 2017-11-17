/**
 *  Author: Antony Lulciuc
 *  Defines all triggers assigned to system tables 
 */
DROP SEQUENCE mb_seq_user_id_counter;
DROP SEQUENCE mb_seq_rate_id_counter;
DROP SEQUENCE mb_seq_account_number_counter;
DROP SEQUENCE mb_seq_code_list_id_counter;
DROP SEQUENCE mb_seq_invoice_id_counter;
DROP SEQUENCE mb_seq_receipt_id_counter;
DROP TRIGGER mb_increment_user_id;
DROP TRIGGER mb_increment_rate_id;
DROP TRIGGER mb_increment_account_number;
DROP TRIGGER mb_increment_code_list_id;
DROP TRIGGER mb_increment_invoice_id;
DROP TRIGGER mb_increment_receipt_id;
DROP TRIGGER mb_invoice_date;
DROP TRIGGER mb_receipt_date;

CREATE SEQUENCE mb_seq_user_id_counter 
    START WITH 1
    INCREMENT BY 1;
/

CREATE SEQUENCE mb_seq_rate_id_counter 
    START WITH 1
    INCREMENT BY 1;
/

CREATE SEQUENCE mb_seq_account_number_counter
    START WITH 1
    INCREMENT BY 1;
/

CREATE SEQUENCE mb_seq_code_list_id_counter
    START WITH 1
    INCREMENT BY 1;
/

CREATE SEQUENCE mb_seq_invoice_id_counter 
    START WITH 1
    INCREMENT BY 1;
/    

CREATE SEQUENCE mb_seq_receipt_id_counter
    START WITH 1
    INCREMENT BY 1;
/



-- TRIGGERS

CREATE OR REPLACE TRIGGER mb_increment_user_id 
    BEFORE INSERT ON mb_user
    FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN 
        SELECT mb_seq_user_id_counter.nextval INTO :new.id FROM dual;
    END IF;
END;    
/

CREATE OR REPLACE TRIGGER mb_increment_rate_id
    BEFORE INSERT ON mb_user
    FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT mb_seq_rate_id_counter.nextval INTO :new.id FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER mb_increment_account_number
    BEFORE INSERT ON mb_account
    FOR EACH ROW
BEGIN
    IF :new.account_number IS NULL THEN
        SELECT mb_seq_account_number_counter.nextval INTO :new.account_number FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER mb_increment_code_list_id
    BEFORE INSERT ON mb_code_list
    FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT mb_seq_code_list_id_counter.nextval INTO :new.id FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER mb_increment_invoice_id 
    BEFORE INSERT ON mb_invoice
    FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN 
        SELECT mb_seq_invoice_id_counter.nextval INTO :new.id FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER mb_increment_receipt_id
    BEFORE INSERT ON mb_receipt
    FOR EACH ROW 
BEGIN
    IF :new.id IS NULL THEN 
        SELECT mb_seq_receipt_id_counter.nextval INTO :new.id FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER mb_invoice_date 
    BEFORE INSERT ON mb_invoice
    FOR EACH ROW
BEGIN
    IF :new.date_issued IS NULL THEN
        :new.date_issued := GET_DATE(0);
    END IF;
    IF :new.payment_date IS NULL THEN 
        :new.payment_date := GET_DATE(1);
    END IF;
END;
/

CREATE OR REPLACE TRIGGER mb_receipt_date
    BEFORE INSERT ON mb_receipt 
    FOR EACH ROW
BEGIN
    IF :new.date_paid IS NULL THEN 
        :new.date_paid := GET_DATE(0);
    END IF;
END;
/







