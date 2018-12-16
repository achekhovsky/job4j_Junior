package ru.job4j.servlets.cinema;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Parser {
	private static final Logger LOG = Logger.getLogger(Parser.class.getName());
	
	public static String parseToJson(Object value) {
		String json = "";
		try {
			ObjectMapper om = new ObjectMapper();
			json = om.writeValueAsString(value);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Parse::parseToJson", e);
		} 
		return json;
	}
}
