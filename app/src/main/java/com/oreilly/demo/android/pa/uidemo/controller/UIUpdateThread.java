package com.oreilly.demo.android.pa.uidemo.controller;

import android.view.View;

/**
 * Created by nickpredey on 12/15/16.
 */

class UIUpdateThread implements Runnable {

    private volatile boolean done;
    private View view;

    UIUpdateThread(View view) {
        this.view = view;
    }

    public void done() { done = true; }

    @Override
    public void run() {

        while (!done) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            view.postInvalidate();
        }
    }
}
