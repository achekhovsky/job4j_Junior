package ru.job4j.waitnotify;

import java.util.LinkedList;
import java.util.Queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Consumer for SimpleBlockingQueue
 * @author achekhovsky
 * @version 0.1
 * @since 0.1
 * @param <T>
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
	@GuardedBy("this")
	private Queue<T> queue = new LinkedList<>();
	final int maxSize;
	
	public SimpleBlockingQueue(int size) {
		this.maxSize = size;
	}

	/**
	 * Adds the specified element as the tail (last element) of this queue.
	 * @param value - specified element for adding
	 */
	public void offer(T value) {
		synchronized (this) {
			while (this.maxSize <= this.queue.size()) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.queue.offer(value);
			this.notifyAll();
		}
	}

	/**
	 * Retrieves and removes the head (first element) of this queue.
	 * @return The first element of this queue
	 */
	public T poll() {
		synchronized (this) {
			while (this.queue.size() < 1) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.notifyAll();
			return this.queue.poll();
		}
	}
	
	
	/**
	 * Returns the number of elements in this queue.
	 * @return the number of elements
	 */
	public synchronized int getSize() {
		return this.queue.size();
	}
}
