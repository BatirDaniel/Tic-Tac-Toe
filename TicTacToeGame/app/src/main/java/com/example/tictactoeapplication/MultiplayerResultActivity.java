package com.example.tictactoeapplication;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MultiplayerResultActivity extends Dialog {

    private  final String message;
    private final MultiplayerGameActivity gameActivity;

    public MultiplayerResultActivity(@NonNull Context context, String message, MultiplayerGameActivity gameActivity){
        super(context);
        this.message = message;
        this.gameActivity = gameActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView messageText = findViewById(R.id.messageText);
        Button startAgainButton = findViewById(R.id.startAgainButton);

        messageText.setText(message);

        startAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameActivity.restartMatch();
                dismiss();
            }
        });
    }
}