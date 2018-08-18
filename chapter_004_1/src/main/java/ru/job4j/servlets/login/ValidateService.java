package ru.job4j.servlets.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Validating the MemoryStore state change methods
 * @author achekhovsky
 * @version 1.0
 */
public class ValidateService {
	private static final Logger LOG = Logger.getLogger(ValidateService.class.getName());
	private static final ValidateService V_SERVICE = new ValidateService();
	private final Store logic = SQLStore.getInstance();
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
	 * List all users names from the store
	 * @return
	 */
	public String[] getAllNames() {
		List<User> users = this.findAll();
		String[] names = new String[users.size()];
		for (int i = 0; i < users.size(); i++) { 
			names[i] = users.get(i).getName();
		}
		return names;
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
	 * User authentication
	 * @param name
	 * @param password
	 * @return
	 */
	public User authenticateUser(String name, String password) {
		return logic.authenticateUser(name, password);
	}
	
	/**
	 * Perform the specified action using the parameters: id, name, email, roleName, password
	 * @param act one of the actions listed in the class ValidateService.Actions
	 * @param id
	 * @param name
	 * @param email
	 * @param roleName
	 * @param password
	 */
	public void doAction(ValidateService.Actions act, String id, String name, String email, String roleName, String password) {
		this.disp.manage(act, this.validateParams(id, name, email, roleName, password));
	}
	
	/**
	 * Creating a new user after checking its parameters
	 * @param id
	 * @param name
	 * @param email
	 * @param roleName
	 * @param password
	 * @return a new user if its parameters match the validation conditions otherwise null
	 */
	private User validateParams(String id, String name, String email, String roleName, String password) {
		User user = null;
		if (Pattern.matches("\\d+", id) && Pattern.matches("\\S*", email)) {
			user = new User(Integer.parseInt(id), name, email, roleName, password);
		}
		return user;
	}
	
	public enum Actions {
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
