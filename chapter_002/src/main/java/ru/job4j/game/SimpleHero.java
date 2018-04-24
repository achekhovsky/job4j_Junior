package ru.job4j.game;

import java.awt.Point;

public interface SimpleHero extends Runnable {

	/**
	 * Character movement algorithm
	 */
	void motion();
	
	/**
	 * Checks the step size and throws an exception if the step fails validation
	 * @param x
	 * @param y
	 * @throws IncorrectStepSizeException
	 */
	void checkStepSize(int x, int y) throws IncorrectStepSizeException;
	
	/**
	 * Set a board on which will be placed the character with the initial coordinates.
	 * @param board - the board on which placing the character
	 * @param x - x-coordinate
	 * @param y - y-coordinate 
	 */
	void setBoard(AbstractBoard board, int x, int y);
	
	/**
	 * @return character type
	 */
	int getType();
	
	/**
	 * @return the board on which the hero is placed
	 */
	AbstractBoard getBoard();
	
	
	/**
	 * @return current position of the hero
	 */
	Point getCurrentPosition();
	
	/**
	 * Returns the state of character
	 * @return the state of character
	 */
	public boolean isLive();
	
	/**
	 * Stops the movement of the character 
	 */
	void killHero();
	
	/**
	 * Checks whether the transition to another cell is possible 
	 * and if possible, the hero moves to another cell
	 * @param x - step along the x axis
	 * @param y - step along the y axis
	 * @return true if step is taken
	 */
	public boolean doStep(int x, int y);
}
