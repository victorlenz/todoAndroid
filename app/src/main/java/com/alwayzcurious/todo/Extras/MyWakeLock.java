package com.alwayzcurious.todo.Extras;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by vivek on 4/11/2017.
 */

public abstract class MyWakeLock {

    private static PowerManager.WakeLock wakeLock;

    public static void acquire(Context c) {
        if (wakeLock != null) wakeLock.release();

        PowerManager pm = (PowerManager) c.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "com.alwayzcurious.todo");
        wakeLock.acquire();
    }

    public static void release() {
        if (wakeLock != null){
            wakeLock.release();
        }
        wakeLock = null;
    }
}
