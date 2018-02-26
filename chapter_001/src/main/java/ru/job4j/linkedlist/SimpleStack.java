package ru.job4j.linkedlist;

/**
 * Order elements in a LIFO (last-in-first-out) manner. 
 * @author achekhovsky
 * @version 1.0
 * @since 1.0
 * @param <E> type of objects
 */
public class SimpleStack<T> {
	private DynamicLinkedContainer<T> container;
	private int size = -1;
	
	public SimpleStack() {
		container = new DynamicLinkedContainer<T>();
	}
	
	
	/**
	 * Retrieves and removes the tail of this stack, or returns null if this stack is empty.
	 * @return the tail of this stack, or returns null if this stack is empty
	 */
	public T poll() {
		T result = null;
		if (size >= 0) {
			result = container.get(size--);
			container.unlinkLast();
		}
		return result;
	}
	
	/**
	 * Inserts the specified element into this stack.
	 * @param value - the element to add
	 */
	public void push(T value) {
		container.add(value);
		size++;
	}
}
