package ru.job4j.jdbc.tracker;

/**
 * The class describes the action that the user chooses
 * @author achekhovskiy
 * @version 1.0
 */
public abstract class BaseAction implements UserAction {
    private String name;
    private int key;
    /**
     * Constructor which initializes the key and action name
     * @param key -  key
     * @param name - action name
     */
    BaseAction(int key, String name) {
        this.key = key;
        this.name = name;
    }

    /**
     * This method return key value
     * @return key value
     */
    @Override
    public int key() {
     return this.key;
    }

    /**
     * This method return action info
     * @return info
     */
    @Override
    public String info() {
        return String.format("%s. %s", this.key, this.name);
    }
}
