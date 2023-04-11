package com.example.tictactoeapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.tictactoeapplication.databinding.ActivitySingleplayerGameBinding;

import java.util.Random;

public class SingleplayerGameActivity extends AppCompatActivity {
    ActivitySingleplayerGameBinding binding;
    private ImageView[][] gameBoard = new ImageView[3][3];
    private boolean playerTurn = true;
    private boolean gameActive = true;
    private Boolean getIconX;
    public int roundCount = 0;
    private String getplayerOne;
    private String getplayerAI;
    private String getDificulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Singleplayer");
        }

        binding = ActivitySingleplayerGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.playerOneLayoutS.setBackgroundResource(R.drawable.black_border);

        getplayerOne = getIntent().getStringExtra("playerOne");
        getplayerAI = getIntent().getStringExtra("playerAI");
        getIconX = getIntent().getBooleanExtra("iconX", true);
        getDificulty = getIntent().getStringExtra("selected_difficulty");

        binding.playerOneName.setText(getplayerOne);
        binding.playerTwoName.setText(getplayerAI);

        gameBoard[0][0] = binding.image1;
        gameBoard[0][1] = binding.image2;
        gameBoard[0][2] = binding.image3;
        gameBoard[1][0] = binding.image4;
        gameBoard[1][1] = binding.image5;
        gameBoard[1][2] = binding.image6;
        gameBoard[2][0] = binding.image7;
        gameBoard[2][1] = binding.image8;
        gameBoard[2][2] = binding.image9;

        if (getIconX) {
            binding.iconX.setImageResource(R.drawable.crossed1);
            binding.iconO.setImageResource(R.drawable.zero1);
        } else {
            binding.iconX.setImageResource(R.drawable.zero1);
            binding.iconO.setImageResource(R.drawable.crossed1);
            makeAIMove();
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameBoard[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changePlayerTurn(playerTurn);

                        if (((ImageView) view).getDrawable() == null && gameActive) {
                            if (getIconX){
                                ((ImageView) view).setImageResource(R.drawable.crossed1);
                                roundCount++;
                                if (checkWin(R.drawable.crossed1)) {
                                    playerWin();
                                }
                                else if (roundCount == 9) {
                                    playersDraw();
                                }
                                else{
                                    changePlayerTurn(playerTurn);
                                    playerTurn  = !playerTurn;
                                    if (gameActive) {
                                        makeAIMove();
                                        changePlayerTurn(playerTurn);
                                        playerTurn  = !playerTurn;
                                    }
                                }
                            }
                            else{
                                makeAIMove();
                                changePlayerTurn(playerTurn);
                                playerTurn = !playerTurn;
                                if (gameActive) {
                                    ((ImageView) view).setImageResource(R.drawable.zero1);
                                    roundCount++;
                                    if (checkWin(R.drawable.zero1)) {
                                        playerWin();
                                    }
                                    changePlayerTurn(playerTurn);
                                    playerTurn = !playerTurn;
                                }
                            }
                        }
                    }
                });
            }
        }

        Button resetButton = findViewById(R.id.resetButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartMatch();
            }
        });
    }

    private void playerWin(){
        gameActive = false;
        SingleplayerResultActivity resultActivity = new SingleplayerResultActivity(SingleplayerGameActivity.this,
                getplayerOne.toString() + " este castigatorul !", SingleplayerGameActivity.this);
        resultActivity.setCancelable(false);
        resultActivity.show();
    }
    private void playerAIWin(){
        gameActive = false;
        SingleplayerResultActivity resultActivity = new SingleplayerResultActivity(SingleplayerGameActivity.this,
                getplayerAI.toString() + " este castigatorul !", SingleplayerGameActivity.this);
        resultActivity.setCancelable(false);
        resultActivity.show();
    }

    private void playersDraw(){
        gameActive = false;
        SingleplayerResultActivity resultActivity = new SingleplayerResultActivity(SingleplayerGameActivity.this,
                "Egalitate", SingleplayerGameActivity.this);
        resultActivity.setCancelable(false);
        resultActivity.show();
    }
    private boolean checkWin(int drawableResId) {
        for (int i = 0; i < gameBoard.length; i++) {
            if (areDrawablesEqual(gameBoard[i][0].getDrawable(), gameBoard[i][1].getDrawable(), gameBoard[i][2].getDrawable(), drawableResId)) {
                return true;
            }
        }

        for (int i = 0; i < gameBoard.length; i++) {
            if (areDrawablesEqual(gameBoard[0][i].getDrawable(), gameBoard[1][i].getDrawable(), gameBoard[2][i].getDrawable(), drawableResId)) {
                return true;
            }
        }

        if (areDrawablesEqual(gameBoard[0][0].getDrawable(), gameBoard[1][1].getDrawable(), gameBoard[2][2].getDrawable(), drawableResId)) {
            return true;
        }
        if (areDrawablesEqual(gameBoard[0][2].getDrawable(), gameBoard[1][1].getDrawable(), gameBoard[2][0].getDrawable(), drawableResId)) {
            return true;
        }
        return false;
    }
    private boolean areDrawablesEqual(Drawable drawable1, Drawable drawable2, Drawable drawable3, int drawableResId) {
        return drawable1 != null &&
                drawable2 != null &&
                drawable3 != null &&
                drawable1.getConstantState() == getResources().getDrawable(drawableResId, getTheme()).getConstantState() &&
                drawable2.getConstantState() == getResources().getDrawable(drawableResId, getTheme()).getConstantState() &&
                drawable3.getConstantState() == getResources().getDrawable(drawableResId, getTheme()).getConstantState();
    }

    private void makeAIMove() {
        CountDownTimer timer = new CountDownTimer(300, 300){
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                switch (getDificulty){
                    case "Easy":
                        makeAIMoveEasy();
                        if (roundCount == 9) {
                            playersDraw();
                        }
                        break;
                    case "Medium":
                        makeAIMoveMedium();
                        if (roundCount == 9) {
                            playersDraw();
                        }
                        break;
                    case "Hard":
                        makeAIMoveHard();
                        if (roundCount == 9) {
                            playersDraw();
                        }
                        break;
                }
            }
        };
        timer.start();
    }

    private void makeAIMoveEasy() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (gameBoard[row][col].getDrawable() != null);

        gameBoard[row][col].setImageResource(R.drawable.zero1);
        if (checkWin(getIconX ? R.drawable.zero1 : R.drawable.crossed1)) {
            playerAIWin();
        }
        changePlayerTurn(playerTurn);
        roundCount++;
    }



    private void makeAIMoveMedium() {
        if (checkTwoInARow(getIconX ? R.drawable.zero1 : R.drawable.crossed1)) {
            if (checkWin(getIconX ? R.drawable.zero1 : R.drawable.crossed1)) {
                playerAIWin();
            }
            changePlayerTurn(playerTurn);
            return;
        }

        if (gameBoard[1][1].getDrawable() == null) {
            gameBoard[1][1].setImageResource(getIconX ? R.drawable.zero1 : R.drawable.crossed1);
            if (checkWin(getIconX ? R.drawable.zero1 : R.drawable.crossed1)) {
                playerAIWin();
            }
            changePlayerTurn(playerTurn);
            roundCount++;
            return;
        }

        if (checkFork(getIconX ? R.drawable.zero1 : R.drawable.crossed1)) {
            if (checkWin(getIconX ? R.drawable.zero1 : R.drawable.crossed1)) {
                playerAIWin();
            }
            changePlayerTurn(playerTurn);
            return;
        }

        if (checkFork(!getIconX ? R.drawable.zero1 : R.drawable.crossed1)) {
            if (checkWin(getIconX ? R.drawable.zero1 : R.drawable.crossed1)) {
                playerAIWin();
            }
            changePlayerTurn(playerTurn);
            return;
        }

        Random random = new Random();
        int i, j;
        do {
            i = random.nextInt(3);
            j = random.nextInt(3);
        } while (gameBoard[i][j].getDrawable() != null);

        gameBoard[i][j].setImageResource(getIconX ? R.drawable.zero1 : R.drawable.crossed1);
        if (checkWin(getIconX ? R.drawable.zero1 : R.drawable.crossed1)) {
            playerAIWin();
        }
        changePlayerTurn(playerTurn);
        roundCount++;
    }
    private boolean checkTwoInARow(int imageResourceId) {
        for (int i = 0; i < 3; i++) {
            int count = 0;
            int emptyCol = -1;
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j].getDrawable() == null) {
                    emptyCol = j;
                } else if (((BitmapDrawable) gameBoard[i][j].getDrawable()).getBitmap()
                        .sameAs(BitmapFactory.decodeResource(getResources(), imageResourceId))) {
                    count++;
                }
            }
            if (count == 2 && emptyCol != -1) {
                gameBoard[i][emptyCol].setImageResource(getIconX ? R.drawable.zero1 : R.drawable.crossed1);
                roundCount++;
                return true;
            }
        }

        for (int j = 0; j < 3; j++) {
            int count = 0;
            int emptyRow = -1;
            for (int i = 0; i < 3; i++) {
                if (gameBoard[i][j].getDrawable() == null) {
                    emptyRow = i;
                } else if (((BitmapDrawable) gameBoard[i][j].getDrawable()).getBitmap()
                        .sameAs(BitmapFactory.decodeResource(getResources(), imageResourceId))) {
                    count++;
                }
            }
            if (count == 2 && emptyRow != -1) {
                gameBoard[emptyRow][j].setImageResource(getIconX ? R.drawable.zero1 : R.drawable.crossed1);
                roundCount++;
                return true;
            }
        }

        int count = 0;
        int emptyRow = -1;
        int emptyCol = -1;
        for (int i = 0; i < 3; i++) {
            if (gameBoard[i][i].getDrawable() == null) {
                emptyRow = i;
                emptyCol = i;
            } else if (((BitmapDrawable) gameBoard[i][i].getDrawable()).getBitmap()
                    .sameAs(BitmapFactory.decodeResource(getResources(), imageResourceId))) {
                count++;
            }
        }
        if (count == 2 && emptyRow != -1) {
            gameBoard[emptyRow][emptyCol].setImageResource(getIconX ? R.drawable.zero1 : R.drawable.crossed1);
            roundCount++;
            return true;
        }

        count = 0;
        emptyRow = -1;
        emptyCol = -1;
        for (int i = 0; i < 3; i++) {
            if (gameBoard[i][2 - i].getDrawable() == null) {
                emptyRow = i;
                emptyCol = 2 - i;
            } else if (((BitmapDrawable) gameBoard[i][2 - i].getDrawable()).getBitmap()
                    .sameAs(BitmapFactory.decodeResource(getResources(), imageResourceId))) {
                count++;
            }
        }
        if (count == 2 && emptyRow != -1) {
            gameBoard[emptyRow][emptyCol].setImageResource(getIconX ? R.drawable.zero1 : R.drawable.crossed1);
            roundCount++;
            return true;
        }
        return false;
    }

    private boolean checkFork(int drawableId) {
        for (int i = 0; i < 3; i++) {
            int emptyCount = 0;
            int occupiedCount = 0;
            int emptyI = -1;
            int emptyJ = -1;
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j].getDrawable() == null) {
                    emptyCount++;
                    emptyI = i;
                    emptyJ = j;
                } else if (gameBoard[i][j].getDrawable().getConstantState().equals(getResources().getDrawable(drawableId).getConstantState())) {
                    occupiedCount++;
                }
            }

            if (emptyCount == 1 && occupiedCount == 2) {
                gameBoard[emptyI][emptyJ].setImageResource(getIconX ? R.drawable.zero1 : R.drawable.crossed1);
                roundCount++;
                return true;
            }
        }
        for (int j = 0; j < 3; j++) {
            int emptyCount = 0;
            int occupiedCount = 0;
            int emptyI = -1;
            int emptyJ = -1;

            for (int i = 0; i < 3; i++) {
                if (gameBoard[i][j].getDrawable() == null) {
                    emptyCount++;
                    emptyI = i;
                    emptyJ = j;
                } else if (gameBoard[i][j].getDrawable().getConstantState().equals(getResources().getDrawable(drawableId).getConstantState())) {
                    occupiedCount++;
                }
            }

            if (emptyCount == 1 && occupiedCount == 2) {
                gameBoard[emptyI][emptyJ].setImageResource(getIconX ? R.drawable.zero1 : R.drawable.crossed1);
                roundCount++;
                return true;
            }
        }
        int emptyCount = 0;
        int occupiedCount = 0;
        int emptyI = -1;
        int emptyJ = -1;

        for (int i = 0; i < 3; i++) {
            if (gameBoard[i][i].getDrawable() == null) {
                emptyCount++;
                emptyI = i;
                emptyJ = i;
            } else if (gameBoard[i][i].getDrawable().getConstantState().equals(getResources().getDrawable(drawableId).getConstantState())) {
                occupiedCount++;
            }
        }

        if (emptyCount == 1 && occupiedCount == 2) {
            gameBoard[emptyI][emptyJ].setImageResource(getIconX ? R.drawable.zero1 : R.drawable.crossed1);
            roundCount++;
            return true;
        }
        emptyCount = 0;
        occupiedCount = 0;
        emptyI = -1;
        emptyJ = -1;

        for (int i = 0, j = 2; i < 3 && j >= 0; i++, j--) {
            if (gameBoard[i][j].getDrawable() == null) {
                emptyCount++;
                emptyI = i;
                emptyJ = j;
            } else if (gameBoard[i][j].getDrawable().getConstantState().equals(getResources().getDrawable(drawableId).getConstantState())) {
                occupiedCount++;
            }
        }

        if (emptyCount == 1 && occupiedCount == 2) {
            gameBoard[emptyI][emptyJ].setImageResource(getIconX ? R.drawable.zero1 : R.drawable.crossed1);
            roundCount++;
            return true;
        }
        return false;
    }

    private void makeAIMoveHard() {
        int[] move = getBestMove();
        gameBoard[move[0]][move[1]].setImageResource(getIconX ? R.drawable.zero1 : R.drawable.crossed1);

        changePlayerTurn(playerTurn);
        roundCount++;

        if (checkWin(getIconX ? R.drawable.zero1 : R.drawable.crossed1)) {
            playerAIWin();
        }
    }

    private int[] getBestMove() {
        int[] result = {-1, -1};
        int bestScore = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (((ImageView) gameBoard[i][j]).getDrawable() == null) {
                    gameBoard[i][j].setImageResource(getIconX ? R.drawable.zero1 : R.drawable.crossed1);

                    changePlayerTurn(playerTurn);
                    roundCount++;

                    int score = minimax(false, 0, alpha, beta, new int[3][3], getIconX ? R.drawable.zero1 : R.drawable.crossed1, 5);
                    gameBoard[i][j].setImageResource(0);

                    if (score > bestScore) {
                        bestScore = score;
                        result[0] = i;
                        result[1] = j;
                    }
                }
            }
        }

        return result;
    }

    private int minimax(boolean isMaximizingPlayer, int depth, int alpha, int beta, int[][] memo, int currentPlayer, int maxDepth) {
        if (checkWin(getIconX ? R.drawable.zero1 : R.drawable.crossed1)) {
            return -10 + depth;
        } else if (checkWin(!getIconX ? R.drawable.zero1 : R.drawable.crossed1)) {
            return 10 - depth;
        } else if (roundCount == 9) {
            return 0;
        } else if (depth >= maxDepth) {
            return evaluateBoard(memo, currentPlayer);
        }


        if (isMaximizingPlayer) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (memo[i][j] == 0) {
                        memo[i][j] = currentPlayer;
                        int score = minimax(false, depth + 1, alpha, beta, memo, currentPlayer = getIconX ? R.drawable.zero1 : R.drawable.crossed1, maxDepth);
                        memo[i][j] = 0;
                        bestScore = Math.max(bestScore, score);
                        alpha = Math.max(alpha, bestScore);
                        if (alpha >= beta) {
                            return bestScore;
                        }
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (memo[i][j] == 0) {
                        memo[i][j] = currentPlayer;
                        int score = minimax(true, depth + 1, alpha, beta, memo, currentPlayer = getIconX ? R.drawable.zero1 : R.drawable.crossed1, maxDepth);
                        memo[i][j] = 0;
                        bestScore = Math.min(bestScore, score);
                        beta = Math.min(beta, bestScore);
                        if (alpha >= beta) {
                            return bestScore;
                        }
                    }
                }
            }
            return bestScore;
        }
    }

    private int evaluateBoard(int[][] board, int currentPlayer) {
        int score = 0;
        int playerPiece = currentPlayer == R.drawable.crossed1 ? R.drawable.zero1 : R.drawable.crossed1;
        int opponentPiece = playerPiece;

        for (int i = 0; i < 3; i++) {
            int rowSum = board[i][0] + board[i][1] + board[i][2];
            int colSum = board[0][i] + board[1][i] + board[2][i];
            if (rowSum == playerPiece * 3 || colSum == playerPiece * 3) {
                score += 100;
            } else if (rowSum == opponentPiece * 3 || colSum == opponentPiece * 3) {
                score -= 100;
            }
        }

        int diagSum1 = board[0][0] + board[1][1] + board[2][2];
        int diagSum2 = board[0][2] + board[1][1] + board[2][0];
        if (diagSum1 == playerPiece * 3 || diagSum2 == playerPiece * 3) {
            score += 100;
        } else if (diagSum1 == opponentPiece * 3 || diagSum2 == opponentPiece * 3) {
            score -= 100;
        }

        int openRows = 0;
        int openCols = 0;
        int openDiags = 0;
        for (int i = 0; i < 3; i++) {
            int rowSum = board[i][0] + board[i][1] + board[i][2];
            int colSum = board[0][i] + board[1][i] + board[2][i];
            if (rowSum == playerPiece * 2 || colSum == playerPiece * 2) {
                openRows++;
                openCols++;
            } else if (rowSum == opponentPiece * 2 || colSum == opponentPiece * 2) {
                openRows--;
                openCols--;
            }
        }
        if (board[1][1] == playerPiece) {
            if (board[0][0] == playerPiece && board[2][2] == 0 || board[0][2] == playerPiece && board[2][0] == 0
                    || board[2][0] == playerPiece && board[0][2] == 0 || board[2][2] == playerPiece && board[0][0] == 0) {
                openDiags++;
            } else if (board[0][0] == opponentPiece && board[2][2] == 0 || board[0][2] == opponentPiece && board[2][0] == 0
                    || board[2][0] == opponentPiece && board[0][2] == 0 || board[2][2] == opponentPiece && board[0][0] == 0) {
                openDiags--;
            }
        } else if (board[1][1] == opponentPiece) {
            if (board[0][0] == playerPiece && board[2][2] == 0 || board[0][2] == playerPiece && board[2][0] == 0
                    || board[2][0] == playerPiece && board[0][2] == 0 ||board[2][2] == playerPiece && board[0][0] == 0) {
                openDiags--;
            } else if (board[0][0] == opponentPiece && board[2][2] == 0 || board[0][2] == opponentPiece && board[2][0] == 0
                    || board[2][0] == opponentPiece && board[0][2] == 0 || board[2][2] == opponentPiece && board[0][0] == 0) {
                openDiags++;
            }
        }
        score += (openRows + openCols + openDiags) * 10;

        return score;

    }

    private void changePlayerTurn(boolean playerOneTurn) {
        if (playerOneTurn) {
            binding.playerOneLayoutS.setBackgroundResource(R.drawable.black_border);
            binding.playerTwoLayoutS.setBackgroundResource(R.drawable.white_border);
        } else {
            binding.playerOneLayoutS.setBackgroundResource(R.drawable.white_border);
            binding.playerTwoLayoutS.setBackgroundResource(R.drawable.black_border);
        }
    }

    public void restartMatch() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j].setImageDrawable(null);
            }
        }

        gameActive = true;
        roundCount = 0;

        if (getIconX){
            playerTurn = true;
            binding.playerOneLayoutS.setBackgroundResource(R.drawable.black_border);
            binding.playerTwoLayoutS.setBackgroundResource(R.drawable.white_border);
        }else{
            playerTurn = false;
            binding.playerOneLayoutS.setBackgroundResource(R.drawable.white_border);
            binding.playerTwoLayoutS.setBackgroundResource(R.drawable.black_border);
            makeAIMove();
        }
    }
}