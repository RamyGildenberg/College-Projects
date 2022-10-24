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
    private final String wonString = "You Won!";
    private TextView nameText;
    private String name;
    private String name2;
    private String name3;
    private int minutes;
    private int seconds;
    private int scoreCheck;
    private int scoreBefore;
    private int score2;
    private int score3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        newGame = findViewById(R.id.newGameButton);
        exit = findViewById(R.id.exitButton);

        long elapsedMillis = SystemClock.elapsedRealtime() - GameActivity.chronometer.getBase();
        long timeInSeconds = elapsedMillis / 1000;
        if (timeInSeconds < 60) {

            TextView textView = findViewById(R.id.time);
            textView.setText("Your time was: " + timeInSeconds + " Seconds");
            textView=findViewById((R.id.gameOver)) ;
            textView.setText(GameEngine.gameStatus);
        } else {
            minutes = (int) (timeInSeconds / 60);
            seconds = (int) timeInSeconds % 60;
            TextView textView = findViewById(R.id.time);
            textView.setText("Your time was: " + minutes + " Minutes and " + seconds + " Seconds");
            textView=findViewById((R.id.gameOver)) ;
            textView.setText(GameEngine.gameStatus);
        }


        if (GameEngine.gameWon) {
            GameEngine.gameWon = false;
            if (newHighScore()) {
                seconds = (int) timeInSeconds ;


                if (MainActivity.hardActive == true) {
                    MainActivity.hardActive = false;
                    setScore("hard1Int", "hard2Int", "hard3Int","hard4Int", "hard5Int", "hard6Int","hard7Int","hard8Int","hard9Int","hard10Int",
                            "hard1", "hard2", "hard3", "hard4", "hard5", "hard6", "hard7", "hard8", "hard9", "hard10");
                } else if (MainActivity.mediumActive == true) {
                    MainActivity.mediumActive = false;
                    setScore("medium1Int", "medium2Int", "medium3Int","medium4Int", "medium5Int", "medium6Int",
                            "medium7Int","medium8Int","medium9Int","medium10Int",
                            "medium1", "medium2", "medium3", "medium4", "medium5", "medium6", "medium7", "medium8", "medium9", "medium10");
                } else {
                    MainActivity.easyActive = false;
                    setScore("easy1Int", "easy2Int", "easy3Int","easy4Int", "easy5Int", "easy6Int",
                            "easy7Int","easy8Int","easy9Int","easy10Int",
                            "easy1", "easy2", "easy3", "easy4", "easy5", "easy6", "easy7", "easy8", "easy9", "easy10");
                }

            }
        }



        newGame.setOnClickListener(new View.OnClickListener(){


        public void onClick (View v){
        openNewGame();
    }
    });
        exit.setOnClickListener(new View.OnClickListener()

    {
        public void onClick (View v){
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

    public boolean newHighScore() {
        SharedPreferences prefs = getSharedPreferences("MyHighScores", MODE_PRIVATE);
        int e3, m3, h3;
        e3 = prefs.getInt("easy3Int", 0);
        m3 = prefs.getInt("medium3Int", 0);
        h3 = prefs.getInt("hard3Int", 0);
        if (e3 > seconds || e3 == 0) {
            return true;
        }
        if (m3 > seconds || m3 == 0) {
            return true;
        }
        if (h3 > seconds || h3 == 0) {
            return true;
        }
        return false;
    }

    public void setScore(String numKey1, String numKey2, String numKey3, String numKey4,String numKey5,String numKey6,String numKey7,String numKey8,String numKey9,String numKey10,
                         String key1, String key2, String key3,String key4,String key5,String key6,String key7,String key8,String key9,String key10) {
        SharedPreferences prefs = getSharedPreferences("MyHighScores", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
//        clear shared preferences
//       editor.clear();
//        editor.commit();
        scoreCheck = prefs.getInt(numKey1, 0);
        if (scoreCheck > seconds || scoreCheck == 0) {

            score2 = prefs.getInt(numKey1, 0);
            score3 = prefs.getInt(numKey2, 0);

            editor.putInt(numKey1, seconds);
            editor.putString(key1,  seconds + " Seconds");
            editor.commit();
            if (score2 != 0) {
                editor.putInt(numKey2, score2);
                editor.putString(key2,  score2 + " Seconds");
                editor.commit();
            }
            if (score3 != 0) {
                editor.putInt(numKey3, score3);
                editor.putString(key3, score3 + " Seconds");
                editor.commit();
            }
        } else {
            scoreCheck = prefs.getInt(numKey2, 0);
            scoreBefore = prefs.getInt(numKey1, 0);
            score3 = prefs.getInt(numKey2, 0);
            if (scoreCheck > seconds || scoreCheck == 0) {
                if (seconds > scoreBefore) {

                    editor.putInt(numKey2, seconds);
                    editor.putString(key2,  seconds + " Seconds");
                    editor.commit();
                    if (score3 != 0) {
                        editor.putInt(numKey3, score3);
                        editor.putString(key3,  score3 + " Seconds");
                        editor.commit();
                    }

                }
            } else {
                scoreCheck = prefs.getInt(numKey3, 0);
                scoreBefore = prefs.getInt(numKey2, 0);
                if (scoreCheck > seconds || scoreCheck == 0) {
                    if (seconds > scoreBefore) {

                        editor.putInt(numKey3, seconds);
                        editor.putString(key3,  seconds + " Seconds");
                        editor.commit();
                    }
                }
            }
        }
    }

}

