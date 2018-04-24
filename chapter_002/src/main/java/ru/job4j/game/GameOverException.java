package ru.job4j.game;

/**
 * It occured when the character can't moving
 * @author achekhovsky
 * @version 0.1
 */
public class GameOverException extends RuntimeException {
	private final String heroName;
	
	public GameOverException(String name) {
		this.heroName = name;
	}

	@Override
	public String toString() {
		return String.format("GameOverException: hero %s died!", this.heroName);
	}
}
