package com.oreilly.demo.android.pa.uidemo.model.cell;

/**
 * Created by nickpredey on 12/15/16.
 */

import com.oreilly.demo.android.pa.uidemo.model.monster.PseudoMonster;

/**
 * This interface represents an Arena that can be populated with actors.
 * Yes, this an Arena. Few players enter, only one can win >:).
 * @see PseudoMonster
 */
public interface Arena {
    /**
     * This method adds an pseudoMonster to this world at the given position.
     */
    void addActor(PseudoMonster pseudoMonster, int xpos, int ypos);
}
