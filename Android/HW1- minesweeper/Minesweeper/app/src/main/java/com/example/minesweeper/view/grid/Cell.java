package com.example.minesweeper.view.grid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.minesweeper.GameEngine;
import com.example.minesweeper.R;

public class Cell extends BaseCell implements View.OnClickListener , View.OnLongClickListener{
    public static int numOfClicks=0;
    public Cell(Context context , int x , int y ){
        super(context);

        setPosition(x,y);

        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    public void onClick(View v) {
        numOfClicks++;
        GameEngine.getInstance().click( getXPos(), getYPos() );

    }

    @Override
    public boolean onLongClick(View v) {
        GameEngine.getInstance().flag( getXPos() , getYPos() );
        numOfClicks++;
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawButton(canvas);

        if( isFlagged() ){
            drawFlag(canvas);
        }else if( isRevealed() && isBomb() && !isClicked() ){
            drawNormalBomb(canvas);
        }else {
            if( isClicked() ){
                if( getValue() == -1 ){
                    drawBombExploded(canvas);
                }else {
                    drawNumber(canvas);
                }
            }else{
                drawButton(canvas);
            }
        }
    }

    private void drawBombExploded(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_bomb_exploded);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawFlag( Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_flag);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawButton(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_button);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawNormalBomb(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_bomb_normal);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawNumber( Canvas canvas ){
        Drawable drawable = null;

        switch (getValue() ){
            case 0:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_number_0);
                break;
            case 1:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_number_1);
                break;
            case 2:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_number_2);
                break;
            case 3:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_number_3);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_number_4);
                break;
            case 5:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_number_5);
                break;
            case 6:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_number_6);
                break;
            case 7:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_number_7);
                break;
            case 8:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_number_8);
                break;
        }

        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }
    public int getNumOfClicks()
    {
        return numOfClicks;
    }


}