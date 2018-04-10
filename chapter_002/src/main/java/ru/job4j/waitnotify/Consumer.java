package ru.job4j.waitnotify;

/**
 * Consumer for SimpleBlockingQueue
 * @author achekhovsky
 * @version 0.1
 * @since 0.1
 * @param <T>
 */
public class Consumer<T> implements Runnable {
	private SimpleBlockingQueue<T> sbq;
	
	public Consumer(SimpleBlockingQueue<T> sbq) {
		this.sbq = sbq;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			this.sbq.poll();
		}
	}
}
