package ru.job4j.primeit;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Primitive integer iterator
 * @author achekhovsky
 *
 */
public class PrimeIt implements Iterator<Integer> {
	
	private final int[] numbers;
	private int index = 0;
	
	/**
	 * The constructor in which the array is initialized
	 * @param numbers - new array
	 */
	public PrimeIt(int[] numbers) {
		this.numbers = numbers; 
	}

	/* 
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		boolean has = false;
		for (int i = this.index; i < this.numbers.length; i++) {
			if (this.isPrimitive(this.numbers[i])) {
				this.index = i;
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
		return this.numbers[this.index++];
	}
	
	/**
	 * Check the number for primitivity
	 * @param number - number to check
	 * @return true if number is primitive, otherwise false
	 */
	private boolean isPrimitive(int number) {
		boolean result = true;
		for (int i = 1; i <= Math.ceil(Math.sqrt(number)); i += 2) {
			if (number == 1 
					|| (number % 2 == 0 && number != 2) 
					|| (number % i == 0 && i != 1)) {
				result = false;
				break;
			}
		}
		return result;
	}

}
