<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Account Page</title>
</head>
<body>
<p>
	Hello, ${currentEm.getFname()}! <br>
	Remaining Reimbursement Funds: ${currentEm.getFunds()}
</p>
<hr>
<a href="form.jsp"><button name="toForm" value="Go To Form"></button></a>
</body>
</html>