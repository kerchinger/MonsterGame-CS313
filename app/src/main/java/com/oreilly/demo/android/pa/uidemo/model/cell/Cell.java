package com.oreilly.demo.android.pa.uidemo.model.cell;

/**
 * Created by nickpredey on 12/15/16.
 */

import com.oreilly.demo.android.pa.uidemo.model.common.CellChangeListener;
import com.oreilly.demo.android.pa.uidemo.model.monster.PseudoMonster;

import java.util.List;

/**
 * This interface represents a cell in a world consisting of cells.
 *
 * @see PseudoMonster
 * @see World
 * @see CellView
 */
public interface Cell extends CellChangeListener {

    /**
     * This method adds another cell as a neighbor to this cell.  The
     * other cell does not automatically become a neighbor of this cell.
     */
    void addNeighbor(Cell cell);

    /**
     * This method returns the list of cells that are neighbors of this cell.
     */
    List<Cell> getNeighbors();

    /**
     * This method returns the list of actors currently occupying this cell.
     */
    List<PseudoMonster> getOccupants();

    /**
     * This method allows an pseudoMonster to enter this cell.
     */
    void enter(PseudoMonster pseudoMonster) throws InterruptedException;

    /**
     * This method allows an pseudoMonster to leave this cell.
     */
    void leave(PseudoMonster pseudoMonster);

    /**
     * This method returns the graphical view associated with this cell.
     */
    // CellView getView();

    /**
     * This method returns a random neighbor of this cell.
     */
    Cell randomNeighbor();
}