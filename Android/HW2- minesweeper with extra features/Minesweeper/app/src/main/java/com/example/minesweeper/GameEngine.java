package com.example.minesweeper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.minesweeper.util.Generator;
import com.example.minesweeper.view.grid.Cell;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;


public class GameEngine {
    public static int WIDTH;
    public static int HEIGHT;
    public static int BOMB_NUMBER;
    public static String gameStatus;
    public static boolean gameWon;



    private GameEngine() {

    }


    private static GameEngine instance;

    private Context context;

    private Cell[][] MinesweeperGrid = new Cell[WIDTH][HEIGHT];


    public static GameEngine getInstance() {
        if (instance == null) {
            if (MainActivity.easyActive) {
                WIDTH = 10;
                HEIGHT = 10;
                BOMB_NUMBER = 10;
                //MainActivity.easyActive = false;
            } else if (MainActivity.mediumActive) {
                WIDTH = 15;
                HEIGHT = 15;
                BOMB_NUMBER = 35;
                //MainActivity.mediumActive = false;
            } else if (MainActivity.hardActive) {
                WIDTH = 20;
                HEIGHT = 20;
                BOMB_NUMBER = 80;
                //MainActivity.hardActive = false;
            }
            instance = new GameEngine();
        }
        return instance;
    }


    public void createGrid(Context context) {
        this.context = context;

        // create the grid and store it
        int[][] GeneratedGrid = Generator.generate(BOMB_NUMBER, WIDTH, HEIGHT);
        System.out.print(WIDTH);
        setGrid(context, GeneratedGrid);
    }

    private void setGrid(final Context context, final int[][] grid) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (MinesweeperGrid[x][y] == null) {
                    MinesweeperGrid[x][y] = new Cell(context, x, y);
                }
                MinesweeperGrid[x][y].setValue(grid[x][y]);
                MinesweeperGrid[x][y].invalidate();
            }
        }
    }

    public Cell getCellAt(int position) {
        int x = position % WIDTH;
        int y = position / WIDTH;

        return MinesweeperGrid[x][y];
    }

    public Cell getCellAt(int x, int y) {
        return MinesweeperGrid[x][y];
    }

    public void click(int x, int y) {
        if (x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT && !getCellAt(x, y).isClicked()) {
            getCellAt(x, y).setClicked();

            if (getCellAt(x, y).getValue() == 0) {
                for (int xt = -1; xt <= 1; xt++) {
                    for (int yt = -1; yt <= 1; yt++) {
                        if (xt != yt) {
                            click(x + xt, y + yt);
                        }
                    }
                }
            }

            if (getCellAt(x, y).isBomb()) {
                onGameLost();
            }
        }

        checkEnd();
    }


    private void checkEnd() {
        int bombNotFound = BOMB_NUMBER;
        int notRevealed = WIDTH * HEIGHT;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (getCellAt(x, y).isRevealed() || getCellAt(x, y).isFlagged()) {
                    notRevealed--;

                }

                if (getCellAt(x, y).isFlagged() && getCellAt(x, y).isBomb()) {
                    bombNotFound--;
                }
            }
        }

        if (bombNotFound == 0 && notRevealed == 0) {
            GameActivity.chronometer.stop();
            gameStatus = context.getString(R.string.youWin);
            instance = null;
            gameWon = true;
            Intent i = new Intent().setClass(context, EndGameActivity.class);
            context.startActivity(i);


        }
    }

    public void flag(int x, int y) {
        boolean isFlagged = getCellAt(x, y).isFlagged();
        boolean isClicked = getCellAt(x, y).isClicked();
        getCellAt(x, y).setFlagged(!isFlagged && !isClicked);
        getCellAt(x, y).invalidate();
    }

    private void onGameLost() {
        // handle lost game
        GameActivity.chronometer.stop();
        gameStatus = context.getString(R.string.youLose);
        instance = null;
        Intent i = new Intent().setClass(context, EndGameActivity.class);
        context.startActivity(i);

    }





}
