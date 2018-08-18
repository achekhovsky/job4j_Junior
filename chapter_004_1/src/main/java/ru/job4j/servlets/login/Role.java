package ru.job4j.servlets.login;

public class Role {
	private String name;
	private int[] rights = new int[3];
	
	public Role() {
		this.name = "";
		this.rights[0] = 0;
		this.rights[1] = 0;
		this.rights[2] = 0;
	}
	
	public Role(String name, int add, int upd, int del) {
		this.name = name;
		this.rights[0] = add;
		this.rights[1] = upd;
		this.rights[2] = del;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the rights
	 */
	public int[] getRights() {
		return rights;
	}

	/**
	 * @param rights the rights to set
	 */
	public void setRights(int[] rights) {
		this.rights = rights;
	}
}
