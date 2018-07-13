package ru.job4j.servlets.http;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

import ru.job4j.servlets.http.MemoryStore;
import ru.job4j.servlets.http.User;

public class MemoryStoreTest {
	private MemoryStore ms;
	private User firstUser;
	private User secondUser;

	@Before
	public void setUp() throws Exception {
		ms = MemoryStore.getInstance();
		ms.clearStore();
		firstUser = new User(1, "user1", "email1");
		secondUser = new User(2, "user2", "email2");
	}
	
	@Test
	public void whenAdd() {
		assertThat(ms.findAll().size(), is(0));
		ms.add(firstUser);
		assertThat(ms.findAll().size(), is(1));
	}
	
	@Test
	public void whenFindById() {
		ms.add(firstUser);
		assertThat(ms.findById(1), is(firstUser));
		assertNull(ms.findById(2));
	}
	
	@Test
	public void whenDelete() {
		ms.add(firstUser);
		ms.delete(secondUser);
		assertThat(ms.findAll().size(), is(1));
		ms.delete(firstUser);
		assertThat(ms.findAll().size(), is(0));
	}
	
	@Test
	public void whenUpdate() {
		ms.add(firstUser);
		ms.add(secondUser);
		assertThat(ms.findById(1).getName(), is("user1"));
		assertThat(ms.findById(2).getName(), is("user2"));
		User updater = new User(2, "updatedName", "updatedEmail");
		ms.update(updater);
		assertThat(ms.findById(1).getName(), is("user1"));
		assertThat(ms.findById(2).getName(), is("updatedName"));
	}
}
