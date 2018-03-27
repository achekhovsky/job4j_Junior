package ru.job4j.stock;

public class WrongOrderException extends RuntimeException {
	private Order throwableOrder;

	public WrongOrderException(Order exOrd) {
		this.throwableOrder = exOrd;
	}

	@Override
	public String toString() {
		return "WrongOrderException: Order " + throwableOrder.id + " is wrong!";
	}

}
