package ru.job4j.servlets.login;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {
	private static Logger log = Logger.getLogger(LoginFilter.class.getName());
	private final ValidateService vService = ValidateService.getInstance();
	private final RoleStore roles = RoleStore.getInstance();

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest rqst = (HttpServletRequest) request;
		HttpServletResponse rpns = (HttpServletResponse) response;
		rqst.setAttribute("usersList", vService.getAllNames());
		User login = vService.authenticateUser(
				rqst.getParameter("username"), 
				rqst.getParameter("userpassword"));
		if (login != null) {
			rqst.getSession().setAttribute("login", login);
			rqst.getSession().setAttribute("loginRole", roles.getByName(login.getRoleName()));
			rpns.sendRedirect(rpns.encodeRedirectURL(rqst.getServletContext().getContextPath() + "/main"));
		} else {
			if (rqst.getParameter("username") != null && rqst.getParameter("userpassword") != null) {
				rqst.setAttribute("error", "Incorrect password! Please try again...");
			}
			chain.doFilter(request, response);
		}
	}
}
