package ru.job4j.jdbc.tracker;

/**
 * Interface for handle of user actions
 */
public interface UserAction {
    //Return index of action
    public int key();
    //Performs the action
    public void execute(Input input, Tracker tracker);
    //Return action info
    public String info();
}
