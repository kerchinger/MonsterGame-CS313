package com.oreilly.demo.android.pa.uidemo.model.monster;

/**
 * Created by nickpredey on 12/15/16.
 * Now, the monster can actually be killed. Let's take advantage of that and kill some monsters
 * in the name of Loyola!!!
 */

import com.oreilly.demo.android.pa.uidemo.model.common.Constants;
import com.oreilly.demo.android.pa.uidemo.model.common.MonsterStateInterface;

public class VulnerableState implements DefaultMonsterState {
    public VulnerableState(MonsterStateInterface stateInterface) {
        this.stateInterface = stateInterface;
    }

    private MonsterStateInterface stateInterface;

    @Override
    public int getId() { return Constants.STATE_VULNERABLE; }

    @Override
    public String toString() { return "VulnerableState"; }
}
