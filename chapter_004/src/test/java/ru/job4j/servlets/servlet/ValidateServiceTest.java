package ru.job4j.servlets.servlet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ru.job4j.servlets.http.MemoryStore;
import ru.job4j.servlets.http.User;
import ru.job4j.servlets.http.ValidateService;

public class ValidateServiceTest {
	private ValidateService vs;
	private User firstUser;
	private User secondUser;

	@Before
	public void setUp() throws Exception {
		vs = ValidateService.getInstance();
		((MemoryStore) vs.getStore()).clearStore();
		firstUser = new User(1, "user1", "email1");
		secondUser = new User(2, "user2", "email2");
	}
	
	@Test
	public void whenAdd() {
		assertTrue(vs.add(firstUser));
		assertFalse(vs.add(firstUser));
	}
	
	@Test
	public void whenFindById() {
		vs.add(firstUser);
		assertThat(vs.findById(1), is(firstUser));
		assertNull(vs.findById(2));
	}
	
	@Test
	public void whenDelete() {
		vs.add(firstUser);
		vs.delete(secondUser);
		assertThat(vs.findAll().size(), is(1));
		vs.delete(firstUser);
		assertThat(vs.findAll().size(), is(0));
	}
	
	@Test
	public void whenUpdate() {
		assertFalse(vs.update(firstUser));
		vs.add(firstUser);
		User updater = new User(1, "updatedName", "updatedEmail");
		assertTrue(vs.update(updater));
	}
}
