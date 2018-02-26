package ru.job4j.loop;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for Node methods
 */
public class NodeTest {
	Node<Integer> first;
	Node<Integer> second;
	Node<Integer> third;
	Node<Integer> four;

	@Before
	public void setUp() throws Exception {
		first = new Node<Integer>(1);
		second = new Node<Integer>(2);
		third = new Node<Integer>(3);
		four = new Node<Integer>(4);
	}

	@Test
	public void ifLoopedThenTrue1() {
		first.next = second;
		second.next = third;
		third.next = four;
		four.next = first;
		assertThat(Node.hasCycle(first), is(true));
	}
	
	@Test
	public void ifLoopedThenTrue2() {
		first.next = first;
		assertThat(Node.hasCycle(first), is(true));
	}
	
	@Test
	public void ifNotLoopedThenFlase1() {
		first.next = second;
		second.next = null;
		third.next = four;
		assertThat(Node.hasCycle(first), is(false));
	}
	
	@Test
	public void ifNotLoopedThenFlase2() {
		first.next = second;
		second.next = third;
		third.next = four;
		assertThat(Node.hasCycle(first), is(false));
	}

}
