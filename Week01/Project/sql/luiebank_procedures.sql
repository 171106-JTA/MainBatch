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
CREATE OR REPLACE PROCEDURE get_user_id(my_username IN EAR_USER.username%TYPE, my_password IN EAR_USER.PASSWORD%TYPE, user_id OUT EAR_USER.id%TYPE)
    IS
BEGIN
    SELECT id INTO user_id FROM EAR_USER WHERE username=my_username AND password=my_password;
END;
/

/**
 *  Queries database for id associated with supplied arguments 
 *  @param code
 *  @param value
 *  @param desc (optional)
 */
CREATE OR REPLACE PROCEDURE get_code_list_id(my_code IN CODE_LIST.code%TYPE, my_value IN CODE_LIST.value%TYPE, my_description IN CODE_LIST.description%TYPE,
    code_list_id OUT CODE_LIST.id%TYPE)
IS
    CURSOR with_desc IS SELECT l.id FROM CODE_LIST l WHERE l.code=UPPER(my_code) AND l.value = UPPER(my_value) AND l.description = UPPER(my_description);
    CURSOR without_desc IS SELECT l.id FROM CODE_LIST l WHERE l.code=UPPER(my_code) AND l.value = UPPER(my_value);
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
 *  Acquires CODE_LIST.id for code='CITY-CODE-GROUP', with pair state_code as value and city_code as description 
 *  @param state_code - value of CODE_LIST record whose code='US-STATE'
 *  @param city_code - value of CODE_LIST record whose value='CITY-CODE'
 *  @param code_group_id - id of CODE_LIST record with code='CITY-CODE-GROUP', is 0 if failed
 */
CREATE OR REPLACE PROCEDURE get_city_code_group_id (state_code IN CODE_LIST.value%TYPE, city_code IN CODE_LIST.value%TYPE, code_group_id OUT CODE_LIST.id%TYPE)
    IS
BEGIN
    get_code_list_id('CITY-CODE-GROUP', state_code, city_code, code_group_id);
END;
/    

/**
 *  Acquires id of specified user roll (see CODE_LIST.code='USER-STATUS')
 *  @param status - desired user status
 *  @param status_id - id of desired status
 */
CREATE OR REPLACE PROCEDURE get_user_role_id(status IN CODE_LIST.value%TYPE, status_id OUT CODE_LIST.id%TYPE)
    IS
BEGIN
    get_code_list_id('USER-ROLE', status, NULL, status_id);
END;
/

/** 
 *  Creates user account
 *  @param status - user status see where code='USER-STATUS'
 *  @param username - user account name
 *  @param password - what is used to validate user
 *  @param first_name - users first name
 *  @param last_name - users last name
 *  @uSERparam email - user email account
 *  @param phone_number - user personal phonenumber
 *  @param address - where they live (primary)
 *  @param state - name of state of where user resides 
 *  @param city - name of city where user resides 
 */
CREATE OR REPLACE PROCEDURE create_user (status IN CODE_LIST.value%TYPE, my_username IN EAR_USER.username%TYPE, password IN EAR_USER.password%TYPE, 
                                         first_name IN EAR_USER_INFO.first_name%TYPE, lastname IN EAR_USER_INFO.lastname%TYPE, email IN EAR_USER_INFO.email%TYPE, 
                                         phone_number IN EAR_USER_INFO.phonenumber%TYPE, address IN EAR_USER_INFO.address%TYPE, 
                                         state IN CODE_LIST.code%TYPE, city IN CODE_LIST.code%TYPE)
    IS
     CURSOR c_user_id IS (SELECT u.id FROM EAR_USER u WHERE u.username = my_username);
     user_id NUMBER;
     user_role_id NUMBER;
     user_state_city_id NUMBER;
BEGIN
    -- CREATE SAVE POINT FOR NEW USE
    SAVEPOINT new_user;
   
    -- create user
    INSERT INTO EAR_USER(USERNAME, PASSWORD)VALUES(my_username,password);
    
    -- get user data
    OPEN c_user_id;
    FETCH c_user_id INTO user_id;
    CLOSE c_user_id;
    
    DBMS_OUTPUT.PUT_LINE(my_username);
    DBMS_OUTPUT.PUT_LINE(user_id);
    
    get_user_role_id(status, user_role_id);
    get_city_code_group_id(state, city, user_state_city_id);
    
    -- Generate User Information data
    INSERT INTO EAR_USER_INFO (USERID, STATECITYID, FIRSTNAME, LASTNAME,
                               PHONENUMBER, ADDRESS, EMAIL)
                VALUES(user_id, user_state_city_id, first_name,  last_name,
                       phone_number, address, email);
                       
    -- Save changes 
    COMMIT;
    
    -- If any error occured, then go back to prev state
    EXCEPTION     
        WHEN OTHERS THEN 
            DBMS_OUTPUT.PUT_LINE(SQLERRM);
            ROLLBACK TO new_user;
END;
/









