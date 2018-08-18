<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/" var="encodedLoginUrl" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<title>LoginPage</title>
<style type="text/css">
<%@ include file="../css/Login.css" %>
</style>
</head>
  <body>
    <form name="LoginForm" action="${encodedLoginUrl}" method="POST"> 
    Select user:<br>
      <select name="username">
      	<c:forEach var="r" items="${usersList}">
      		<option value="${r}">${r}</option> 
      	</c:forEach> 
      </select>
      <p> </p>
      Password:<br>
      <input name="userpassword" type="password">
      <c:if test="${not empty error}">
      <div style="color: red;"> Incorrect password! Please try again... </div>
      </c:if>
      <p> <button name="Ok" value="ok" type="submit">Login</button>
      </p>
    </form>
  </body>
</html>