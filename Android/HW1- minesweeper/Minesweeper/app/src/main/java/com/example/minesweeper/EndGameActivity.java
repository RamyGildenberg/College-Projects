package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.minesweeper.view.grid.Cell;

public class EndGameActivity extends AppCompatActivity {
    private Button newGame;
    private Button exit;
    private int savedTime;
    private final String wonString="You Won!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        newGame = findViewById(R.id.newGameButton);
        exit = findViewById(R.id.exitButton);


        long elapsedMillis = SystemClock.elapsedRealtime() - GameActivity.chronometer.getBase();
        long timeInSeconds = elapsedMillis / 1000;
        if (timeInSeconds < 60)
        {
            TextView textView = findViewById(R.id.time);
            timeInSeconds=(int)(timeInSeconds);

            textView.setText(this.getString(R.string.yourTimeWas) + (int)(timeInSeconds) + this.getString(R.string.seconds));


            textView=findViewById((R.id.gameOver)) ;
            textView.setText(GameEngine.gameStatus);


            if(GameEngine.gameStatus.equals(wonString))
            {
                    savedTime =sharedPref.getInt(this.getString(R.string.ezScore),-5);
                    if(MainActivity.easyActive && timeInSeconds>0 && timeInSeconds<(savedTime)|| savedTime==-5)
                    {
                        editor.putInt(this.getString(R.string.ezScore),(int)(timeInSeconds));
                        editor.commit();
                    }
                    savedTime =sharedPref.getInt(this.getString(R.string.medScore),-5);
                    if(MainActivity.mediumActive && timeInSeconds>0 && timeInSeconds<(savedTime)|| savedTime==-5)
                    {
                        editor.putInt(this.getString(R.string.medScore),(int)(timeInSeconds));
                        editor.commit();
                    }
                    savedTime =sharedPref.getInt(this.getString(R.string.hardScore),-5);
                    if(MainActivity.hardActive && timeInSeconds>0 && timeInSeconds<(savedTime)|| savedTime==-5)
                    {
                        editor.putInt(this.getString(R.string.hardScore),(int)(timeInSeconds));
                        editor.commit();
                    }



            }
        }


        else {
            int minutes = (int) (timeInSeconds / 60);
            int seconds = (int) timeInSeconds % 60;
            TextView textView = findViewById(R.id.time);
            textView.setText(this.getString(R.string.yourTimeWas) + minutes + this.getString(R.string.minutesAnd) + seconds + this.getString(R.string.seconds));

            textView=findViewById((R.id.gameOver)) ;
            textView.setText(GameEngine.gameStatus);

            if(GameEngine.gameStatus.equals(wonString))
            {
                savedTime =sharedPref.getInt("Easy Score",-5);
                if((MainActivity.easyActive && timeInSeconds>0 && timeInSeconds<(savedTime)) || savedTime==-5)
                {
                    editor.putInt(this.getString(R.string.ezScore),(int)(timeInSeconds));
                    editor.commit();
                }
                savedTime =sharedPref.getInt("Medium Score",-5);
                if(MainActivity.mediumActive && timeInSeconds>0 && timeInSeconds<(savedTime)|| savedTime==-5)
                {
                    editor.putInt(this.getString(R.string.medScore),(int)(timeInSeconds));
                    editor.commit();
                }
                savedTime =sharedPref.getInt("Hard Score",-5);
                if(MainActivity.hardActive && timeInSeconds>0 && timeInSeconds<(savedTime)|| savedTime==-5)
                {
                    editor.putInt(this.getString(R.string.hardScore),(int)(timeInSeconds));
                    editor.commit();
                }



            }
        }
        newGame.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openNewGame();
            }
        });
        exit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                exitGame();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);        // Specify any activity here e.g. home or splash or login etc
        startActivity(i);
        finish();

    }
    public void openNewGame(){
        Intent i = new Intent(this, MainActivity.class);        // Specify any activity here e.g. home or splash or login etc
        startActivity(i);
        finish();
    }
    public void exitGame(){
        ActivityCompat.finishAffinity(EndGameActivity.this);
    }

}

