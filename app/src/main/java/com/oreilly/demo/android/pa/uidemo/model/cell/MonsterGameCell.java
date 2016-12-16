package com.oreilly.demo.android.pa.uidemo.model.cell;

import android.util.Log;

import com.oreilly.demo.android.pa.uidemo.model.common.CellChangeListener;
import com.oreilly.demo.android.pa.uidemo.model.common.Constants;
import com.oreilly.demo.android.pa.uidemo.model.monster.PseudoMonster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by nickpredey on 12/15/16.
 */

public class MonsterGameCell implements Cell {

    /**
     * This creates a specific cell with a certain capacity.
     */
    public MonsterGameCell(int capacity, CellChangeListener listener) {
        sema = new Semaphore(capacity);
        this.cellChangeListener = listener;
    }
    protected CellChangeListener cellChangeListener;


    private static Random random = new Random(System.currentTimeMillis());

    /**
     * This list is all the neighboring cells
     */
    protected ArrayList<Cell> neighbors = new ArrayList<Cell>();

    /**
     * The current occupants of this cell.
     */
    protected ArrayList<PseudoMonster> occupants = new ArrayList<PseudoMonster>();

    /**
     * A semaphore to control entry into this limited-capacity cell.
     */
    protected Semaphore sema;


    /**
     * This method adds a neighbor to this cell in a thread-safe manner.
     */
    public synchronized void addNeighbor(Cell cell) { neighbors.add(cell); }

    /**
     * This method adds an occupant to this cell in a thread-safe manner.
     */
    protected synchronized void addOccupant(PseudoMonster pseudoMonster) { occupants.add(pseudoMonster); }

    /**
     * This method removes an occupant from this cell in a thread-safe manner.
     */
    protected synchronized void removeOccupant(PseudoMonster pseudoMonster) { occupants.remove(pseudoMonster); }

    /**
     * This method waits for space to open in this cell, if necessary, and then
     * places the pseudoMonster inside the cell.
     */
    public void enter(PseudoMonster pseudoMonster) throws InterruptedException {
        Cell previous = pseudoMonster.getCell();
        Log.i(Constants.TAG, "enter: Entering cell "+ this);
        if (this != previous) {
            // if necessary, wait for space in this cell
            sema.acquire();
            // if the pseudoMonster was somewhere else before, take them out of there
            if (previous != null) {
                previous.leave(pseudoMonster);
            }
            // put the pseudoMonster into this cell
            pseudoMonster.setCell(this);
            addOccupant(pseudoMonster);
            // fire event to tell all occupants of this cell about the new arrival
            onEnterCell(new CellEvent(this, pseudoMonster));
        } else {
            Log.i(Constants.TAG, "Staying in same cell");
        }
    }

    public void leave(PseudoMonster pseudoMonster) {
        Log.i(Constants.TAG, "leave: Leaving cell "+ this);
        removeOccupant(pseudoMonster);
        // fire event to tell all occupants of this cell about the departure
        onLeaveCell(new CellEvent(this, pseudoMonster));
        // release the space that was occupied by the pseudoMonster who just left
        sema.release();
    }

    /**
     * This method fires an CellEvent.enterCell event
     * to all occupants of this cell.
     */
    @SuppressWarnings("unchecked")
    public void onEnterCell(final CellEvent event) {
        // Notify listener
        this.cellChangeListener.onEnterCell(event);
        List<PseudoMonster> currentOccupants;
        synchronized (this) {
            currentOccupants = (List<PseudoMonster>) this.occupants.clone();
        }
        for (PseudoMonster a : currentOccupants) {
            a.onEnterCell(event);
        }
    }

    /**
     * This method fires an <code>CellEvent.leaveCell</code> event
     * to all occupants of this cell.
     */
    @SuppressWarnings("unchecked")
    public void onLeaveCell(final CellEvent event) {
        // Notify listener
        this.cellChangeListener.onLeaveCell(event);
        List<PseudoMonster> currentOccupants;
        synchronized (this) {
            currentOccupants = (List<PseudoMonster>) this.occupants.clone();
        }
        for (PseudoMonster a : currentOccupants) {
            a.onEnterCell(event);
        }
    }

    /**
     * This method returns a clone of this cell's neighbors.
     */
    @SuppressWarnings("unchecked")
    public synchronized List<Cell> getNeighbors() { return (List<Cell>) neighbors.clone(); }

    /**
     * This method returns a clone of this cell's occupants.
     */
    @SuppressWarnings("unchecked")
    public synchronized List<PseudoMonster> getOccupants() { return (List<PseudoMonster>) occupants.clone(); }

    public synchronized Cell randomNeighbor() {
        int size = neighbors.size();
        return size == 0 ? null : (Cell) neighbors.get(random.nextInt(size));
    }

}
