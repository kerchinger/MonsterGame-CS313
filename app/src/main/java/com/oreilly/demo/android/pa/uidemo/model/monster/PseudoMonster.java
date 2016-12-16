package com.oreilly.demo.android.pa.uidemo.model.monster;

/**
 * Created by nickpredey on 12/15/16.
 */

import com.oreilly.demo.android.pa.uidemo.model.cell.Cell;
import com.oreilly.demo.android.pa.uidemo.model.common.CellChangeListener;

public interface PseudoMonster extends CellChangeListener {

    /**
     * This method sets the cell that this pseudoMonster currently occupies.
     * This method is meant to be called by the <code>leave</code> method
     * in the <code>Cell</code> class.
     */
    void setCell(Cell cell);

    /**
     * This will return the cell that the monster occupies currently.
     */
    Cell getCell();

    /**
     * This essentially creates each "PseudoMonster" which will basically be a monster in each of the
     * multiple states
     */
    void start();

    /**
     * This will kill the monster/PseudoMonster.
     * @return
     */
    boolean kill();
}
