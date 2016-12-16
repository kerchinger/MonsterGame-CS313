package com.oreilly.demo.android.pa.uidemo.model.common;

/**
 * Created by nickpredey on 12/15/16.
 */

public interface ModelChangeListener extends MonsterStateChangeListener, CellChangeListener {
    public void onModelChange();
}
