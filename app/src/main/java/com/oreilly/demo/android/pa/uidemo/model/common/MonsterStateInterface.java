package com.oreilly.demo.android.pa.uidemo.model.common;

/**
 * Created by nickpredey on 12/15/16.
 */

public interface MonsterStateInterface {

    // transitions
    void toVulnerable();
    void toProtected();

    // actions
    void actionInit();
}
