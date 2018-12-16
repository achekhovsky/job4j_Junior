package ru.job4j.servlets.cinema;

public class Place {
	private int place;
	private int price;
	private String customer;
	private boolean paid;
	
	public Place(int place, int price) {
		this.place = place;
		this.price = price;
		this.customer = "";
		this.paid = false;
	}
	
	public Place(int place, int price, String customer, boolean paid) {
		this.place = place;
		this.price = price;
		this.customer = "";
		this.paid = paid;
	}
	
	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the customer
	 */
	public String getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	/**
	 * @return the paid
	 */
	public boolean isPaid() {
		return paid;
	}

	/**
	 * @param paid the paid to set
	 */
	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	/**
	 * @return the place
	 */
	public int getPlace() {
		return place;
	}

	/* 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + place;
		return result;
	}

	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Place other = (Place) obj;
		if (place != other.place) {
			return false;
		}
		return true;
	}
	
	
}
