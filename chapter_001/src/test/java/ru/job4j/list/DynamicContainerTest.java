package ru.job4j.list;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for DynamicContainer methods
 */
public class DynamicContainerTest {
	private DynamicContainer<String> dCon;

	@Before
	public void setUp() throws Exception {
		dCon = new DynamicContainer<String>(2);
	}
	
	@Test
	public void ifElementsAreLagerThenCapacityIsDoubled() {
		assertThat(dCon.getSize(), is(2));
		dCon.add("Str1");
		dCon.add("Str2");
		dCon.add("Str3");
		assertThat(dCon.getSize(), is(4));
		dCon.add("Str4");
		dCon.add("Str5");
		assertThat(dCon.getSize(), is(8));
	}
	
	@Test
	public void ifAddValueThenGetValue() {
		dCon.add("Value");
		assertThat(dCon.get(0), is("Value"));
	}
	
	@Test(expected = ConcurrentModificationException.class)
	public void ifCreateTwoIteratorsButFirtsIsNextAfterChangeInContainerThenConcurrentModification() {
		dCon.add("Str1");
		dCon.add("Str2");
		Iterator<String> iteratorTestWithConcurrent = dCon.iterator();
		assertThat(iteratorTestWithConcurrent.hasNext(), is(true));
		assertThat(iteratorTestWithConcurrent.hasNext(), is(true));
		assertThat(iteratorTestWithConcurrent.hasNext(), is(true));
		assertThat(iteratorTestWithConcurrent.next(), is("Str1"));
		dCon.add("Str3");
		Iterator<String> iteratorTestWithoutConcurrent = dCon.iterator();
		assertThat(iteratorTestWithoutConcurrent.next(), is("Str1"));
		assertThat(iteratorTestWithoutConcurrent.next(), is("Str2"));
		assertThat(iteratorTestWithoutConcurrent.next(), is("Str3"));
		iteratorTestWithConcurrent.next();
	}
	


}
