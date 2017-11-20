    SELECT usernames, passwords FROM (
        SELECT * FROM  Credentials WHERE usernames = 'test' AND passwords = 'script'
    ) T1 JOIN Accounts ON T1.CREDENTIALS_ID = ACCOUNTS.CREDENTIALS_ID AND ACCOUNTS.ACCOUNT_TYPE_ID = 'id' -- get any matching credentials
    JOIN Account_Type T2 ON ACCOUNTS.ACCOUNT_TYPE_ID = T2.ACCOUNT_TYPE_ID;

