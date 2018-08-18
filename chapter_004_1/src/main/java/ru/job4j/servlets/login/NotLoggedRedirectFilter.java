package ru.job4j.servlets.login;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class NotLoggedRedirectFilter
 */
public class NotLoggedRedirectFilter implements Filter {

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest rqst = (HttpServletRequest) request;
		HttpServletResponse rpns = (HttpServletResponse) response;
		if (!rqst.getHttpServletMapping().getServletName().equals("LoginCtrl") && rqst.getSession().getAttribute("login") == null) {
			System.out.println("!!!!!main");
			rpns.sendRedirect(rpns.encodeRedirectURL(rqst.getServletContext().getContextPath() + "/"));
		} else {
			chain.doFilter(rqst, rpns);
		}
	}
}
