package ru.job4j.servlets.ajax;

public interface Store {
	void add(User usr);
	void remove(User usr);
	void clearStore();
}
