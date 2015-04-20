package com.lpa.citygame;

import com.lpa.citygame.Entity.PlayState;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class PreferencesActivity extends ActionBarActivity implements OnClickListener{
    public static final String PLAY_PREFERENCES = "PLAY_PREFERENCES";
    public static final String ANSWER_TIME = "ANSWER_TIME";
    public static final String AMOUNT_HINT = "AMOUNT_HINT";
    public static final String ANDROID_LEVEL = "ANDROID_LEVEL";

    private EditText player1;
    private EditText player2;

    Spinner answerTimeSpinner;
    Spinner amountHintSpinner;
    Spinner androidLevelSpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
		
		player1 = (EditText)findViewById (R.id.player1);
		player2 = (EditText)findViewById (R.id.player2);
		
		answerTimeSpinner = (Spinner)findViewById (R.id.answer_time);
		ArrayAdapter<CharSequence> answerTimeAdapter = ArrayAdapter.createFromResource(
				this, R.array.answer_time_array, android.R.layout.simple_spinner_item);
		answerTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		answerTimeSpinner.setAdapter(answerTimeAdapter);
		
		amountHintSpinner = (Spinner)findViewById (R.id.amount_hint);
		ArrayAdapter<CharSequence> amountHintAdapter = ArrayAdapter.createFromResource(
				this, R.array.amount_hint_array, android.R.layout.simple_spinner_item);
		amountHintAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		amountHintSpinner.setAdapter(amountHintAdapter);
		
		androidLevelSpinner = (Spinner)findViewById (R.id.android_level);
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
                // Save preferences
                SharedPreferences preferences = getSharedPreferences(PLAY_PREFERENCES, Activity.MODE_PRIVATE);
                SharedPreferences.Editor preferencesEditor = preferences.edit();
                preferencesEditor.putLong(ANSWER_TIME, 30l * answerTimeSpinner.getSelectedItemPosition());
                preferencesEditor.apply();

				PlayState.getInstance().createNewGame(player1.getText().toString(), player2.getText().toString());
				Intent intent = new Intent (this, PlayActivity.class);
				startActivity (intent);
				break;
		}
	}

}
