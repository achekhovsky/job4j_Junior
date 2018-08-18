package ru.job4j.servlets.login;

import java.time.LocalDate;

public class User {
	private final int id;
	private final LocalDate createDate;
	private String name;
	private String email;
	private String password;
	private String roleName;
	
	public User(int id, String name, String email, String roleName, String password) {
		this.id = id;
		this.createDate = LocalDate.now();
		this.name = name;
		this.email = email;
		this.roleName = roleName;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the role
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param role the role to set
	 */
	public void setRoleName(String role) {
		this.roleName = role;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/* (non-Javadoc)
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
		if (id != other.id) {
			return false;
		}
		return true;
	}
}
