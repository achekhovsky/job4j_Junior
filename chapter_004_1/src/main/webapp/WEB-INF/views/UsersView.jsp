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
</head>
<body>
    <div style="float: right; position: relative; font-size: small; text-align: center;">
    	<form action="${encodedLogoutUrl}" method=POST>
    		${login.name} [${loginRole.name}]
			<button name="logout" value="logout" type="submit">Logout</button>
		</form>
    </div>
	<h3 style="text-align: center;">
		<u>Users store</u>
	</h3>
	<table style="width: 100%; height: 64px;" border="0">
		<tbody>
			<tr>
				<td style="text-align: center;"><b>id<br>
				</b></td>
				<td style="text-align: center; width: 455.5px;"><b>create
						date<br>
				</b></td>
				<td style="text-align: center; width: 229.05px;"><b>name<br>
				</b></td>
				<td style="text-align: center;"><b>email<br>
				</b></td>
				<td><br></td>
				<td><br></td>
			</tr>
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
						<td style="text-align: center;"><c:choose>
								<c:when test="${loginRole.rights[1] == 1 || usr.id == login.id}">
									<button name="action" formaction="${encodedUpdateUrl}"
										value="UPDATE" type="submit" formmethod="post">Update</button>
									<br>
								</c:when>
								<c:otherwise>
									<button name="action" formaction="${encodedUpdateUrl}"
										value="UPDATE" type="submit" formmethod="post"
										disabled="disabled">Update</button>
									<br>
								</c:otherwise>
							</c:choose></td>
						<td style="text-align: center;"><c:choose>
								<c:when test="${loginRole.rights[2] == 1 && usr.roleName != 'Administrator'}">
									<button name="action" value="DELETE" type="submit"
										formmethod="post">Delete</button>
									<br>
								</c:when>
								<c:otherwise>
									<button name="action" value="DELETE" type="submit"
										formmethod="post" disabled="disabled">Delete</button>
									<br>
								</c:otherwise>
							</c:choose></td>
					</form>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<form action="${encodedAddUrl}" method=GET>
		<c:choose>
			<c:when test="${loginRole.rights[2] == 1}">
				<p>
					<button name="Add" value="ADD" type="submit">Add</button>
				</p>
			</c:when>
			<c:otherwise>
				<p>
					<button name="Add" value="ADD" type="submit" disabled="disabled">Add</button>
				</p>
			</c:otherwise>
		</c:choose>
	</form>
	<footer>
		<p>SessionId - ${pageContext.session.id}</p>
	</footer>
</body>
</html>