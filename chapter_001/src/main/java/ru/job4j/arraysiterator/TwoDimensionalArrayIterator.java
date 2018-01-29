package ru.job4j.arraysiterator;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Iterator for two dimensional array
 * @author achekhovsky
 *
 */
public class TwoDimensionalArrayIterator implements Iterator<Integer> {

	private int indexX = 0;
	private int indexY = 0;
	private int[][] iterableArray;
	
	
	/**
	 * The constructor in which the array is initialized
	 * @param ar - new array
	 */
	public TwoDimensionalArrayIterator(int[][] ar) {
		iterableArray = ar;
	}
	
	
	/* 
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		boolean result = true;
		if (indexX == iterableArray.length - 1 && indexY == iterableArray[indexX].length) {
			result = false;
		}
		
		return result;
	}

	/* 
	 * @see java.util.Iterator#next()
	 */
	@Override
	public Integer next() throws NoSuchElementException {
		int result;
			if (indexY < iterableArray[indexX].length) {
				result = iterableArray[indexX][indexY++];
			} else {
				if (indexX + 1 < iterableArray.length) {
					indexY = 0;
					result = iterableArray[++indexX][indexY++];
				} else {
					throw new NoSuchElementException();
				}
			}
		return result;
	}

}
