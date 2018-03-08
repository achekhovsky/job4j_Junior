package ru.job4j.map;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Test for User
 */
public class UserTest {

	@Test
	public void test() {
		Calendar birthday = Calendar.getInstance(); 
		User firstUsr = new User("User", 1, birthday);
		User secondUsr = new User("User", 1, birthday);
		
		Map<User, Object> usrMap = new HashMap<User, Object>();
		usrMap.put(firstUsr, "User1");
		usrMap.put(secondUsr, "User2");
		
		for (Object usr : usrMap.values()) {
			System.out.println(usr);
		}
	}

}
