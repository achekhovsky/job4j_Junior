package ru.job4j.map;

import java.util.Calendar;

/**
 * User
 * @author achekhovsky
 */
public class User {
	private String name;
	private int children;
	private Calendar birthday;
	
	/**
	 * @param nm - new user name
	 * @param ch - new user children
	 * @param bd - new user birthday
	 */
	public User(String nm, int ch, Calendar bd) {
		this.name = nm;
		this.children = ch;
		this.birthday = bd;
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
		User other = (User) obj;
		if (birthday == null) {
			if (other.birthday != null) {
				return false;
			}
		} else if (!birthday.equals(other.birthday)) {
			return false;
		}
		if (children != other.children) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the birthday of the user
	 * @return the users's birthday
	 */
	public Calendar getBirthday() {
		return birthday;
	}
	/**
	 * Set the birthday of the user
	 * @param birthday
	 */
	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}
	/**
	 * Returns the name of the user
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set the name of the user
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Returns the children of the user
	 * @return
	 */
	public int getChildren() {
		return children;
	}
	/**
	 * Returns the children of the user
	 * @param children
	 */
	public void setChildren(int children) {
		this.children = children;
	}
}
