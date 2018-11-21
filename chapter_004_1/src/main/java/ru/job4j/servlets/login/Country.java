package ru.job4j.servlets.login;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Country implements Comparable<Country> {
	private final TreeSet<String> cities = new TreeSet<>();
	private final String name;

	public Country(String name) {
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	public boolean addCity(String city) {
		return this.cities.add(city);
	}
	
	public boolean removeCity(String city) {
		return this.cities.remove(city);
	}
	
	public Set<String> getCities() {
		return this.cities;
	}
	
	public String getCitiesInJSON() {
		String result = "";
		try {
			ObjectMapper om = new ObjectMapper();
			result = om.writeValueAsString(this.cities);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return result;
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Country o) {
		return this.name.compareTo(o.name);
	}
	
	
}
