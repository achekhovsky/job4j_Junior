package ru.job4j.jdbc.tracker;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class-wrapper for items
 */
public class Tracker implements AutoCloseable {
	private final TrackJdbc tj;
	
	public Tracker(String cfg) {
		tj = new TrackJdbc(cfg);
	}
	
	public Tracker(Connection con) {
		tj = new TrackJdbc(con);
	}
	
	public TrackJdbc getConn() {
		return tj;
	}
	
	/**
	 * Adds item to the tracker
	 * @param item - the item to add
	 * @return added item
	 */
	public Item add(Item item) {
		item.setId(Tracker.generateId());
		tj.saveItem(item);
		return item;
	}
	
	/**
	 * Adds item with specified id to the tracker
	 * @param item - the item to add
	 * @return added item
	 */
	public Item add(Item item, String id) {
		item.setId(id);
		tj.saveItem(item);
		return item;
	}

	/**
	 * Editing the item
	 * @param item
	 */
	public void updateItem(Item item) {
		tj.updateItem(item);
	}

	/**
	 * Removing the item
	 * @param item
	 */
	public void deleteItem(Item item) {
		tj.deleteItem(item);
	}

	/**
	 * Find item by id
	 * @param id - the id of the item to search
	 * @return If the item has been found then item, else - null
	 */
	public Item findById(String id) {
		return tj.findItemById(id);
	}

	/**
	 * Find items by name
	 * @param name - the name of the items to search
	 * @return The array of items with the specified names
	 */
	public Item[] findByName(String name) {
		ArrayList<Item> al = tj.findItemsByName(name);
		return al.toArray(new Item[al.size()]);
	}

	/**
	 * Returns items array with no empty elements
	 * @return Items array with no empty elements
	 */
	public Item[] findAll() {
		ArrayList<Item> al = tj.findAll();
		return al.toArray(new Item[al.size()]);
	}

	/**
	 * Generation of item id
	 * @return The item id
	 */
	private static String generateId() {
	    String appender = String.valueOf(System.nanoTime());
		return "item_" + appender.substring(appender.length() - 4, appender.length() - 1);
	}

	/* 
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() throws Exception {
		tj.closeConnection();
	}

}
