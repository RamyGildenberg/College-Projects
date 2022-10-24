package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.OrientationEventListener;
import android.widget.Chronometer;

public class GameActivity extends AppCompatActivity {
    public static Chronometer chronometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        chronometer = findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        GameEngine.getInstance().createGrid(this);



    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();;

    }







}
