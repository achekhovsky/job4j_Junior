package ru.job4j.loop;

/**
 * Node class.
 * @author achekhovsky
 * @param <E> - type of objects
 */
public class Node<E> {
	E value;
	Node<E>	next;
	public Node(E newVal) {
		this.value = newVal;
	}
	
	/**
	 * Checks the presence of looping in the chain
	 * @param first - the first element in the chain
	 * @return true if it's looped, otherwise false
	 */
	public static boolean hasCycle(Node<?> first) {
		boolean result = false;
		int count = 0;
		Node<?> loopFinder = first;
		while (loopFinder != null && !result) {
			result = compareWithEachElementOfSegment(count, first, loopFinder);
			loopFinder = loopFinder.next;
			count++;
		}
		return result;
	}
	
	/**
	 * Compare each element of segment with the specified element
	 * @param segmentLength - length of segment
	 * @param firstlementOfSegment - the first element of the segment 
	 * @param compared - compared element
	 * @return true if compared references point to the same element
	 */
	private static boolean compareWithEachElementOfSegment(int segmentLength,
			Node<?> firstlementOfSegment,
			Node<?> compared) {
		boolean result = false;
		Node<?> segment = null;
		segment = firstlementOfSegment;
		for (int i = 0; i < segmentLength; i++) {
			if (compared == segment) {
				result = true;
				break;
			}
			segment = segment.next;
		}
		return result;
	}
	
}
