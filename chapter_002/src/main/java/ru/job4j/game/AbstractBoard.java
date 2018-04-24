package ru.job4j.game;

import java.awt.Point;

/**
 * Abstract board
 * @author achekhovsky
 */
public abstract class AbstractBoard {
	public final Cell[][] board;
	public final int width;
	public final int height;
	
	public AbstractBoard(int width, int height) {
		this.width = width;
		this.height = height;
		this.board = new Cell[width][height];
		this.initBoard();
	}
	
	/**
	 * Initializing a board with special parameters
	 */
	abstract void initBoard();
	
	/**
	 * Checking the possibility of moving the character to the cell
	 * @param moveTo - coordinates of the new cell
	 * @return true if if moving is possible
	 */
	abstract boolean tryMove(Point moveTo, AbstractHero hero);
	
	/**
	 * Make cell free 
	 * @param cell - cell for release
	 */
	public void makeCellFree(Point cell) {
		if (this.checkBorders(cell.x, cell.y)) {
			this.board[cell.x][cell.y].removeHero();
		}
	}
	
	/**
	 * Checks borders of the board 
	 * @param x
	 * @param y
	 * @return true if doesn't go beyond the borders
	 */
	public boolean checkBorders(int x, int y) {
		return (x >= 0 && y >= 0 && x < this.width && y < this.height) ? true : false;
	}

	public synchronized void showBoard() {
		for (int w = 0; w < this.width; w++) {
			for (int h = 0; h < this.height; h++) {
				System.out.printf("  [%s][%s](%s)", w, h, this.board[w][h].isLocked());
			}
			System.out.println();
		} 
		System.out.println();
	}
}
