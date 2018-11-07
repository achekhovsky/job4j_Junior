package ru.job4j.servlets.ajax;

import java.util.concurrent.ConcurrentHashMap;

public class HashMapStore implements Store {
	private final ConcurrentHashMap<User, User> store = new  ConcurrentHashMap<>();
	
	@Override
	public void add(User usr) {
		store.put(usr, usr);
	}

	@Override
	public void remove(User usr) {
		store.remove(usr);
	}

	@Override
	public void clearStore() {
		store.clear();
	}

}
