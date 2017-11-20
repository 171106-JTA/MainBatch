/**
 *  Author: Antony Lulciuc
 *  Defines all system procedures 
 */
 
 /**
  * Acquires current date, can also  add months to current date
  * @param padding - pushes calendar forward by x months
  */
CREATE OR REPLACE FUNCTION get_date (padding IN NUMBER)
    RETURN VARCHAR2 
IS
   date_timestamp VARCHAR2(32);
BEGIN
   SELECT TO_CHAR (ADD_MONTHS(SYSDATE, padding),'YYYY-MM-DD HH24:MI:SS') INTO date_timestamp FROM DUAL;
   RETURN date_timestamp;
END;
/

/**
 *  Queries database for id associated with user credentials 
 *  @param my_username - users username
 *  @param my_password - users password
 *  @param user_id - id found for credentials supplied 
 */
CREATE OR REPLACE PROCEDURE get_user_id(my_username IN MB_USER.username%TYPE, my_password IN MB_USER.PASSWORD%TYPE, user_id OUT MB_USER.id%TYPE)
    IS
BEGIN
    SELECT id INTO user_id FROM MB_USER WHERE username=my_username AND password=my_password;
END;
/


/**
 *  Queries database for id associated with supplied arguments 
 *  @param code
 *  @param value
 *  @param desc (optional)
 */
CREATE OR REPLACE PROCEDURE get_code_list_id(my_code IN MB_CODE_LIST.code%TYPE, my_value IN MB_CODE_LIST.value%TYPE, my_description IN MB_CODE_LIST.description%TYPE,
    code_list_id OUT MB_CODE_LIST.id%TYPE)
IS
    CURSOR with_desc IS SELECT l.id FROM MB_CODE_LIST l WHERE l.code=UPPER(my_code) AND l.value = UPPER(my_value) AND l.description = UPPER(my_description);
    CURSOR without_desc IS SELECT l.id FROM MB_CODE_LIST l WHERE l.code=UPPER(my_code) AND l.value = UPPER(my_value);
    missing_params EXCEPTION;
    PRAGMA EXCEPTION_INIT(missing_params, -20001);
BEGIN
    IF my_code IS NULL OR my_value IS NULL THEN
        RAISE_APPLICATION_ERROR(-20001, 'Missing required parameters<code or value>');
    END IF;

    IF my_description IS NULL THEN
        DBMS_OUTPUT.PUT_LINE('no desc');
        OPEN without_desc;
        FETCH without_desc INTO code_list_id;
        CLOSE without_desc;
    ELSE
        DBMS_OUTPUT.PUT_LINE('desc');
         OPEN with_desc;
        FETCH with_desc INTO code_list_id;
        CLOSE with_desc;
    END IF;
    
    EXCEPTION
        WHEN missing_params THEN 
            DBMS_OUTPUT.PUT_LINE(SQLERRM);
END;
/

/**
 *  Acquires MB_CODE_LIST.id for code='CITY-CODE-GROUP', with pair state_code as value and city_code as description 
 *  @param state_code - value of MB_CODE_LIST record whose code='US-STATE'
 *  @param city_code - value of MB_CODE_LIST record whose value='CITY-CODE'
 *  @param code_group_id - id of MB_CODE_LIST record with code='CITY-CODE-GROUP', is 0 if failed
 */
CREATE OR REPLACE PROCEDURE get_city_code_group_id (state_code IN MB_CODE_LIST.value%TYPE, city_code IN MB_CODE_LIST.value%TYPE, code_group_id OUT MB_CODE_LIST.id%TYPE)
    IS
BEGIN
    get_code_list_id('CITY-CODE-GROUP', state_code, city_code, code_group_id);
END;
/    

/**
 *  Acquires id of specified user roll (see MB_CODE_LIST.code='USER-STATUS')
 *  @param status - desired user status
 *  @param status_id - id of desired status
 */
CREATE OR REPLACE PROCEDURE get_user_status_id(status IN MB_CODE_LIST.value%TYPE, status_id OUT MB_CODE_LIST.id%TYPE)
    IS
BEGIN
    get_code_list_id('USER-STATUS', status, NULL, status_id);
END;
/

/**
 *  Acquires id of specified account status (see MB_CODE_LIST.code='ACCOUNT-STATUS')
 *  @param status - desired account status
 *  @param status_id - id of desired status
 */
CREATE OR REPLACE PROCEDURE get_account_status_id(status IN MB_CODE_LIST.value%TYPE, status_id OUT MB_CODE_LIST.id%TYPE)
    IS
BEGIN
    get_code_list_id('ACCOUNT-STATUS', status, NULL, status_id);
END;
/

/**
 *  Acquires id of specified account type (see MB_CODE_LIST.code='ACCOUNT-TYPE')
 *  @param account_type - desired account type
 *  @param type_id - id of desired type
 */
CREATE OR REPLACE PROCEDURE get_account_type_id(account_type IN MB_CODE_LIST.value%TYPE, type_id OUT MB_CODE_LIST.id%TYPE)
    IS
BEGIN
    get_code_list_id('ACCOUNT-TYPE', account_type, NULL, type_id);
END;
/

/** 
 *  Creates user account
 *  @param status - user status see where code='USER-STATUS'
 *  @param username - user account name
 *  @param password - what is used to validate user
 *  @param ssn
 *  @param first_name - users first name
 *  @param last_name - users last name
 *  @uSERparam email - user email account
 *  @param phone_number - user personal phonenumber
 *  @param address_1 - where they live (primary)
 *  @param address_2 - second place of residency (can be null)
 *  @param postal_code - zip code
 *  @param state - name of state of where user resides 
 *  @param city - name of city where user resides 
 */
CREATE OR REPLACE PROCEDURE create_user (status IN MB_CODE_LIST.value%TYPE, my_username IN MB_USER.USERNAME%TYPE, password IN MB_USER.PASSWORD%TYPE, 
                                         ssn IN MB_USER_INFO.ssn%TYPE, first_name IN MB_USER_INFO.first_name%TYPE, last_name IN MB_USER_INFO.last_name%TYPE,
                                         email IN MB_USER_INFO.email%TYPE, phone_number IN MB_USER_INFO.phone_number%TYPE, address_1 IN MB_USER_INFO.address_1%TYPE, 
                                         address_2 IN MB_USER_INFO.address_2%TYPE,  postal_code IN MB_USER_INFO.postal_code%TYPE, state IN MB_CODE_LIST.code%TYPE,
                                         city IN MB_CODE_LIST.code%TYPE)
    IS
     CURSOR c_user_id IS (SELECT u.id FROM MB_USER u WHERE u.username = my_username);
     user_id NUMBER;
     user_status_id NUMBER;
     user_state_city_id NUMBER;
BEGIN
    -- CREATE SAVE POINT FOR NEW USE
    SAVEPOINT new_user;
   
    -- create user
    INSERT INTO MB_USER (USERNAME, PASSWORD)VALUES(my_username,password);
    
    -- get user data
    OPEN c_user_id;
    FETCH c_user_id INTO user_id;
    CLOSE c_user_id;
    
    DBMS_OUTPUT.PUT_LINE(my_username);
    DBMS_OUTPUT.PUT_LINE(user_id);
    
    get_user_status_id(status, user_status_id);
    get_city_code_group_id(state, city, user_state_city_id);
    
    -- Generate User Information data
    INSERT INTO MB_USER_INFO 
                VALUES(ssn, 
                       user_id,
                       first_name, 
                       last_name,
                       email,
                       phone_number, 
                       user_state_city_id,  
                       address_1, 
                       address_2, 
                       postal_code,
                       user_status_id);
                       
    -- Save changes 
    COMMIT;
    
    -- If any error occured, then go back to prev state
    EXCEPTION     
        WHEN OTHERS THEN 
            DBMS_OUTPUT.PUT_LINE(SQLERRM);
            ROLLBACK TO new_user;
END;
/

/**
 *
 */
CREATE OR REPLACE PROCEDURE create_account(my_user_id IN MB_USER.id%TYPE, account_status IN MB_CODE_LIST.value%TYPE, account_type IN MB_CODE_LIST.value%TYPE,
                                rate_level IN MB_CODE_LIST.value%TYPE)
IS
    account_id MB_ACCOUNT.account_number%TYPE;
    creation_date VARCHAR2(32) := get_date(0);
    pending_id NUMBER;
    account_type_id NUMBER;
    rate_id NUMBER;
    CURSOR c_account_id 
        IS SELECT account_number 
            FROM MB_ACCOUNT 
            WHERE user_id = my_user_id AND 
                  status_id = pending_id AND 
                  created = creation_date;
BEGIN
    SAVEPOINT new_account;
    
    -- get account meta data 
    get_account_status_id(account_status, pending_id);
    get_account_type_id(account_type, account_type_id);
    
    -- create new account 
    INSERT INTO MB_ACCOUNT (USER_ID, STATUS_ID, TYPE_ID, CREATED)
        VALUES(my_user_id, pending_id, account_type_id, creation_date);
    
    -- Get created account id
    OPEN c_account_id;
    FETCH c_account_id INTO account_id;
    CLOSE c_account_id;
    
     DBMS_OUTPUT.PUT_LINE(account_type);
    IF UPPER(account_type) = 'CHECKING' THEN 
        DBMS_OUTPUT.PUT_LINE(account_id);
       INSERT INTO MB_BANK_ACCOUNT (ACCOUNT_NUMBER, BALANCE)VALUES(account_id, 0.0);
    ELSE 
        IF UPPER(account_type) = 'CREDIT' THEN 
            IF rate_level IS NULL THEN
                -- acquire default rate 
                get_code_list_id('CREDIT-RATE', 'NORMAL', NULL, rate_id);
            ELSE 
                get_code_list_id('CREDIT-RATE', rate_level, NULL, rate_id);
            END IF;
            
            INSERT INTO MB_CREDIT_ACCOUNT(ACCOUNT_NUMBER, RATE_ID) VALUES(account_id, rate_id);
        ELSE 
            IF UPPER(account_type) = 'SAVINGS' THEN
                 IF rate_level IS NULL THEN
                    -- acquire default rate 
                    get_code_list_id('SAVINGS-RATE', 'NORMAL', NULL, rate_id);
                 ELSE 
                    get_code_list_id('SAVINGS-RATE', rate_level, NULL, rate_id);
                 END IF;
                 
                 INSERT INTO MB_SAVINGS_ACCOUNT(ACCOUNT_NUMBER, RATE_ID) VALUES(account_id, rate_id);
            END IF;
        END IF;
    END IF;
    
    
    -- Save changes 
    COMMIT;
    
    -- If any error occured, then go back to prev state
    EXCEPTION
        WHEN OTHERS THEN 
            DBMS_OUTPUT.PUT_LINE(SQLERRM);
            ROLLBACK TO new_account;
END;
/








