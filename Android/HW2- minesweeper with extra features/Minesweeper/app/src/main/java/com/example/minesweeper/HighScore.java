package com.example.minesweeper;

import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.gson.Gson;

import androidx.fragment.app.Fragment;

import java.util.LinkedList;

public class HighScore<mOrientationListener> extends Fragment {
    public TextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view =  lf.inflate(R.layout.fragment_table_high_score, container, false);
        String score;
        SharedPreferences prefs = getActivity().getSharedPreferences("MyHighScores", Context.MODE_PRIVATE);

        LinkedList<Integer> easy =new LinkedList<Integer>();
        LinkedList<Integer> medium =new LinkedList<Integer>();
        LinkedList<Integer> hard =new LinkedList<Integer>();

        Gson gson=new Gson();

        for(int j =0; j<10;j++)
        {
            easy.add(0);
            medium.add(0);
            hard.add(0);
        }





        tv = view.findViewById(R.id.easy_1);
        score = prefs.getString("easy1", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.easy_2);
        score = prefs.getString("easy2", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.easy_3);
        score = prefs.getString("easy3", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.easy_4);
        score = prefs.getString("easy4", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.easy_5);
        score = prefs.getString("easy5", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.easy_6);
        score = prefs.getString("easy6", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.easy_7);
        score = prefs.getString("easy7", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.easy_8);
        score = prefs.getString("easy8", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.easy_9);
        score = prefs.getString("easy9", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.easy_10);
        score = prefs.getString("easy10", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.medium_1);
        score = prefs.getString("medium1", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.medium_2);
        score = prefs.getString("medium2", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.medium_3);
        score = prefs.getString("medium3", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.medium_4);
        score = prefs.getString("medium4", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.medium_5);
        score = prefs.getString("medium5", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.medium_6);
        score = prefs.getString("medium6", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.medium_7);
        score = prefs.getString("medium7", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.medium_8);
        score = prefs.getString("medium8", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.medium_9);
        score = prefs.getString("medium9", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.medium_10);
        score = prefs.getString("medium10", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.hard_1);
        score = prefs.getString("hard1", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.hard_2);
        score = prefs.getString("hard2", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.hard_3);
        score = prefs.getString("hard3", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.hard_4);
        score = prefs.getString("hard4", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.hard_5);
        score = prefs.getString("hard5", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.hard_6);
        score = prefs.getString("hard6", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.hard_7);
        score = prefs.getString("hard7", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.hard_8);
        score = prefs.getString("hard8", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.hard_9);
        score = prefs.getString("hard9", "No Score");
        tv.setText(tv.getText() +" "+ score);
        tv = view.findViewById(R.id.hard_10);
        score = prefs.getString("hard10", "No Score");
        tv.setText(tv.getText() +" "+ score);
        return view;
    }



}
