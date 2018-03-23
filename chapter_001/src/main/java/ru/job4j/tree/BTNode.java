package ru.job4j.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Node for BinarySearchTree
 * 
 * @param <E>
 */
public class BTNode<E extends Comparable<E>> {
	private final E value;
	private BTNode<E> leftChildren, rightChildren;

	public BTNode(final E value) {
		this.value = value;
	}

	/**
	 * Sets the left child
	 * 
	 * @param child
	 */
	public void setLeft(BTNode<E> child) {
		this.leftChildren = child;
	}

	/**
	 * Sets the right child
	 * 
	 * @param child
	 */
	public void setRight(BTNode<E> child) {
		this.rightChildren = child;
	}

	/**
	 * Returns the left child
	 * 
	 * @return the left child
	 */
	public BTNode<E> getLeft() {
		return this.leftChildren;
	}

	/**
	 * Returns the right child
	 * 
	 * @return the right child
	 */
	public BTNode<E> getRight() {
		return this.rightChildren;
	}

	/**
	 * Returns the value
	 * 
	 * @return the value
	 */
	public E getValue() {
		return this.value;
	}

	public boolean eqValue(E that) {
		return this.value.compareTo(that) == 0;
	}

}
