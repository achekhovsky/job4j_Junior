package ru.job4j.servlets.http;

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
        out.println("<title>Users store</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>Users store</h3>");
        out.println("Users:<br>");
        if (vService.findAll().size() > 0) {
            for (User usr : vService.findAll()) {
                out.println("Id: ");
                out.println(usr.getId() + "<br>");
                out.println("CreateDate: ");
                out.println(usr.getCreateDate() + "<br>");
                out.println("Name: ");
                out.println(usr.getName() + "<br>");
                out.println("Email: ");
                out.println(usr.getEmail() + "<br>");
            }
        } else {
            out.println("No users, Please enter some");
        }
        out.println("<P>");
        out.print("<form "); 
        out.println("method=POST>");
        
        out.println("Id:");
        out.println("<input type=text size=20 name=userid>");
        out.println("<br>");
        out.println("Name:");
        out.println("<input type=text size=20 name=username>");
        out.println("<br>");
        out.println("Email:");
        out.println("<input type=text size=20 name=useremail>");
        out.println("<br>");
        
		out.println("<select name=action size=1>");
		out.println("<option value=ADD>Add</option>");
		out.println("<option value=UPDATE>Update</option>");
		out.println("<option value=DELETE>Delete</option>");
		out.println("</select>");
        
        out.println("<br>");
        out.println("<input type=submit>");
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
