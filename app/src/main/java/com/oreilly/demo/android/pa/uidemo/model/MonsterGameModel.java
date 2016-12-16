package com.oreilly.demo.android.pa.uidemo.model;

import com.oreilly.demo.android.pa.uidemo.model.cell.CellArena;
import com.oreilly.demo.android.pa.uidemo.model.common.LevelChangeListener;
import com.oreilly.demo.android.pa.uidemo.model.common.ModelChangeListener;
import com.oreilly.demo.android.pa.uidemo.model.common.MonsterStateChangeListener;
import com.oreilly.demo.android.pa.uidemo.model.monster.PseudoMonster;

import java.util.List;

/**
 * Created by nickpredey on 12/15/16.
 */

public interface MonsterGameModel extends TimerChangeListener, MonsterStateChangeListener {
    public void setModelChangeListener(ModelChangeListener listener);
    public CellArena getArena();
    public void addActor(PseudoMonster pseudoMonster, int xpos, int ypos);
    public List<PseudoMonster> getLivingPseudoMonsters();
    public int getScore();
    public void updateScore(int score);
    public int getCurrentLevel();
    public void addLivingActor(PseudoMonster pseudoMonster);
    public void removeLivingActor(PseudoMonster pseudoMonster);
    public void setLevelChangeListener(LevelChangeListener listener);
    public void exitApp();
}
