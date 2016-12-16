package com.oreilly.demo.android.pa.uidemo.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.oreilly.demo.android.pa.uidemo.model.DefaultMonsterGameModel;
import com.oreilly.demo.android.pa.uidemo.R;
import com.oreilly.demo.android.pa.uidemo.model.MonsterGameModel;
import com.oreilly.demo.android.pa.uidemo.model.cell.Cell;
import com.oreilly.demo.android.pa.uidemo.model.cell.CellEvent;
import com.oreilly.demo.android.pa.uidemo.model.cell.MonsterGameCell;
import com.oreilly.demo.android.pa.uidemo.model.common.Constants;
import com.oreilly.demo.android.pa.uidemo.model.common.LevelChangeListener;
import com.oreilly.demo.android.pa.uidemo.model.common.ModelChangeListener;
import com.oreilly.demo.android.pa.uidemo.model.monster.PseudoMonster;
import com.oreilly.demo.android.pa.uidemo.model.monster.Monster;
import com.oreilly.demo.android.pa.uidemo.view.DefaultMonsterGameView;

import java.util.Random;

//import android.media.MediaPlayer;


/**
 * Created by nickpredey on 12/15/16.
 */

public class MonsterGameController extends Activity implements ModelChangeListener,
        ViewChangeListener,
        LevelChangeListener {

    private DefaultMonsterGameView view;
    private MonsterGameModel model;
    UIUpdateThread uiUpdater;
    private int gridWidth;
    private int gridHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        view = (DefaultMonsterGameView) findViewById(R.id.Monster_Game_view);

        // Get display dimensions and derive grid width&height
        final Point displayDimensions = new Point();
        getWindowManager().getDefaultDisplay().getSize(displayDimensions);
        gridWidth = displayDimensions.x / Constants.CELL_SIZE;
        gridHeight = (displayDimensions.y - Constants.VIEW_HEIGHT_MINUS) / Constants.CELL_SIZE;
        Cell[][] cells = new MonsterGameCell[gridWidth][gridHeight];
        // create cells according to display dimensions
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                cells[i][j] = new MonsterGameCell(1, this);
            }
        }

        // Initialize the model
        model = new DefaultMonsterGameModel(cells, this);

        // create actors and populate world in the model
        Random random = new Random(System.currentTimeMillis());

        //see commons.Constants if the monster count should be changed
        for (int i = 0; i < Constants.MONSTER_COUNT; i++) {
            int x = random.nextInt(gridWidth);
            int y = random.nextInt(gridHeight);
            PseudoMonster monster = new Monster();
            ((Monster) monster).setMonsterStateChangeListener(this);
            model.addLivingActor(monster);
            model.addActor(monster, y, x);
            Log.i(Constants.TAG, "Monster created: " + monster);
        }

        // Dependency injection on model
        model.setModelChangeListener(this);
        model.setLevelChangeListener(this);


        // Dependency injection on view
        view.setModel(model);
        view.setOnTouchListener(this);

        view.init(cells, Constants.CELL_SIZE);

        // start the initial actors
        for (PseudoMonster a : model.getLivingPseudoMonsters()) {
            a.start();
        }


        uiUpdater = new UIUpdateThread(view);
        (new Thread(uiUpdater)).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    public void exitProgram(MenuItem item) {
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    /**
     * This will handle all the touch events from the view.
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(Constants.TAG, "Touch event has been handled.");
        Cell[][] grid = model.getArena().getGrid();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int gridX = x / Constants.CELL_SIZE;
            int gridY = y / Constants.CELL_SIZE;
            if (gridX >= gridWidth || gridY >= gridHeight) {
                return false;
            }
            Cell cell = grid[gridX][gridY];
            int count = cell.getOccupants().size();
            if (count > 0) {
                Monster monster = (Monster) cell.getOccupants().get(0);
                if (!monster.isProtected()) {
                    if (monster.kill()) {
                        model.updateScore(1); // Add 1 to current score
                        model.removeLivingActor(monster);
                        return true;
                    }
                }
            }

        }

        return false;
    }

    @Override
    public void onMonsterStateChange(int stateId) {

    }

    @Override
    public void onModelChange() {

    }

    @Override
    public void onEnterCell(CellEvent event) {

    }

    @Override
    public void onLeaveCell(CellEvent event) {

    }


    /**
     * This method will either advance level or exit the program and subsequently let the user know
     * @param level
     */
    @Override
    public void onLevelChange(int level) {
        if (level <= 2) {
            String msg = "Keep it up!! " + model.getCurrentLevel();
            displayToastMessage(msg, Toast.LENGTH_SHORT);
        } else if (level >= 3) {
            exitApp("Time is up!! " + model.getScore());
            if (model.getScore() >= 5) {
                displayToastMessage("YOU'RE AN ANIMAL!!! " + model.getScore() + " points!! WOW",
                        Toast.LENGTH_SHORT);
            }
            displayToastMessage("Game over! Your score: ", model.getScore());
        }
    }

    /**
     * This will take in a message for a certain duration and display a message to the user
     * (Toast is not only a breakfast item...)
     * @param msg
     * @param lengthShort
     */
    private void displayToastMessage(String msg, int lengthShort) {
        Context context = getApplicationContext();

        Toast toast = Toast.makeText(context, msg, lengthShort);
        toast.show();
    }

    /**
     * This will exit the app, print the score, and a little message.
     *
     * @param msg The message to be displayed upon exit
     */
    private void exitApp(String msg) {
        model.exitApp();
        uiUpdater.done();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        // set title
        alert.setTitle("Monster game");
        // set dialog message
        alert
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Exit Game", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MonsterGameController.this.finish();
                        System.exit(0);
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alert.create();
        // show it
        alertDialog.show();

        view.invalidate();

    }
}
