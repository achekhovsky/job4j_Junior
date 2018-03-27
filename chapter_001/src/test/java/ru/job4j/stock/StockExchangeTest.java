package ru.job4j.stock;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import ru.job4j.stock.Order;
import ru.job4j.stock.StockExchange;

public class StockExchangeTest {
	private StockExchange stock;
	private ByteArrayOutputStream baos;
	private PrintStream ps;

	@Before
	public void setUp() throws Exception {
		stock = new StockExchange();
		baos = new ByteArrayOutputStream();
		ps = new PrintStream(baos);
		System.setOut(ps);

	}

	@Test
	public void ifAddNotSortedValuesThenSort() {
		stock.acceptOrder(new Order("TestGlassName", 10.0d, 1, Order.ACTION_ASK, Order.TYPE_ADD));
		stock.acceptOrder(new Order("TestGlassName", 8.0d, 1, Order.ACTION_ASK, Order.TYPE_ADD));
		stock.acceptOrder(new Order("TestGlassName", 9.0d, 1, Order.ACTION_ASK, Order.TYPE_ADD));
		stock.viewGlass("TestGlassName");
		String expected = "Покупка  Цена  Продажа\n"
				        + "   1     8.0\n" 
				        + "   1     9.0\n" 
				        + "   1     10.0\n\n";
		assertThat(baos.toString(), is(expected));
		
	}

	@Test
	public void ifAskAndBidWithNotCompatiblePriceThenTwoOrderInGlass() {
		stock.acceptOrder(new Order("TestGlassName", 10.0d, 1, Order.ACTION_ASK, Order.TYPE_ADD));
		stock.acceptOrder(new Order("TestGlassName", 12.0d, 1, Order.ACTION_BID, Order.TYPE_ADD));
		stock.viewGlass("TestGlassName");
		String expected = "Покупка  Цена  Продажа\n"
				        + "   1     10.0\n" 
				        + "         12.0     1\n\n";
		assertThat(baos.toString(), is(expected));
	}

	@Test
	public void ifAskAndBidWithCompatiblePriceThenVolumeIsReduced() {
		stock.acceptOrder(new Order("TestGlassName", 10.0d, 1, Order.ACTION_ASK, Order.TYPE_ADD));
		stock.acceptOrder(new Order("TestGlassName", 8.0d, 1, Order.ACTION_BID, Order.TYPE_ADD));
		stock.acceptOrder(new Order("TestGlassName", 8.0d, 1, Order.ACTION_BID, Order.TYPE_ADD));
		stock.acceptOrder(new Order("TestGlassName", 10.0d, 2, Order.ACTION_ASK, Order.TYPE_ADD));
		stock.viewGlass("TestGlassName");
		String expected = "Покупка  Цена  Продажа\n"
				 		+ "   1     10.0\n\n"; 
		assertThat(baos.toString(), is(expected));
	}

	@Test
	public void ifAddTwoOrdersWithSamePriceThenPrintOnlyOneButWithTotalVolume() {
		stock.acceptOrder(new Order("TestGlassName", 10.0d, 1, Order.ACTION_ASK, Order.TYPE_ADD));
		stock.acceptOrder(new Order("TestGlassName", 10.0d, 1, Order.ACTION_ASK, Order.TYPE_ADD));
		stock.viewGlass("TestGlassName");
		String expected = "Покупка  Цена  Продажа\n"
		 				+ "   2     10.0\n\n"; 
		assertThat(baos.toString(), is(expected));
	}
	
	@Test
	public void ifAskAndBidWithCompatiblePriceButOtherVolumesThenVolumeReduced() {
		stock.acceptOrder(new Order("TestGlassName", 10.0d, 2, Order.ACTION_ASK, Order.TYPE_ADD));
		stock.acceptOrder(new Order("TestGlassName", 10.0d, 1, Order.ACTION_BID, Order.TYPE_ADD));
		stock.acceptOrder(new Order("TestGlassName", 10.0d, 3, Order.ACTION_BID, Order.TYPE_ADD));
		stock.acceptOrder(new Order("TestGlassName", 10.0d, 1, Order.ACTION_ASK, Order.TYPE_ADD));
		stock.viewGlass("TestGlassName");
		String expected = "Покупка  Цена  Продажа\n"
		        		+ "         10.0     1\n\n";
		assertThat(baos.toString(), is(expected));
	}

	@Test
	public void ifAddOrderAndDeleteItThenGlassIsEmpty() {
		stock.acceptOrder(new Order("TestGlassName", 10.0d, 1, Order.ACTION_ASK, Order.TYPE_ADD));
		stock.acceptOrder(new Order("TestGlassName", 10.0d, 1, Order.ACTION_ASK, Order.TYPE_DELETE));
		stock.viewGlass("TestGlassName");
		String expected = "Покупка  Цена  Продажа\n\n";
		assertThat(baos.toString(), is(expected));
	}
	
	@Test
	public void ifAddOrderAndDeleteItWithOtherVolumeThenVolumeIsReduced() {
		stock.acceptOrder(new Order("TestGlassName", 10.0d, 5, Order.ACTION_ASK, Order.TYPE_ADD));
		stock.acceptOrder(new Order("TestGlassName", 10.0d, 1, Order.ACTION_ASK, Order.TYPE_DELETE));
		stock.viewGlass("TestGlassName");
		String expected = "Покупка  Цена  Продажа\n"
						+ "   4     10.0\n\n"; 
		assertThat(baos.toString(), is(expected));
	}
	
	@Test
	public void ifAddOrderWithTypeADDAndTypeDELETETButWithOtherPricehenNothing() {
		stock.acceptOrder(new Order("TestGlassName", 10.0d, 5, Order.ACTION_ASK, Order.TYPE_ADD));
		stock.acceptOrder(new Order("TestGlassName", 12.0d, 1, Order.ACTION_ASK, Order.TYPE_DELETE));
		stock.viewGlass("TestGlassName");
		String expected = "Покупка  Цена  Продажа\n"
						+ "   5     10.0\n\n"; 
		assertThat(baos.toString(), is(expected));
	}

}
