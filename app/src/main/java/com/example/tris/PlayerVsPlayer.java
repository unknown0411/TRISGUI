package com.example.tris;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerVsPlayer extends AppCompatActivity implements View.OnClickListener{

    Button[][] buttons = new Button[3][3];

    boolean turno = true;

    int turnCounter=0;
    int player1Point =0;
    int player2Point =0;

    TextView textViewPlayer = null;
    TextView textViewBot = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player_vs_player);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        textViewPlayer = findViewById(R.id.textView);
        textViewBot = findViewById(R.id.textView2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.resetButton);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
        Button buttonBack = findViewById(R.id.toHome);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlayerVsPlayer.this, MainActivity.class));
            }
        });
    }
    @Override
    public void onClick(View view) {
        if (!((Button)view).getText().toString().equals("")){
            return;
        }

        if (turno){
            ((Button)view).setText("X");
            ((Button)view).setEnabled(false);
        } else {
            ((Button)view).setText("O");
            ((Button)view).setEnabled(false);
        }

        turnCounter++;

        if (checkWin()){
            if (turno){
                player1Win();
            } else {
                player2Win();
            }
        } else if (turnCounter==9){
            draw();
        } else {
            turno = !turno;
        }
    }
    public boolean checkWin(){
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")){
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")){
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")){
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")){
                return true;
            }
        }
        return false;
    }

    public void player1Win(){
        player1Point++;
        Toast.makeText(this, "Player1 win!", Toast.LENGTH_SHORT).show();
        updatePointText();
        resetBoard();
    }

    public void player2Win(){
        player2Point++;
        Toast.makeText(this, "Player2 win!", Toast.LENGTH_SHORT).show();
        updatePointText();
        resetBoard();
    }

    public void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    public void updatePointText(){
        textViewPlayer.setText("Player1: " + player1Point);
        textViewBot.setText("Player2: " + player2Point);
    }

    public void resetBoard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        turnCounter=0;
        turno=true;
    }

    public void resetGame(){
        player1Point =0;
        player2Point =0;
        updatePointText();
        resetBoard();
    }
}