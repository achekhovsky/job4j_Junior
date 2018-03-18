package ru.job4j.tree;

import static org.junit.Assert.*;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the SimpleTreeImplementation
 * @author achekhovsky
 */
public class SimpleTreeImplementationTest {
	SimpleTreeImplementation<Integer> tree;
	
	@Before
	public void setUp() throws Exception {
		tree = new SimpleTreeImplementation<Integer>(1);
	}

    @Test
    public void when6ElFindLastThen6() {
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(
                tree.findBy(2).isPresent(),
                is(true)
        );
        assertThat(
                tree.findBy(3).isPresent(),
                is(true)
        );
        assertThat(
                tree.findBy(4).isPresent(),
                is(true)
        );
        assertThat(
                tree.findBy(5).isPresent(),
                is(true)
        );
        assertThat(
                tree.findBy(6).isPresent(),
                is(true)
        );
        assertThat(
                tree.findBy(7).isPresent(),
                is(false)
        );
    }
    
    @Test
    public void whenAddedSixElementThenIteratorIsNextSixTimes() {
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        Iterator<Integer> itr = tree.iterator();
        int count = 0;
        while (itr.hasNext()) {
        	itr.next();
        	count++;
        }
        assertThat(count, is(6));
    }

	@Test
	public void whenIsBinaryThenTrue() {
		tree.add(1, 2);
		tree.add(2, 5);
		tree.add(2, 4);
		assertThat(tree.add(3, 6), is(false));
		assertThat(tree.isBinary(), is(true));
	}
	
	@Test
	public void whenIsNotBinaryThenFalse() {
		tree.add(1, 2);
		tree.add(2, 5);
		tree.add(2, 4);
		assertThat(tree.add(2, 6), is(true));
		assertThat(tree.isBinary(), is(false));
	}

    @Test
    public void whenSevenElFindNotExistThenOptionEmpty() {
        tree.add(1, 2);
        assertThat(
                tree.findBy(2).isPresent(),
                is(true)
        );
        assertThat(
                tree.findBy(7).isPresent(),
                is(false)
        );
    }
    
    @Test
    public void whenAddedSameElementThenFalse() {
        assertThat(
        		tree.add(1, 2),
                is(true)
        );
        assertThat(
        		tree.add(1, 2),
                is(false)
        );
    }

}
