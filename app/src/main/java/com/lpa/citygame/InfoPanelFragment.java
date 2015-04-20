package com.lpa.citygame;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class InfoPanelFragment extends Fragment implements TimerService.ITimerObserver {

    private TextView playerTextView;
    private TextView timerTextView;

    private String player;
    private int answerTime;
    private int amountHints;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle bundle){
        View view = inflater.inflate(R.layout.fragment_info_panel, container, false);
        playerTextView = (TextView)view.findViewById(R.id.player_current);
        timerTextView = (TextView)view.findViewById(R.id.timer);
        return view;
    }

    public void onPlayerChanged (String player, int answerTime, int amountHints){
        this.player = player;
        this.answerTime = answerTime;
        this.amountHints = amountHints;

        playerTextView.setText(player);

        /*
        new CountDownTimer (30000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerTextView.setText("done!");
            }
        }.start();
        */
    }

    public void onTimerTick (long secondsUntilFinish){
        timerTextView.setText ("seconds remaining: " + secondsUntilFinish);
    }

    public void onTimerFinish (){
        timerTextView.setText ("done");
    }

}
