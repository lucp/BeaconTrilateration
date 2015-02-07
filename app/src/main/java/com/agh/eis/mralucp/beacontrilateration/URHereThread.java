package com.agh.eis.mralucp.beacontrilateration;

import android.util.Log;

import com.agh.eis.mralucp.beacontrilateration.handlers.BeaconHandler;
import com.agh.eis.mralucp.beacontrilateration.handlers.PathFinder;
import com.agh.eis.mralucp.beacontrilateration.model.Beacon;
import com.agh.eis.mralucp.beacontrilateration.model.Point;

import java.util.LinkedList;

/**
 * Created by Lucp on 2015-02-07.
 */
public class URHereThread implements Runnable {

    URHereActivity urHereActivity;

    private LinkedList<Beacon> beacons;

    volatile boolean finished = false;

    private long startTime = 0;
    private long endTime = 0;
    private long waitTime = 1000;

    public URHereThread(URHereActivity urHereActivity) {
        this.urHereActivity = urHereActivity;
    }

    @Override
    public void run() {
        while (!finished) {
            if (urHereActivity.mService != null) {
                this.startTime = System.currentTimeMillis();
                beacons = urHereActivity.mService.getBeacons();
                if (BeaconHandler.getActiveBeaconsNumber(beacons) >= 3) {
                    Point point = PathFinder.findPoint(beacons);

                    //TODO draw point and text
                    Log.d("URHereThread", "Trilateration: " + point);
                }
                this.endTime = System.currentTimeMillis();
                try {Thread.sleep(this.waitTime - (this.endTime - this.startTime));}
                catch (InterruptedException e) {e.printStackTrace();}
            }
        }
    }

    public void finish() {
        this.finished = true;
    }

}
