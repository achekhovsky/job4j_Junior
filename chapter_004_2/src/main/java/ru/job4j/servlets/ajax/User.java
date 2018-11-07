package ru.job4j.servlets.ajax;

public class User {
	private String usrName;
	private String usrLName;
	private String usrDesc;
	private String gender;
	
	public User() {
		this.usrName = "";
		this.usrLName = "";
		this.usrDesc = "";
		this.gender = "";
	}

	public User(String name, String lname, String description, String gender) {
		this.usrName = name;
		this.usrLName = lname;
		this.usrDesc = description;
		this.gender = gender;
	}
	
	/**
	 * @return the usrName
	 */
	public String getUsrName() {
		return usrName;
	}

	/**
	 * @param usrName the usrName to set
	 */
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	/**
	 * @return the usrLName
	 */
	public String getUsrLName() {
		return usrLName;
	}

	/**
	 * @param usrLName the usrLName to set
	 */
	public void setUsrLName(String usrLName) {
		this.usrLName = usrLName;
	}

	/**
	 * @return the usrDesc
	 */
	public String getUsrDesc() {
		return usrDesc;
	}

	/**
	 * @param usrDesc the usrDesc to set
	 */
	public void setUsrDesc(String usrDesc) {
		this.usrDesc = usrDesc;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((usrLName == null) ? 0 : usrLName.hashCode());
		result = prime * result + ((usrName == null) ? 0 : usrName.hashCode());
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
		if (gender == null) {
			if (other.gender != null) {
				return false;
			}
		} else if (!gender.equals(other.gender)) {
			return false;
		}
		if (usrLName == null) {
			if (other.usrLName != null) {
				return false;
			}
		} else if (!usrLName.equals(other.usrLName)) {
			return false;
		}
		if (usrName == null) {
			if (other.usrName != null) {
				return false;
			}
		} else if (!usrName.equals(other.usrName)) {
			return false;
		}
		return true;
	}
}
