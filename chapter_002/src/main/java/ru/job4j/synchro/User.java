package ru.job4j.synchro;

import java.util.Objects;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * User class 
 * @author achekhovsky
 * @version 0.1
 * @since 0.1
 */
@ThreadSafe
public class User {
	private final int id;
	@GuardedBy("this")
	private int amount;
	
	public User(int id, int amount) {
		this.id = id;
		this.amount = amount;
	}
	
	public int getId() {
		return this.id;
	}

	/**
	 * Transfer amount from this user to another user.
	 * @param toUser - receiving user
	 * @param amount - transfer amount
	 * @return true if success
	 */
	public synchronized boolean transferHimself(User toUser, int amount) {
        boolean success = false;
         if (toUser != null && amount > 0 && this.amount >= amount) {
             toUser.amount += amount;
             this.amount -= amount;
             success = true;
         }
         return success;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}

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
		User other = (User) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}
}
