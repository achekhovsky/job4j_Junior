package ru.job4j.set;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for SimpleHashSet methods
 */
public class SimpleHashSetTest {
	private SimpleHashSet<Integer>  shs;

	@Before
	public void setUp() throws Exception {
		shs = new SimpleHashSet<Integer>();
	}

	@Test
	public void ifAddedTwoTwiceThenAddedOnlyFirst() {
		assertThat(shs.add(2), is(true));
		assertThat(shs.add(2), is(false));
	}
	
	@Test
	public void ifAddedThreeThenContainsThree() {
		shs.add(3);
		assertThat(shs.contains(2), is(false));
		assertThat(shs.contains(3), is(true));
	}
	
	@Test
	public void ifAddedOneAndRemoveThenHashSetIsEmpty() {
		shs.add(1);
		assertThat(shs.remove(2), is(false));
		assertThat(shs.remove(1), is(true));
		assertThat(shs.contains(1), is(false));		
	}

}
