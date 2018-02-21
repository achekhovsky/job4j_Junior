package ru.job4j.linkedlist;

import static org.junit.Assert.assertThat;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for DynamicLinkedContainer methods
 */
public class DynamicLinkedContainerTest {
	private DynamicLinkedContainer<String> dlCon;
	
	@Before
	public void setUp() throws Exception {
		dlCon = new DynamicLinkedContainer<String>();
	}

	@Test
	public void ifAddOneTwoThreeThenGetOneTwoThree() {
		dlCon.add("String 1");
		dlCon.add("String 2");
		dlCon.add("String 3");
		assertThat(dlCon.get(0), is("String 1"));
		assertThat(dlCon.get(1), is("String 2"));
		assertThat(dlCon.get(2), is("String 3"));
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void ifIndexIsOutOfBoundsThenException() {
		dlCon.add("String 1");
		dlCon.get(2);
	}
	
	@Test
	public void ifIteratorWorkCorrectlyThenOneTwoThree() {
		StringBuilder actual = new StringBuilder();
		dlCon.add("1");
		dlCon.add("2");
		dlCon.add("3");
		Iterator<String> dlConItr = dlCon.iterator();
		while (dlConItr.hasNext()) {
			actual.append(dlConItr.next());
		}
		assertThat(actual.toString(), is("123"));
	}
	
	@Test(expected = NoSuchElementException.class)
	public void ifNextWhenHasNextIsFalseThenNoSuchElementException() {
		dlCon.add("1");
		dlCon.add("2");
		Iterator<String> dlConItr = dlCon.iterator();
		while (dlConItr.hasNext()) {
			dlConItr.next();
		}
		dlConItr.next();
	}
	
	@Test(expected = ConcurrentModificationException.class)
	public void ifModificateAfterCreatingTheIteratorThenConcurrentModificationException() {
		dlCon.add("1");
		dlCon.add("2");
		Iterator<String> dlConItr = dlCon.iterator();
		dlConItr.next();
		dlCon.add("3");
		dlConItr.next();
	}
	
	@Test
	public void ifAddTwoButUnlinkFirstThenRemindSecond() {
		dlCon.unlinkFirst();
		dlCon.add("1");
		dlCon.unlinkFirst();
		dlCon.add("2");
		assertThat(dlCon.get(0), is("2"));
	}
	
	@Test
	public void ifAddedThreeButUnlinkSecondThenRemindFirstAndThird() {
		dlCon.unlinkLast();
		dlCon.add("1");
		dlCon.add("2");
		dlCon.unlinkLast();
		dlCon.add("3");
		assertThat(dlCon.get(0), is("1"));
		assertThat(dlCon.get(1), is("3"));
	}
	
	@Test
	public void ifLinkFirstTwiceFromOneToFive() {
		dlCon.linkFirst("2");
		dlCon.add("3");
		dlCon.add("4");
		dlCon.linkFirst("1");
		dlCon.add("5");
		StringBuilder expected = new StringBuilder();
		Iterator<String> dlConItr = dlCon.iterator();
		while (dlConItr.hasNext()) {
			expected.append(dlConItr.next());
		}
		assertThat(expected.toString(), is("12345"));
	}
}
