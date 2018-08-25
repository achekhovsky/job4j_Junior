package ru.job4j.servlets.login;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

public class LogoutControllerTest {
	private LogoutController logoutCtrl;

	@Before
	public void setUp() throws Exception {
		logoutCtrl = new LogoutController();
	}

	@Test
	public void test() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		ServletContext context = mock(ServletContext.class);
		
		when(request.getParameter("logout")).thenReturn("logout");
		when(request.getSession()).thenReturn(session);
		when(request.getServletContext()).thenReturn(context);
		try {
			doNothing().when(response).sendRedirect(anyString());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			logoutCtrl.doPost(request, response);
			
			verify(session, times(1)).removeAttribute("login");
			verify(response, times(1)).sendRedirect(anyString());
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

}
