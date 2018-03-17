package ru.job4j.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

/**
 * SimpleTree
 * @author achekhovsky
 * @version 1.0
 * @since 1.0
 * @param <E>
 */
public class SimpleTreeImplementation<E extends Comparable<E>> implements SimpleTree<E> {
	
	private Node<E> root;

	public SimpleTreeImplementation(E firstElement) {
		root = new Node<E>(firstElement); 
	}
	
	/* 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		return new SimpleTreeIterator();
	}

	/**
	 * Added new element (child) to the parent. If parent is absent, 
	 * then child becomes the parent. 
	 * If the child duplicates any element of the tree, it is not added.
	 * @param parent
	 * @param child
	 * @return true if added, otherwise false
	 */
	@Override
	public boolean add(E parent, E child) {
		boolean result = false;
		if (!this.findBy(child).isPresent()) {
			Node<E> newNode = new Node<>(child);
			Optional<Node<E>> parentOpt = this.findBy(parent);
			if (parentOpt.isPresent()) {
				parentOpt.get().add(newNode);
				result = true;
			} 
		} 
		return result;
	}

	/** 
	 * Finds the element of the tree by its value
	 * @param - the value of the element to be searched for
	 * @return if element is found then return Optional with 
	 * Node, otherwise empty Optional
	 */
	@Override
	public Optional<Node<E>> findBy(E value) {
	    Optional<Node<E>> rsl = Optional.empty();
	    Queue<Node<E>> data = new LinkedList<>();
	    if (root != null) {
	    	data.offer(this.root);
	    }
	    while (!data.isEmpty()) {
	        Node<E> el = data.poll();
	        if (el.eqValue(value)) {
	            rsl = Optional.of(el);
	            break;
	        }
	        for (Node<E> child : el.leaves()) {
	            data.offer(child);
	        }
	    }
	    return rsl;
	}
	
	/**
	 * Simple iterator for SimpleTree
	 * @author achekhovsky
	 */
	private class SimpleTreeIterator implements Iterator<E> {
	    Queue<Node<E>> nodes = new LinkedList<>();
	    
	    SimpleTreeIterator() {
	    	nodes.add(root);
	    }
		
		@Override
		public boolean hasNext() {
			return nodes.size() > 0;
		}

		@Override
		public E next() {
			Node<E> el = nodes.poll();
	        for (Node<E> child : el.leaves()) {
	        	nodes.offer(child);
	        }
			return el.getValue();
		}
		
	}

}
