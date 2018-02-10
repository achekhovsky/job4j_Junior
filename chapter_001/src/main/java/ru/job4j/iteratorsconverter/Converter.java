package ru.job4j.iteratorsconverter;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator of iterators
 * @author achekhovsky
 */
public class Converter {
	private Iterator<Integer> currentIt;
	/**
	 * The general iterator for the iterator list
	 * @param itr - iterator of iterators
	 * @return general iterator
	 */
	public Iterator<Integer> convert(Iterator<Iterator<Integer>> itr) {
		return new Iterator<Integer>() {
			@Override
			public boolean hasNext() {
				if (currentIt == null && itr.hasNext() 
						|| !currentIt.hasNext() && itr.hasNext()) {
					currentIt = itr.next();
				}
				return currentIt.hasNext();
			}

			@Override
			public Integer next() throws NoSuchElementException {
				if (this.hasNext()) {
					return currentIt.next();
				} else {
					 throw new NoSuchElementException();
				}
			} };
	}

}
