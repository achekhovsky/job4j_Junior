package ru.job4j.servlets.login;

public interface CountryStore<T> {
	
	public boolean addCountry(Country ctry);
	public boolean removeCountry(Country ctry);
	public Country getCountry(String ctry);
	public T getCountries();
}
