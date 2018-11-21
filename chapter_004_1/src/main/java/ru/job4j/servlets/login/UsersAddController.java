package ru.job4j.servlets.login;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UsersAddController
 */
public class UsersAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	       //response.setContentType("text/html");
			if (request.getParameter("password") != null 
					&& request.getParameter("passwordConfirm") != null
					&& request.getParameter("password").equals(request.getParameter("passwordConfirm"))) {
				request.getRequestDispatcher(response.encodeRedirectURL("/main")).forward(request, response);
			} else {
				request.getRequestDispatcher(response.encodeRedirectURL("/WEB-INF/views/UsersAdd.jsp")).forward(request, response);
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
