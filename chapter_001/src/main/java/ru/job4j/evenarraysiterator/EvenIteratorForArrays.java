package ru.job4j.evenarraysiterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EvenIteratorForArrays implements Iterator<Integer> {

	private int index = 0;
	private int[] iterableArray;
	
	
	/**
	 * The constructor in which the array is initialized
	 * @param ar - new array
	 */
	public EvenIteratorForArrays(int[] ar) {
		iterableArray = ar;
	}
	
	
	/* 
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		boolean has = false;
		for (int i = index; i < iterableArray.length; i++) {
			if (iterableArray[i] % 2 == 0) {
				index = i;
				has = true;
				break;
			}
		}
		return has;
	}

	/* 
	 * @see java.util.Iterator#next()
	 */
	@Override
	public Integer next() throws NoSuchElementException { 
		if (!this.hasNext()) {
			throw new NoSuchElementException();
		}
		return this.iterableArray[this.index++];
	}
}
