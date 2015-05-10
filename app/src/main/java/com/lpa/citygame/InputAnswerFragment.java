package com.lpa.citygame;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lpa.citygame.Entity.AndroidPlayer;
import com.lpa.citygame.Entity.AnswerManager;
import com.lpa.citygame.Entity.AnswerManager.AnswerStatus;
import com.lpa.citygame.Entity.PlayState;


public class InputAnswerFragment extends Fragment {
	public interface OnInputAnswerListener {
		public AnswerManager.AnswerStatus onInputAnswer (String city);
	}
	
	private OnInputAnswerListener onInputAnswerListener;
	
	private TextView answerStatusTextView;
    private TextView answerFirstCharTextView;
    private EditText answerEditText;
    private Button inputAnswerButton;

    private AndroidPlayer androidPlayer;
	
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_input_answer, container, false);
		
		answerFirstCharTextView = (TextView)view.findViewById(R.id.answer_first_char);
		answerEditText = (EditText)view.findViewById(R.id.answer);
		answerStatusTextView = (TextView)view.findViewById(R.id.answer_status);
		inputAnswerButton = (Button)view.findViewById(R.id.input_answer);
		
		inputAnswerButton.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
                        answerButtonPressed();
					}
				}
		);

        androidPlayer = new AndroidPlayer ();

		return view;
	}

    public void updateUI (AnswerStatus answerStatus, String cityName){
        switch (answerStatus){
            case SUCCESS:
                answerStatusTextView.setText(R.string.answer_status_success);
                answerStatusTextView.setTextColor(getResources().getColor(R.color.answer_status_success));
                answerEditText.setText("");
                String lastCharCityName = AnswerManager.getInstance(getActivity()).getLastChar(cityName);
                answerFirstCharTextView.setText(lastCharCityName);

                Log.v("AnswerButtonPressed","Before Android");
                if (PlayState.getInstance().isCurrentPlayerAndroid()){
                    String cityNameAndroid = androidPlayer.generateAnswer(lastCharCityName);
                    AnswerStatus answerStatusAndroid = onInputAnswerListener.onInputAnswer(cityNameAndroid);
                    updateUI (answerStatusAndroid, cityNameAndroid);
                    Log.v("AnswerButtonPressed","After Android");
                }

                break;
            case ALREADY_EXIST:
                answerStatusTextView.setText(R.string.answer_status_already_exist);
                answerStatusTextView.setTextColor(getResources().getColor(R.color.answer_status_already_exist));
                break;
            case CITY_NOT_EXIST:
                answerStatusTextView.setText(R.string.answer_status_city_not_exist);
                answerStatusTextView.setTextColor(getResources().getColor(R.color.answer_status_city_not_exist));
                break;
        }
    }

    public void answerButtonPressed (){
        AnswerStatus answerStatus;
        String cityName = answerFirstCharTextView.getText().toString().trim() +
                answerEditText.getText().toString().toUpperCase();
        answerStatus = onInputAnswerListener.onInputAnswer(cityName);
        updateUI (answerStatus, cityName);

    }
	
	@Override
	public void onAttach (Activity activity){
		super.onAttach(activity);
		
		try {
			onInputAnswerListener = (OnInputAnswerListener)activity;
		} catch (ClassCastException e){
			throw new ClassCastException (activity.toString() + " must implement OnInputAnswerListener");
		}
	}

}
