package ru.job4j.game;

/**
 * It occured when the character con't placed on the board
 * @author achekhovsky
 * @version 0.1
 */
public class PlaceOnBoardException extends RuntimeException {
	private final String heroName;
	
	public PlaceOnBoardException(String name) {
		this.heroName = name;
	}

	@Override
	public String toString() {
		return String.format("PlaceOnBoardException: hero %s can't be placed on the board!", this.heroName);
	}
}
