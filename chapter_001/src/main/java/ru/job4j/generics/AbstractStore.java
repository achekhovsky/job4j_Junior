package ru.job4j.generics;

import java.util.NoSuchElementException;

/**
 * A store for objects with the ability to add, change and delete items.
 * @author achekhovsky
 * @version 1.0
 * @since 1.0
 * @param <T> type of objects
 */
public abstract class AbstractStore<T extends Base> implements Store<T> {
	
	private SimpleArray<T> items;
	
	public AbstractStore(int storeSize) {
		items = new SimpleArray<T>(storeSize);
	}

    /**
     * Appends the specified element to the end of store.
     * @param model - element to be appended to store
     * @throws InvalidIdException if item with the same id already exist
     */
	@Override
	public void add(T model) throws InvalidIdException {
		if (items.contain(model)) {
			throw new InvalidIdException("Item with the same id already exist in the store and can't be added!");
		} else {
			items.add(model);	
		}
	}
	 
	/**
	 * Replaces the element at the specified position in this store with
	 * the specified element.
	 * @param id - id of the element to replace
	 * @param model element to be replace with the specified element
	 * @return true if the replacement has passed
	 * @throws NoSuchElementException if the item is not found
	 */
	@Override
	public boolean replace(String id, T model) {
				items.set(this.getIndexById(id), model);
		return true;
	}
	
	/**
     * Remove specified element in this store
     * @param id - id of the element to delete
     * @return true if the delete has passed
     * @throws NoSuchElementException if the item is not found
     */
	@Override
	public boolean delete(String id) {
		items.delete(this.getIndexById(id));
		return true;
	}

	/**
     * Find element by id
     * @param id - id of the element 
     * @return true if the element is found
     * @throws NoSuchElementException if the item is not found
     */
	@Override
	public T findById(String id) {
		return items.get(this.getIndexById(id));
	}
	
	/**
	 * Find element by id and return it index 
	 * @param id - id of the element 
	 * @return index
	 * @throws NoSuchElementException if the item is not found
	 */
	private int getIndexById(String id) throws NoSuchElementException {
		for (int i = 0; i < items.getSize(); i++) {
			if (items.get(i).getId().equals(id)) {
				return i;
			}
		}
		throw new NoSuchElementException();
	}
	
	/**
	 * Return store size
	 * @return store size
	 */
	public int getSize() {
		return items.getSize();
	}
}
