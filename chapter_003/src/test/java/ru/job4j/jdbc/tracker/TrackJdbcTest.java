package ru.job4j.jdbc.tracker;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue; 


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TrackJdbcTest {
	Connection conn;
	
	@Before
	public void setUp() throws Exception {
		Properties prp = new Properties();
		prp.setProperty("user", "Alex");
		prp.setProperty("password", "123");
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/j4jcui", prp);
			conn.createStatement().executeUpdate("TRUNCATE items CASCADE");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
    @Test
    public void whenInsertThreeItemsThenObtainFromTheDatabaseTreeItems() {
        Input input = new StubInput(
                "0",
                "name1", "desc1",
                "y",
                "0",
                "name2", "desc2",
                "y",
                "0",
                "name1", "desc3",
                "exit");
        try (Tracker trc = new Tracker(conn)) {
            new StartUI(input, trc).init();
            assertThat(trc.findAll().length, is(3));
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void whenFindItemById() {
        try (Tracker trc = new Tracker(conn)) {
        	Item itm = new Item("name", "find by id");
        	trc.add(itm, "item_1");
            assertThat(trc.findById("item_1").getName(), is("name"));
            assertThat(trc.findById("item_2"), nullValue());
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void whenTwoItemsHaveTheSameNameFindByNameWillReturnTwoItems() {
        Input input = new StubInput(
                "0",
                "name1", "desc1",
                "y",
                "0",
                "name2", "desc2",
                "y",
                "0",
                "name1", "desc3",
                "exit");
        try (Tracker trc = new Tracker(conn)) {
            new StartUI(input, trc).init();
            assertThat(trc.findByName("name1").length, is(2));
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void whenRemovingItemFromTheDatabaseThenTableIsEmpty() {
        try (Tracker trc = new Tracker(conn)) {
        	Item itm = new Item("name1", "desc1");
        	itm.setId("item_1");
        	trc.add(itm);
            assertThat(trc.findAll().length, is(1));
            trc.deleteItem(itm);
            assertThat(trc.findAll().length, is(0));
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void whenUpdatingItemThenNameWillChange() {
        try (Tracker trc = new Tracker(conn)) {
        	Item itm = new Item("name1", "desc1");
        	trc.add(itm, "item_1");
            assertThat(trc.findById("item_1").getName(), is("name1"));
            itm.setName("changed name");
            trc.updateItem(itm);
            assertThat(trc.findById("item_1").getName(), is("changed name"));
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
}
