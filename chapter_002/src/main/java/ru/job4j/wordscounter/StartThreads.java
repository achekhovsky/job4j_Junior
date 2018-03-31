package ru.job4j.wordscounter;

import java.util.concurrent.Semaphore;

public class StartThreads {
	public static void main(String[] args) {
		Semaphore sem = new Semaphore(0);
		System.out.println("Chars counter programm is started");
		new WordsCounter(sem,
				"Like a byte buffer, a char buffer is either" + " direct or non-direct. A char buffer created via the "
						+ "wrap methods of this class will be non-direct. A char "
						+ "buffer created as a view of a byte buffer will be "
						+ "direct if, and only if, the byte buffer itself is direct. "
						+ "Whether or not a char buffer is direct may be determined "
						+ "by invoking the isDirect method.");
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Chars counter programm is stopped");
	}
}
