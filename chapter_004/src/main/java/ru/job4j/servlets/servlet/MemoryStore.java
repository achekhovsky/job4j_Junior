package ru.job4j.servlets.servlet;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * User Storage
 * @author achekhovsky
 */
public class MemoryStore implements Store {
	private static final MemoryStore STORE = new MemoryStore();
	private List<User> users;
	
	private MemoryStore() {
		this.users = new CopyOnWriteArrayList<>();
	}
	
	public static MemoryStore getInstance() {
		return STORE;
	}
	
	@Override
	public boolean add(User usr) {
		return this.users.add(usr);
	}

	@Override
	public boolean update(User usr) {
		boolean result = false;
		for (User user : this.users) {
			if (user.equals(usr)) {
				user.setName(usr.getName());
				user.setEmail(usr.getEmail());
				result = true;
			}
		}
		return result;
	}

	@Override
	public boolean delete(User usr) {
		return this.users.remove(usr);
	}

	@Override
	public List<User> findAll() {
		return this.users;
	}

	@Override
	public User findById(int id) {
		User result = null;
		for (User usr : this.users) {
			if (usr.getId() == id) {
				result = usr;
			}
		}
		return result;
	}
	
	public void clearStore() {
		this.users.clear();
	}
}
