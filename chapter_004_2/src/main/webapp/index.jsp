<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
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
	<script type="text/javascript">
    	<%@include file="/WEB-INF/js/index.js"%>
	</script>

	<div align="center" class="container">
		<h1>Bootstrap Page</h1>
		<p>This is some text.</p>
	</div>

	<div class="container">
		<form id="usrForm">
			<div class="form-group">
				<label for="usr">Name:</label> <input type="text"
					class="form-control" title="Enter user name." id="usr" name="usrName">
			</div>
			<div class="form-group">
				<label for="lname">Last name:</label> <input type="text"
					class="form-control" title="Enter user last name." id="lname" name="usrLName">
			</div>
			<div class="form-group">
				<label class="radio-inline"><input type="radio"
					name="gender" value="male">Male</label> <label class="radio-inline"><input
					type="radio" name="gender" value="female">Female</label>
			</div>
			<div class="form-group">
				<label for="desc">Description:</label>
				<textarea class="form-control" rows="5" id="desc" name="usrDesc"></textarea>
			</div>
			<div class="form-group">
				<button type="button" class="btn btn-default" onclick="sendAjaxjQuery('JsonController', 'usrForm')" id="btn">Submit</button>
			</div>
		</form>
	</div>

	<div class="container" align="center">
   		<h2><u>Users Table</u></h2>
  		<p><i>This table displays information about users</i></p><br/>  
		<table id="tbl" class="table table-hover text-center">
			<thead>
				<tr>
					<th class="text-center">Name</th>
					<th class="text-center">Last name</th>
					<th class="text-center">Description</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>
