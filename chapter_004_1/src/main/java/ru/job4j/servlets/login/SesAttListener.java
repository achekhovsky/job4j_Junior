package ru.job4j.servlets.login;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Application Lifecycle Listener implementation class SesAttListener
 *
 */
public class SesAttListener implements HttpSessionAttributeListener {

    /**
     * Default constructor. 
     */
    public SesAttListener() {
    }
    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
       	if (event.getName().equals("login")) {
    		event.getSession().invalidate();
    	}
    	HttpSessionAttributeListener.super.attributeRemoved(event);
    	
    }
}
