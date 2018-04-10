package ru.job4j.list;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Resize container for objects with the ability to add and get items.
 * @author achekhovsky
 * @version 1.0
 * @since 1.0
 * @param <T> type of objects
 */
@ThreadSafe
public class DynamicContainer<E> implements Iterable<E> {
    private static final int DEFAULT_CAPACITY = 3;
    private static final Object[] EMPTY_CONTAINER = {};
    @GuardedBy("this")
    private Object[] elementBox; 
    private int size;
    @GuardedBy("this")
    private int index = 0;
    @GuardedBy("this")
    private int modCount = 0;
    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param  initialCapacity  the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *         is negative
     */
    public DynamicContainer(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementBox = new Object[initialCapacity];
            size = initialCapacity;
        } else if (initialCapacity == 0) {
            this.elementBox = EMPTY_CONTAINER;
            size = 0;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public DynamicContainer() {
        this.elementBox = EMPTY_CONTAINER;
        size = 0;
    }
    
    /**
     * Appends the specified element to the end of this container.
     *
     * @param e element to be appended to this container
     * @return true if success
     */
    public synchronized boolean add(E e) {
        this.checkCapacity();
        this.modCount++;
        elementBox[index++] = e;
        return true;
    }
    
	/**
	 * Returns the element at the specified position in this container
	 * @param index - specified index
	 * @return the element at the specified position in this container
	 * @throws IndexOutOfBoundsException if index is out of range
	 */
	@SuppressWarnings("unchecked")
    public synchronized E get(int inx) {
    	this.checkIndex(inx);
    	return (E) elementBox[inx];
    }
	
	/**
	 * Return container size
	 * @return container size
	 */
	public synchronized int getSize() {
		return elementBox.length;
	}
    
    /**
     * Doubled container capacity if current size not enough
     */
    private void increaseCapacity() {
    	this.modCount++;
    	
    	if (elementBox == EMPTY_CONTAINER) {
    		size = DEFAULT_CAPACITY;
    		elementBox =  Arrays.copyOf(elementBox, size);
    	} else {
    		size *= 2;
    		elementBox =  Arrays.copyOf(elementBox, size);
    	}
    }
    
    /**
     * Check container capacity and invoke increaseCapacity method
     * if current size not enough
     */
    private void checkCapacity() {
    	if (index + 1 >= size) {
    		this.increaseCapacity();
    	}
    }
    
    /**
     * Checks if the given index is in range.  If not, throws an appropriate
     * runtime exception.  This method does *not* check if the index is
     * negative: It is always used immediately prior to an array access,
     * which throws an ArrayIndexOutOfBoundsException if index is negative.
     */
	private void checkIndex(int index) {
		if (index >= elementBox.length) {
			throw new IndexOutOfBoundsException();
		}
	}
	
	/* 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		return new ConcurrentProtectedIterator(this.modCount);
	}

	/**
	 * Fail-fast iterator.If the container is structurally modified at any time 
	 * after the iterator is created, in any way except through the iterator's own
	 * methods, the iterator will throw a {@link ConcurrentModificationException}
	 * @author achekhovsky
	 * @version 1.0
	 * @since 1.0
	 */
	private class ConcurrentProtectedIterator implements Iterator<E> {
		private final int expectedModCount;
		private int index = 0;
		
		public ConcurrentProtectedIterator(int modCount) {
			expectedModCount = modCount;
		}
		
		/* 
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return this.index < DynamicContainer.this.size;
		}

		/* 
		 * @see java.util.Iterator#next()
		 */
		@Override
		public E next() {
			synchronized (DynamicContainer.this) {
				this.checkModification(DynamicContainer.this.modCount);	
			}
			if (this.hasNext()) {
				return (E) DynamicContainer.this.get(this.index++);	
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
}

