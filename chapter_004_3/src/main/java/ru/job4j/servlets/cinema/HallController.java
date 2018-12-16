package ru.job4j.servlets.cinema;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HallController
 */
public class HallController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		if (request.getParameter("getAction") != null) {
		switch (request.getParameter("getAction")) {
		case "getPlaces":
			this.sendData(response, Parser.parseToJson(SQLStore.getInstance().getOccupiedPlaces())); 
			break;
		case "forPaid": 
			Place plc = SQLStore.getInstance().findByPlaceNumber(Integer.parseInt(request.getParameter("place")));
			request.getSession().setAttribute("forPaid", plc);
			break;
		case "getPlaceForPaid":
			Place plcForPaid = (Place) request.getSession().getAttribute("forPaid");
			this.sendData(response, Parser.parseToJson(plcForPaid)); 
 			break;
 		case "paid":
			Place paidPlc = (Place) request.getSession().getAttribute("forPaid");
			Account acc = new Account(request.getParameter("account"), request.getParameter("phone"));
			SQLStore.getInstance().addAccount(acc);
			paidPlc.setCustomer(acc.getName());
			paidPlc.setPaid(false);
			SQLStore.getInstance().updateHall(paidPlc);
 			break;
 		default: break;
		} }
		doGet(request, response);
	}
	
	private void sendData(HttpServletResponse response, String data) throws ServletException, IOException {
		response.reset();
		response.setContentType("text/plain");
		response.getWriter().append(data);
		response.getWriter().flush();
	}
}
