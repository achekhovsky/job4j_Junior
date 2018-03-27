package ru.job4j.stock;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

public class OrderTest {
	private Order testedOrder;
	@Before
	public void setUp() throws Exception {
		testedOrder = new Order("TestGlass", 10.0d, 1, Order.ACTION_ASK, Order.TYPE_ADD);
	}

	@Test(expected = WrongOrderException.class)
	public void test() {
		testedOrder.setType(Order.TYPE_DELETE);
		assertThat(testedOrder.getType(), is(Order.TYPE_DELETE));
		testedOrder.setType(0);
	}

}
