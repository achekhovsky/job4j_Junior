package ru.job4j.game;

import java.awt.Point;

/**
 * Abstract hero.
 * @author achekhovsky
 * @version 0.1
 */
public abstract class AbstractHero implements SimpleHero {
	public final String name;
	public final int type;
	public final int stepSizeX;
	public final int stepSizeY;	
	private boolean isLive = true;
	private Point currentPosition;
	private AbstractBoard board;
	
	/**
	 * Initial hero with the specified name, type and step size.
	 * @param name - hero name
	 * @param heroType - character type
	 * @param stepSizeX - step size
	 * @param stepSizeY - step size
	 */
	public AbstractHero(String name, int heroType, int stepSizeX, int stepSizeY) {
		this.name = name;
		this.type = heroType;
		this.stepSizeX = stepSizeX;
		this.stepSizeY = stepSizeY;
	}
	

	public void setBoard(AbstractBoard board, int x, int y) {
		this.board = board;
		this.currentPosition = new Point(x, y);
	}
	
	@Override
	public void run() {
		try {
			this.placeOnTheBoard(4);
			while (isLive) {
				motion();
			}
		} finally {
			this.board.makeCellFree(this.currentPosition);
		}
	}
	
	public int getType() {
		return this.type;
	} 
	
	public synchronized AbstractBoard getBoard() {
		return this.board;
	}
	
	public synchronized Point getCurrentPosition() {
		return this.currentPosition;
	}
	
	public synchronized boolean isLive() {
		return this.isLive;
	}
	
	public void killHero() {
		this.isLive = false;
	}
	
	public boolean doStep(int x, int y) {
		this.checkStepSize(x, y);
		Point moveTo = new Point(this.currentPosition.x + x, this.currentPosition.y + y);
		boolean success = false;
		if (this.board.tryMove(moveTo, this)) {
			this.board.makeCellFree(this.currentPosition);
			this.currentPosition = moveTo;
			success = true;
		}
		return success;
	}	
	
	/**
	 * Attempt to place the hero on the board
	 * @throws PlaceOnBoardException if not possible to place the hero on the board
	 */
	private void placeOnTheBoard(int numberOfAttempts) throws PlaceOnBoardException {
		for (int i = 0; i < numberOfAttempts && this.isLive; i++) {
			if (board.tryMove(this.currentPosition, this)) {
				return;
			}
		}
		this.isLive = false;
		throw new PlaceOnBoardException(this.name);
	}
}
