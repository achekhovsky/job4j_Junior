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
<style type="text/css">
	<%@ include file="/bootstrap/css/bootstrap.min.css" %>
</style>
<script type="text/javascript">
   	<%@include file="/bootstrap/js/bootstrap.js"%>
</script>
<script type="text/javascript">
   	<%@include file="/WEB-INF/js/jquery-3.3.1.min.js"%>
</script>
</head>
  <body>
    <form class="form-signin" id="logForm" name="LoginForm" action="${encodedLoginUrl}" method="POST">
    <img class="d-block mx-auto mb-4" src="images/java.jpg" width="72px" height="72px">
    <h1 class="h3 mb-3 font-weight-normal text-center">Please sign in</h1>
    <label for="username" class="sr-only">Email address</label>
    <select class="custom-select" name="username">
      	<c:forEach var="r" items="${usersList}">
      		<option value="${r}">${r}</option> 
      	</c:forEach> 
    </select>
    <label for="userpassword" class="sr-only">Password</label>
    <input id="userPass"name="userpassword" type="password" class="form-control" placeholder="Password" aria-describedby="basic-addon3">
      <div class="input-group mb-3">
      	<c:if test="${not empty error}">
    		<div class="invalid-feedback" style="display: inline-block;"> Incorrect password! Please try again... </div>
    		<script type="text/javascript">userPass.classList.add("is-invalid");</script>
    	</c:if> 
   </div>
    <button class="btn btn-lg btn-primary btn-block" name="Ok" value="ok" type="submit">Sign in</button>
    </form>
  </body>
</html>