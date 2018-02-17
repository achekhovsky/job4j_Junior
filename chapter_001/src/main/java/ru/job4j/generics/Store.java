package ru.job4j.generics;

/**
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public interface Store<T extends Base> {
    
    void add(T model);
    
    boolean replace(String id, T model);
    
    boolean delete(String id);

    T findById(String id);
}