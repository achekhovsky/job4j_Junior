package ru.job4j.nonblocked;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * NonBlockedCash
 * @author achekhovsky
 * @version 0.1
 *
 */
public class NonBlockedCash {
	
	private ConcurrentHashMap<Integer, Model> models;
	
	public NonBlockedCash() {
		this.models = new ConcurrentHashMap<>();
	}
	
	/**
	 * If the specified id is not already associated with a model, 
	 * attempts to compute its model using the given mapping function 
	 * and enters it into this map unless null.
	 * @param id - model id
	 * @param name - model name
	 */
	public void add(int id, String name) {
		this.models.computeIfAbsent(id, (v) -> {
			return new Model(id, name);
		});
	}
	
	public void delete(int id) {
		this.models.remove(id);
	}
	
	/**
	 * If the model for the specified id is present, attempts to compute
	 * a new mapping given the id and its current mapped model.
	 * @param id - model id
	 * @param newName - new model name
	 */
	public void update(int id, String newName) {
		int currentVersion = this.models.get(id).version;
		this.models.computeIfPresent(id, (ident, model) -> {
			if (currentVersion == model.version) {
				model.setName(newName);
				return model;
			} else {
				throw new OptimisticException("Model was updated in another thread");
			}
		});
	}

	private class Model {
		private final int id;
		private int version; 
		private String name;
		
		Model(int id, String name) {
			this.id = id;
			this.version = 0;
			this.name = name;
		}
		
		private void setName(String name) {
			version++;
			this.name = name;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(id);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Model other = (Model) obj;
			if (id != other.id) {
				return false;
			} 
			return true;
		}
	}
}
