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
public class UsersSvt extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ValidateService vService = ValidateService.getInstance();
       
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
		out.println("<title>Users store</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h3 style=\" text-align: center;\"><u>User Store</u></h3>");
		out.println("<table style=\"width: 100%; height: 64px;\" border=\"0\">");
		out.println("<tbody>");
		out.println("<tr>");
		out.println("<td style=\"text-align: center;\"><b>id<br>");
		out.println("</b> </td>");
		out.println("<td style=\"text-align: center;\"><b>create date<br>");
		out.println("</b> </td>");
		out.println("<td style=\"text-align: center;\"><b>name<br>");
		out.println("</b> </td>");
		out.println("<td style=\"text-align: center;\"><b>email<br>");
		out.println("</b> </td>");
		out.println("<td><br>");
		out.println("</td>");
		out.println("<td><br>");
		out.println("</td>");
		out.println("</tr>");
		if (vService.findAll().size() > 0) {
			for (User usr : vService.findAll()) {
				out.println("<form name=\"user\" action=\"/Job4JWebApp/store\" method=\"POST\">");
				out.println("<tr>");
				out.println("<td style=\"text-align: center; vertical-align: middle;\">");
				out.println("<input name=\"userid\" value=\"" + usr.getId() + "\" type=\"hidden\">");
				out.println(usr.getId() + "<br>");
				out.println("</td>");
				out.println("<td style=\"text-align: center; vertical-align: middle;\">");
				out.println(usr.getCreateDate() + "<br>");
				out.println("</td>");
				out.println("<td style=\"text-align: center; vertical-align: middle;\">");
				out.println("<input name=\"username\" value=\"" + usr.getName() + "\" type=\"hidden\">");
				out.println(usr.getName() + "<br>");
				out.println("</td>");
				out.println("<td style=\"text-align: center; vertical-align: middle;\">");
				out.println("<input name=\"useremail\" value=\"" + usr.getEmail() + "\" type=\"hidden\">");
				out.println(usr.getEmail() + "<br>");
				out.println("</td>");
				out.println(
						"<td style=\"text-align: center;\"><button name=\"action\" formaction=\"/Job4JWebApp/update\" value=\"UPDATE\" type=\"submit\"");
				out.println("formmethod=\"post\">Update</button><br>");
				out.println("</td>");
				out.println(
						"<td style=\"text-align: center;\"><button name=\"action\" value=\"DELETE\" type=\"submit\"");
				out.println("formmethod=\"post\">Delete</button><br>");
				out.println("</td>");
				out.println("</tr>");
				out.println("</form>");
			}
		}
		out.println("</tbody>");
		out.println("</table>");
        out.print("<form action=\"/Job4JWebApp/add\""); 
        out.println("method=GET>");
		out.println("<p> <button name=\"Add\" value=\"ADD\" type=\"submit\">ADD</button></p>");
	    out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		vService.doAction(ValidateService.Actions.valueOf(action), 
				request.getParameter("userid"),
				request.getParameter("username"),
				request.getParameter("useremail"));
		doGet(request, response);
	}

}
