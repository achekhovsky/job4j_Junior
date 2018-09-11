package ru.job4j.list;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class FailSafeContainerTest {
	private FailSafeContainer<String> fsc;

	@Before
	public void setUp() throws Exception {
		fsc = new FailSafeContainer<>();
		fsc.add("first value");
		fsc.add("second value");
		fsc.add("third value");
	}

	@Test
	public void ifContainerChangedAfterIteratorWasCreatedThenNothing() {
		Iterator<String> itr = fsc.iterator();
		assertThat(itr.next(), is("first value"));
		fsc.add("fourth value");
		assertThat(itr.next(), is("second value"));
		fsc.add("fifth value");
		assertThat(itr.next(), is("third value"));
		assertNull(itr.next());
	}

}
