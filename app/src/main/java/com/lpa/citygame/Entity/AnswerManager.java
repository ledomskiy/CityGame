package com.lpa.citygame.Entity;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import com.lpa.citygame.database.CityLoader;

public class AnswerManager {
    private Context context;

	public enum AnswerStatus {
		SUCCESS,
		ALREADY_EXIST,
		CITY_NOT_EXIST
	}
	
	private static AnswerManager instance;
	private HashMap <String, City> cityList;
	private ArrayList<Answer> answers;
	
	private AnswerManager(Context context){
        this.context = context;
		CityLoader cityLoader = new CityLoader (context);
		cityList = cityLoader.getCityAnswers();
		answers = new ArrayList<Answer>();
	};
	
	public static AnswerManager getInstance (Context context){
		if (instance == null){
			instance = new AnswerManager(context);
		}
		return instance;
	}
	
	// Проверка, что такой ответ уже был
	private boolean isAnswerAlreadyExist (Answer answer){
		for (Answer rAnswer : answers){
			if (rAnswer.getCity().equals(answer.getCity())){
				return true;
			}
		}
		return false;
	}
	
	// Проверка наличия города в списке городов
	private boolean isCityExist (Answer answer){
		return cityList.containsKey(answer.getCity());
	}
	
	// Попытка добавления нового ответа
	public AnswerStatus addAnswer (Answer answer){
		if (isAnswerAlreadyExist(answer)){
			return AnswerStatus.ALREADY_EXIST;
		}
		
		if (!isCityExist(answer)){
			return AnswerStatus.CITY_NOT_EXIST;
		}
		// Добавляем в начало, чтобы история просматривалась в обратном направлении
		answers.add(0, answer);
		return AnswerStatus.SUCCESS;
	}
	
	// Получение последнего символа по городу
	public String getLastChar (String cityName){
		return cityList.get(cityName).getLastChar();
	}

	// Возвращает историю ответов
	public ArrayList<Answer> getAnswers (){
		return answers;
	}
}
