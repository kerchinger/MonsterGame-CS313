package com.oreilly.demo.android.pa.uidemo.model;

import android.util.Log;

import com.oreilly.demo.android.pa.uidemo.model.cell.Cell;
import com.oreilly.demo.android.pa.uidemo.model.cell.CellArena;
import com.oreilly.demo.android.pa.uidemo.model.common.Constants;
import com.oreilly.demo.android.pa.uidemo.model.common.LevelChangeListener;
import com.oreilly.demo.android.pa.uidemo.model.common.ModelChangeListener;
import com.oreilly.demo.android.pa.uidemo.model.monster.PseudoMonster;
import com.oreilly.demo.android.pa.uidemo.model.monster.Monster;
import com.oreilly.demo.android.pa.uidemo.model.timer.MonsterCreationTimer;
import com.oreilly.demo.android.pa.uidemo.model.timer.MonsterGameTimer;
import com.oreilly.demo.android.pa.uidemo.model.timer.NewLevelTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by nickpredey on 12/15/16.
 */

public class DefaultMonsterGameModel implements MonsterGameModel {
    private ModelChangeListener modelChangeListener;
    private CellArena arena;
    ArrayList<PseudoMonster> livingPseudoMonsters;
    private int score = 0;
    private int currentLevel = 1;
    private MonsterGameTimer levelTimer = new NewLevelTimer();
    private MonsterGameTimer monsterCreator = new MonsterCreationTimer();

    public int getCurrentLevel() {
        return currentLevel;
    }


    public DefaultMonsterGameModel(Cell[][] grid, ModelChangeListener listener) {
        setModelChangeListener(listener);
        arena = new CellArena(grid);
        livingPseudoMonsters = new ArrayList<>();
        levelTimer.setOnTimerChangeListener(this);
        monsterCreator.setOnTimerChangeListener(this);
        levelTimer.start();
    }

    public CellArena getArena() {
        return arena;
    }

    @Override
    public void setModelChangeListener(ModelChangeListener listener) {
        this.modelChangeListener = listener;
    }

    public void setLevelChangeListener(LevelChangeListener listener) {
        this.levelChangeListener = listener;
    }
    LevelChangeListener levelChangeListener;

    @Override
    public void addActor(PseudoMonster pseudoMonster, int xpos, int ypos) {
        arena.addActor(pseudoMonster, xpos, ypos);
    }

    public List<PseudoMonster> getLivingPseudoMonsters() {
        return livingPseudoMonsters;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void updateScore(int score) {
        this.score += score;
    }


    @Override
    public synchronized void addLivingActor(PseudoMonster pseudoMonster) {
        livingPseudoMonsters.add(pseudoMonster);
    }


    @Override
    public synchronized void removeLivingActor(PseudoMonster pseudoMonster) {
        livingPseudoMonsters.remove(pseudoMonster);
    }


    @Override
    public void onTimerChange(MonsterGameTimer observer) {
        Random random = new Random();
        Log.w(Constants.TAG, "Timer has been triggered.");
        if (observer instanceof NewLevelTimer) {
            currentLevel ++;
            levelChangeListener.onLevelChange(currentLevel);
            levelTimer.stop();
            levelTimer.start();
            monsterCreator.start();
        } else {
            Log.w(Constants.TAG, "Creating new monsters.");
            Monster m = new Monster();
            if (m != null) {
                m.setStateSleepTime(200);
                m.setMoveSleepTime(1800);
                m.setMonsterStateChangeListener(this);
                int x = random.nextInt(arena.getGrid().length);
                int y = random.nextInt(arena.getGrid()[0].length);
                arena.addActor(m, y, x);
                Log.w(Constants.TAG, "Current number of monsters: "+ livingPseudoMonsters.size());
                livingPseudoMonsters.add(m);
                m.start();
            }
        }
    }


    @Override
    public void onMonsterStateChange(int stateId) {
        modelChangeListener.onMonsterStateChange(stateId);
    }


    /**
     * This will make sure that the application is cleanly stopped when the program finishes.
     * I believe this was something that Dr. Laufer was talking about when he said to a group that
     * the app needs to finish cleanly.
     */
    @Override
    public void exitApp() {
        levelTimer.stop();
        monsterCreator.stop();
        for (PseudoMonster m : livingPseudoMonsters) {
            m.kill();
        }

    }

}
