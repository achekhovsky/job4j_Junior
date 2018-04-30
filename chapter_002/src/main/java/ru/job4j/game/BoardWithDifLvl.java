package ru.job4j.game;

import java.awt.Point;
import java.util.Random;

public class BoardWithDifLvl extends AbstractBoard {
	public static final int HARD_DIFFICULTY = 4;
	public static final int MEDIUM_DIFFICULTY = 3;
	public static final int SIMPLE_DIFFICULTY = 2;
	public final int lvl;

	public BoardWithDifLvl(int w, int h, int difficultyLevel) {
		super(w, h);
		this.lvl = difficultyLevel;
	}
	
	@Override
	void initBoard() {
		for (int w = 0; w < this.width; w++) {
			for (int h = 0; h < this.height; h++) {
				this.board[w][h] = new Cell(this.generateCellState());
			}
		}		
	}

	@Override
	synchronized boolean tryMove(Point moveTo, AbstractHero hero) {
		boolean result = false;
		if (this.checkBorders(moveTo.x, moveTo.y)) {
			result = this.board[moveTo.x][moveTo.y].setHero(hero);
		}
		return result;
	}
	
	private boolean generateCellState() {
		return lvl >= (new Random()).nextInt(11);
	}

}
