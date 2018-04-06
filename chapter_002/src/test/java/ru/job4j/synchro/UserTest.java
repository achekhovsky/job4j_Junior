package ru.job4j.synchro;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

public class UserTest {
	User first;
	User second;

	@Before
	public void setUp() throws Exception {
		first = new User(1, 10);
		second = new User(2, 20);
	}

	@Test
	public void ifUserIdSameThanEquals() {
		assertThat(first.equals(second), is(false));
		assertThat(first.equals(new User(1, 15)), is(true));
	}
	
	
	@Test
	public void ifTransferSucceessThenTrue() {
		assertThat(first.transferHimself(second, 10), is(true));
		assertThat(first.transferHimself(second, 10), is(false));
	}

}
