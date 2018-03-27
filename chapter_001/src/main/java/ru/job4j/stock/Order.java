package ru.job4j.stock;

import java.util.Objects;

/**
 * Заявка системы трейдинга
 * @author achekhovsky
 * @version 0.1
 * @since 0.1
 */
public class Order implements Comparable<Order> {
	public static final int TYPE_ADD = 100, TYPE_DELETE = 101, ACTION_BID = 200, ACTION_ASK = 201;

	public final int id;
	public final double price;
	public final String book;

	private int action;

	private int type;
	private int volume;

	public Order(String book, double price, int volume, int action, int type) {
		this.id = this.generateId();
		this.book = book;
		this.price = price;
		this.volume = volume;
		this.setAction(action);
		this.setType(type);
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getType() {
		return type;
	}

	/**
	 * Устанавливает тип заявки. Если в качестве параметра указан
	 * невеный тип, то генерируется исключение 
	 * @param type - тип заявки
	 * @exception WrongOrderException
	 */
	public void setType(int type) throws WrongOrderException {
		if (type != TYPE_DELETE && type != TYPE_ADD) {
			throw new WrongOrderException(this);
		} 
		this.type = type;			
	}
	
	public int getAction() {
		return action;
	}

	/**
	 * Устанавливает действие заявки. Если в качестве параметра неверно 
	 * указано действие, то генерируется исключение 
	 * @param action -  действие заявки
	 * @exception WrongOrderException
	 */
	public void setAction(int action) throws WrongOrderException {
		if (action != ACTION_ASK && action != ACTION_BID) {
			throw new WrongOrderException(this);
		} 
		this.action = action;			
	}

	/**
	 * Генерация уникального ключа.  
	 * @return уникальный ключ
	 */
	private int generateId() { 
		return Objects.hashCode(System.nanoTime());
	}

	/* 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Order order) {
		int result = (this.price - order.price) == 0 ? this.id - order.id : (int) (this.price - order.price);
		return result;
	}
}
