package com.lpa.citygame.Entity;

public class Answer {
	private String player;
	private String cityName;
	private int timeInSecond;
	
	public Answer(String player, String cityName, int timeInSecond) {
		this.player = player;
		this.cityName = cityName;
		this.timeInSecond = timeInSecond;
	}
	
	public String getPlayer() {
		return player;
	}
	
	public String getCity() {
		return cityName;
	}
	
	public int getTimeInSecond() {
		return timeInSecond;
	}
	
	@Override
	public String toString(){
		return player + ": " + cityName; 
	}
}
