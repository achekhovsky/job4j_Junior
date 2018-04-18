package ru.job4j.nonblocked;

import static org.junit.Assert.*;

import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

public class NonBlockedCashTest {
	private NonBlockedCash cash;
	private boolean exceptionIsOccure;

	@Before
	public void setUp() throws Exception {
		cash = new NonBlockedCash();
		cash.add(1, "m1");
		cash.add(2, "m2");
		exceptionIsOccure = false;
	}

	@Test
	public void test() {
		Thread userOne = new Thread() {
			@Override
			public void run() {
				while (!exceptionIsOccure) {
					try {
						cash.update(1, "m1.1");	
					} catch (OptimisticException e) {
						exceptionIsOccure = true;
					} catch (Exception e) {
						fail("Caught unexpected exception");
					}
				}
			}
		};
		Thread userTwo = new Thread() {
			@Override
			public void run() {
				while (!exceptionIsOccure) {
					try {
						cash.update(1, "m1.2");	
					} catch (OptimisticException e) {
						exceptionIsOccure = true;
					} catch (Exception e) {
						fail("Caught unexpected exception");
					}
				}
			}
		};
		userOne.start();
		userTwo.start();			
		try {
			userOne.join();
			userTwo.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertThat(exceptionIsOccure, is(true));
	}
}
