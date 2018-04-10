package ru.job4j.linkedlist;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Linked container for objects with the ability to add, get etc.
 * @author achekhovsky
 * @version 1.1
 * @since 1.0
 * @param <E> type of objects
 */
@ThreadSafe
public class DynamicLinkedContainer<E> implements Iterable<E> {
	private int size = 0;
	@GuardedBy("this")
	private Node<E> firstElement;
	@GuardedBy("this")
	private Node<E> lastElement;
	@GuardedBy("this")
    private int modCount = 0;
   
    /**
     * Link the specified element to the end of this container.
     * @param element element to be appended to this container
     */
	public synchronized void add(E element) {
		if (firstElement == null) {
			this.firstInit(element);
		} else {
			this.linkLast(element);
		}
	}
	
    /**
     * First link in this container.
     * @param element element to be appended to this container
     */
	private void firstInit(E element) {
		firstElement = new Node<E>(null, element, null);
		size++;
		modCount++;
	}
	
    /**
     * Link the specified element to the end of this container.
     * @param element element to be appended to this container
     */
	private void linkLast(E element) {
		if (this.lastElement == null) {
			this.lastElement = new Node<E>(this.firstElement, element, null);
			this.firstElement.next = this.lastElement;
		} else {
			Node<E> newElement = new Node<E>(this.lastElement, element, null);
			this.lastElement.next = newElement;
			this.lastElement = newElement;
		}
		size++;
		modCount++;
	}
	
    /**
     * Unlink the specified element from the end of this container.
     */
	public synchronized void unlinkLast() {
		if (size > 1) {
			this.lastElement = this.lastElement.prev;
			this.lastElement.next = null;
			size--;
			modCount++;				
		} else {
			this.unlinkFirst(); 
		}

	}
	
    /**
     * Link the specified element to the head of this container.
     * @param element element to be added to this container
     */
	public synchronized void linkFirst(E element) {
		if (size > 0) {
			this.firstElement.prev = new Node<E>(null, element, this.firstElement);
			this.firstElement = this.firstElement.prev;			
		} else {
			this.firstInit(element);
		}
		size++;
		modCount++;
	}
	
    /**
     * Unlink the specified element from the head of this container.
     */
	public synchronized void unlinkFirst() {
		if (size > 0) {
			if (this.firstElement.next == null) {
				this.firstElement = null;
			} else {
				this.firstElement = this.firstElement.next;
				this.firstElement.prev = null;
			}
			size--;
			modCount++;						
		}
	}
	
    /**
     * Return element with the specified index of this container.
     * @param element element to be appended to this container
     * @return true if success
     * @throws NullPointerException if container is empty
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
	public synchronized E get(int index) {
		Node<E> resultNode = this.firstElement;
		if (index < size &&  index > 0) {
			for (int i = 1; i <= index; i++) {
				resultNode = resultNode.next;
			}
		} else {
			if (index != 0) {
				throw new IndexOutOfBoundsException();
			}
		}
		return resultNode.item;
	}
	
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new ConcurrentProtectedIterator(this.modCount);
	}
	
	/**
	 * Fail-fast iterator.If the container is structurally modified at any time 
	 * after the iterator is created, in any way except through the iterator's own
	 * methods, the iterator will throw a {@link ConcurrentModificationException}
	 * @author achekhovsky
	 * @version 1.1
	 * @since 1.0
	 */
	private class ConcurrentProtectedIterator implements Iterator<E> {
		private final int expectedModCount;
		private Node<E> element = null;
		
		public ConcurrentProtectedIterator(int modCount) {
			expectedModCount = modCount;
		}
		
		/* 
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			boolean result = false;
			if (element == null && DynamicLinkedContainer.this.firstElement != null 
					|| element != null && element.next != null) {
				result = true;
			}
			return result;
		}

		/* 
		 * @see java.util.Iterator#next()
		 */
		@Override
		public E next() {
			synchronized (DynamicLinkedContainer.this) {
				this.checkModification(DynamicLinkedContainer.this.modCount);	
			}
			
			if (this.hasNext()) {
				if (element == null) {
					element = DynamicLinkedContainer.this.firstElement;
				} else {
					element = element.next;
				}
				return element.item;	
			} else {
				throw new NoSuchElementException();
			}
		}
		
		/**
		 * Compare current modifications counter with the counter
		 * after the iterator is created
		 * @param mCount
		 */
		private void checkModification(int mCount) {
			if (expectedModCount != mCount) {
				throw new ConcurrentModificationException();
			}
		}
	}
	
    /**
     * Node for DynamicLinkedContainer
     * @param <E>
     */
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
