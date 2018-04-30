package ru.job4j.game;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

public class BoardWithDifLvlTest {
	private BoardWithDifLvl board;

	@Before
	public void setUp() throws Exception {
		board =  new BoardWithDifLvl(10, 10, BoardWithDifLvl.HARD_DIFFICULTY);
	}

	@Test
	public void ifBoardWithDifLvlThenLockedCellsArePresent() {
		int lockedCounter = 0;
		for (Cell[] cells : board.board) {
			for (Cell cell : cells) {
				if (cell.isHeldByCurrentThread()) {
					lockedCounter++;
				}
			}
		}	
		assertThat(lockedCounter > 0, is(true));
	}

}
