package ru.job4j.servlets.ajax;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Parser {
	private static final Logger LOG = Logger.getLogger(Parser.class.getName());
	
	public static User parseToUser(String jsonUser) {
		User usr = null;
		try {
			ObjectMapper om = new ObjectMapper();
			usr = om.readValue(jsonUser, User.class);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Parse::parseToUser", e);
		} 
		return usr;
	}
}
