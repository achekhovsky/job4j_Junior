package ru.job4j.parallelsearch;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ru.job4j.parallelsearch.ParallelSearch;

public class ParallelSearchTest {
	ParallelSearch ps;

	@Before
	public void setUp() throws Exception {
		String directoryPath = ParallelSearchTest.class.getResource("").getPath();
		ps = new ParallelSearch(directoryPath, 
				this.getClass().getSimpleName(), Arrays.asList("class"));
	}

	@Test
	public void test() {
		ps.init();
		assertTrue(ps.result().size() > 0);
	}

}
