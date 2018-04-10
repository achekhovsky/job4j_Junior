package ru.job4j.waitnotify;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This pool starts the specifiet number of threads
 * in which work is performed. If the number of work exceeds
 * a number of thread then work placed in the queue. 
 * @author achekhovsky
 * @version 0.1
 * 
 */
public class CustomThreadPool {
	private volatile boolean isRuning = true;
	private final Queue<Runnable> works = new LinkedBlockingQueue<>();
	private final ArrayList<Thread> threads;
	
	public CustomThreadPool(int poolSize) {
		this.threads = new ArrayList<>(poolSize);
		for (int i = 0; i < poolSize; i++) {
			this.threads.add(new Thread(new WorkIt()));
			this.threads.get(i).start();
		}
	}
	
	/**
	 * If poll running then puts the work in the queue.
	 * Else nothing. 
	 * @param work - new work
	 */
	public void add(Work work) {
		if (this.isRuning && work != null) {
			this.works.offer(work);
			System.out.printf("Work %s added to queue\n", work.getId());
		}
	}
	
	/**
	 * Exits the pool.
	 */
	public void shutDown() {
		this.isRuning = false;
		this.stopAllThread();
	}
	
	/**
	 * Wait specified time (at mills) and exits the pool.
	 */
	public void shutDownAndWait(int mills) {
		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			this.isRuning = false;
			this.stopAllThread();
		}
	}
	
	private void stopAllThread() {
		for (Thread thr : this.threads) {
			 thr.interrupt();
		}
	}
	
	private final class WorkIt implements Runnable {
		private Runnable currentWork;
		@Override
		public void run() {
			while (CustomThreadPool.this.isRuning) {
				currentWork = CustomThreadPool.this.works.poll();
				if (currentWork != null) {
					System.out.printf("Work %s retrieved from queue and runned\n", ((Work) currentWork).getId());
					currentWork.run();
				}
			}
		}
	}
}
