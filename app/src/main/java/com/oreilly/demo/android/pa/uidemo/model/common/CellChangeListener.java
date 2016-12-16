package com.oreilly.demo.android.pa.uidemo.model.common;


import com.oreilly.demo.android.pa.uidemo.model.cell.CellEvent;
import com.oreilly.demo.android.pa.uidemo.model.monster.PseudoMonster;

/**
 * Created by nickpredey on 12/15/16.
 */

/**
 * A listener to cell events.
 * @see CellEvent
 * @see PseudoMonster
 */
public interface CellChangeListener {

    /**
     * This method indicates that an actor has entered a cell.
     * May be called from any thread, usually not the Swing thread.
     */
    void onEnterCell(CellEvent event);

    /**
     * This method indicates that an actor has left a cell.
     * May be called from any thread, usually not the Swing thread.
     */
    void onLeaveCell(CellEvent event);
}
