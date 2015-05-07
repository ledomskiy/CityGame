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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class PreferencesActivity extends ActionBarActivity implements OnClickListener{
    public static final String PLAY_PREFERENCES = "PLAY_PREFERENCES";
    public static final String ANSWER_TIME = "ANSWER_TIME";
    public static final String AMOUNT_HINT = "AMOUNT_HINT";
    public static final String VS_ANDROID = "VS_ANDROID";
    public static final String ANDROID_LEVEL = "ANDROID_LEVEL";

    private EditText player1;
    private EditText player2;

    private CheckBox vsAndroidCheckBox;

    Spinner answerTimeSpinner;
    Spinner amountHintSpinner;
    Spinner androidLevelSpinner;

    private void loadPreferences (){
        SharedPreferences preferences = getSharedPreferences(PLAY_PREFERENCES, Activity.MODE_PRIVATE);
        answerTimeSpinner.setSelection((int)preferences.getLong(ANSWER_TIME, 0)/30);
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
		
		player1 = (EditText)findViewById (R.id.player1);
		player2 = (EditText)findViewById (R.id.player2);

        vsAndroidCheckBox = (CheckBox)findViewById(R.id.vs_android);
        vsAndroidCheckBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vsAndroidCheckBox.isChecked()){
                    player2.setEnabled (false);
                }else{
                    player2.setEnabled (true);
                }
            }
        });
		
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

        loadPreferences();

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
                preferencesEditor.putBoolean(VS_ANDROID, vsAndroidCheckBox.isChecked());
                preferencesEditor.apply();

                PlayState playState = PlayState.getInstance();
				playState.createNewGame(player1.getText().toString(), player2.getText().toString());
                playState.setVsAndroid(vsAndroidCheckBox.isChecked());
				Intent intent = new Intent (this, PlayActivity.class);
				startActivity (intent);
				break;
		}
	}

}
