package com.oreilly.demo.android.pa.uidemo.model.monster;


import com.oreilly.demo.android.pa.uidemo.model.cell.Cell;
import com.oreilly.demo.android.pa.uidemo.model.cell.CellEvent;
import com.oreilly.demo.android.pa.uidemo.model.common.Constants;
import com.oreilly.demo.android.pa.uidemo.model.common.MonsterStateChangeListener;
import com.oreilly.demo.android.pa.uidemo.model.common.MonsterStateInterface;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Monster implements
        PseudoMonster, Runnable, MonsterStateInterface {


    private ExecutorService livingThread;
    /* An Executor that provides methods to manage termination and methods
    * that can produce a Future for tracking progress of one or more asynchronous tasks.
    * This idea was brought to my attention by the tutor, feel free to read the documentation
    * at https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ExecutorService.html.
    * From the documentation, if you decided not to read it,
    * An Executor is normally used instead of explicitly creating threads.
    * For example, rather than invoking new Thread(new(RunnableTask())).start()
    * for each of a set of tasks, you might use:
    * Executor executor = anExecutor;
    * executor.execute(new RunnableTask1());
    * executor.execute(new RunnableTask2());
    *
    * We basically need to define two different ExecutorService objects because it may cause an
    * error in the program if two different PseudoMonster/Monsters try to enter the same cell or something
    * like that. It gives the PseudoMonster interface some "brains", if you will, to determine which task
    * takes precedence over the other. For example, if a task is currently being executed, it won't
    * stop that from happening.
    */

    private ExecutorService workingThread;

    /**
     * The destination of the most recent attempt to move.
     */
    private Future task = null;

    /**
     * This shows whether or not the pseudomonster is alive.
     */
    protected synchronized boolean isAlive() { return livingThread != null; }

    /**
     * This method brings this pseudomonster to life by starting its internal threads.
     *
     */
    public synchronized void start() {
        // So, Executors.newFixedThreadPool() just uses the pool of threads, it does not explicitly
        // create one. It may seem like it's creating, but it's just using the existing thread.
        if (!isAlive()) {
            livingThread = Executors.newFixedThreadPool(1);
            workingThread = Executors.newFixedThreadPool(1);
        }
        livingThread.execute(this);
    }

    /**
     * This method is used to schedule the runnable for execution by this
     * pseudomonster.  If the pseudomonster is still waiting for a previously scheduled
     * runnable to execute, then this invocation preempts the previous one.
     */
    protected synchronized void execute(Runnable runnable) {
        if (task != null && ! task.isDone()) {
            task.cancel(true);
        }
        task = workingThread.submit(runnable);
    }

    private int stateSleepTime = 1000;
    public void setStateSleepTime(int time) {
        this.stateSleepTime = time;
    }

    private int moveSleepTime = 1000;
    public void setMoveSleepTime(int time) {
        this.moveSleepTime = time;
    }

    public Monster() {
        this.state = PROTECTED;
    }

    public void run() {
        while (! Thread.interrupted()) {
            try {
                execute(changeState);
                Thread.sleep(stateSleepTime);
                execute(move);

                Thread.sleep(moveSleepTime);

            } catch (InterruptedException exc) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static Random random = new Random();

    /**
     * A runnable representing a move. The movement allows the monster to go into neighboring cells
     * which was one of the requirements on the rubric
     */
    private Runnable move = new Runnable() {
        public void run() {
            try {
                getCell().randomNeighbor().enter(Monster.this);

            } catch (InterruptedException e) {
                // if interrupted before entering the cell, then set interrupted flag
                // so that the worker thread can detect this
                Thread.currentThread().interrupt();
            }
        }
    };

    /**
     * A change in state.
     */
    private Runnable changeState = new Runnable() {
        public void run() {
            alternateState();
        }
    };

    /**
     * This will remove the dead PseudoMonster/Monster from the cell (it will be out of the view at this
     * point)
     */
    protected synchronized void die() {
        Cell cell = getCell();
        setCell(null);
        cell.leave(this);
    }

    /**
     * The pseudomonster will be killed and its threads shut off.
     */
    public synchronized boolean kill() {
        if (isAlive()) {
            die();
            return true;
        }
        return false;
    }

    public synchronized void setCell(Cell cell) { currentCell = cell; }

    public synchronized Cell getCell() { return currentCell; }

    /**
     * The cell this pseudomonster is currently occupying.
     */
    private Cell currentCell;


    /**
     * This method utilizes a Random variable to change states between protected and vulnerable.
     */
    protected void alternateState() {


        int n = random.nextInt(2);
        boolean flag = n == 0;
        if (flag) return;

        if (state == PROTECTED)
            toVulnerable();
        else
            toProtected();
    }

    private final ProtectedState PROTECTED = new ProtectedState(this);
    private final VulnerableState VULNERABLE = new VulnerableState(this);

    DefaultMonsterState state;

    /**
     * This sets the current state of a Monster.
     * @param state
     */
    protected void setState(DefaultMonsterState state) {
        this.state = state;
    }

    private MonsterStateChangeListener monsterStateChageListener;

    public int getStateId() {
        return state.getId();
    }

    public boolean isProtected() {
        return state.getId() == Constants.STATE_PROTECTED;
    }

    @Override
    public void toVulnerable() {
        setState(VULNERABLE);
    }

    @Override
    public void toProtected() {
        setState(PROTECTED);
    }

    @Override
    public void actionInit() {
        toProtected();
    }

    @Override
    public void onEnterCell(CellEvent event) {
        monsterStateChageListener.onMonsterStateChange(state.getId());
    }

    public void setMonsterStateChangeListener(
            MonsterStateChangeListener listener) {
        this.monsterStateChageListener = listener;
    }

    /**
     * Notifies the listener when the state is changed.
     * @param event
     */
    @Override
    public void onLeaveCell(CellEvent event) {
        monsterStateChageListener.onMonsterStateChange(state.getId());

    }

}