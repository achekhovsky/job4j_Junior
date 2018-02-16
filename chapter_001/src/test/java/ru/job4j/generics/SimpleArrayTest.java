package ru.job4j.generics;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for SimpleArray.class methods
 */
public class SimpleArrayTest {

	SimpleArray<String> testArray;

	@Before 
	public void setUp() {
		testArray = new SimpleArray<String>(3);
	}
	
	@Test
	public void ifAddThreeElementsAndGetIts() {
		testArray.add("1");
		testArray.add("2");
		testArray.add("3");
		assertThat(testArray.get(0), is("1"));
		assertThat(testArray.get(1), is("2"));
		assertThat(testArray.get(2), is("3"));
	}
	
	@Test
	public void ifAddFourElementsButStartSizeIsThreeThenSizeIsFour() {
		testArray.add("1");
		testArray.add("2");
		testArray.add("3");
		testArray.add("4");
		assertThat(testArray.toString(), is("SimpleArray [1, 2, 3, 4]"));
	}
	
	@Test
	public void ifDeleteSecondThenRemainTheFirstAndThird() {
		testArray.add("1");
		testArray.add("2");
		testArray.add("3");
		testArray.delete(1);
		assertThat(testArray.toString(), is("SimpleArray [1, 3]"));
	}
	
	@Test
	public void ifAddOneAndTwoAndSetThemThreeAndFourThenThreeAndFour() {
		testArray.add("1");
		testArray.add("2");
		testArray.set(0, "3");
		testArray.set(1, "4");
		assertThat(testArray.get(0), is("3"));
		assertThat(testArray.get(1), is("4"));
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void ifGetIndexIsOutOfRange() {
		testArray.get(3);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void ifSetIndexIsOutOfRange() {
		testArray.set(3, "1");
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void ifDeleteIndexIsOutOfRange() {
		testArray.get(3);
	}
	
	@Test
	public void ifTheIteratorPassedAllElementsThenTwoThreeFour() {
		testArray.add("2");
		testArray.add("3");
		testArray.add("4");
		Iterator<String> itr = testArray.iterator();
		StringBuilder expected = new StringBuilder();
		while (itr.hasNext()) {
			expected.append(itr.next());
		}
		assertThat(expected.toString(), is("234"));
	}
}
