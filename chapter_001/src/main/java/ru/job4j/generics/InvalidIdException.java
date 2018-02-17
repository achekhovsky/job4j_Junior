package ru.job4j.generics;

/**
 * The exception that occurs when the id is entered incorrectly
 * @version 1.0
 * @since 1.0
 * @author achekhovsky
 */
public class InvalidIdException extends RuntimeException {
    private String message = "";

    public InvalidIdException(String message) {
        this.message = message;
    }

    /* 
     * @see java.lang.Throwable#toString()
     */
    @Override
    public String toString() {
        return String.format("InvalidIdException: %s", message);
    }
}
