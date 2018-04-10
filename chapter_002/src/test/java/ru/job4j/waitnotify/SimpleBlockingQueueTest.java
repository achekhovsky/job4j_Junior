package ru.job4j.waitnotify;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

import ru.job4j.waitnotify.Consumer;
import ru.job4j.waitnotify.Producer;
import ru.job4j.waitnotify.SimpleBlockingQueue;

/**
 * Test for SimpleBlockingQueue
 * @author achekhovsky
 *
 */
public class SimpleBlockingQueueTest {
	private SimpleBlockingQueue<Integer> sbq;
	private Consumer<Integer> consumer;
	private Producer<Integer> producer;

	@Before
	public void setUp() throws Exception {
		sbq = new SimpleBlockingQueue<>(3);
		consumer = new Consumer<>(sbq);
		producer = new Producer<>(sbq, 1);
	}

	@Test
	public void ifProducedTheSameNumberOfTimesWithConsumedThenQueueSizeIsZero() {
		Thread consumerThread = new Thread(consumer);
		Thread producerThread = new Thread(producer);
		consumerThread.start();
		producerThread.start();
		try {
			consumerThread.join();
			producerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertThat(sbq.getSize(), is(0));
	}

}
