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

<link rel="stylesheet" type="text/css"
	href="resources/css/default.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Examples</title>
</head>
<body class="darkfont">
	<div class=well>
		<h1 class="darkfont">HERE ARE JSTL EXAMPLES</h1>
	</div>
	<h2>Scopes</h2>
	<c:set var="test" scope="page" value="page"></c:set>
	<c:set var="test" scope="request" value="request"></c:set>
	<c:set var="test" scope="session" value="session"></c:set>
	<c:set var="test" scope="application" value="application"></c:set>
	<c:out value="${test}"></c:out>
	<br>
	<c:out value="${pageScope.test}"></c:out>
	<c:out value="${requestScope.test}"></c:out>
	<c:out value="${sessionScope.test}"></c:out>
	<c:out value="${applicationScope.test}"></c:out>
	
	<h2>FOR LOOPS</h2>
	<c:forEach var="j" begin="1" end="5">
		<h4><c:out value="${j}"></c:out> </h4>
	</c:forEach>
	
	<h2>EXCEPTION HANDLING</h2>
	<c:catch var="caughtException">
		<% int i = 1/0; %>
	</c:catch>
	<c:if test="${caughtException!=null}">
		Exception: ${caughtException}<br>
		Info:		${caughtException.message}
	</c:if>
	
	<h2>FUNCTIONS</h2>
	<c:set var="str" value="This is a string!"></c:set>
	<c:if test="${fn:contains(str, 'string')}">
		<c:out value="SUCCESS"></c:out>
	</c:if>
		<!-- 
			other functions:
			-contains()
			-containsIgnoreCase()
			-endsWith()
			-indexOf()
			-trim()
			-startsWith()
			-split()
			-toLowerCase()
			-toUpperCase()
			-subString()
			-length()
			-replace()
		 -->
		 
	<h2>FORMATTING</h2>
		<c:set var="Date" value="<%=new java.util.Date()%>" />  
		<p>  
		Formatted Time :  
		<fmt:formatDate type="time" value="${Date}" />  
		</p>  
		<p>  
		Formatted Date :  
		<fmt:formatDate type="date" value="${Date}" />  
		</p>  
		<p>  
		Formatted Date and Time :  
		<fmt:formatDate type="both" value="${Date}" />  
		</p>  
		<p>  
		Formatted Date and Time in short style :  
		<fmt:formatDate type="both" dateStyle="short" timeStyle="short"  
		value="${Date}" />  
		</p>  
		<p>  
		Formatted Date and Time in medium style :  
		<fmt:formatDate type="both" dateStyle="medium" timeStyle="medium"  
		value="${Date}" />  
		</p>  
		<p>  
		Formatted Date and Time in long style :  
		<fmt:formatDate type="both" dateStyle="long" timeStyle="long"  
		value="${Date}" />  
		</p> 		
	 	<!-- 
			other functions:
			-parseNumber()
			-timeZone()
			-formatNumber()
			-parseDate()
			-setTimeZone()
			-formatDate()
		 -->
	 	<h2>CONNECTING TO A SQL DATABASE</h2>
	 	
	 	<sql:setDataSource var="db" 
	 					driver="oracle.jdbc.driver.OracleDriver"
	 					url="jdbc:oracle:thin:@localhost:1521:xe"
	 					user="admin"
	 					password="admin"/>
	 	
	 	<sql:query var="query" dataSource="${db}">
	 		SELECT * FROM FLASH_CARDS
	 	</sql:query>
	 	
	 	<table border="2" width="100%">
	 		<tr>
	 			<th>fc_id</th>
	 			<th>fc_question></th>
	 			<th>fc_answer</th>
 			</tr>
 			<c:forEach var="table" items="${query.rows}">
 				<tr>
 					<td><c:out value="${table.fc_id}"></c:out></td>
 					<td><c:out value="${table.fc_question}"></c:out></td>
 					<td><c:out value="${table.fc_answer}"></c:out></td>
 				</tr>
 			</c:forEach>
	 	</table>
</body>
</html>