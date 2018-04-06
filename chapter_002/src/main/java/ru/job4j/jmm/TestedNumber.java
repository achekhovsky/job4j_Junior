package ru.job4j.jmm;

/**
 * Java Bean для иллюстрации проблем с многопоточностью 
 * @author achekhovsky	
 * @version 0.1
 * @since 0.1
 */
public class TestedNumber {
	private int testedNumber;
	
	public TestedNumber(int value) {
		this.testedNumber = value;
	}
	
	/**
	 * @return testedNumber
	 */
	public int getValue() {
		return this.testedNumber;
	}
	
	/**
	 * @param newValue - новое значение для testedNumber
	 */
	public void setValue(int newValue) {
		this.testedNumber = newValue;
	}
}


