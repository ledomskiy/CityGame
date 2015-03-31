package com.lpa.citygame.Entity;

public class City {
	private String cityName;
	private String firstChar;
	private String lastChar;
	
	public City (String cityName, String firstChar, String lastChar)
	{
		this.cityName = cityName;
		this.firstChar = firstChar;
		this.lastChar = lastChar;
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public String getFirstChar() {
		return firstChar;
	}
	
	public String getLastChar() {
		return lastChar;
	}
}
