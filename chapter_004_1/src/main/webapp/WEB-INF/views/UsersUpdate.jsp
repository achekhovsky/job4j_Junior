<%@ page isELIgnored="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/main" var="encodedMainUrl"/>
<c:url value="/logout" var="encodedLogoutUrl"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Update user</title>
<style type="text/css">
#userForm {
	position: static;
	text-align: center;
}
</style>
</head>
<body>
    <div style="float: right; position: relative; font-size: small; text-align: center;">
    	<form action="${encodedLogoutUrl}" method=POST>
    		${login.name} [${loginRole.name}]
			<button name="logout" value="logout" type="submit">Logout</button>
		</form>
    </div>
	<h3 style="text-align: center;">
		<u>Update user</u>
	</h3>
	<div style="text-align: center;">
		<form name="user" action="${encodedMainUrl}" method="POST"
			id="userForm">
			<div style="text-align: center;">User id:</div>
			<input name="userid" type="text" readonly="readonly"
				value="${param['userid']}"><br> User name:<br> <input
				name="username" type="text" value="${param['username']}"><br>
			User email:<br> <input name="useremail" type="text"
				value="${param['useremail']}"><br>
			<button name="Cancel" value="CANCEL" formmethod="get"
				onclick="history.back()">Cancel</button>
			<input name="action" value="UPDATE" type="submit"><br>
		</form>
	</div>
	<p>
		<br>
	</p>
</body>
</html>
