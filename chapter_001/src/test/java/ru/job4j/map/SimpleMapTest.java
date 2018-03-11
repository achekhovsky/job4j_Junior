package ru.job4j.map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class SimpleMapTest {
	private SimpleMap<String, Integer>  sm;

	@Before
	public void setUp() throws Exception {
		sm = new SimpleMap<String, Integer>();
	}

	@Test
	public void ifInsertMoreElementThenMapSizeThenAutoresize() {
		sm = new SimpleMap<String, Integer>(2);
		assertThat(sm.insert("first", 1), is(true));
		assertThat(sm.insert("second", 2), is(true));
		assertThat(sm.insert("third", 3), is(true));
		assertThat(sm.getSize(), is(3));
	}
	
	@Test
	public void ifGetFirstThenOne() {
		sm.insert("first", 1);
		assertThat(sm.get("first"), is(1));
	}
	
	@Test
	public void ifAddedTwoTwiceThenAddedOnlySecond() {
		assertThat(sm.insert("first", 1), is(true));
		assertThat(sm.insert("first", 1), is(false));
	}
	
	@Test
	public void ifAddedThreeThenContainsThree() {
		sm.insert("third", 3);
		assertThat(sm.contains("first"), is(false));
		assertThat(sm.contains("third"), is(true));
	}
	
	@Test
	public void ifAddedOneAndRemoveThenMapIsEmpty() {
		sm.insert("first", 1);
		assertThat(sm.delete("second"), is(false));
		assertThat(sm.delete("first"), is(true));
		assertThat(sm.contains("first"), is(false));		
	}
	
	@Test
	public void ifAddedOneAndRemoveItTwiceThenSecondRemoveReturnFalse() {
		sm.insert("first", 1);
		assertThat(sm.delete("first"), is(true));
		assertThat(sm.delete("first"), is(false));		
	}
	
	@Test
	public void ifAddedDuplicateThenSizeIsOne() {
		assertThat(sm.insert("first", 1), is(true));
		assertThat(sm.insert("first", 1), is(false));
		assertThat(sm.getSize(), is(1));
	}
	
	@Test
	public void ifAddedThreeButSecondAndThirdIsDuplicateThenSizeIsTwo() {
		assertThat(sm.insert("first", 1), is(true));
		assertThat(sm.insert("second", 2), is(true));
		assertThat(sm.insert("second", 3), is(false));
		assertThat(sm.getSize(), is(2));
	}
	
	@Test
	public void sequentialHasNextInvocationDoesntAffectRetrievalOrder() {
		sm.insert("1", 1);
		Iterator<Integer> itr = sm.iterator();
		
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(1));
		assertThat(itr.hasNext(), is(false));
	}
	
	@Test(expected = NoSuchElementException.class)
	public void ifNextAfterLastElementThenNoSuchElement() {
		sm.insert("1", 1);
		Iterator<Integer> itr = sm.iterator();
		
		assertThat(itr.next(), is(1));
		itr.next();
	}
	
	@Test
	public void ifThreeElementIntoMapThenNextInvokeThreeTimes() {
		sm.insert("1", 1);
		sm.insert("2", 2);
		sm.insert("3", 3);
		int count = 0;
		
		Iterator<Integer> itr = sm.iterator();
		while (itr.hasNext()) {
			itr.next();
			count++;
		}
		assertThat(count, is(3));
	}
	
	@Test
	public void ifRemovingAllElementsThenSizeIsZero() {
		sm = new SimpleMap<String, Integer>(3);
		sm.insert("1", 1);
		sm.insert("2", 2);
		sm.insert("3", 3);
		sm.delete("3");
		sm.delete("2");
		sm.delete("1");
		assertThat(sm.getSize(), is(0));
	}
	
	@Test
	public void ifOtherNodesAreCheckedForEquivalence() {
		SimpleMap.Node<String, Integer> firstNode = 
				new SimpleMap.Node<String, Integer>(3, "first", 1, null);
		SimpleMap.Node<String, Integer> secondNode = 
				new SimpleMap.Node<String, Integer>(3, "first", 1, null);
		SimpleMap.Node<String, Integer> thirdNode = 
				new SimpleMap.Node<String, Integer>(3, null, 2, null);
		SimpleMap.Node<String, Integer> fourthNode = 
				new SimpleMap.Node<String, Integer>(3, "first", 1, firstNode);
		SimpleMap.Node<String, Integer> fifthNode = 
				new SimpleMap.Node<String, Integer>(0, "first", 1, null);
		assertThat(firstNode.equals(secondNode), is(true));
		assertThat(firstNode.equals(thirdNode), is(false));
		assertThat(firstNode.equals(fourthNode), is(false));
		assertThat(firstNode.equals(fifthNode), is(false));
		assertThat(firstNode.equals(null), is(false));
	}
	
	@Test(expected = ConcurrentModificationException.class)
	public void ifTheMapIsChangingAfterCreatingTheIteratorThenConcurrentModification() {
		sm.insert("1", 1);
		sm.insert("2", 2);
		
		Iterator<Integer> itr = sm.iterator();
		itr.next();
		sm.insert("3", 3);
		itr.next();
	}
}
