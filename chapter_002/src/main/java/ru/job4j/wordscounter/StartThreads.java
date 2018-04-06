package ru.job4j.wordscounter;

public class StartThreads {
	public static volatile boolean timeIsOver = false;
	public static void main(String[] args) {
		Thread timeThread = new Thread(new Time(0.001d), "Time tread");
		System.out.println("Chars counter programm is started");
		timeThread.start();
		Thread counter = new Thread(new CountChar(
				"Like a byte buffer, a char buffer is either" + " direct or non-direct. A char buffer created via the "
						+ "wrap methods of this class will be non-direct. A char "
						+ "buffer created as a view of a byte buffer will be "
						+ "direct if, and only if, the byte buffer itself is direct. "
						+ "Whether or not a char buffer is direct may be determined "
						+ "by invoking the isDirect method."), "Count char");
		counter.start();
		try {
			timeThread.join();
			counter.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Chars counter programm is stopped");
	}
}
