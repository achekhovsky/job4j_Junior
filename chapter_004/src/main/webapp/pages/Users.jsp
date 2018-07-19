<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="ru.job4j.servlets.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Users store</title>
<link rel="stylesheet" type="text/css" href="../css/Store.css">
</head>
  <body>
    <h3 style="text-align: center;"><u>Users store</u></h3>
    <table style="width: 100%; height: 64px;" border="0">
      <tbody>
        <tr>
          <td style="text-align: center;"><b>id<br>
            </b> </td>
          <td style="text-align: center; width: 455.5px;"><b>create date<br>
            </b> </td>
          <td style="text-align: center; width: 229.05px;"><b>name<br>
            </b> </td>
          <td style="text-align: center;"><b>email<br>
            </b> </td>
          <td><br>
          </td>
          <td><br>
          </td>
        </tr>
        <%! ValidateService vService = ValidateService.getInstance(); %>
        <% 
        	String action = request.getParameter("action");
        	if (action != null && !action.equals("")) {
    			vService.doAction(ValidateService.Actions.valueOf(action), 
    					request.getParameter("userid"),
    					request.getParameter("username"),
    					request.getParameter("useremail"));
        	}
        %>
        <% if(vService.findAll().size() > 0) { %>
        <% for(User usr : vService.findAll()) { %>
        <tr>
        <form name="user" action="/Job4JWebApp/pages/Users.jsp" method="POST">
          <td>
          <input name="userid" value=<%= usr.getId() %> type="hidden">
          <%= usr.getId() %><br>
          </td>
          <td>
          <%= usr.getCreateDate() %><br>
          </td>
          <td>
          <input name="username" value=<%= usr.getName() %> type="hidden">
          <%= usr.getName() %><br>
          </td>
          <td>
          <input name="useremail" value=<%= usr.getEmail() %> type="hidden">
          <%= usr.getEmail() %><br>
          </td>
          <td style="text-align: center;"><button name="action" formaction="/Job4JWebApp/update" value="UPDATE" type="submit"
              formmethod="post">Update</button><br>
          </td>
          <td style="text-align: center;"><button name="action" value="DELETE" type="submit"
              formmethod="post">Delete</button><br>
          </td>
          </form>
        </tr>
        <% } %>
        <% } %>
      </tbody>
    </table>
    <form action="/Job4JWebApp/add" method=GET>
    <p> <button name="Add" value="ADD" type="submit">Add</button></p>
    </form>
    <footer><p> SessionId - <%=session.getId() %></p></footer>
  </body>
</html>