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
 * Servlet Filter implementation class AddFilter
 */
public class AddFilter implements Filter {
	private static Logger log = Logger.getLogger(AddFilter.class.getName());
	private final RoleStore roles = RoleStore.getInstance();

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest rqst = (HttpServletRequest) request;
		rqst.setAttribute("rolesList", roles.getAllNames());
		chain.doFilter(request, response);
	}

}
