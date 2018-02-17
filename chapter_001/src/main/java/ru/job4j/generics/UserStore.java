package ru.job4j.generics;

/**
 * Store for User
 * @author achekhovsky
 * @version 1.0
 * @since 1.0
 */
public class UserStore extends AbstractStore<User> {

	public UserStore(int storeSize) {
		super(storeSize);
	}

}
