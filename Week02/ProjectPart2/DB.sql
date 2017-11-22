--dropping the tables for good measure
DROP TABLE Admins CASCADE CONSTRAINTS;
DROP TABLE States CASCADE CONSTRAINTS;
DROP TABLE Users CASCADE CONSTRAINTS;
DROP TABLE Accounts CASCADE CONSTRAINTS;

--creating the tables
CREATE TABLE States(
    state_ID    NUMBER PRIMARY KEY,
    state_name  VARCHAR(10)
    
);
/

CREATE TABLE Users(
    user_ID     NUMBER PRIMARY KEY,
    user_name   VARCHAR2(20),
    user_pass   VARCHAR2(64) NOT NULL,
    state_ID    NUMBER NOT NULL, -- a reference to the user states table
    --add foreign keys to this table
    CONSTRAINT fk_state_ID FOREIGN KEY (state_ID) REFERENCES States(state_ID)
);
/

CREATE TABLE Admins(
    admin_ID    NUMBER PRIMARY KEY NOT NULL,
    user_ID     NUMBER NOT NULL, -- a reference to the user table (should we make this not null?)
    CONSTRAINT fk_user_id FOREIGN KEY (user_ID)  REFERENCES Users(user_ID)
);
/

CREATE TABLE Accounts(
    account_ID  NUMBER PRIMARY KEY NOT NULL,
    user_ID     NUMBER NOT NULL,
    account_balance NUMBER(9,2) DEFAULT 0 NOT NULL,
    account_creation_date   DATE DEFAULT CURRENT_TIMESTAMP
);
/

--putting auto-increment on all the tables...
--...first, we create all the sequences...
--...dropping the all first for good measure...
DROP SEQUENCE users_seqence;
DROP SEQUENCE admins_sequence;
DROP SEQUENCE states_sequence;
DROP SEQUENCE accounts_sequence;
--...now we actually create the seqences....
CREATE SEQUENCE users_sequence
    START WITH 1
    INCREMENT BY 1;
CREATE SEQUENCE admins_sequence
    START WITH 1
    INCREMENT BY 1;
CREATE SEQUENCE states_sequence
    START WITH 1
    INCREMENT BY 1;
CREATE SEQUENCE accounts_sequence
    START WITH 1
    INCREMENT BY 1;
/
--...next, we create the triggers
CREATE OR REPLACE TRIGGER users_auto_increment
    BEFORE INSERT ON users
        FOR EACH ROW
            BEGIN
                IF :new.user_id IS NULL THEN
                    SELECT users_sequence.nextval INTO :new.user_id FROM dual;
                END IF;
            END;
/
CREATE OR REPLACE TRIGGER admins_auto_increment
    BEFORE INSERT ON admins
        FOR EACH ROW
            BEGIN
                IF :new.admin_id IS NULL THEN
                    SELECT admins_sequence.nextval INTO :new.admin_id FROM dual;
                END IF;
            END;
/
CREATE OR REPLACE TRIGGER states_auto_increment
    BEFORE INSERT ON states
        FOR EACH ROW
            BEGIN
                IF :new.state_id IS NULL THEN
                    SELECT states_sequence.nextval INTO :new.state_id FROM dual;
                END IF;
            END;
/
CREATE OR REPLACE TRIGGER accounts_auto_increment
    BEFORE INSERT ON accounts
        FOR EACH ROW
            BEGIN
                IF :new.account_id IS NULL THEN
                    SELECT accounts_sequence.nextval INTO :new.account_id FROM dual;
                END IF;
            END;
/
--let's insert some states:
--• active
--• locked
--• banned
--• flagged
INSERT INTO states(state_name) VALUES ('active');
INSERT INTO states(state_name) VALUES ('locked');
INSERT INTO states(state_name) VALUES ('flagged');
INSERT INTO states(state_name) VALUES ('banned');
/
--Let's insert a base admin into the table 
INSERT INTO users(user_id, user_name, user_pass, state_id) VALUES (0, 'DefaultAdmin', sha256.encrypt('SuperUser'), 1);
INSERT INTO admins(user_id) VALUES (0);
/
--Now, let's create some views...
DROP VIEW users_view;
CREATE VIEW users_view AS 
    SELECT u.user_id, u.user_name, u.user_pass, s.state_name FROM users u
        INNER JOIN states s
            USING(state_id)
;
/
DROP VIEW users_accounts_view;
CREATE VIEW users_accounts_view AS
    SELECT * FROM users_view
        INNER JOIN accounts 
            USING (User_ID)
;
/

DROP VIEW admins_view;
CREATE VIEW admins_view AS
    SELECT a.admin_id, u.user_name, u.user_pass, s.state_name FROM admins a
    INNER JOIN users u
        USING (user_id)
    INNER JOIN states s 
        USING (state_id);
/

DROP VIEW base_users_view;
CREATE VIEW base_users_view AS
    SELECT * FROM users_view WHERE user_id NOT IN (SELECT user_id FROM admins);
/
--let's write some stored procedures and functions
CREATE OR REPLACE FUNCTION get_state_id_for_name(state states.state_name%TYPE)
RETURN NUMBER
IS
    id NUMBER;
BEGIN
    SELECT state_id INTO id FROM states WHERE state_name = state;
    return id;
END;
/

--this function takes username,plaintext password and returns the id to the user having those credentials
CREATE OR REPLACE FUNCTION find_user_by_credentials(username IN users.user_name%TYPE, pass IN users.user_pass%TYPE)
RETURN NUMBER
IS
    found_id NUMBER := -1;
BEGIN
    SELECT user_id INTO found_id FROM users
        WHERE user_name  = username AND user_pass = sha256.encrypt(pass);
    return found_id;
    EXCEPTION
        WHEN NO_DATA_FOUND
            THEN return -1;
END;
/

CREATE OR REPLACE PROCEDURE get_users_by_state(state IN states.state_name%TYPE)
IS 
BEGIN
    SELECT * FROM users_view WHERE state_name = state;
END;
/

CREATE OR REPLACE PROCEDURE create_user(username IN users.user_name%TYPE,
    pass IN users.user_pass%TYPE,
    state IN states.state_name%TYPE)
IS
BEGIN
    INSERT INTO users (user_name, user_pass, state_id)
        VALUES (
            username,
            sha256.encrypt(pass),
            get_state_id_for_name(state)
        );
END;
/

CREATE OR REPLACE PROCEDURE update_user(username IN users.user_name%TYPE, pass IN users.user_pass%TYPE, state IN states.state_name%TYPE)
IS 
BEGIN
    UPDATE users
        SET user_pass = sha256.encrypt(pass),
            state_id  = (SELECT state_id FROM states WHERE state_name = state)
        WHERE user_name = username;
    commit;
        
END;
/

CREATE OR REPLACE PROCEDURE update_user(id IN NUMBER, 
    username IN users.user_name%TYPE, 
    pass IN users.user_pass%TYPE, 
    state IN states.state_name%TYPE)
IS
BEGIN
    UPDATE users
        SET user_name = username,
            user_pass = sha256.encrypt(pass),
            state_id  = (SELECT state_id FROM states WHERE state_name = state)
        WHERE user_id = id;
    commit;
END;
/

CREATE OR REPLACE PROCEDURE change_user_state_by_id(id IN NUMBER, new_state IN states.state_name%TYPE)
IS 
BEGIN
    UPDATE users 
        SET state_id = get_state_id_for_name(new_state)
        WHERE user_id = id;
END;
/

CREATE OR REPLACE PROCEDURE activate_by_id(id IN NUMBER)
IS
BEGIN
    change_user_state_by_id(id, 'active');
END;
/

CREATE OR REPLACE PROCEDURE lock_by_id(id IN NUMBER)
IS
BEGIN
    change_user_state_by_id(id, 'locked');
END;
/

CREATE OR REPLACE PROCEDURE flag_by_id(id IN NUMBER)
IS
BEGIN
    change_user_state_by_id(id, 'flagged');
END;
/

CREATE OR REPLACE PROCEDURE ban_by_id(id IN NUMBER)
IS
BEGIN
    change_user_state_by_id(id, 'banned');
END;
/
create or replace PROCEDURE promote_by_username(username IN users.user_name%TYPE)
IS 
    found_admin_id NUMBER;
    userID NUMBER;
BEGIN
    -- get the user id for the user specified by username
    SELECT user_id INTO userID FROM users WHERE user_name = username;
    -- try to find the user in the admins view
    SELECT admin_id INTO found_admin_id FROM admins_view WHERE user_name = username;
    -- if we didn't find any user in that view, then
    IF found_admin_id IS NULL THEN
        -- add them to the admins table
        INSERT INTO admins(user_id) VALUES (userID);
        commit;
    END IF;
END;
/

--if we have a promote_by_username, we ought to have a demote_by_username!
CREATE OR REPLACE PROCEDURE demote_by_username(username IN users.user_name%TYPE)
IS
BEGIN
    DELETE FROM admins
        WHERE admin_id = (SELECT admin_id FROM admins_view WHERE user_name = username);
    
END;
/

--having an account created for a user if they don't have one yet
CREATE OR REPLACE TRIGGER create_default_account
    AFTER INSERT ON users
        FOR EACH ROW
            BEGIN
                INSERT INTO accounts(user_id)
                    SELECT (:new.user_id) FROM dual
                    WHERE NOT EXISTS (SELECT * FROM Users_Accounts_View WHERE user_id = :new.user_id);
                commit;
            END;
/