package com.lpa.citygame;

import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.lpa.citygame.Entity.Answer;
import com.lpa.citygame.Entity.AnswerManager;
import com.lpa.citygame.Entity.AnswerManager.AnswerStatus;
import com.lpa.citygame.Entity.PlayState;

public class PlayActivity extends ActionBarActivity implements InputAnswerFragment.OnInputAnswerListener, TimerService.ITimerObserver{

    private InfoPanelFragment infoPanelFragment;
    private ArrayAdapter<Answer> historyArrayAdapter;

    private TimerService timerService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected (ComponentName className, IBinder service){
            Log.v("TIMER_SERVICE", "ServiceConnected");
            timerService = ((TimerService.TimerBinder)service).getService();
            timerService.attachObserver(infoPanelFragment);
            timerService.attachObserver(PlayActivity. this);
            timerService.startTimer();
        }

        public void onServiceDisconnected (ComponentName className){
            Log.v ("TIMER_SERVICE", "ServiceDisconnected");
            timerService = null;
        }
    };

    @Override
	public void onCreate (Bundle savedInstanceState){
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_play);

        FragmentManager fragmentManager = getFragmentManager();

        infoPanelFragment = (InfoPanelFragment)fragmentManager.findFragmentById(R.id.info_panel_fragment);
        PlayState playState = PlayState.getInstance();

        infoPanelFragment.onPlayerChanged(playState.getCurrentPlayer(), 0, 0);
		
		HistoryAnswerFragment historyAnswerFragment = (HistoryAnswerFragment)fragmentManager.findFragmentById(R.id.history_answer_fragment);
		historyArrayAdapter = new ArrayAdapter<Answer> (this, android.R.layout.simple_list_item_1, AnswerManager.getInstance().getAnswers());
		historyAnswerFragment.setListAdapter(historyArrayAdapter);

        // Привязываем TimerService
        Log.v ("BIND_TIMER", "Start bind service...");
        Intent bindTimerIntent = new Intent (this, TimerService.class);
        bindService (bindTimerIntent, serviceConnection, Context.BIND_AUTO_CREATE);

	}
	
	public AnswerStatus onInputAnswer(String city) {
		PlayState playState = PlayState.getInstance();
		AnswerManager answerManager = AnswerManager.getInstance();
		Answer answer = new Answer (playState.getCurrentPlayer(), city, 0);
		AnswerStatus answerStatus = answerManager.addAnswer(answer);
		
		if (answerStatus == AnswerStatus.SUCCESS){
			playState.switchCurrentPlayer();
			infoPanelFragment.onPlayerChanged(playState.getCurrentPlayer(), 0, 0);
			historyArrayAdapter.notifyDataSetChanged();
            timerService.restartTimer();
		}
		return answerStatus;
	}

    public void showResultActivity (){
        Intent intent = new Intent (this, ResultActivity.class);
        startActivity (intent);
    }

    @Override
    public void onTimerTick(long secondsUntilFinish) {

    }

    @Override
    public void onTimerFinish() {
       showResultActivity();
    }
}
