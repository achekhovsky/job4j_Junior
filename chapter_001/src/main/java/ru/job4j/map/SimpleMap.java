package ru.job4j.map;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;

public class SimpleMap<K, V> implements Iterable<V> {
	private static final int DEFAULT_CAPACITY = 16;
	private int modCount = 0;
	private int size;
	private int capasityFilling;
	private Object[] container;
	
	/**
     * Constructs an empty map with the specified capacity.
     */
	public SimpleMap(int size) {
		this.size = size;
		this.capasityFilling = 0;
		this.container = new Object[this.size];
	}
	
	/**
     * Constructs an empty map with an initial capacity of sixteen.
     */
	public SimpleMap() {
		this.container = new Object[DEFAULT_CAPACITY];
		this.size = DEFAULT_CAPACITY;
		this.capasityFilling = 0;
	}
	
    /**
     * Adds the specified element to this map if it is not already present
     * @param key - key of element witch be appended to this container
     * @param value - element to be appended to this container
     * @return true if successfully appended
     */
	@SuppressWarnings("unchecked")
	public boolean insert(K key, V value) {
		boolean result = false;
		if (!this.contains(key)) {
			this.checkCapacity();
			int hash = this.generateHash(key);
			if (this.container[hash] == null) {
				Node<K, V> newNode = new Node<>(hash, key, value, null);
				this.container[hash] = newNode;				
			} else {
				this.getLastInChain((Node<K, V>) this.container[hash])
				.setNext(new Node<>(hash, key, value, null));
			}
			result = true;
			this.capasityFilling++;
		} else {
			this.updateNode(key, value);
		}
		modCount++;
		return result;
	}
	
    /**
     * Returns true if this map contains the specified element.
     *
     * @param key - key whose presence in this map is to be tested
     * @return true if this map contains the specified key
     */
	public boolean contains(K key) {
		boolean result = false;
		@SuppressWarnings("unchecked")
		Node<K, V> node = (Node<K, V>) this.container[this.generateHash(key)];
		while (node != null) {
			if (node.getKey().equals(key)) {
				result = true;
				break;
			}
			node = node.next;
		}
		return result;
	}
	
	/**
	 * Returns the value of the element with the specified key
	 * Returns only the first element of the chain, in the collisions 
	 * the remaining elements are not taken  
	 * @param key - the element's key 
	 * @return value
	 */
	@SuppressWarnings("unchecked")
	public V get(K key) {
		V result = null;
		if (this.contains(key)) {
			result = ((Node<K, V>) this.container[this.generateHash(key)]).getValue();
		}
		return result; 
	}
	
	/**
	 * Remove specified element by its key form this map 
	 * @param key - the specified key
	 * @return true if the item was successfully removed
	 */
	public boolean delete(K key) {
		boolean result = false;
		if (this.contains(key)) {
			@SuppressWarnings("unchecked")
			Node<K, V> node = (Node<K, V>) this.container[this.generateHash(key)];
			if (node.getKey().equals(key)) {
				this.removeFirstInChain(node);
			} else {
				this.removeMiddleInChain(node, key);
			}	
			this.modCount++;
			result = true;
		}
		return result;
	}
	
    /**
     * Returns the number of elements in this map.
     * @return the number of elements in this map
     */
	public int getSize() {
		return this.capasityFilling;
	}
	
	/**
	 * Generates a hash indicating the cell number in the array.
	 * The hash values depend on the size of array.
	 * @param key - key for hash generation
	 * @return the hash
	 */
	private int generateHash(K key) {
		int hash = Objects.hashCode(key);
		hash = (hash >>> 9) ^ (hash >>> 6) ^ (hash >>> 3) ^ (hash >>> 1);
		return hash & (size - 1);
	}
	
	
	/**
	 * Checks the capacity of the map. 
	 * If the number of elements is same as size of array, 
	 * then the capacity is doubled
	 */
	private void checkCapacity() {
		if (this.size <= this.capasityFilling) {
			this.size *= 2;
			this.redistributeHash();
			this.redistributeNodes();
			modCount++;
		}
	}
	
	/**
	 * Updates the hash value for each item in the map
	 */
	@SuppressWarnings("unchecked")
	private void redistributeHash() {
		Node<K, V> currentNode;
		for (int i = 0; i < this.container.length; i++) {
			currentNode = (Node<K, V>) this.container[i]; 
			if (currentNode != null) {
				do {
					currentNode.setHash(this.generateHash(currentNode.getKey()));
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
		Node<K, V> currentNode;
		Object[] redistributedContainer = new Node[this.size];
		for (int i = 0; i < this.container.length; i++) {
			currentNode = (Node<K, V>) this.container[i]; 
			if (currentNode != null) {
				for (Node<K, V> node : currentNode.toList()) {
					if (redistributedContainer[node.getHash()] == null) {
						redistributedContainer[node.getHash()] = node;
					} else {
						this.getLastInChain((Node<K, V>) redistributedContainer[node.getHash()])
						.setNext(node);
					}
				}
			}
		}
		this.container = redistributedContainer;
	}

	/**
	 * Return the last element in the cell of the array
	 * @param key - the cell number
	 * @return the last element in the cell of the array
	 */
	private Node<K, V> getLastInChain(Node<K, V> firstInChain) {
		Node<K, V> currentNode = firstInChain;
		while (currentNode != null && currentNode.getNext() != null) {
			currentNode = currentNode.getNext();
		}
		return currentNode;
	}

	/**
	 * Remove the first element in the cell of the array
	 * @param node - element for removing
	 */
	private void removeFirstInChain(Node<K, V> node) {
		this.container[node.getHash()] = node.getNext();
		this.capasityFilling--;
	}
	
	/**
	 * Remove middle element in the cell of the array
	 * @param initialNode - the first node in the chain
	 * @param key - key with this value will be removed
	 */
	private void removeMiddleInChain(Node<K, V> initialNode, K key) {
		Node<K, V> nextInChain;
		while (initialNode.getNext() != null) {
			nextInChain = initialNode.getNext();
			if (nextInChain.getKey().equals(key)) {
				initialNode.setNext(nextInChain.getNext());
				this.capasityFilling--;
				break;
			} else {
				initialNode = nextInChain;
			}			
		}
	}
	
	
	/**
	 * Updates the element value with the specified key
	 * @param key - the key of the element to be updated
	 * @param value - new value
	 */
	@SuppressWarnings("unchecked")
	private void updateNode(K key, V value) {
		Node<K, V> updated = (Node<K, V>) this.container[this.generateHash(key)];
		do {
			if (updated.getKey().equals(key)) {
				updated.setValue(value);
				break;
			}			
			updated = updated.getNext();
		} while (updated != null);
	}
	
	/**
     * Node for SimpleHashMap
     * @param <K>
     * @param <V>
     */
	static class Node<K, V> {
		private int hash;
		private K key;
		private V value;
		private Node<K, V> next;
		
		public Node(int hash, K key, V value, Node<K, V> next) {
			this.hash = hash;
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Returns the hash of node
		 * @return the hash of node
		 */
		public int getHash() {
			return hash;
		}
		/**
		 * Sets the new hash for the node
		 * @param next
		 */
		public void setHash(int hash) {
			this.hash = hash;
		}		
		/**
		 * Returns the value of node
		 * @return the value of node
		 */
		public Node<K, V> getNext() {
			return next;
		}

		/**
		 * Sets the new value for the next field
		 * @param next
		 */
		public void setNext(Node<K, V> next) {
			this.next = next;
		}

		/**
		 * Returns the value of key
		 * @return the value of key
		 */
		public K getKey() {
			return this.key;
		}

		/**
		 * Sets the new value for the key field
		 * @param key
		 */
		public void setKey(K key) {
			this.key = key;
		}
		
		/**
		 * Returns the value of field "value"
		 * @return the value of field "value"
		 */
		public V getValue() {
			return this.value;
		}

		/**
		 * Sets the new value for the value field
		 * @param value
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		private ArrayList<Node<K, V>> toList() {
			ArrayList<Node<K, V>> list = new ArrayList<Node<K, V>>();
			Node<K, V> currentNode = this;
			do {
				list.add(new Node<K, V>(
						currentNode.getHash(), 
						currentNode.getKey(), 
						currentNode.getValue(), 
						null));
				currentNode = currentNode.getNext();
			} while (currentNode != null);
			return list;
		}

		/* 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + hash;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((next == null) ? 0 : next.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
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
			Node<K, V> other = (Node<K, V>) obj;
			if (hash != other.hash) {
				return false;				
			}
			if (key == null) {
				if (other.key != null) {
					return false;
				}
			} else if (!key.equals(other.key)) {
				return false;
			}
			if (next == null) {
				if (other.next != null) {
					return false;
				}
			} else if (!next.equals(other.next)) {
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
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<V> iterator() {
		return new ConcurrentProtectedIterator(this.modCount);
	}

	/**
	 * Fail-fast iterator.If the container is structurally modified at any time 
	 * after the iterator is created, in any way except through the iterator's own
	 * methods, the iterator will throw a {@link ConcurrentModificationException}
	 * @author achekhovsky
	 * @version 1.0
	 * @since 1.0
	 */
	private class ConcurrentProtectedIterator implements Iterator<V> {
		private final int expectedModCount;
		private int index = 0;
		private Node<K, V> currentNode = null;
		
		public ConcurrentProtectedIterator(int modCount) {
			expectedModCount = modCount;
		}
		
		/* 
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			if (currentNode != null && currentNode.getNext() == null) {
				index++;
				currentNode = null;
			}
			while (index < SimpleMap.this.size && SimpleMap.this.container[index] == null) {
				index++;
			}
			return (index < SimpleMap.this.size);
		}

		/* 
		 * @see java.util.Iterator#next()
		 */
		@SuppressWarnings("unchecked")
		@Override
		public V next() {
			this.checkModification(SimpleMap.this.modCount);
			if (this.hasNext()) {
				if (currentNode == null) {
					currentNode = (Node<K, V>) SimpleMap.this.container[index];
				} else {
					currentNode = currentNode.getNext();
				}
				return currentNode.getValue();	
			} else {
				throw new NoSuchElementException();
			}
		}
		
		/**
		 * Compare current modifications counter with the counter
		 * after the iterator is created
		 * @param mCount
		 */
		private void checkModification(int mCount) {
			if (expectedModCount != mCount) {
				throw new ConcurrentModificationException();
			}
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
		Node<K, V> node;
		for (int i = 0; i < this.container.length; i++) {
			node = (Node<K, V>) this.container[i]; 
			if (node != null) {
				do {
					sb.append(String.format("[%s] -> [%s (%s) | %s] \n", i, node.getKey(), node.getHash(), node.getValue()));
					node = node.getNext();
				} while (node != null);				
			}
		}
		return sb.toString();
	}
}
