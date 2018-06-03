package ru.job4j.jdbc.tracker;

/**
 * Defines method of data input
 * @author achekhovsky
 */
public interface Input {
    /**
     * Defines method of data input
     * @param question - the question that is displayed before reading the data
     * @return the data which were receive
     */
    public String ask(String question);
    /**
     * Defines method of data input with the restrictions on the input
     * @param question - the question that is displayed before reading the data
     * @param range - restrictions
     * @return the data which were receive
     */
    public String ask(String question, int[] range) throws MenuOutException;
}
