package ru.job4j.game;

import static org.junit.Assert.*;

import java.awt.Point;

import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	private Board board;

	@Before
	public void setUp() throws Exception {
		board = new Board(2, 2);
	}

	@Test
	public void ifInitThenAllCellsUnlocked() {
		boolean result = false;
		for (Cell[] cells : board.board) {
			for (Cell cell : cells) {
				if (cell.isLocked()) {
					result = true;
					break;
				}
			}
		}
		assertThat(result, is(false));
	}
	
	@Test
	public void ifTryMoveOnAFreeCellThenTrueOtherwiseFalse() {
		assertThat(board.tryMove(new Point(0, 0), null), is(true));
		assertThat(board.tryMove(new Point(0, 0), null), is(false));
	}
	
	@Test
	public void ifCellIsFreedThenTryMoveIsTrue() {
		Point point = new Point(0, 0);
		assertThat(board.tryMove(point, null), is(true));
		assertThat(board.tryMove(point, null), is(false));
		board.makeCellFree(point);
		assertThat(board.tryMove(point, null), is(true));
	}
	
	@Test
	public void ifItGoesBeyondTheBoard() {
		assertThat(board.checkBorders(1, 1), is(true));
		assertThat(board.tryMove(new Point(2, 2), null), is(false));
	}
	
}
