package ru.job4j.generics;

import java.util.Arrays;
import java.util.Iterator;

/**
 * A store for objects with the ability to add, change and delete items.
 * @author achekhovsky
 * In this version added ability to get the length of array and check the array for the content of the specified element
 * @version 1.1
 * @since 1.0
 * @param <T> type of objects
 */
public class SimpleArray<T> implements Iterable<T> {
	
	private Object[] ar;
	private int index = 0;
	
	public SimpleArray(int size) {
		ar = new Object[size];
	}
	
    /**
     * Appends the specified element to the end of this array.
     * @param e element to be appended to this list
     * @return true
     */
	public boolean add(T model) {
		if (index < ar.length) {
			ar[index++] = model;
		} else { 
			ar = Arrays.copyOf(ar, ar.length + 1);
			ar[index++] = model;
		}
		return true;
	}        

   /**
    * Replaces the element at the specified position in this array with
    * the specified element.
    *
    * @param index index of the element to replace
    * @param element element to be stored at the specified position
    * @return the element previously at the specified position
    * @throws IndexOutOfBoundsException if index is out of range
    */
	public void set(int index, T model) throws ArrayIndexOutOfBoundsException  {
			ar[index] = model;
	}
	 
    /**
     * Remove the element at the specified position in this array
     * @throws ClassCastException {@inheritDoc}
     * @throws IndexOutOfBoundsException if index is out of range
     */
	@SuppressWarnings("unchecked")
	public boolean delete(int index) {
		checkIndex(index);
		T[] bufAr = (T[]) new Object[ar.length - 1];
		ar[index] = null;
		if (index == 0 || index == ar.length - 1) {
			System.arraycopy(ar, ((ar.length - 1) - index) / (ar.length - 1), bufAr, 0, ar.length - 1);
		} else {
			System.arraycopy(ar, 0, bufAr, 0, index);
			System.arraycopy(ar, index + 1, bufAr, index, ar.length - index - 1);
		}
		ar = bufAr;
		this.index--;
		return true;
	}

	/**
	 * Returns the element at the specified position in this array
	 * @param index - specified index
	 * @return the element at the specified position in this array
	 * @throws IndexOutOfBoundsException if index is out of range
	 */
	@SuppressWarnings("unchecked")
	public T get(int index) {
		checkIndex(index);
		return (T) ar[index];
	}
	
	/**
	 * Checks if the array contains the specified element
	 * @param item - item to search  for
	 * @return true if contain, otherwise false 
	 */
	public boolean contain(T item) {
		boolean result = false;
		for (int i = 0; i < index; i++) {
			if (ar[i].equals(item)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Return array size
	 * @return array size
	 */
	public int getSize() {
		return index;
	}

	/* 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Iterator<T> iterator() {
		return (Iterator<T>) Arrays.asList(ar).iterator();
	}
	
	
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("SimpleArray %s", Arrays.toString(ar));
	}
	
    /**
     * Checks if the given index is in range.  If not, throws an appropriate
     * runtime exception.  This method does *not* check if the index is
     * negative: It is always used immediately prior to an array access,
     * which throws an ArrayIndexOutOfBoundsException if index is negative.
     */
	private void checkIndex(int index) {
		if (index >= ar.length) {
			throw new IndexOutOfBoundsException();
		}
	}
}
