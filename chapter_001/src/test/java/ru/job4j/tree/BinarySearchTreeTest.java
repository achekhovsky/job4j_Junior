package ru.job4j.tree;

import static org.junit.Assert.*;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for BinarySearchTree
 */
public class BinarySearchTreeTest {
	BinarySearchTree<Integer> bst;

	@Before
	public void setUp() throws Exception {
		bst = new BinarySearchTree<>(20);
		bst.add(15);
		bst.add(25);
		bst.add(13);
		bst.add(18);
		bst.add(22);
		bst.add(27);
		bst.add(11);
		bst.add(14);
		bst.add(21);
		bst.add(24);
	}

	@Test
	public void checksTheCorrectnessOfTheTree() {
		BTNode<Integer> root = bst.getRoot();
		assertThat(root.getRight().getValue(), is(25));
		assertThat(root.getLeft().getValue(), is(15));
		assertThat(root.getRight().getRight().getValue(), is(27));
		assertThat(root.getRight().getLeft().getValue(), is(22));
		assertThat(root.getLeft().getLeft().getValue(), is(13));
		assertThat(root.getLeft().getLeft().getRight().getValue(), is(14));
		assertThat(root.getLeft().getLeft().getLeft().getValue(), is(11));
	}

	@Test
	public void ifIteratorWorksCorrectlyThenAllElementsAreSorted() {
		Iterator<Integer> itr = bst.iterator();
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(11));
		assertThat(itr.next(), is(13));
		assertThat(itr.next(), is(14));
		assertThat(itr.next(), is(15));
		assertThat(itr.next(), is(18));
		assertThat(itr.next(), is(20));
		assertThat(itr.next(), is(21));
		assertThat(itr.next(), is(22));
		assertThat(itr.next(), is(24));
		assertThat(itr.next(), is(25));
		assertThat(itr.next(), is(27));
		assertThat(itr.hasNext(), is(false));
	}

	@Test
	public void ifAddedSameElementThenFalse() {
		BinarySearchTree<Integer> tree = new BinarySearchTree<>(20);
		assertThat(tree.add(15), is(true));
		assertThat(tree.add(15), is(false));
		assertThat(tree.add(16), is(true));
	}
}
