// constants used throughout this app
// error constants
const EMPTY_FIELD  = "field is empty",
	NOT_AN_EMAIL   = "not an email address",
	INVALID_NAME   = "name contains invalid characters",
	USER_NOT_FOUND = "user is not in our system";
// email regex constant
const emailPattern = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;