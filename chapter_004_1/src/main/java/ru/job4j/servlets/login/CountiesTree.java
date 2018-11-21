package ru.job4j.servlets.login;

import java.util.Set;
import java.util.TreeSet;

public class CountiesTree implements CountryStore<Set<Country>> {
	private final TreeSet<Country> countries = new TreeSet<>();
	private static final CountiesTree COUNRIES_STORE = new CountiesTree();

	private CountiesTree() {
		Country firstC = new Country("First country");
		firstC.addCity("First city");
		firstC.addCity("Second city");
		firstC.addCity("Third city");
		Country secondC = new Country("Second country");
		secondC.addCity("Fourth city");
		secondC.addCity("Fifth city");
		secondC.addCity("Sixth city");
		Country thirdC = new Country("Third country");
		thirdC.addCity("Seventh city");
		thirdC.addCity("Eighth city");
		thirdC.addCity("Ninth city");
		
		this.countries.add(firstC);
		this.countries.add(secondC);
		this.countries.add(thirdC);
	}
	
	public static CountiesTree getInstance() {
		return COUNRIES_STORE;
	}
	
	@Override
	public boolean addCountry(Country ctry) {
		return this.countries.add(ctry);
	}

	@Override
	public boolean removeCountry(Country ctry) {
		return this.countries.remove(ctry);
	}

	@Override
	public Country getCountry(String ctry) {
		Country result = null;
		for (Country c : this.countries) {
			if (c.getName().equals(ctry)) {
				result = c;
				break;
			}
		}
		return result;
	}

	@Override
	public TreeSet<Country> getCountries() {
		return this.countries;
	}
}
