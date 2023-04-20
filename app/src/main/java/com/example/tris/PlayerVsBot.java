package com.example.tris;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;

import kotlinx.coroutines.Delay;

public class PlayerVsBot extends AppCompatActivity implements View.OnClickListener{

    Button[][] buttons = new Button[3][3];

    int winner;
    int turnCounter=0;
    boolean turno=true;
    int player1Point =0;
    int player2Point =0;
    int[][] base = new int[3][3];
    int[][] punti = new int[3][3];

    TextView textViewPlayer = null;
    TextView textViewBot = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player_vs_bot);

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
        Button buttonBack = findViewById(R.id.toHome2);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlayerVsBot.this, MainActivity.class));
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
            turno=false;
            turnCounter++;
            checkWin();
            if (winner==10){
                botWin();
            }else if (winner==1){
                playerWin();
            }else if (turnCounter==9){
                draw();
            } else {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        botAction();
                        turno=true;
                    }
                }, 520);
            }
        }


    }
    public void checkWin(){
        converti();
        for (int i = 0; i < 3; i++) {
            if (base[i][0]==base[i][1] && base[i][0]==base[i][2] && base[i][0]!=0){
                winner=base[i][0];
            }
        }
        for (int i = 0; i < 3; i++) {
            if (base[0][i]==base[1][i] && base[0][i]==base[2][i] && base[0][i]!=0){
                winner=base[0][i];
            }
        }
        for (int i = 0; i < 3; i++) {
            if (base[0][0]==base[1][1] && base[0][0]==base[2][2] && base[0][0]!=0){
                winner=base[0][0];
            }
        }
        for (int i = 0; i < 3; i++) {
            if (base[0][2]==base[1][1] && base[0][2]==base[2][0] && base[0][2]!=0){
                winner=base[0][2];
            }
        }

    }

    public void playerWin(){
        player1Point++;
        Toast.makeText(this, "Player win!", Toast.LENGTH_SHORT).show();
        updatePointText();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                resetBoard();
            }
        }, 820);
    }

    public void botWin(){
        player2Point++;
        Toast.makeText(this, "Bot win!", Toast.LENGTH_SHORT).show();
        updatePointText();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                resetBoard();
            }
        }, 820);
    }

    public void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                resetBoard();
            }
        }, 820);
    }

    public void updatePointText(){
        textViewPlayer.setText("Player: " + player1Point);
        textViewBot.setText("Bot: " + player2Point);
    }

    public void resetBoard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        turnCounter=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                base[i][j]=0;
            }
        }
        winner=0;
        turno=true;
    }

    public void resetGame(){
        player1Point =0;
        player2Point =0;
        updatePointText();
        resetBoard();
    }
    public void botAction(){
        converti();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (base[i][j]==0){
                    punti[i][j]=controlloCasella(i, j);
                } else {
                    punti[i][j]=0;
                }
            }
        }
        int posX=0, posY=0, max=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (max < punti[i][j]){
                    max = punti[i][j];
                    posX=i;
                    posY=j;
                }
            }
        }
        buttons[posX][posY].setText("O");
        buttons[posX][posY].setEnabled(false);
        turnCounter++;
        checkWin();
        if (winner==10){
            botWin();
        }else if (winner==1){
            playerWin();
        }else if (turnCounter==9){
            draw();
        }
    }

    public void converti(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().toString().equals("X")){
                    base[i][j]=1;
                } else if (buttons[i][j].getText().toString().equals("O")){
                    base[i][j]=10;
                } else {
                    base[i][j]=0;
                }
            }
        }
    }
    /*
    player = 1          bot = 10
    vuoto = 0
    x = 1
    o = 10
     */
    public int eVincente(int x1, int y1, int x2, int y2, int x3, int y3){
        int punti=0;

        /*
        xx- = 1000
        x-- = 50
        xo- = 1
        oo- = 100
        o-- = 10
        --- = 25
         */

        if (x1<0 || x2<0 || x3<0 || x1>=3 || x2>=3 || x3>=3 || y1<0 || y2<0 || y3<0 || y1>=3 || y2>=3 || y3>=3){
            return -1;
        }
        int n = base[x1][y1] + base[x2][y2] + base[x3][y3];
        if (n==20){
            punti+=1000;
        } else if (n==10){
            punti+=50;
        } else if (n==11){
            punti+=1;
        } else if (n==2){
            punti+=80;
        } else if (n==1){
            punti+=10;
        } else {
            punti+=25;
        }
        return punti;

    }

    public int controlloCasella(int x, int y){
        int punti= controllaRighe(x, y) + controllaColonne(x, y) + controlloDiagonaleMaggiore(x, y) + controlloDiagonaleMinore(x, y);
        return punti;
    }

    public int controllaRighe(int x, int y){
        int punti= eVincente(x, y, x, y+1, x, y+2) + eVincente(x, y-1, x, y, x, y+1) + eVincente(x, y-2, x, y-1, x, y);
        return punti;
    }
    public int controllaColonne(int x, int y){
        int punti = eVincente(x, y, x+1, y, x+2, y) + eVincente(x-1, y, x, y, x+1, y) + eVincente(x-2, y, x-1, y, x, y);
        return punti;
    }
    public int controlloDiagonaleMaggiore(int x, int y){
        int punti = eVincente(x, y, x+1, y+1, x+2, y+2) + eVincente(x-1, y-1, x, y, x+1, y+1) + eVincente(x-2, y-2, x-1, y-1, x, y);
        return punti;
    }
    public int controlloDiagonaleMinore(int x, int y){
        int punti = eVincente(x, y, x+1, y-1, x+2, y-2) + eVincente(x+1, y-1, x, y, x-1, y+1) + eVincente(x, y, x-1, y+1, x-2, y+2);
        return punti;
    }
}