package com.lpa.citygame;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lpa.citygame.Entity.Answer;
import com.lpa.citygame.Entity.AnswerManager;
import com.lpa.citygame.Entity.AnswerManager.AnswerStatus;
import com.lpa.citygame.Entity.PlayState;

public class PlayActivity extends ActionBarActivity implements InputAnswerFragment.OnInputAnswerListener{
	private TextView playerCurrent;
	
	private ArrayAdapter<Answer> historyArrayAdapter;
	
	@Override
	public void onCreate (Bundle savedInstanceState){
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_play);
		playerCurrent = (TextView)findViewById (R.id.player_current);
		playerCurrent.setText (PlayState.getInstance().getCurrentPlayer());
		
		FragmentManager fragmentManager = getFragmentManager();
		HistoryAnswerFragment historyAnswerFragment = (HistoryAnswerFragment)fragmentManager.findFragmentById(R.id.history_answer_fragment);
		historyArrayAdapter = new ArrayAdapter<Answer> (this, android.R.layout.simple_list_item_1, AnswerManager.getInstance().getAnswers());
		historyAnswerFragment.setListAdapter(historyArrayAdapter);
		
		
	}
	
	public AnswerStatus onInputAnswer(String city) {
		PlayState playState = PlayState.getInstance();
		AnswerManager answerManager = AnswerManager.getInstance();
		Answer answer = new Answer (playState.getCurrentPlayer(), city, 0);
		AnswerStatus answerStatus = answerManager.addAnswer(answer);
		
		if (answerStatus == AnswerStatus.SUCCESS){
			playState.switchCurrentPlayer();
			playerCurrent.setText (PlayState.getInstance().getCurrentPlayer());
			historyArrayAdapter.notifyDataSetChanged();
		}
		return answerStatus;
	}

}
