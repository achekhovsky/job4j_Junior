package ru.job4j.set;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import ru.job4j.linkedlist.DynamicLinkedContainer;

/**
 * A collection that contains no duplicate elements.
 * Based on linked nodes.
 * @author achekhovsky
 * @version 1.0
 * @since 1.0
 * @param <T> type of objects
 */
public class SimpleLinkedSet<E> {
	private DynamicLinkedContainer<E> container;
	   
	/**
     * Constructs an empty set with an initial capacity of ten.
     */
	public SimpleLinkedSet() { 
		container = new DynamicLinkedContainer<>();
	}
	
    /**
     * Adds the specified element to this set if it is not already present
     *
     * @param element element to be appended to this container
     */
	public void add(E element) {
		if (!this.isDuplicates(element)) {
			this.container.add(element);
		}
	}

	/**
	 * Fail-fast iterator.If the set is structurally modified at any time 
	 * after the iterator is created, in any way except through the iterator's own
	 * methods, the iterator will throw a {@link ConcurrentModificationException}
	 */
	public Iterator<E> iterator() {
		return container.iterator();
	}
	
	
	/**
	 * Check this set for the presence of a duplicate specified element
	 * @param element - the element that checks
	 * @return true if duplicate
	 */
	private boolean isDuplicates(E element) {
		boolean result = false;
		Iterator<E> itr = this.iterator();
		while (itr.hasNext()) {
			if (element.equals(itr.next())) {
				result = true;
				break;
			}
		}
		return result;
	}
}
