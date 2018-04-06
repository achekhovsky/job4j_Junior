package ru.job4j.synchro;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

public class UserStorageTest {
	private UserStorage us;
	User first;
	User second;
	
	@Before
	public void setUp() throws Exception {
		us = new UserStorage();
		first = new User(1, 20);
		second = new User(2, 10);
	}

	@Test
	public void ifAddUserFirstTimeThenTrueIfSecondThenFalse() {
		assertThat(us.add(first), is(true));
		assertThat(us.add(first), is(false));
		assertThat(us.add(second), is(true));
	}
	
	@Test
	public void ifTransferAmountMoreThenOnHandsThenFalse() {
		us.add(first);
		us.add(second);
		assertThat(us.transfer(1, 2, 10), is(true));
		assertThat(us.transfer(1, 2, 15), is(false));
		assertThat(us.transfer(2, 1, 20), is(true));
		assertThat(us.transfer(2, 1, 1), is(false));
	}
	
	@Test
	public void ifUpdateNonExistentElementThenFalse() {
		us.add(first);
		assertThat(us.update(new User(1, 10)), is(true));
		assertThat(us.update(new User(3, 10)), is(false));
	}
	
	@Test
	public void ifAddOneUserThenDeleteHimThenStorageIsEmpty() {
		us.add(first);
		us.delete(first);
		assertThat(us.getUsersCount(), is(0));
	}
	
	@Test
	public void ifAddedTwoUsersThenUsersCountIsTwo() {
		us.add(first);
		assertThat(us.getUsersCount(), is(1));
		us.add(new User(1, 10));
		assertThat(us.getUsersCount(), is(1));
		us.add(second);
		assertThat(us.getUsersCount(), is(2));
	}

}
