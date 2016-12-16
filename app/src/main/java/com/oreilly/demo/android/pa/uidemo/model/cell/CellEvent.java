package com.oreilly.demo.android.pa.uidemo.model.cell;

import com.oreilly.demo.android.pa.uidemo.model.common.CellChangeListener;
import com.oreilly.demo.android.pa.uidemo.model.monster.PseudoMonster;

import java.util.EventObject;

/**
 * This class represents events occuring in cells.  The cell in question
 * is usually the source of the event.
 * @see Cell
 * @see PseudoMonster
 * @see CellChangeListener
 */
public class CellEvent extends EventObject {

    private static final long serialVersionUID = 6621950138682436187L;

    /**
     * The pseudoMonster that is involved
     */
    private PseudoMonster pseudoMonster;

    /**
     * The constructor here will build an pseudoMonster based off of a cell event.
     */
    public CellEvent(Object source, PseudoMonster pseudoMonster) {
        super(source);
        this.pseudoMonster = pseudoMonster;
    }

    /**
     * This method returns the pseudoMonster involved in this event.
     */
    public PseudoMonster getPseudoMonster() { return pseudoMonster; }

    public String toString() {
        return getClass().getName() + "[" + source + "," + pseudoMonster + "]";
    }
}
