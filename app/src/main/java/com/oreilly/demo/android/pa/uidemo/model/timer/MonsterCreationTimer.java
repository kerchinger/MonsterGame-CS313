package com.oreilly.demo.android.pa.uidemo.model.timer;

/**
 * Created by nickpredey on 12/15/16.
 */

import android.os.Handler;

import com.oreilly.demo.android.pa.uidemo.model.TimerChangeListener;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This timer will create a monster every two seconds, after the first level is over
 * Made the addition to add levels to the project. This supplements that idea.
 *
 */
public class MonsterCreationTimer implements MonsterGameTimer {

    private Timer timer;

    @Override
    public void setOnTimerChangeListener(TimerChangeListener listener) {
        this.timerChangeListener = listener;
    }
    private TimerChangeListener timerChangeListener;

    private Handler handler = new Handler();

    @Override
    public void start() {
        timer = new Timer();

        // The tickModel runs every 2000 milliseconds
        timer.schedule(new TimerTask() {
            @Override public void run() {
                // schedule the onTick event on the UI thread
                handler.post(new Runnable() {
                    @Override public void run() {
                        // fire event
                        timerChangeListener.onTimerChange(MonsterCreationTimer.this);
                    }
                });
            }
        }, /*initial delay*/ 1000, /*periodic delay */ 2000);
    }

    @Override
    public void stop() {
        timer.cancel();
    }

}
