package com.example.minesweeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {
    private Button buttonEasy;
    private Button buttonMedium;
    private Button buttonHard;
    private Button buttonStart;
    public static boolean mediumActive;
    public static boolean easyActive;
    public static boolean hardActive;
    private boolean isActive;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create shared preference file
        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        //control over game difficulty done by determining boolean value
        mediumActive = false;
        easyActive = false;
        hardActive = false;
        isActive=false;
        //code that was used for testing
        editor.remove(getString(R.string.ezScore));
        editor.remove(getString(R.string.medScore));
        editor.remove(getString(R.string.hardScore));
        editor.apply();

        /*TextView ezScoreText = findViewById(R.id.ezScore);
        TextView medScoreText = findViewById(R.id.medScore);
        TextView hardScoreText = findViewById(R.id.hardScore);*/
        //gets the high score for each difficulty from the shared preference
        /*ezScoreText.setText(String.valueOf(sharedPref.getInt(getString(R.string.ezScore),-1)));
        medScoreText.setText(String.valueOf(sharedPref.getInt(getString(R.string.medScore),-1)));
        hardScoreText.setText(String.valueOf(sharedPref.getInt(getString(R.string.hardScore),-1)));*/
        Button bHighScore = findViewById(R.id.highScore);
        buttonEasy = findViewById(R.id.buttonEasy);
        buttonMedium = findViewById(R.id.buttonMedium);
        buttonHard = findViewById(R.id.buttonHard);
        buttonStart = findViewById(R.id.buttonStart);
        //Controls the Game Difficulty
//
        //Writes Default Difficulty to memory
        Boolean ezDiff=sharedPref.getBoolean("easy", false );
        Boolean medDiff=sharedPref.getBoolean("medium", false );
        Boolean hardDiff=sharedPref.getBoolean("hard", false );
        if(ezDiff)
        {
            easyActive = true;
            mediumActive = false;
            hardActive = false;
            buttonEasy.setBackgroundColor(getResources().getColor(R.color.cool));
        }
        else if(medDiff)
        {
            easyActive = false;
            mediumActive = true;
            hardActive = false;
            buttonMedium.setBackgroundColor(getResources().getColor(R.color.cool));
        }
        else if(hardDiff)
        {
            easyActive = false;
            mediumActive = false;
            hardActive = true;
            buttonHard.setBackgroundColor(getResources().getColor(R.color.cool));
        }
        buttonEasy.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    easyActive = true;
                    hardActive = false;
                    mediumActive = false;
                    buttonMedium.setBackgroundColor(getResources().getColor(R.color.gray));
                    buttonHard.setBackgroundColor(getResources().getColor(R.color.gray));
                    buttonEasy.setBackgroundColor(getResources().getColor(R.color.cool));
                    editor.putBoolean("easy", true );
                    editor.putBoolean("medium", false );
                    editor.putBoolean("hard", false );
                    editor.apply();


                }
            });
        buttonMedium.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    mediumActive = true;
                    easyActive = false;
                    hardActive = false;
                    buttonMedium.setBackgroundColor(getResources().getColor(R.color.cool));
                    buttonHard.setBackgroundColor(getResources().getColor(R.color.gray));
                    buttonEasy.setBackgroundColor(getResources().getColor(R.color.gray));
                    editor.putBoolean("easy", false );
                    editor.putBoolean("medium", true );
                    editor.putBoolean("hard", false );
                    editor.apply();

            }
        });
        buttonHard.setOnClickListener(new View.OnClickListener(){
             public void onClick(View v){
                 hardActive = true;
                 mediumActive = false;
                 easyActive = false;
                 buttonMedium.setBackgroundColor(getResources().getColor(R.color.gray));
                 buttonHard.setBackgroundColor(getResources().getColor(R.color.cool));
                 buttonEasy.setBackgroundColor(getResources().getColor(R.color.gray));
                 editor.putBoolean("easy", false );
                 editor.putBoolean("medium", false );
                 editor.putBoolean("hard", true );
                 editor.apply();

            }
        });


        bHighScore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (findViewById(R.id.fragment_container) != null) {
                    if (savedInstanceState != null) {
                        return;
                    }
                    if (!isActive) {
                        isActive = true;
                        HighScore hsFragment = new HighScore();
                        hsFragment.setArguments(getIntent().getExtras());
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, hsFragment).commit();
                    }
                }
            }
        });

        buttonStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                openGame();
            }
        });




    }
    public void openGame(){
        Intent intent = new Intent(this, GameActivity.class
        );
        startActivity(intent);
        finish();
    }




}


