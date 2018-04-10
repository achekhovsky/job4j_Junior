package ru.job4j.waitnotify;


/**
 * Producer for SimpleBlockingQueue
 * @author achekhovsky
 * @version 0.1
 * @since 0.1
 * @param <T>
 */
public class Producer<T> implements Runnable {
	private SimpleBlockingQueue<T> sbq;
	private T value;
	
	public Producer(SimpleBlockingQueue<T> sbq, T value) {
		this.sbq = sbq;
		this.value = value;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			this.sbq.offer(value);
		}
	}
}
