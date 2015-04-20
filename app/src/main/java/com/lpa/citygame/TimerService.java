package com.lpa.citygame;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;

import java.util.ArrayList;

/**
 * Created by lpa on 14.04.15.
 */
public class TimerService extends IntentService{

    public interface ITimerObserver {
        public void onTimerTick (long secondsUntilFinish);
        public void onTimerFinish ();
    }

    private ArrayList<ITimerObserver> observers;
    private CountDownTimer timer;

    public TimerService (){
        super ("TimerService");
        observers = new ArrayList<ITimerObserver> ();
        timer = null;
    }

    public void attachObserver (ITimerObserver observer){
        observers.add (observer);
    }


    public void startTimer (){
        SharedPreferences preferences = getSharedPreferences(PreferencesActivity.PLAY_PREFERENCES, Activity.MODE_PRIVATE);
        long answerTimeInSeconds = preferences.getLong(PreferencesActivity.ANSWER_TIME, 0);

        if(timer == null) {
            timer = new CountDownTimer(answerTimeInSeconds * 1000, 1000) {
                public void onTick(long milliUntilFinish) {
                    for (ITimerObserver observer : observers) {
                        observer.onTimerTick(milliUntilFinish / 1000);
                    }
                }

                public void onFinish() {
                    for (ITimerObserver observer : observers) {
                        observer.onTimerFinish();
                    }
                }
            };
            timer.start();
        }
    }

    public void finishTimer (){
        timer.cancel();
        timer = null;
    }

    @Override
    protected void onHandleIntent (Intent intent){

    }

    @Override
    public IBinder onBind (Intent intent){
        return binder;
    }

    public class TimerBinder extends Binder {
        TimerService getService (){
            return TimerService.this;
        }
    }

    private final IBinder binder = new TimerBinder();
}
