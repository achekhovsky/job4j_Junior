package ru.job4j.waitnotify;

/**
 * Work for CustomThreadPool
 * @author achekhovsky
 * @version 0.1
 */
public class Work implements Runnable {
	private final int id;

	public Work(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("Work %s complited \n", this.id);
	}

}
