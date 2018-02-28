package ru.job4j.set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for SimpleSet methods
 */
public class SimpleSetTest {
	private SimpleSet<String> st;

	@Before
	public void setUp() throws Exception {
		st = new SimpleSet<String>(3);
	}

	@Test
	public void ifAddThreeElementButOneIsDuplicateThenAddedOnlyTwo() {
		st.add("1");
		st.add("2");
		st.add("1");
		
		Iterator<String> itr = st.iterator();
		assertThat(itr.next(), is("1"));
		assertThat(itr.next(), is("2"));
		assertThat(itr.next(), nullValue());
	}
	
	@Test(expected = ConcurrentModificationException.class)
	public void ifCreateTwoIteratorsButFirtsIsNextAfterChangeInContainerThenConcurrentModification() {
		st.add("Str1");
		st.add("Str2");
		Iterator<String> iteratorTestWithConcurrent = st.iterator();
		assertThat(iteratorTestWithConcurrent.hasNext(), is(true));
		assertThat(iteratorTestWithConcurrent.hasNext(), is(true));
		assertThat(iteratorTestWithConcurrent.hasNext(), is(true));
		assertThat(iteratorTestWithConcurrent.next(), is("Str1"));
		st.add("Str3");
		Iterator<String> iteratorTestWithoutConcurrent = st.iterator();
		assertThat(iteratorTestWithoutConcurrent.next(), is("Str1"));
		assertThat(iteratorTestWithoutConcurrent.next(), is("Str2"));
		assertThat(iteratorTestWithoutConcurrent.next(), is("Str3"));
		iteratorTestWithConcurrent.next();
	}

}
