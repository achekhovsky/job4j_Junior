package ru.job4j.jdbc.tracker;

import java.util.Arrays;

/**
 * The implementation of the input interface to test the application
 */
public class StubInput implements Input {
    private int position = 0;
    private String[] action;

    /**
     * The constructor accepting as parameter an array of commands
     * @param act - the commands that the input by user
     */
    StubInput(String...act) {
        action = act;
    }
    /**
     * Method which organizes reading data from the StubInput.action
     * @param question - the question that is displayed to the console before reading the data
     * @return The data that the user entered in the StubInput.action
     */
    @Override
    public String ask(String question) {
        return action[position++];
    }

    /**
     * Method which organizes reading data from the StubInput.action with the input restrictions
     * @param question - the question that is displayed to the console before reading the data
     * @param range - restrictions
     * @return The data that the user entered in the StubInput.action
     */
    @Override
    public String ask(String question, int[] range) throws MenuOutException {
        String inp = action[position++];
        if (Arrays.stream(range).anyMatch((v)->inp.equals(String.valueOf(v)))) {
            return inp;
        } else {
            throw new MenuOutException(inp);
        }
    }
}
