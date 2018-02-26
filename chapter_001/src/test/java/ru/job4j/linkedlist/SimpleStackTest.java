package ru.job4j.linkedlist;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class SimpleStackTest {
	private SimpleStack<String> stack;

	@Before
	public void setUp() throws Exception {
		stack = new SimpleStack<String>();
	}

	@Test
	public void test() {
		stack.push("First str");
		stack.push("Second str");
		
		assertThat(stack.poll(), is("Second str"));
		assertThat(stack.poll(), is("First str"));
		assertThat(stack.poll(), nullValue());
	}
}
