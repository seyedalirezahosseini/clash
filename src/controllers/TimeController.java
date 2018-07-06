package controllers;

import models.interfaces.TimeSensitive;

public class TimeController extends Thread {
    public static final int CYCLE_MILLI_SECOND = 100;
    private TimeSensitive owner;
    private long nextTime;
    public TimeController(TimeSensitive owner) {
        this.owner = owner;
    }

    @Override
    public void run() {
        nextTime = System.currentTimeMillis();
        while (true) {
            if (Math.abs(System.currentTimeMillis() - nextTime)  < 2) {
                nextTime += CYCLE_MILLI_SECOND;
                owner.cyclePass();
            }
            if (System.currentTimeMillis() - nextTime > 0) {
                nextTime = System.currentTimeMillis() + 100;
            }
        }
    }
}
