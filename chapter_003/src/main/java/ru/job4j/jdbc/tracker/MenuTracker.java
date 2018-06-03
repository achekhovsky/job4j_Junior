package ru.job4j.jdbc.tracker;

/**
 * Display the menu and handle the actions selected by the user
 * @author achekhovsky
 * @version 1.0
 */
public class MenuTracker {
    private Input input;
    private Tracker tracker;
    private UserAction[] actions;
    private int position;

    /**
     * Constructor which initializes the tracker and input method
     * @param input -  input method
     * @param tracker - tracker
     */
    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
        actions  = new UserAction[6];
        position = 0;
    }

    /**
     * Show menu in the console
     */
    public void showMenu() {
        for (UserAction ua : actions) {
            System.out.println(ua.info());
        }
    }

    public void addToMenu(BaseAction ba) {
        this.actions[position++] = ba;
    }

    /**
     * Handles the action that the user chooses
     * @param i - the index of action
     */
    public void select(int i) {
        this.actions[i].execute(this.input, this.tracker);
    }

}
