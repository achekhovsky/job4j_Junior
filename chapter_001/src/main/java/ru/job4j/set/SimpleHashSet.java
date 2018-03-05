package ru.job4j.set;

import java.util.Objects;

/**
 * A collection that contains no duplicate elements.
 * Based on hash table.
 * @author achekhovsky
 * @version 1.0
 * @since 1.0
 * @param <T> type of objects
 */
public class SimpleHashSet<E> {
	private static final int DEFAULT_CAPACITY = 16;
	private int size;
	private int capasityFilling;
	private Object[] container;
	
	/**
     * Constructs an empty set with the specified capacity.
     */
	public SimpleHashSet(int size) {
		this.size = size;
		this.capasityFilling = 0;
		this.container = new Object[this.size];
	}
	
	/**
     * Constructs an empty set with an initial capacity of sixteen.
     */
	public SimpleHashSet() {
		this.container = new Object[DEFAULT_CAPACITY];
		this.size = DEFAULT_CAPACITY;
		this.capasityFilling = 0;
	}
	
    /**
     * Adds the specified element to this set if it is not already present
     * @param e - element to be appended to this container
     */
	public boolean add(E e) {
		boolean result = false;
		if (!this.contains(e)) {
			this.checkCapacity();
			int key = this.generateKey(e);
			if (this.container[key] == null) {
				Node<E> newNode = new Node<>(key, e, null);
				this.container[key] = newNode;				
			} else {
				this.getLastInChain(key).setNext(new Node<>(key, e, null));
			}
			result = true;
			this.capasityFilling++;
		}
		return result;
	}
	
    /**
     * Returns true if this set contains the specified element.
     *
     * @param e - element whose presence in this set is to be tested
     * @return true if this set contains the specified element
     */
	public boolean contains(E e) {
		boolean result = false;
		@SuppressWarnings("unchecked")
		Node<E> node = (Node<E>) this.container[this.generateKey(e)];
		while (node != null) {
			if (node.getValue().equals(e)) {
				result = true;
				break;
			}
			node = node.next;
		}
		return result;
	}
	
	/**
	 * Remove specified element form this set
	 * @param e - the specified element
	 * @return true if the item was successfully removed
	 */
	public boolean remove(E e) {
		boolean result = false;
		if (this.contains(e)) {
			@SuppressWarnings("unchecked")
			Node<E> node = (Node<E>) this.container[this.generateKey(e)];
			if (node.getValue().equals(e)) {
				this.removeFirstInChain(node);
			} else {
				this.removeMiddleInChain(node, e);
			}		
			result = true;
		}
		return result;
	}
	
    /**
     * Returns the number of elements in this set.
     * @return the number of elements in this set
     */
	public int getSize() {
		return this.capasityFilling;
	}
	
	/**
	 * Generates a key indicating the cell number in the array.
	 * The key values depend on the size of array.
	 * @param value - value for key generation
	 * @return the key
	 */
	private int generateKey(E value) {
		int key = Objects.hashCode(value);
		key = (key >>> 9) ^ (key >>> 6) ^ (key >>> 3) ^ (key >>> 1);
		return key & (size - 1);
	}
	
	
	/**
	 * Checks the capacity of the set. 
	 * If the number of elements is same as size of array, 
	 * then the capacity is doubled
	 */
	private void checkCapacity() {
		if (this.size <= this.capasityFilling) {
			this.size *= 2;
			this.redistributeKeys();
			this.redistributeNodes();
		}
	}
	
	/**
	 * Updates the key value for each item in the set
	 */
	@SuppressWarnings("unchecked")
	private void redistributeKeys() {
		Node<E> currentNode;
		int newKey;
		for (int i = 0; i < this.container.length; i++) {
			currentNode = (Node<E>) this.container[i]; 
			if (currentNode != null) {
				newKey = this.generateKey(currentNode.getValue());
				do {
					currentNode.setKey(newKey);
					currentNode = currentNode.getNext();
				} while (currentNode != null);				
			}
		}
	}
	
	/**
	 * Redistributes elements in array cells
	 */
	@SuppressWarnings("unchecked")
	private void redistributeNodes() {
		Node<E> currentNode;
		Object[] redistributedContainer = new Node[this.size];
		for (int i = 0; i < this.container.length; i++) {
			currentNode = (Node<E>) this.container[i]; 
			if (currentNode != null) {
				redistributedContainer[currentNode.getKey()] = currentNode;
			}
		}
		this.container = redistributedContainer;
	}
	
	/**
	 * Return the last element in the cell of the array
	 * @param key - the cell number
	 * @return the last element in the cell of the array
	 */
	@SuppressWarnings("unchecked")
	private Node<E> getLastInChain(int key) {
		Node<E> result = (Node<E>) this.container[key];
		if (result.getNext() != null) {
			do {
				result = result.getNext();
			} while (result.getNext() != null);
		}
		return result;
	}
	
	/**
	 * Remove the first element in the cell of the array
	 * @param node - element for removing
	 */
	private void removeFirstInChain(Node<E> node) {
		this.container[this.generateKey(node.getValue())] = node.getNext();
		this.capasityFilling--;
	}
	
	/**
	 * Remove middle element in the cell of the array
	 * @param initialNode - the first node in the chain
	 * @param e - element with this value will be removed
	 */
	private void removeMiddleInChain(Node<E> initialNode, E e) {
		Node<E> nextInChain;
		while (initialNode.getNext() != null) {
			nextInChain = initialNode.getNext();
			if (nextInChain.getValue().equals(e)) {
				initialNode.setNext(nextInChain.getNext());
				this.capasityFilling--;
				break;
			} else {
				initialNode = nextInChain;
			}			
		}
	}
	
	/**
     * Node for SimpleHashSet
     * @param <E>
     */
	static class Node<E> {
		private int key;
		private E value;
		private Node<E> next;
		
		public Node(int key, E value, Node<E> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Returns the value of next
		 * @return the value of next
		 */
		public Node<E> getNext() {
			return next;
		}

		/**
		 * Sets the new value for the next field
		 * @param next
		 */
		public void setNext(Node<E> next) {
			this.next = next;
		}

		/**
		 * Returns the value of key
		 * @return the value of key
		 */
		public int getKey() {
			return key;
		}

		/**
		 * Sets the new value for the key field
		 * @param key
		 */
		public void setKey(int key) {
			this.key = key;
		}
		
		/**
		 * Returns the value of field "value"
		 * @return the value of field "value"
		 */
		public E getValue() {
			return value;
		}

		/**
		 * Sets the new value for the value field
		 * @param value
		 */
		public void setValue(E value) {
			this.value = value;
		}

		/* 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return Objects.hash(this.key, this.value);
		}

		/* 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@SuppressWarnings("unchecked")
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
			Node<E> other = (Node<E>) obj;
			if (key != other.key) {
				return false;				
			}
			if (value == null) {
				if (other.value != null) {
					return false;					
				}
			} else if (!value.equals(other.value)) {
				return false;				
			}
			return true;
		}
	}

	/* 
	 * Displaying all values in the set
	 * @see java.lang.Object#toString()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Node<E> node;
		for (int i = 0; i < this.container.length; i++) {
			node = (Node<E>) this.container[i]; 
			if (node != null) {
				do {
					sb.append(String.format("%s) %s \n", i, node.getValue()));
					node = node.getNext();
				} while (node != null);				
			}
		}
		return sb.toString();
	}
	
}
