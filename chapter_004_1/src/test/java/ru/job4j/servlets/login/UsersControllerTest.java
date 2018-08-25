package ru.job4j.servlets.login;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
//import java.lang.reflect.Field;

import javax.naming.Context;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author achekhovsky
 *
 */
public class UsersControllerTest {
	private User usr;
	private UsersController usrCtrl;
	private ValidateService vService;
	
	@BeforeClass
	public static void setupInitialContext() {
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "ru.job4j.servlets.login.TesterInitialContextFactory");
	}

	@Before
	public void setUp() throws Exception {
		//It is used in the case when the V_SERVICE field of class ValidateService does not have a modifier final.
		//This allows to test the ValidateService class methods that are called by the UsersController servlet.
/*		vService = mock(ValidateService.class);
		setMockedSingleton(vService);*/
		usr = new User(1, "name", "mail", "role", "123");
		usrCtrl = new UsersController();
		
	}

	@Test
	public void test() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		when(response.encodeRedirectURL("/WEB-INF/views/UsersView.jsp")).thenReturn("");
		
		when(request.getParameter("action")).thenReturn("ADD", "DELETE");
		when(request.getParameter("userid")).thenReturn(String.valueOf(usr.getId()));
		when(request.getParameter("username")).thenReturn(usr.getName());
		when(request.getParameter("useremail")).thenReturn(usr.getEmail());
		when(request.getParameter("rolename")).thenReturn(usr.getRoleName());
		when(request.getParameter("password")).thenReturn(usr.getPassword());
		
		try {
			usrCtrl.doPost(request, response);
			//It is used in the case when the V_SERVICE field of class ValidateService does not have a modifier final.
			//This allows to test the ValidateService class methods that are called by the UsersController servlet.
/*			verify(vService,times(1)).doAction(ValidateService.Actions.ADD, String.valueOf(usr.getId()), usr.getName(), usr.getEmail(), usr.getRoleName(), usr.getPassword());
			usrCtrl.doPost(request, response);
			verify(vService,times(1)).doAction(ValidateService.Actions.DELETE, String.valueOf(usr.getId()), usr.getName(), usr.getEmail(), usr.getRoleName(), usr.getPassword());
			verify(vService,times(0)).doAction(ValidateService.Actions.UPDATE, String.valueOf(usr.getId()), usr.getName(), usr.getEmail(), usr.getRoleName(), usr.getPassword());*/
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown() {
		File dataBaseForRemove = new File("test.db");
		if (dataBaseForRemove.exists()) {
			dataBaseForRemove.delete();
		}
		//It is used in the case when the V_SERVICE field of class ValidateService does not have a modifier final.
		//this.resetMockedSingleton();
	}
	
	//It is used in the case when the V_SERVICE field of class ValidateService does not have a modifier final.
	//This allows to test the ValidateService class methods that are called by the UsersController servlet.
/*	@After
	public void uninstall() {
		this.resetMockedSingleton();
	}
	
	private void setMockedSingleton(ValidateService mock) {
		try {
			Field instance = ValidateService.class.getDeclaredField("V_SERVICE");
			instance.setAccessible(true);
			instance.set(instance, mock);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		} 
	}
	
	private void resetMockedSingleton() {
		try {
			Field instance = ValidateService.class.getDeclaredField("V_SERVICE");
			instance.setAccessible(true);
			instance.set(null, null);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		} 
	}*/
}

