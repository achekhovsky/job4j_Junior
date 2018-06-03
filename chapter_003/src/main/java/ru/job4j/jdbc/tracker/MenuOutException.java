package ru.job4j.jdbc.tracker;

import java.util.regex.Pattern;

/**
 * The class which generates exception when incorrectly entered data
 */
public class MenuOutException extends RuntimeException {
    private String msg;

    /**
     * The constructor accepting as parameter the user input data
     * @param msg - the user input data
     */
    public MenuOutException(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        String resultStr = String.format("MenuOutException{%s}",
                Pattern.matches("[\\d]*", msg)
                        ? "Enter a value from the range of menu!"
                        : "You entered incorrect value, pls try again!");
        return resultStr;
    }
}
