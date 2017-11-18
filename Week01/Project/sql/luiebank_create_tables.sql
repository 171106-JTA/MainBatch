/**
 *  Author: Antony Lulciuc
 *  Create fresh start when running script
 *  - Removes All prior data from system
 *  - Initializes All tables used in system
 */
DROP TABLE MB_USER_INFO CASCADE CONSTRAINTS;
DROP TABLE MB_CODE_LIST CASCADE CONSTRAINTS;
DROP TABLE MB_USER CASCADE CONSTRAINTS;
DROP TABLE MB_JOINT_ACCOUNT CASCADE CONSTRAINTS;
DROP TABLE MB_BANK_ACCOUNT CASCADE CONSTRAINTS;
DROP TABLE MB_CREDIT_ACCOUNT CASCADE CONSTRAINTS;
DROP TABLE MB_SAVINGS_ACCOUNT CASCADE CONSTRAINTS;
DROP TABLE MB_RECEIPT CASCADE CONSTRAINTS;
DROP TABLE MB_INVOICE CASCADE CONSTRAINTS;
DROP TABLE MB_RATE CASCADE CONSTRAINTS;
DROP TABLE MB_ACCOUNT CASCADE CONSTRAINTS;

CREATE TABLE MB_CODE_LIST (
    id NUMBER,
    code VARCHAR2(32) NOT NULL,
    value VARCHAR2(32) NOT NULL,
    description VARCHAR2(250) NULL,
    CONSTRAINT pk_code_id PRIMARY KEY (id),
    CONSTRAINT unique_code UNIQUE (code,value,description)
);

CREATE TABLE MB_USER (
    id NUMBER,
    username VARCHAR2(32) NOT NULL,
    password VARCHAR2(32) NOT NULL,
    CONSTRAINT pk_user_id PRIMARY KEY (id),
    CONSTRAINT unique_username UNIQUE (username)
);

CREATE TABLE MB_USER_INFO (
    ssn NUMBER (9),
    user_id NUMBER,
    first_name VARCHAR2(24) NOT NULL,
    last_name VARCHAR2(24) NOT NULL,
    email VARCHAR2(50) NOT NULL,
    phone_number VARCHAR2(11) NOT NULL,
    state_city_id NUMBER NOT NULL,
    address_1 VARCHAR2(50) NOT NULL,
    address_2 VARCHAR2(50) NULL,
    postal_code NUMBER NOT NULL,
    status_id NUMBER,
    role_id NUMBER,
    CONSTRAINT pk_ssn PRIMARY KEY (ssn),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES MB_USER(id),
    CONSTRAINT fk_state_city_id FOREIGN KEY (state_city_id) REFERENCES MB_CODE_LIST(id),
    CONSTRAINT fk_user_status_id FOREIGN KEY (status_id) REFERENCES MB_CODE_LIST(id),
    CONSTRAINT fk_user_role_id FOREIGN KEY (role_id) REFERENCES MB_CODE_LIST(id)
);

CREATE TABLE MB_ACCOUNT (
    account_number VARCHAR2(32),
    user_id NUMBER,
    status_id NUMBER,
    type_id NUMBER,
    created VARCHAR2(32),
    CONSTRAINT pk_account_number PRIMARY KEY (account_number),
    CONSTRAINT fk_account_user_id FOREIGN KEY (user_id) REFERENCES MB_USER(id),
    CONSTRAINT fk_account_status FOREIGN KEY (status_id) REFERENCES MB_CODE_LIST(id),
    CONSTRAINT fk_account_type FOREIGN KEY (type_id) REFERENCES MB_CODE_LIST(id),
    CONSTRAINT check_create_date CHECK(created IS NOT NULL)
);

CREATE TABLE MB_BANK_ACCOUNT (
    account_number VARCHAR2(32),
    balance NUMBER(32, 2) DEFAULT 0,
    CONSTRAINT fk_account_number FOREIGN KEY (account_number) REFERENCES MB_ACCOUNT(account_number),
    CONSTRAINT check_balance CHECK (balance >= 0.0)
);

CREATE TABLE MB_CREDIT_ACCOUNT(
    account_number VARCHAR2(32),
    credit_limit NUMBER(32,2) DEFAULT 0,
    minimal_payment_due NUMBER(32,2) DEFAULT 0,
    rate_id NUMBER,
    CONSTRAINT fk_bank_credit_number FOREIGN KEY (account_number) REFERENCES MB_ACCOUNT(account_number),
    CONSTRAINT fk_credit_rate_id FOREIGN KEY (rate_id) REFERENCES MB_CODE_LIST(id)
);

CREATE TABLE MB_SAVINGS_ACCOUNT (
    account_number VARCHAR2(32),
    rate_id NUMBER,
    CONSTRAINT fk_bank_savings_number FOREIGN KEY (account_number) REFERENCES MB_ACCOUNT(account_number),
    CONSTRAINT fk_savings_rate_id FOREIGN KEY (rate_id) REFERENCES MB_CODE_LIST(id)
);

CREATE TABLE MB_JOINT_ACCOUNT(
    user_id_1 NUMBER,
    user_id_2 NUMBER,
    account_number VARCHAR2(32),
    CONSTRAINT fk_user_id_1 FOREIGN KEY (user_id_1) REFERENCES MB_USER(id),
    CONSTRAINT fk_user_id_2 FOREIGN KEY (user_id_2) REFERENCES MB_USER(id),
    CONSTRAINT fk_joint_bank_number FOREIGN KEY (account_number) REFERENCES MB_ACCOUNT(account_number)
);

CREATE TABLE MB_INVOICE (
    id NUMBER,
    account_number VARCHAR2(32),
    date_issued VARCHAR2(32) NOT NULL,
    payment_date VARCHAR2(32),
    amount_due NUMBER(32,2) DEFAULT 0.0,
    CONSTRAINT pk_invoice_id PRIMARY KEY (id),
    CONSTRAINT fk_invoice_account_number FOREIGN KEY (account_number) REFERENCES MB_ACCOUNT(account_number),
    CONSTRAINT check_amount_due CHECK (amount_due >= 0.0),
    CONSTRAINT check_date_issued CHECK(date_issued IS NOT NULL),
    CONSTRAINT check_payment_date CHECK(payment_date IS NOT NULL)
);

CREATE TABLE MB_RECEIPT (
    id NUMBER,
    invoice_id NUMBER,
    amount_paid NUMBER(32,2) DEFAULT 0,
    date_paid VARCHAR2(32),
    CONSTRAINT pk_receipt_id PRIMARY KEY (id),
    CONSTRAINT fk_invoice_id FOREIGN KEY (invoice_id) REFERENCES MB_INVOICE(id),
    CONSTRAINT check_payment_amount CHECK(amount_paid >= 0.0),
    CONSTRAINT check_date_paid CHECK(date_paid IS NOT NULL)
);
