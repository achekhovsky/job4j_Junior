package ru.job4j.wordscounter;

import java.nio.CharBuffer;

/**
 * Подсчет количества слов и пробелов в тексте
 * @author achekhovsky
 * @version 0.1
 * @since 0.1
 */
public class WordsCounter {
	private String stringForCount;
	
	public static void main(String[] args) {
		WordsCounter wc = new WordsCounter("Like a byte buffer, a char buffer is either"
				+ " direct or non-direct. A char buffer created via the "
				+ "wrap methods of this class will be non-direct. A char "
				+ "buffer created as a view of a byte buffer will be "
				+ "direct if, and only if, the byte buffer itself is direct. "
				+ "Whether or not a char buffer is direct may be determined "
				+ "by invoking the isDirect method.");
				Thread spacesThread = new Thread(new Runnable() {
					@Override
					public void run() {
						wc.spacesCounter();
					}
				}, "Spaces counter");
				Thread wordsThread = new Thread(new Runnable() {
					@Override
					public void run() {
						wc.wordsCounter();				
					}
				}, "Words counter");
				spacesThread.start();
				wordsThread.start();
	}
	
	public WordsCounter(String str) {
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

}
