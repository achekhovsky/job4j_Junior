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
public class UpdateFilter implements Filter {
	private static Logger log = Logger.getLogger(UpdateFilter.class.getName());
	private final RoleStore roles = RoleStore.getInstance();
	private final CountiesTree countries = CountiesTree.getInstance();

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest rqst = (HttpServletRequest) request;
		rqst.setAttribute("rolesList", roles.getAllNames());
		rqst.setAttribute("countriesList", countries.getCountries());
		if (request.getParameter("getCitiesForThisCountry") != null && !request.getParameter("getCitiesForThisCountry").isEmpty()) {
			response.reset();
			response.setContentType("text/plain");
			response.getWriter().append(countries.getCountry(request.getParameter("getCitiesForThisCountry")).getCitiesInJSON());
			response.getWriter().flush();
		} else if (!request.getParameter("usercountry").isEmpty()) {
			request.setAttribute("citiesList", countries.getCountry(request.getParameter("usercountry")).getCities());
		}
		chain.doFilter(request, response);
	}

}
