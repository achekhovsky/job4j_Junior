package ru.job4j.servlets.login;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class RoleStore {
	private static Logger log = Logger.getLogger(RoleStore.class.getName());
	private final Map<String, Role> roles;
	private static final RoleStore INSTANCE = new RoleStore();
	
	private RoleStore() {
		this.roles = new HashMap<>();
		this.addRole(new Role("User", 0, 0, 0));
		this.addRole(new Role("Administrator", 1, 1, 1));
	}
	
	public static RoleStore getInstance() {
		return INSTANCE;
	}
	
	public void addRole(Role r) {
		this.roles.putIfAbsent(r.getName(), r);
	}
	
	public void removeRole(Role r) {
		this.roles.remove(r.getName(), r);
	}
	
	public String[] getAllNames() {
		return this.roles.keySet().toArray(new String[this.roles.size()]);
	}
	
	public Role getByName(String name) {
		return this.roles.get(name);
	}
}
