package ru.job4j.linkedlist;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

import org.junit.Before;
import org.junit.Test;

public class SimpleQueueTest {
	private SimpleQueue<String> queue;

	@Before
	public void setUp() throws Exception {
		queue = new SimpleQueue<String>();
	}

	@Test
	public void test() {
		queue.push("First str");
		queue.push("Second str");
		
		assertThat(queue.poll(), is("First str"));
		assertThat(queue.poll(), is("Second str"));
		assertThat(queue.poll(), nullValue());
	}

}
