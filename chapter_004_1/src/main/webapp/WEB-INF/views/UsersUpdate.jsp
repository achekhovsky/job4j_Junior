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
	<title>Add user</title>
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
	<script type="text/javascript">
    	<%@include file="/WEB-INF/js/index.js"%>
	</script>
    <div style="float: right; position: relative; font-size: small; text-align: center;">
    	<form class="form-signin" action="${encodedLogoutUrl}" method=POST>
    		${login.name} [${loginRole.name}]
			<button class="btn btn-sm btn-link" name="logout" value="logout" type="submit">Logout</button>
		</form>
    </div>
		<br>
		<form class="form-signin" name="user" action="${encodedMainUrl}" method="POST"
			id="userForm" onsubmit="return findEmptyInputs()">
			
			<div class="row justify-content-center">
			<div class="col-4">
			<h4 class="h4 text-center mb-4 font-weight-normal">Update user</h3>
			<div class="form-row mb-4">
			 <div class="col">
              <div class="input-group">
                <div class="input-group-prepend">
         			<div class="input-group-text">id:</div>
        		</div>
                <input class="form-control" id="usrId" name="userid" placeholder="" value="${param['userid']}" type="text">
                <div class="invalid-feedback">
                  Valid id is required.
                </div>
              </div>
              </div>
              <div class="col">
                <select class="custom-select" id="rolename" name="rolename" disabled>
 					<c:forEach var="r" items="${rolesList}">
						<option value="${r}">${r}</option>
					</c:forEach>
                </select>
                <div class="invalid-feedback">
                  Please select a valid role.
              </div>
            </div>
   		  	</div>
   		  	
   		  	<div class="form-row mb-4">
   		  	  <div class="col">
              <div class="input-group">
                <div class="input-group-prepend">
         			<div class="input-group-text">name:</div>
        		</div>
                <input class="form-control" id="usrName" name="username" placeholder="" value="${param['username']}" type="text">
                <div class="invalid-feedback">
                  Valid name is required.
                </div>
              </div>
              </div>
   		  	</div>
   		  	
   		  	<div class="form-row mb-4">
   		  	<div class="col">
   		  	<div class="input-group">
   		  	  <div class="input-group-prepend">
         		<div class="input-group-text">email:</div>
        	  </div>
              <input class="form-control" id="email" name="useremail" value="${param['useremail']}" placeholder="example@email.ru" type="email">
              <div class="invalid-feedback">
                Please enter a valid email address for shipping updates.
              </div>
            </div>
            </div>
   		  	</div>
   		  	
            <div class="form-row mb-3">
              <div class="input-group col">
                <div class="input-group-prepend">
    				<label class="input-group-text" for="countries">country:</label>
  				</div>
                <select class="custom-select" id="countries" name="usercountry" onchange="onChangeSelect(event)" required="">
                  <c:forEach var="c" items="${countriesList}">
                  	<c:choose>
                  		<c:when test="${c == param.usercountry}">
                  			<option value="${c}" selected="true">${c}</option>
                  		</c:when>
                  		<c:otherwise>
                  			<option value="${c}">${c}</option>
                  		</c:otherwise>
                 	 </c:choose>
				  </c:forEach>
                </select>
                <div class="invalid-feedback">
                  Please select a valid country.
                </div>
              </div>
              <div class="input-group col">
                <div class="input-group-prepend">
    				<label class="input-group-text" for="cities">city:</label>
  				</div>
                <select class="custom-select" id="cities" name="usercity" required="">
                   <c:forEach var="c" items="${citiesList}">
                  	<c:choose>
                  		<c:when test="${c == param.usercity}">
                  			<option value="${c}" selected="true">${c}</option>
                  		</c:when>
                  		<c:otherwise>
                  			<option value="${c}">${c}</option>
                  		</c:otherwise>
                 	 </c:choose>
				  </c:forEach>
                </select>
                <div class="invalid-feedback">
                  Please provide a valid state.
                </div>
              </div>
            </div>
            <hr>
   		  	<div class="row justify-content-end">
   		  	<div>
				<button class="btn btn-outline-secondary btn-sm my-2" name="Cancel" value="CANCEL" onclick="history.back()">Cancel</button>
				<input class="btn btn-outline-success btn-sm my-2" name="action" value="UPDATE" type="submit">
			</div>
			</div>
			</div>
			</div>
		</form>
</body>
</html>
