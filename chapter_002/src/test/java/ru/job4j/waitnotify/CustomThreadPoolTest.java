package ru.job4j.waitnotify;

import org.junit.Before;
import org.junit.Test;

public class CustomThreadPoolTest {
	private Work w1;
	private Work w2;
	private Work w3;
	private Work w4;
	private Work w5;
	private Work w6;
	private CustomThreadPool ctp;

	@Before
	public void setUp() throws Exception {		
	w1 = new Work(1);
	w2 = new Work(2);
	w3 = new Work(3);
	w4 = new Work(4);
	w5 = new Work(5);
	w6 = new Work(6);
	ctp = new CustomThreadPool(Runtime.getRuntime().availableProcessors());
	}

	@Test
	public void test() {
		ctp.add(w1);
		ctp.add(w2);
		ctp.add(w3);
		ctp.add(w4);
		ctp.add(w5);
		ctp.add(w6);
		ctp.shutDownAndWait(2000);
	}

}
