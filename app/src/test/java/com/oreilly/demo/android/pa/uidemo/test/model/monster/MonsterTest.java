package com.oreilly.demo.android.pa.uidemo.test.model.monster;

import com.oreilly.demo.android.pa.uidemo.model.monster.Monster;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Grazyna on 12/15/2016.
 */

public class MonsterTest {
    @Test
    public void changeState() {
        Monster monster = new Monster();
        assertEquals(true, monster.isProtected());
        monster.toVulnerable();
        assertEquals(false, monster.isProtected());
        monster.toProtected();
        assertEquals(true, monster.isProtected());
    }
}
