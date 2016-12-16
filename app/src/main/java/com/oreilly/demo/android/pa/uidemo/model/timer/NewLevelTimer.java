package com.oreilly.demo.android.pa.uidemo.model.timer;

/**
 * Created by nickpredey on 12/15/16.
 */

import android.os.Handler;

import com.oreilly.demo.android.pa.uidemo.model.TimerChangeListener;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This timer ticks every 20 seconds to notify the user that a level has been changed.
 *
 */
public class NewLevelTimer implements MonsterGameTimer {

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

        // The tickModel runs performAction() every 20000 milliseconds (20 seconds)
        timer.schedule(new TimerTask() {
            @Override public void run() {
                // schedule the onTick event on the UI thread
                handler.post(new Runnable() {
                    @Override public void run() {
                        // fire event
                        timerChangeListener.onTimerChange(NewLevelTimer.this);
                    }
                });
            }
        }, /* Fire after */ 20000);
    }

    @Override
    public void stop() {
        timer.cancel();
    }

}