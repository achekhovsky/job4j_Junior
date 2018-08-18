<%@ page isELIgnored="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/add" var="encodedAddUrl" />
<c:url value="/logout" var="encodedLogoutUrl"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Add user</title>
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
		<u>Add user</u>
	</h3>
	<div style="text-align: center;">
		<form name="user" action="${encodedAddUrl}" method="POST"
			id="userForm">
			<div style="text-align: center;">User id:</div>
			<input name="userid" type="text"><br> User name:<br>
			<input name="username" type="text"><br> User email:<br>
			<input name="useremail" type="text"><br> Role:<br>
			<select	name="rolename">
				<c:forEach var="r" items="${rolesList}">
					<option value="${r}">${r}</option>
				</c:forEach>
			</select><br> Password:<br>
			<input name="password" type="password"><br> Confirm password:<br>
			<input name="passwordConfirm" type="password"><br>
			<button name="Cancel" value="CANCEL" onclick="history.back()">Cancel</button>
			<input name="action" value="ADD" type="submit"><br>
		</form>
	</div>
	<p>
		<br>
	</p>
</body>
</html>
