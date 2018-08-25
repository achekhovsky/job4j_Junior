package ru.job4j.servlets.login;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

public class UsersUpdateControllerTest {
	private UsersUpdateController usUc;
	private final String path = "/WEB-INF/views/UsersUpdate.jsp";

	@Before
	public void setUp() throws Exception {
		usUc = new UsersUpdateController();
	}

	@Test
	public void test() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		
		when(response.encodeRedirectURL(path)).thenReturn(path);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		
		try {
			usUc.doPost(request, response);
			verify(request, atLeastOnce()).getRequestDispatcher(response.encodeRedirectURL(path));
			verify(dispatcher, times(1)).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
