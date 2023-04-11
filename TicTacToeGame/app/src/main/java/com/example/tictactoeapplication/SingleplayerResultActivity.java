package com.example.tictactoeapplication;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SingleplayerResultActivity extends Dialog {
    private  final String message;
    private final SingleplayerGameActivity singleplayerGameActivity;

    public SingleplayerResultActivity(@NonNull Context context, String message, SingleplayerGameActivity singleplayerGameActivity){
        super(context);
        this.message = message;
        this.singleplayerGameActivity = singleplayerGameActivity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_singleplayer);

        TextView messageText = findViewById(R.id.messageText1);
        Button startAgainButton = findViewById(R.id.startAgainButton1);

        messageText.setText(message);

        startAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleplayerGameActivity.restartMatch();
                dismiss();
            }
        });
    }
}