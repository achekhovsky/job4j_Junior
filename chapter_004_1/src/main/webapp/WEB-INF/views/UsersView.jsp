<%@ page isELIgnored="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List"%>
<%@ page import="ru.job4j.servlets.servlet.*"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:url value="/add" var="encodedAddUrl" />
<c:url value="/main" var="encodedMainUrl"/>
<c:url value="/update" var="encodedUpdateUrl"/>
<c:url value="/logout" var="encodedLogoutUrl"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Users store</title>
<style type="text/css">
<%@ include file="../css/Store.css"%>
</style>
	<script type="text/javascript">
    	<%@include file="/WEB-INF/js/jquery-3.3.1.min.js"%>
	</script>
	<style type="text/css">
		<%@ include file="/bootstrap/css/bootstrap.min.css" %>
	</style>
	<script type="text/javascript">
   		<%@include file="/bootstrap/js/bootstrap.js"%>
	</script>
</head>
<body>
    <div style="float: right; position: relative; font-size: small; text-align: center;">
    	<form action="${encodedLogoutUrl}" method=POST>
    		${login.name} [${loginRole.name}]
			<button class="btn btn-sm btn-link" name="logout" value="logout" type="submit">Logout</button>
		</form>
    </div>
	<div class="row align-items-center align-self-center justify-content-center">
	<div class="col-12 col-md-8">
	<p></p>
	<h4 class="h4 text-center mb-4 font-weight-normal">Users store</h3>
	<table class="table table-borderless table-hover text-center table-sm">
			<thead class="table-sm border-bottom">
				<tr>
					<th class="text-center">id</th>
					<th class="text-center">create date</th>
					<th class="text-center">name</th>
					<th class="text-center">email</th>
					<th class="text-center">country</th>
					<th class="text-center">city</th>
					<th class="text-center"></th>
					<th class="text-center">
		<form action="${encodedAddUrl}" method=GET>
		<c:choose>
			<c:when test="${loginRole.rights[2] == 1}">
					<button class="btn badge badge-pill badge-success" name="Add" value="ADD" type="submit" data-toggle="tooltip" data-placement="top" title="Add new user">Add</button>
			</c:when>
			<c:otherwise>
					<button class="btn badge badge-pill badge-secondary" disabled name="Add" value="ADD" type="submit" data-toggle="tooltip" data-placement="top" title="Add new user">Add</button>
			</c:otherwise>
		</c:choose>
		</form>
				</th>
					
				</tr>
			</thead>
		<tbody>
			<c:forEach var="usr" items="${users}">
				<tr>
					<form name="users" action="${encodedMainUrl}" method="POST">
						<td style="text-align: center;"><input name="userid"
							value="${usr.id}" type="hidden"> <c:out value="${usr.id}"></c:out><br>
						</td>
						<td style="text-align: center;"><c:out
								value="${usr.createDate}"></c:out><br></td>
						<td style="text-align: center;"><input name="username"
							value="${usr.name}" type="hidden"> <c:out
								value="${usr.name}"></c:out><br></td>
						<td style="text-align: center;"><input name="useremail"
							value="${usr.email}" type="hidden"> <c:out
								value="${usr.email}"></c:out><br></td>
						<td style="text-align: center;"><input name="usercountry"
							value="${usr.country}" type="hidden"> <c:out
								value="${usr.country}"></c:out><br></td>
						<td style="text-align: center;"><input name="usercity"
							value="${usr.city}" type="hidden"> <c:out
								value="${usr.city}"></c:out><br></td>
						<td style="text-align: center;"><c:choose>
								<c:when test="${loginRole.rights[1] == 1 || usr.id == login.id}">
									<button class="btn btn-outline-secondary btn-sm" name="action" formaction="${encodedUpdateUrl}"
										value="UPDATE" type="submit" formmethod="post">Update</button>
									<br>
								</c:when>
								<c:otherwise>
									<button class="btn btn-outline-secondary btn-sm" name="action" formaction="${encodedUpdateUrl}"
										value="UPDATE" type="submit" formmethod="post"
										disabled="disabled">Update</button>
									<br>
								</c:otherwise>
							</c:choose></td>
						<td style="text-align: center;"><c:choose>
								<c:when test="${loginRole.rights[2] == 1 && usr.roleName != 'Administrator'}">
									<button class="btn btn-outline-danger btn-sm" name="action" value="DELETE" type="submit"
										formmethod="post">Delete</button>
									<br>
								</c:when>
								<c:otherwise>
									<button class="btn btn-outline-danger btn-sm" name="action" value="DELETE" type="submit"
										formmethod="post" disabled="disabled">Delete</button>
									<br>
								</c:otherwise>
							</c:choose></td>
					</form>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	</div>
	<footer>
		<p>SessionId - ${pageContext.session.id}</p>
	</footer>
</body>
</html>