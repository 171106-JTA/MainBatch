<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- This will ensure that mobile will work with site by allowing proper formatting when zooming in -->
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Select character encoding support -->
<link rel="stylesheet" type="text/css"
	href="resources/css/default.css">


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JSPs!</title>
</head>
<body>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">JSP EXAMPLE</a>
			</div>
			<ul class="nav navbar-nav">
				<li><a href="index.html">HOME</a></li>
				<li><a href="index.jsp">JSP HOME</a></li>
				<li class="active"><a href="indexJSTL.jsp">JSP JSTL</a></li>
			</ul>
			<c:if test="${user!=null}">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a class="dropdown-toggle" 
												data-toggle="dropdown" 
												href="#">
												<span class="caret"></span>
												<span class="glyphicon glyphicon-user"></span>
												<c:out value="${user}"/></a>
						<ul class="dropdown-menu">
							<li><a href="#">Account Information</a></li>
							<li><a href="#">Site Preferences</a></li>
							<li><a href="#">Stuff</a></li>
						</ul>
					</li>
					<li><a href="Logout.do"><span class="glyphicon glyphicon-log-in"></span>LOGOUT</a></li>
				</ul>
			</c:if>
		</div>
	</nav>
	<div class="container-fluid">
		<div class="well darkfont">
			<h1>Welcome to the JSP page (NOW WITH JSTL)!</h1>
		</div>
		
		<c:choose>
			<c:when test="${sessionScope.user==null}">
				<div class="jumbotron">
					<h2 class="darkfont">PLEASE LOGIN!</h2>
					<!-- SCRIPTLETS: Execute any java code within page.
						executes every time page is loaded. -->
					<c:if test="${issue!=null}">
						<div class="alert alert-danger">INVALID CREDENTIALS</div>
					</c:if>
					<form method="post" action="Login.do">
						<div class="input-group">
							<span class="input-group-addon" style="width:40%"><i class="glyphicon glyphicon-user"></i></span>
							<input type="text" name="user" class="form-control" placeholder="USERNAME" required>
						</div>
						<div class="input-group">
							<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
							<input type="password" name="pass" class="form-control" placeholder="PASSWORD" required>
						</div>
						<div class="darkfont">
							<input type="submit" value="LOGIN">
						</div>
					</form>
				</div>
			</c:when>
			<c:otherwise>
				<div class="well" class="darkfont"> 
					<h1 class="darkfont">Welcome, <c:out value="${fname}"></c:out> </h1>
					<jsp:include page="examples.jsp"></jsp:include>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>