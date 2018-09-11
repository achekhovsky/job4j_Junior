package ru.job4j.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Resize container for objects with the ability to add and get items
 * and fail-safe iterator.
 * @author achekhovsky
 * @version 1.0
 * @since 1.0
 * @param <T> type of objects
 */
@ThreadSafe
public class FailSafeContainer<E> extends DynamicContainer<E> {
	private final ReentrantLock lock = new ReentrantLock();
	@GuardedBy("lock")
	private E[] snapshot;
	
	public FailSafeContainer() {
		super();
	}
	
	public FailSafeContainer(int initialCapacity) {
		super(initialCapacity);
	}
	
	@Override
	public boolean add(E e) {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			boolean result = super.add(e);
			this.takeSnapshot();
			return result;
		} finally {
			lock.unlock();
		}
	}
	
	/* 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		return Arrays.asList(this.snapshot).iterator();
	}
	
	/**
	 * Takes a snapshot of the array. When the array is being copied, 
	 * a lock occurs.
	 * @return Copied array
	 */
	@SuppressWarnings("unchecked")
	private void takeSnapshot() {
		E[] snap = (E[]) new Object[this.getSize()];
		for (int i = 0; i < this.getSize(); i++) {
			snap[i] = this.get(i);
		}
		this.snapshot = Arrays.copyOf(snap, snap.length);
	}
}
