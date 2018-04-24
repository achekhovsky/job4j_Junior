package ru.job4j.game;

/**
 * It occured when the character's step failed
 * @author achekhovsky
 * @version 0.1
 */
public class IncorrectStepSizeException extends RuntimeException {
	private final String heroName;
	
	public IncorrectStepSizeException(String name) {
		this.heroName = name;
	}

	@Override
	public String toString() {
		return String.format("IncorrectStepSizeException: hero %s made the wrong move!", this.heroName);
	}
}
