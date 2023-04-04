package com.example.tictactoeapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.tictactoeapplication.databinding.ActivityGameBinding;
import java.util.ArrayList;
import java.util.List;


public class GameActivity extends AppCompatActivity {

    ActivityGameBinding binding;
    private final List<int[]> combinationList = new ArrayList<>();
    private int[] boxPositions = {0,0,0,0,0,0,0,0,0};
    private int playerTurn = 1;
    private int totalSelectedBox = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.playerOneLayout.setBackgroundResource(R.drawable.black_border);

        combinationList.add(new int[]{0,1,2});
        combinationList.add(new int[]{3,4,5});
        combinationList.add(new int[]{6,7,8});
        combinationList.add(new int[]{0,3,6});
        combinationList.add(new int[]{1,4,7});
        combinationList.add(new int[]{2,5,8});
        combinationList.add(new int[]{2,4,6});
        combinationList.add(new int[]{0,4,8});

        String getPlayerOneName = getIntent().getStringExtra("playerOne");
        String getPlayerTwoName = getIntent().getStringExtra("playerTwo");

        binding.playerOneName.setText(getPlayerOneName);
        binding.playerTwoName.setText(getPlayerTwoName);

        binding.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(0)){
                    performAction((ImageView) view,0);
                }
            }
        });
        binding.image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(1)){
                    performAction((ImageView) view,1);
                }
            }
        });
        binding.image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(2)){
                    performAction((ImageView) view,2);
                }
            }
        });
        binding.image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(3)){
                    performAction((ImageView) view,3);
                }
            }
        });
        binding.image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(4)){
                    performAction((ImageView) view,4);
                }
            }
        });
        binding.image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(5)){
                    performAction((ImageView) view,5);
                }
            }
        });
        binding.image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(6)){
                    performAction((ImageView) view,6);
                }
            }
        });
        binding.image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(7)){
                    performAction((ImageView) view,7);
                }
            }
        });
        binding.image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(8)){
                    performAction((ImageView) view,8);
                }
            }
        });
        binding.resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartMatch();
            }
        });
    }
    private void performAction(ImageView imageView, int selectedBoxPosition){
        boxPositions[selectedBoxPosition] = playerTurn;

        if (playerTurn == 1){
            imageView.setImageResource(R.drawable.crossed1);

            if (checkResults()){
                ResultActivity resultActivity = new ResultActivity(GameActivity.this,binding.playerOneName.getText().toString()
                        + " este castigatorul !",GameActivity.this);
                resultActivity.setCancelable(false);
                resultActivity.show();
            }
            else if (totalSelectedBox == 9){
                ResultActivity resultActivity = new ResultActivity(GameActivity.this,"Egalitate",GameActivity.this);
                resultActivity.setCancelable(false);
                resultActivity.show();
            } else{
                changePlayerTurn(2);
                totalSelectedBox++;
            }
        }else{
            imageView.setImageResource(R.drawable.zero1);

            if (checkResults()){
                ResultActivity resultActivity = new ResultActivity(GameActivity.this,binding.playerTwoName.getText().toString()
                        + " este castigatorul !",GameActivity.this);
                resultActivity.setCancelable(false);
                resultActivity.show();
            }
            else if (totalSelectedBox == 9){
                ResultActivity resultActivity = new ResultActivity(GameActivity.this,"Egalitate",GameActivity.this);
                resultActivity.setCancelable(false);
                resultActivity.show();
            } else{
                changePlayerTurn(1);
                totalSelectedBox++;
            }
        }
    }
    private void changePlayerTurn(int curentPlayerTurn){
        playerTurn = curentPlayerTurn;
        if (playerTurn == 1){
            binding.playerOneLayout.setBackgroundResource(R.drawable.black_border);
            binding.playerTwoLayout.setBackgroundResource(R.drawable.white_border);
        }else{
            binding.playerOneLayout.setBackgroundResource(R.drawable.white_border);
            binding.playerTwoLayout.setBackgroundResource(R.drawable.black_border);
        }
    }
    private boolean checkResults(){
        boolean response = false;
        for(int i =0; i < combinationList.size();i++){
            final int[] combination = combinationList.get(i);

            if (boxPositions[combination[0]] == playerTurn && boxPositions[combination[1]] == playerTurn &&
                    boxPositions[combination[2]] == playerTurn){
                response = true;
            }
        }
        return  response;
    }

    private boolean isBoxSelectable(int boxPosition){
        boolean response = false;
        if (boxPositions[boxPosition] == 0){
            response = true;
        }
        return response;
    }

    public void restartMatch(){
        boxPositions = new int[]{0,0,0,0,0,0,0,0,0};
        playerTurn = 1;
        totalSelectedBox = 1;

        binding.image1.setImageResource(R.drawable.white_box);
        binding.image2.setImageResource(R.drawable.white_box);
        binding.image3.setImageResource(R.drawable.white_box);
        binding.image4.setImageResource(R.drawable.white_box);
        binding.image5.setImageResource(R.drawable.white_box);
        binding.image6.setImageResource(R.drawable.white_box);
        binding.image7.setImageResource(R.drawable.white_box);
        binding.image8.setImageResource(R.drawable.white_box);
        binding.image9.setImageResource(R.drawable.white_box);

        binding.playerOneLayout.setBackgroundResource(R.drawable.black_border);
    }
}