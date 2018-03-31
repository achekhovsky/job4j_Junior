package ru.job4j.wordscounter;

import java.nio.CharBuffer;
import java.util.concurrent.Semaphore;

/**
 * Подсчет количества слов и пробелов в тексте
 * @author achekhovsky
 * @version 0.1
 * @since 0.1
 */
public class WordsCounter implements Runnable {
	
	private String stringForCount;
	private Semaphore sem;
	
	public WordsCounter(Semaphore sem, String str) {
		this.stringForCount = str;
		this.sem = sem;
		new Thread(this).start();
	}
	
	/**
	 * Подстчет количества пробелов в тексте
	 * @return - количество пробелов
	 */
	public int spacesCounter() {
		CharBuffer buffer = CharBuffer.allocate(this.stringForCount.length());
		buffer.put(this.stringForCount);
		buffer.position(0);
		int count = 0;
		while (buffer.hasRemaining()) {
			if (Character.isSpaceChar(buffer.get())) {
				count++;
				System.out.println("The number of spaces - " + count);
			}
		}
		return count;
	}
	
	/**
	 * Подстчет количества слов в тексте
	 * @return - количество слов
	 */
	public int wordsCounter() {
		CharBuffer buffer = CharBuffer.allocate(this.stringForCount.length());
		buffer.put(this.stringForCount);
		buffer.rewind();
		int count = !Character.isSpaceChar(buffer.get(0)) ? 1 : 0;
		Character currentChar = null;
		while (buffer.hasRemaining()) {
			if ((Character.isSpaceChar(buffer.get()) 
					&& !Character.isSpaceChar(currentChar))) {
				count++;
				System.out.println("The number of words - " + count);
			}
			currentChar = buffer.get(buffer.position() - 1); 
		}
		return count;
	}

	@Override
	public void run() {
		this.wordsCounter();
		this.spacesCounter();
		sem.release();
	}
}
