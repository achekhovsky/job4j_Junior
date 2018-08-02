package ru.job4j.servlets.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UsersSvt
 */
public class UsersController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ValidateService vService = ValidateService.getInstance();
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.setAttribute("users", vService.findAll());
        request.getRequestDispatcher(response.encodeRedirectURL("/WEB-INF/views/UsersView.jsp")).forward(request, response);
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
