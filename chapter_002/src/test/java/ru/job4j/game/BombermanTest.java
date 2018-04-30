package ru.job4j.game;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

import java.awt.Point;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;

public class BombermanTest {
	Bomberman bm;
	AbstractBoard board;

	@Before
	public void setUp() throws Exception {
		bm = new Bomberman("Bomberman", 1, 1);
		board = new Board(1, 2);
		bm.setBoard(board, 0, 1);
	}

	@Test
	public void ifMonsterStepOnCellWithTheBombermanThenBombermanDie() {
		Monster m = new Monster("Monster", 1, 1);
		m.setBoard(board, 0, 0);
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(bm);
		executor.execute(m);
		while (bm.isLive()) {
			bm.isLive();
		}
		executor.shutdown();
		assertThat(bm.isLive(), is(false));
	}

}
