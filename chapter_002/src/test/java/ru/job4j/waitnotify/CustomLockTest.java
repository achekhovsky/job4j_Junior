package ru.job4j.waitnotify;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for CustomLock. Inside the test, two classes are described: Count, ThreadForTest. 
 * In class "Count" the counter is changed. But at the time of the change, there is a pause
 * for 1 second. Another class it's a thread for changing the counter.
 * @author achekhovsky
 */
public class CustomLockTest {

	private class Count {
		private final CustomLock cLock = new CustomLock();
		public int count = 0;
		public void changeCount() {
			cLock.lock();
			try {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.count++;				
			} finally {
				cLock.unlock();
			}
		}
	}
	
	private class ThreadForTest extends Thread {
		private Count cnt;
		public ThreadForTest(Count cnt) {
			this.cnt = cnt; 
		}
		
        public void run() {
			System.out.printf("Thread '%s' starts to change the count\n", Thread.currentThread().getName());
        	cnt.changeCount();
        	System.out.printf("Thread '%s' changed the count\n", Thread.currentThread().getName());
        }
	}
	
	private Count cnt;
	private ThreadForTest firstThread;
	private ThreadForTest secondThread;
	private ThreadForTest thirdThread;

	@Before
	public void setUp() throws Exception {
		cnt = new Count();
		firstThread = new ThreadForTest(cnt);
		secondThread = new ThreadForTest(cnt);
		thirdThread = new ThreadForTest(cnt);
	}

	@Test
	public void test() {
		firstThread.start();
		secondThread.start();
		thirdThread.start();
		try {
			firstThread.join();
			secondThread.join();
			thirdThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertThat(cnt.count, is(3));
	}

}
