package com.oreilly.demo.android.pa.uidemo.model.monster;

import com.oreilly.demo.android.pa.uidemo.model.common.Constants;
import com.oreilly.demo.android.pa.uidemo.model.common.MonsterStateInterface;

/**
 * Created by nickpredey on 12/15/16.
 * This is the "protected" state that the monster cannot be killed
 * INVINCIBLE!!!!
 */

public class ProtectedState implements DefaultMonsterState {
    public ProtectedState(MonsterStateInterface stateInterface) {
        this.stateInterface = stateInterface;
    }

    private MonsterStateInterface stateInterface;

    @Override
    public	int getId() { return Constants.STATE_PROTECTED; }

    @Override
    public String toString() { return "ProtectedState"; }
}
