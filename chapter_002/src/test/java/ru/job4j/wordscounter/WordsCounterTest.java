package ru.job4j.wordscounter;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;

public class WordsCounterTest {
	private int numberOfSpaces;
	private int numberOfWords;

	
	@Before
	public void setUp() throws Exception {
		WordsCounter wc;
		ExecutorService pool;
		Future<Integer> words;
		Future<Integer> spaces;

		wc = new WordsCounter("Like a byte buffer, a char buffer is either"
		+ " direct or non-direct. A char buffer created via the "
		+ "wrap methods of this class will be non-direct. A char "
		+ "buffer created as a view of a byte buffer will be "
		+ "direct if, and only if, the byte buffer itself is direct. "
		+ "Whether or not a char buffer is direct may be determined "
		+ "by invoking the isDirect method.");
		pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		words = pool.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				return wc.wordsCounter();
			}
		});
		spaces = pool.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				return wc.spacesCounter();
			}
		});
		try {
			numberOfWords = words.get();
			numberOfSpaces = spaces.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pool.shutdown();
	}
	

	@Test
	public void ifCountNumberOfSpacesThenSixtyFive() {
		assertThat(numberOfSpaces, is(65));
	}
	
	@Test
	public void ifCountNumberOfWordsThenSixtySix() {
		assertThat(numberOfWords, is(66));
	}
}
