package ru.job4j.servlets.login;

import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

public class UsersAddControllerTest {
	private UsersAddController usrAdd;

	@Before
	public void setUp() throws Exception {
		usrAdd =  new UsersAddController();
	}

	@Test
	public void test() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		
		when(request.getParameter("password")).thenReturn(null);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		
		try {
			usrAdd.doGet(request, response);
			verify(response, times(1)).encodeRedirectURL("/WEB-INF/views/UsersAdd.jsp");
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
