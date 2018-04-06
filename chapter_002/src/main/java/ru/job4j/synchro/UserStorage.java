package ru.job4j.synchro;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Storage for users. Allows you to transfer amount 
 * from one user to another.
 * @author achekhovsky
 * @version 0.1
 * @since 0.1
 */
@ThreadSafe
public class UserStorage {
	@GuardedBy("this")
	private Set<User> users;
	
	public UserStorage() {
		users = Collections.synchronizedSet(new HashSet<>());
	}
	
	/**
	 * Add user to storage
	 * @param usr - new user
	 * @return true if user added to storage
	 */
	public boolean add(User usr) {
		return this.users.add(usr);
	}
	
	/**
	 * Delete user from storage
	 * @param usr - deleted user
	 * @return true if user is deleted from storage
	 */
	public boolean delete(User usr) {
		return this.users.remove(usr);
	}
	
	/**
	 * Update user in storage
	 * @param usr - updated user
	 * @return true if success 
	 */
	public boolean update(User usr) {
		boolean result = false;
		if (this.users.contains(usr)) {
			this.users.remove(usr);
			result = this.users.add(usr);
		}
		return result;
	}
	
	/**
	 * Transfer amount from one user to another.
	 * @param fromId - transferring user
	 * @param toId - receiving user
	 * @param amount - transfer amount
	 * @return true if success
	 */
	@GuardedBy(value = "User.class")
	public boolean transfer(int fromId, int toId, int amount) {
		boolean result = false;
		User fromUsr = this.getUserById(fromId);
		if (fromUsr != null) {
			result = fromUsr.transferHimself(this.getUserById(toId), amount);
		}
		return result;
	}
	
	public int getUsersCount() {
		return this.users.size();
	}
	
	/**
	 * Find user with specified id 
	 * @param id - specified id 
	 * @return User if he is found
	 */
	private User getUserById(int id) {
		User result = null;
		for (User usr : this.users) {
			if (usr.getId() == id) {
				result = usr;
			}
		}
		return result;
	}
}
