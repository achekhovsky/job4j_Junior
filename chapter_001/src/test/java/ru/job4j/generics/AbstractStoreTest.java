/**
 * 
 */
package ru.job4j.generics;

import static org.junit.Assert.assertThat;

import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for AbstractStore methods
 * @author achekhovsky
 *
 */
public class AbstractStoreTest {
	private AbstractStore<User> aStore;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		aStore = new UserStore(2);
	}

	@Test(expected = InvalidIdException.class)
	public void ifAddedElementWithDuplicateIdThenInvalidIdException() {
		aStore.add(new User("usr1"));
		assertThat(aStore.getSize(), is(1));
		aStore.add(new User("usr2"));
		assertThat(aStore.getSize(), is(2));
		aStore.add(new User("usr3"));
		assertThat(aStore.getSize(), is(3));
		aStore.add(new User("usr1"));
	}
	
	@Test 
	public void ifFindItemByIdThenReturnItItem() {
		User usr1 = new User("usr1");
		User usr2 = new User("usr2");
		aStore.add(usr1);
		aStore.add(usr2);
		assertThat(aStore.findById("usr1"), is(usr1));
	}
	
	@Test(expected = NoSuchElementException.class)
	public void ifDeletItemByIdTwiceButOnceThereIsNoExistingItemThenStoreSizeReducedByOne() {
		User usr1 = new User("usr1");
		User usr2 = new User("usr2");
		aStore.add(usr1);
		aStore.add(usr2);
		assertThat(aStore.getSize(), is(2));
		aStore.delete("usr1");
		assertThat(aStore.getSize(), is(1));
		aStore.delete("usr3");
	}
	
	@Test(expected = NoSuchElementException.class)
	public void ifReplaceItemByIdTwiceButOnceThereIsNoExistingItemThenFirstTrueSecondFalse() {
		User usr1 = new User("usr1");
		User usr2 = new User("usr2");
		aStore.add(usr1);
		aStore.add(usr2);
		assertThat(aStore.replace("usr1", new User("usr3")), is(true));
		aStore.replace("usr1", new User("usr4"));
	}

}
