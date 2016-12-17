package com.oreilly.demo.android.pa.uidemo.test.model.timer;

import com.oreilly.demo.android.pa.uidemo.model.TimerChangeListener;
import com.oreilly.demo.android.pa.uidemo.model.timer.MonsterGameTimer;
import com.oreilly.demo.android.pa.uidemo.model.timer.NewLevelTimer;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Grazyna on 12/15/2016.
 */

public class NewLevelTimerTest {
    private boolean changed;
    @Test
    public void timeout() throws InterruptedException{
        NewLevelTimer newLevelTimer = new NewLevelTimer();

        changed = true;
        newLevelTimer.setOnTimerChangeListener(new TimerChangeListener() {
            @Override
            public void onTimerChange(MonsterGameTimer observer) {
                changed = true;
            }
        });

        newLevelTimer.start();
        Thread.sleep(21000);
        assertEquals(true, changed);
    }
}
