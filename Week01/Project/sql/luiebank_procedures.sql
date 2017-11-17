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
 *  Queries database for id associated with supplied arguments 
 *  @param code
 *  @param value
 *  @param desc (optional)
 */
CREATE OR REPLACE PROCEDURE get_code_list_id(code IN MB_CODE_LIST.code%TYPE, value IN MB_CODE_LIST.value%TYPE, description IN MB_CODE_LIST.description%TYPE,
    code_list_id OUT MB_CODE_LIST.id%TYPE)
    IS
    missing_params EXCEPTION;
    PRAGMA EXCEPTION_INIT(missing_params, -20001);
BEGIN
    IF code IS NULL OR value IS NULL THEN
        RAISE_APPLICATION_ERROR(-20001, 'Missing required parameters<code or value>');
    END IF;

    IF description IS NULL THEN
        SELECT l.id INTO CODE_LIST_ID FROM MB_CODE_LIST l WHERE l.code=UPPER(code) AND l.value = UPPER(value) AND l.description = UPPER(description) AND rownum = 1;
    ELSE
        SELECT l.id INTO CODE_LIST_ID FROM MB_CODE_LIST l WHERE l.code=UPPER(code) AND l.value = UPPER(value) AND rownum = 1;
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
 *  Acquires id of specified user roll (see MB_CODE_LIST.code='USER-ROLE')
 *  @param role - desired user role
 *  @param role_id - id of desired role
 */
CREATE OR REPLACE PROCEDURE get_user_role_id(role IN MB_CODE_LIST.value%TYPE, role_id OUT MB_CODE_LIST.id%TYPE)
    IS
BEGIN
    get_code_list_id('USER-ROLE', role, NULL, role_id);
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
 *  Creates user account
 *  @param role - user role see where code='USER-ROLE'
 *  @param status - user status see where code='USER-STATUS'
 *  @param username - user account name
 *  @param password - what is used to validate user
 *  @param first_name - users first name
 *  @param last_name - users last name
 *  @param email - user email account
 *  @param phone_number - user personal phonenumber
 *  @param address_1 - where they live (primary)
 *  @param address_2 - second place of residency (can be null)
 *  @param postal_code - zip code
 *  @param state - name of state of where user resides 
 *  @param city - name of city where user resides 
 */
CREATE OR REPLACE PROCEDURE create_user (role IN MB_CODE_LIST.value%TYPE, status IN MB_CODE_LIST.value%TYPE, username IN MB_USER.USERNAME%TYPE, password IN MB_USER.PASSWORD%TYPE, 
                                         ssn IN MB_USER_INFO.ssn%TYPE, first_name IN MB_USER_INFO.first_name%TYPE, last_name IN MB_USER_INFO.last_name%TYPE,
                                         email IN MB_USER_INFO.email%TYPE, phone_number IN MB_USER_INFO.phone_number%TYPE, address_1 IN MB_USER_INFO.address_1%TYPE, 
                                         address_2 IN MB_USER_INFO.address_2%TYPE,  postal_code IN MB_USER_INFO.postal_code%TYPE, state IN MB_CODE_LIST.code%TYPE,
                                         city IN MB_CODE_LIST.code%TYPE)
    IS
     user_id NUMBER;
     user_role_id NUMBER;
     user_status_id NUMBER;
     user_state_city_id NUMBER;
BEGIN
    -- CREATE SAVE POINT FOR NEW USE
    SAVEPOINT new_user;
   
    -- create user
    INSERT INTO MB_USER (USERNAME, PASSWORD)VALUES(username,password);
    
    -- get user data
    SELECT id INTO user_id FROM  (SELECT u.id FROM MB_USER u WHERE u.username = username AND rownum = 1);
    get_user_role_id(role, user_role_id);
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
                       user_status_id,
                       user_role_id);
                       
    -- Save changes 
    COMMIT;
    
    -- If any error occured, then go back to prev state
    EXCEPTION     
        WHEN OTHERS THEN 
            DBMS_OUTPUT.PUT_LINE(SQLERRM);
            ROLLBACK TO new_user;
END;
/

CREATE OR REPLACE PROCEDURE create_account(user_id IN MB_USER.id%TYPE)
IS
BEGIN
    SAVEPOINT new_account;
    
    
    
    -- Save changes 
    COMMIT;
    
    -- If any error occured, then go back to prev state
    EXCEPTION
        WHEN OTHERS THEN 
            DBMS_OUTPUT.PUT_LINE(SQLERRM);
            ROLLBACK TO new_account;
END;









