package com.lpa.citygame.database;

import java.util.HashMap;

import android.database.Cursor;

import com.lpa.citygame.Entity.City;

public class CityLoader {
	DatabaseInstance db;
	public CityLoader (){
		db = DatabaseInstance.getInstance();
	}
	
	public HashMap <String, City> getCityAnswers (){
		HashMap <String, City> resultHashMap = new HashMap <String, City>();
		
		Cursor cursor = db.getWritableDatabase().rawQuery("SELECT DISTINCT CityName, FirstChar, LastChar FROM City;", null);
		int indexCityNameColumn;
		int indexFirstCharColumn;
		int indexLastCharColumn;
		indexCityNameColumn = cursor.getColumnIndex("CityName");
		indexFirstCharColumn = cursor.getColumnIndex("FirstChar");
		indexLastCharColumn = cursor.getColumnIndex("LastChar");
				
		String cityName;
		String firstChar;
		String lastChar;
		
		cursor.moveToFirst();
		do {
			cityName = cursor.getString (indexCityNameColumn).toUpperCase();
			firstChar = cursor.getString (indexFirstCharColumn);
			lastChar = cursor.getString (indexLastCharColumn);
			
			resultHashMap.put(cityName, new City (cityName, firstChar, lastChar));
		} while (cursor.moveToNext());
		
		cursor.close();
		
		return resultHashMap;
	}
}
