package ru.job4j.servlets.cinema;

public interface Store {
	boolean add(Place plc);
	boolean addAccount(Account acc);
	boolean deleteAccount(Account acc);
	boolean updateHall(Place plc);
	Place findByPlaceNumber(int place);
	Account findByPhone(String phone);
	Place[] getOccupiedPlaces();
}
