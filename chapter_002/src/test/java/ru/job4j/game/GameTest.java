package ru.job4j.game;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;

public class GameTest {
	private SimpleHero firstHero;
	private SimpleHero secondHero;
	private SimpleHero thirdHero;
	private SimpleHero fourthHero;
	Board board;
	
	@Before
	public void setUp() throws Exception {
		board = new Board(7, 7);
		firstHero = new Hero("first", CharacterTypes.MONSTER, 1, 1);
		firstHero.setBoard(board, 0, 0);
		secondHero = new Hero("second", CharacterTypes.MONSTER, 1, 1);
		secondHero.setBoard(board, 0, 1);
		thirdHero = new Hero("third", CharacterTypes.MONSTER, 1, 1);
		thirdHero.setBoard(board, 1, 0);
		fourthHero = new Hero("fourth", CharacterTypes.MONSTER, 1, 1);
		fourthHero.setBoard(board, 1, 1);
	}

	@Test
	public void ifTheBoardIsMuchLargerThanTheNumberOfHeroesThenEveryoneWillSurvive() {
		ExecutorService executor = Executors.newFixedThreadPool(4);
		executor.execute(firstHero);
		executor.execute(secondHero);
		executor.execute(thirdHero);
		executor.execute(fourthHero);
		try {
			Thread.sleep(2000);
			executor.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertThat(firstHero.isLive(), is(true));
		assertThat(secondHero.isLive(), is(true));
		assertThat(thirdHero.isLive(), is(true));
		assertThat(fourthHero.isLive(), is(true));
	}
	
	@Test 
	public void ifTheBoardSizeIsSameOfTheNumberOfHeroesThenSomeOneWillDie() {
		board = new Board(1, 2);
		firstHero = new Hero("first", CharacterTypes.MONSTER, 1, 1);
		firstHero.setBoard(board, 0, 0);
		secondHero = new Hero("second", CharacterTypes.MONSTER, 1, 1);
		secondHero.setBoard(board, 1, 1);
		SimpleHero[] heros = {firstHero, secondHero};
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(firstHero);
		executor.execute(secondHero);
		try {
			Thread.sleep(2000);
			executor.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int survivorsCounter = 0;
		for (SimpleHero hero : heros) {
			if (hero.isLive()) {
				survivorsCounter++;
			}
		}
		assertThat(survivorsCounter < heros.length, is(true));
	}
}
