package com.sheilambadi.android.tictactoe.singleplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sheilambadi.android.tictactoe.R;

import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ThreeByThreeActivity extends AppCompatActivity implements View.OnClickListener{
    Intent i;
    String playerComp;
    String playerUser;

    private static final Random RANDOMX = new Random();
    private static final Random RANDOMY = new Random();

    private Button[][] buttons = new Button[3][3];
    private TextView tvPlayer1Score;
    private  TextView tvPlayer2Score;

    private boolean player1Turn = true;
    //private boolean player2Turn = false;
    private int roundCount;
    private int player1Points;
    private int player2Points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //calligraphy library
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        setContentView(R.layout.activity_three_by_three);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_32dp);
        i = getIntent();

        playerComp = i.getStringExtra("valueComp");
        playerUser = i.getStringExtra("valueUser");
        //Toast.makeText(getApplicationContext(), "Comp: " + playerComp +" User " + playerUser, Toast.LENGTH_SHORT).show();

        //score textviews
        tvPlayer1Score = findViewById(R.id.tvPlayer1Score);
        tvPlayer2Score = findViewById(R.id.tvPlayer2Score);

        //buttons
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                String buttonID = "b" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    //called when any of the buttons is clicked
    @Override
    public void onClick(View view) {
        //button has input
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }

        if (roundCount < 9) {
            ((Button) view).setText(playerUser);
            if(checkForWin()){
                player1Wins();
            }

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int positionx = -1;
                    int positionY = -1;

                    do {
                        positionx = RANDOMX.nextInt(3);
                        positionY = RANDOMY.nextInt(3);

                    } while (!((buttons[positionx][positionY]).getText().toString().equals("")));
                    buttons[positionx][positionY].setText(playerComp);
                    roundCount++;
                    if(checkForWin()){
                        player2Wins();
                    }
                    //finish();
                }
            }, 1000);
            roundCount++;
            Log.i("User", ""+roundCount);
        }
        if(roundCount == 9 || roundCount == 8){
            draw();
        }

        //roundCount++;
        /*if(roundCount < 9) {
            ((Button) view).setText(playerUser);
            roundCount++;
            Log.i("User", ""+roundCount);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int positionx = -1;
                    int positionY = -1;

                    do {
                        positionx = RANDOMX.nextInt(3);
                        positionY = RANDOMX.nextInt(3);
                    } while (!((buttons[positionx][positionY]).getText().toString().equals("")));
                    buttons[positionx][positionY].setText(playerComp);
                    roundCount++;
                    //finish();
                }
            }, 1000);
        } else if (roundCount == 9){
            draw();
        }
        Log.i("Comp", ""+roundCount);*/
       /* //if empty
        if (player1Turn) {
            ((Button) view).setText(playerUser);
            player1Turn = false;
            player2Turn = true;
        } else if (player2Turn){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int positionx = -1;
                    int positionY = -1;

                    do {
                        positionx = RANDOMX.nextInt(3);
                        positionY = RANDOMX.nextInt(3);
                    } while (!((buttons[positionx][positionY]).getText().toString().equals("")));
                    buttons[positionx][positionY].setText(playerComp);
                    //finish();
                }
            }, 1000);
            player2Turn = false;
            player1Turn = true;
        }
        *//*else {
            ((Button) view).setText(playerComp);
        }*//*
        roundCount++;*/

       /* if(checkForWin()) {
            if (player1Turn){
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } */
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins(){
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins(){
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        tvPlayer1Score.setText("" + player1Points);
        tvPlayer2Score.setText("" + player2Points);
    }

    private void resetBoard(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
