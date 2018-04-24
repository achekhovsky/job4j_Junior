package ru.job4j.game;

import java.awt.Point;

/**
 * Simple board without the choice difficulty level.
 *
 * @author achekhovsky
 * @version 0.1
 */
public class Board extends AbstractBoard {
	
	public Board(int width, int height) {
		super(width, height);
	}
	
	/* 
	 * @see ru.job4j.game.AbstractBoard#initBoard()
	 */
	@Override
	public void initBoard() {
		for (int w = 0; w < this.width; w++) {
			for (int h = 0; h < this.height; h++) {
				this.board[w][h] = new Cell(false);
			}
		}
	}
	
	/* 
	 * @see ru.job4j.game.AbstractBoard#tryMove(java.awt.Point)
	 */
	@Override
	public boolean tryMove(Point moveTo, AbstractHero hero) {
		boolean result = false;
		if (this.checkBorders(moveTo.x, moveTo.y)) {
			result = this.board[moveTo.x][moveTo.y].setHero(hero);
		}
		return result;
	}
}
