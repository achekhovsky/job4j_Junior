package ru.job4j.tree;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * BinarySearchTree
 * 
 * @author achekhovsky
 * @version 1.0
 * @since 1.0
 * @param <E>
 */
public class BinarySearchTree<E extends Comparable<E>> implements Iterable<E> {
	private BTNode<E> root;

	public BinarySearchTree(E value) {
		root = new BTNode<E>(value);
	}

	/**
	 * Added new element (child) to the parent. If the child duplicates any element
	 * of the tree, it is not added.
	 * 
	 * @param value
	 *            - new value
	 * @return true if added, otherwise false
	 */
	public boolean add(E value) {
		boolean result = true;
		BTNode<E> currentNode = this.root;
		while (currentNode != null) {
			BTNode<E> nextNode = this.findBud(currentNode, currentNode.getValue().compareTo(value));
			if (currentNode != nextNode) {
				currentNode = nextNode;
			} else {
				break;
			}
		}
		if (currentNode != null) {
			this.germinate(currentNode, value);
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Return the root node
	 * 
	 * @return the root node
	 */
	public BTNode<E> getRoot() {
		return this.root;
	}

	/**
	 * Returns the next tree leaf to find the place where the new one will be added
	 * 
	 * @param currentLeaf
	 * @param compareResult
	 *            - the result of comparing the values of the current leaf with the
	 *            value that will be added
	 * @return the next tree leaf for search
	 */
	private BTNode<E> findBud(BTNode<E> currentLeaf, int compareResult) {
		if (compareResult > 0) {
			if (currentLeaf.getLeft() != null) {
				currentLeaf = currentLeaf.getLeft();
			}
		} else if (compareResult < 0) {
			if (currentLeaf.getRight() != null) {
				currentLeaf = currentLeaf.getRight();
			}
		} else {
			currentLeaf = null;
		}
		return currentLeaf;
	}

	/**
	 * Adds a sheet on the left or on the right, depending on the compare result
	 * 
	 * @param budge
	 *            - the leaf to add
	 * @param value
	 *            - value that will be added
	 */
	private void germinate(BTNode<E> budge, E value) {
		if (budge.getValue().compareTo(value) > 0) {
			budge.setLeft(new BTNode<E>(value));
		} else {
			budge.setRight(new BTNode<E>(value));
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new BSTIterator(this.root);
	}

	private class BSTIterator implements Iterator<E> {
		LinkedList<BTNode<E>> queue;

		public BSTIterator(BTNode<E> root) {
			queue = this.createQueue(root);
			queue.sort(new Comparator<BTNode<E>>() {
				@Override
				public int compare(BTNode<E> o1, BTNode<E> o2) {
					return o1.getValue().compareTo(o2.getValue());
				}
			});
		}

		@Override
		public boolean hasNext() {
			return queue.size() > 0;
		}

		@Override
		public E next() {
			return queue.poll().getValue();
		}

		private LinkedList<BTNode<E>> createQueue(BTNode<E> rt) {
			LinkedList<BTNode<E>> queue = new LinkedList<BTNode<E>>();
			queue.add(rt);
			for (int i = 0; i < queue.size(); i++) {
				if (queue.get(i).getLeft() != null) {
					queue.add(queue.get(i).getLeft());
				}
				if (queue.get(i).getRight() != null) {
					queue.add(queue.get(i).getRight());
				}
			}
			return queue;
		}
	}
}
