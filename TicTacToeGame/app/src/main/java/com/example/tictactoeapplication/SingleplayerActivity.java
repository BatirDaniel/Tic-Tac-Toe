package com.example.tictactoeapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SingleplayerActivity extends AppCompatActivity {

    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);

        EditText player = findViewById(R.id.playerOneS);
        RadioButton radioButtonX = findViewById(R.id.rb_crossed);
        RadioButton radioButtonO = findViewById(R.id.rb_zero);
        btnStart = findViewById(R.id.startSingleplayerGameButton);

        String[] difficultyLevels = {"Easy", "Medium", "Hard"};
        Spinner spinner = findViewById(R.id.spinner_dificulty);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, difficultyLevels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        btnStart.setOnClickListener(v -> {
            String getPlayerName = player.getText().toString();
            String getPlayerAIName = "Player AI";
            String selectedDifficultyLevel = (String) spinner.getSelectedItem();

            if (getPlayerName.isEmpty()){
                Toast.makeText(SingleplayerActivity.this,"Va rog sa introduceti numele dumneavostra !",
                        Toast.LENGTH_SHORT).show();
            }else if (!radioButtonX.isChecked() && !radioButtonO.isChecked()) {
                Toast.makeText(SingleplayerActivity.this,"Va rog sa selectati din optiunile de mai sus !",
                        Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(SingleplayerActivity.this, SingleplayerGameActivity.class);
                intent.putExtra("playerOne",getPlayerName);
                intent.putExtra("playerAI",getPlayerAIName);
                intent.putExtra("iconX",radioButtonX.isChecked());
                intent.putExtra("selected_difficulty",selectedDifficultyLevel);

                startActivity(intent);
            }
        });
    }
}