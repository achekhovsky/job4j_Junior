package ru.job4j.servlets.http;

import java.util.List;

public interface Store {
	boolean add(User usr);
	boolean update(User usr);
	boolean delete(User usr);
	List<User> findAll();
	User findById(int id);
}
