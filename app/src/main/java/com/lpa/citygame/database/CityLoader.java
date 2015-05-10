package com.lpa.citygame.database;

import java.util.HashMap;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import com.lpa.citygame.Entity.CitiesContentProvider;
import com.lpa.citygame.Entity.City;

public class CityLoader {
	private DatabaseInstance db;
    private Context context;
	public CityLoader (Context context){
		db = DatabaseInstance.getInstance();
        this.context = context;
	}
	
	public HashMap <String, City> getCityAnswers (){
		HashMap <String, City> resultHashMap = new HashMap <String, City>();
        ContentResolver contentResolver = context.getContentResolver();
        String[] projection = {
                CitiesContentProvider.CITY_NAME,
                CitiesContentProvider.FIRST_CHAR,
                CitiesContentProvider.LAST_CHAR
        };
        Cursor cursor = contentResolver.query(CitiesContentProvider.CONTENT_URI, projection, null, null, null);

        int indexCityNameColumn;
		int indexFirstCharColumn;
		int indexLastCharColumn;
		indexCityNameColumn = cursor.getColumnIndex(CitiesContentProvider.CITY_NAME);
		indexFirstCharColumn = cursor.getColumnIndex(CitiesContentProvider.FIRST_CHAR);
		indexLastCharColumn = cursor.getColumnIndex(CitiesContentProvider.LAST_CHAR);
				
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
