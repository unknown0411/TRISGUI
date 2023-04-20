package com.example.tris;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Button button1 = findViewById(R.id.buttonpvsp);
        Button button2 = findViewById(R.id.button2pvsb);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonpvsp:
                Toast.makeText(this, "Player VS Player", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, PlayerVsPlayer.class));
                break;

            case R.id.button2pvsb:
                Toast.makeText(this, "Player VS Bot", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, PlayerVsBot.class));
                break;
        }
    }
}
