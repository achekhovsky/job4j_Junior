package ru.job4j.servlets.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UsersSvt
 */
public class UsersUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        response.getWriter().append("Served at: ").append(request.getContextPath());
        
		out.println("<html>");
		out.println("<head>");
		out.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
		out.println("<title>Update user</title>");
		out.println("<style type=\"text/css\">");
		out.println("#userForm {");
		out.println("position: static;");
		out.println("text-align: center;");
		out.println("}");
		out.println("</style></head>");
		out.println("<body><br>");
		out.println("<h3 style=\" text-align: center;\"><u>Update user</u></h3>");
		out.println("<div style=\"text-align: center;\">");
//		out.println("<form name=\"user\" action=\"/Job4JWebApp/store\" method=\"POST\" id=\"userForm\">");
		out.println("<form name=\"user\" action=\"/Job4JWebApp/pages/Users.jsp\" method=\"POST\" id=\"userForm\">");
		out.println("<div style=\"text-align: center;\">User id:</div>");
		out.println("<input name=\"userid\" type=\"text\" readonly=\"readonly\" value=\"" + request.getParameter("userid") + "\"><br>");
		out.println("User name:<br>");
		out.println("<input name=\"username\" type=\"text\" value=\"" + request.getParameter("username") + "\"><br>");
		out.println("User email:<br>");
		out.println("<input name=\"useremail\" type=\"text\" value=\"" + request.getParameter("useremail") + "\"><br>");
		out.println(
				"<button name=\"Cancel\" value=\"CANCEL\" formmethod=\"get\" onclick=\"history.back()\">Cancel</button>");
		out.println("<input name=\"action\" value=\"UPDATE\" type=\"submit\"><br>");
		out.println("</form>");
		out.println("</div>");
		out.println("<p><br>");
		out.println("</p>");
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
