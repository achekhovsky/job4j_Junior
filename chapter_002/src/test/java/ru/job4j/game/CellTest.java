package ru.job4j.game;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

public class CellTest {
	private Cell cell;

	@Before
	public void setUp() throws Exception {
		cell = new Cell(false);
	}

	@Test
	public void ifHeroLocatedOnACellThenTrueIfHeLeftThenFalse() {
		cell.setHero(null);
		assertThat(cell.isLocked(), is(true));
		cell.removeHero();
		assertThat(cell.isLocked(), is(false));
	}
	
	@Test
	public void ifCellIsBarierAndHeroSteppedOnTheCellThenFalse() {
		Cell cell = new Cell(true);
		assertThat(cell.isLocked(), is(true));
		assertThat(cell.setHero(null), is(false));
	}
	
	@Test
	public void ifCellIsUnblockableThenPermanentlyBlocked() {
		Cell cell = new Cell(true);
		assertThat(cell.isLocked(), is(true));
		cell.removeHero();
		assertThat(cell.isLocked(), is(true));
	}
}
