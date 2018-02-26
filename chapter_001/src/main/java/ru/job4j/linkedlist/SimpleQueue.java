package ru.job4j.linkedlist;


/**
 * Order elements in a FIFO (first-in-first-out) manner. 
 * @author achekhovsky
 * @version 1.0
 * @since 1.0
 * @param <E> type of objects
 */
public class SimpleQueue<T> {
	private DynamicLinkedContainer<T> container;
	private int size = -1;
	
	public SimpleQueue() {
		container = new DynamicLinkedContainer<T>();
	}
	
	
	/**
	 * Retrieves and removes the head of this queue, or returns null if this queue is empty.
	 * @return the head of this queue, or returns null if this queue is empty
	 */
	public T poll() {
		T result = null;
		if (size-- >= 0) {
			result = container.get(0);
			container.unlinkFirst();
		}
		return result;
	}
	
	/**
	 * Inserts the specified element into this queue.
	 * @param value - the element to add
	 */
	public void push(T value) {
		container.add(value);
		size++;
	}
}
