package ru.job4j.wordscounter;

import java.nio.CharBuffer;
import java.util.concurrent.Semaphore;

/**
 * Подсчет количества слов и пробелов в тексте
 * @author achekhovsky
 * @version 0.1
 * @since 0.1
 */
public class CountChar implements Runnable {
	
	private String stringForCount;
	
	public CountChar(String str) {
		this.stringForCount = str;
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
		while (buffer.hasRemaining() && !StartThreads.timeIsOver) {
			if (Character.isSpaceChar(buffer.get())) {
				count++;
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
		while (buffer.hasRemaining() && !StartThreads.timeIsOver) {
			if ((Character.isSpaceChar(buffer.get()) 
					&& !Character.isSpaceChar(currentChar))) {
				count++;
			}
			currentChar = buffer.get(buffer.position() - 1);
		}
		return count;
	}

	@Override
	public void run() {
		System.out.printf("Number of words - %s\n", this.wordsCounter());
		System.out.printf("Number of spaces - %s\n", this.spacesCounter());
	}
}
