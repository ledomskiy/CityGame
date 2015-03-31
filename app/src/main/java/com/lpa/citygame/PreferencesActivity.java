package com.lpa.citygame;

import com.lpa.citygame.Entity.PlayState;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class PreferencesActivity extends ActionBarActivity implements OnClickListener{
    private EditText player1;
    private EditText player2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
		
		player1 = (EditText)findViewById (R.id.player1);
		player2 = (EditText)findViewById (R.id.player2);
		
		Spinner answerTimeSpinner = (Spinner)findViewById (R.id.answer_time);
		ArrayAdapter<CharSequence> answerTimeAdapter = ArrayAdapter.createFromResource(
				this, R.array.answer_time_array, android.R.layout.simple_spinner_item);
		answerTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		answerTimeSpinner.setAdapter(answerTimeAdapter);
		
		Spinner amountHintSpinner = (Spinner)findViewById (R.id.amount_hint);
		ArrayAdapter<CharSequence> amountHintAdapter = ArrayAdapter.createFromResource(
				this, R.array.amount_hint_array, android.R.layout.simple_spinner_item);
		amountHintAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		amountHintSpinner.setAdapter(amountHintAdapter);
		
		Spinner androidLevelSpinner = (Spinner)findViewById (R.id.android_level);
		ArrayAdapter<CharSequence> androidLevelAdapter = ArrayAdapter.createFromResource(
				this, R.array.android_level_array, android.R.layout.simple_spinner_item);
		androidLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		androidLevelSpinner.setAdapter(androidLevelAdapter);
		
		Button playButton = (Button)findViewById (R.id.play);
		playButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick (View v){
		switch (v.getId()){
			case R.id.play:
				PlayState.getInstance().CreateNewGame(player1.getText().toString(), player2.getText().toString());
				Intent intent = new Intent (this, PlayActivity.class);
				startActivity (intent);
				break;
		}
	}

}
