package ru.job4j.servlets.login;

import static org.mockito.Mockito.*;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

import org.sqlite.SQLiteDataSource;

/**
 * A helper class for testing the UserController servlet . Contains method that mocked
 * Context class and returns the DataSource object for sqlite database connection when
 * the lookup method is called
 * @author achekhovsky
 *
 */
public class TesterInitialContextFactory implements InitialContextFactory {
    public Context getInitialContext(Hashtable<?, ?> arg0)
            throws NamingException {
        Context context = mock(Context.class);
        when(context.lookup(anyString())).thenReturn(getDataSource());
        return context;
    }
    
    private SQLiteDataSource getDataSource() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:test.db");
    	return  ds;
    }
}
