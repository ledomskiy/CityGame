package com.lpa.citygame;

import android.app.IntentService;
import android.content.Intent;
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

    public TimerService (){
        super ("TimerService");
        observers = new ArrayList<ITimerObserver> ();
    }

    public void attachObserver (ITimerObserver observer){
        observers.add (observer);
    }


    private void startTimer (long secondsInFuture){
        new CountDownTimer (secondsInFuture*1000, 1000){
            public void onTick (long milliUntilFinish){
                for (ITimerObserver observer : observers){
                    observer.onTimerTick(milliUntilFinish/1000);
                }
            }
            public void onFinish (){
                for (ITimerObserver observer : observers){
                    observer.onTimerFinish();
                }
            }
        };
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
