package ru.job4j.servlets.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Validating the MemoryStore state change methods
 * @author achekhovsky
 * @version 1.0
 */
public class ValidateService {
	private static final ValidateService V_SERVICE = new ValidateService();
	private final Store logic = MemoryStore.getInstance();
	private final ActionsManager disp;
	
	private ValidateService() {
		disp = new ActionsManager();
	}
	
	/**
	 * @return instance of a class
	 */
	public static ValidateService getInstance() {
		return V_SERVICE;
	}
	
	/**
	 * @return user store instance
	 */
	public Store getStore() {
		return this.logic;
	}
	
	/**
	 * Adding user (after checking the user parameters) to the storage
	 * @param usr
	 * @return
	 */
	public boolean add(User usr) {
		boolean result = false;
		if (usr != null && this.findById(usr.getId()) == null) {
			result = this.logic.add(usr);
		}
		return result;
	}
	
	/**
	 * Updating user parameters in the store
	 * @param usr
	 * @return
	 */
	public boolean update(User usr) {
		boolean result = false;
		if (usr != null && this.findById(usr.getId()) != null) {
			result = this.logic.update(usr);
		}
		return result;
	}
	
	/**
	 * Deleting user from the storage
	 * @param usr
	 * @return
	 */
	public boolean delete(User usr) {
		return this.logic.delete(usr);
	}
	
	/**
	 * List all users from the store
	 * @return
	 */
	public List<User> findAll() {
		return logic.findAll();
	}
	
	/**
	 * Find user by id
	 * @param id
	 * @return
	 */
	public User findById(int id) {
		return this.logic.findById(id);
	}
	
	/**
	 * Perform the specified action using the parameters: id, name, email
	 * @param act one of the actions listed in the class ValidateService.Actions
	 * @param id
	 * @param name
	 * @param email
	 */
	public void doAction(ValidateService.Actions act, String id, String name, String email) {
		this.disp.manage(act, this.validateParams(id, name, email));
	}
	
	/**
	 * Creating a new user after checking its parameters
	 * @param id
	 * @param name
	 * @param email
	 * @return a new user if its parameters match the validation conditions otherwise null
	 */
	private User validateParams(String id, String name, String email) {
		User user = null;
		if (Pattern.matches("\\d+", id) && Pattern.matches("\\S*", email)) {
			user = new User(Integer.parseInt(id), name, email);
		}
		return user;
	}
	
	enum Actions {
		ADD, DELETE, UPDATE;
	}
	
	/**
	 * Actions manager
	 */
	private static class ActionsManager {
		private final Map<ValidateService.Actions, Function<User, Boolean>> actions = new HashMap<>();
		
		private ActionsManager() {
			this.init();
		}
		
		/**
		 * Perform the specified action with the user
		 * @param act
		 * @param usr
		 */
		private void manage(ValidateService.Actions act, User usr) {
			this.actions.get(act).apply(usr);
		}
		
		/**
		 * Add action to the manager
		 * @param act - action identifier
		 * @param func - function that will be executed at the specified identifier
		 */
		private void load(ValidateService.Actions act, Function<User, Boolean> func) {
			this.actions.put(act, func);
		}
		
		/**
		 * Manager initialization
		 */
		private void init() {
			this.load(ValidateService.Actions.ADD, (u) -> V_SERVICE.add(u));
			this.load(ValidateService.Actions.UPDATE, (u) -> V_SERVICE.update(u));
			this.load(ValidateService.Actions.DELETE, (u) -> V_SERVICE.delete(u));
		}
	}
}
