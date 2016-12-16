package com.oreilly.demo.android.pa.uidemo.model.timer;

import com.oreilly.demo.android.pa.uidemo.model.TimerChangeListener;

/**
 * Created by nickpredey on 12/15/16.
 */

public interface MonsterGameTimer {
    void setOnTimerChangeListener(TimerChangeListener listener);
    void start();
    void stop();

}
