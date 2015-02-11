package com.agh.eis.mralucp.beacontrilateration;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import com.agh.eis.mralucp.beacontrilateration.handlers.BeaconHandler;
import com.agh.eis.mralucp.beacontrilateration.handlers.DistanceRSSIConverter;
import com.agh.eis.mralucp.beacontrilateration.handlers.PathFinder;
import com.agh.eis.mralucp.beacontrilateration.model.Beacon;
import com.agh.eis.mralucp.beacontrilateration.model.CSVEntry;
import com.agh.eis.mralucp.beacontrilateration.model.Point;

import java.util.LinkedList;
import java.util.UUID;

/*****
 * Serive in charge of reciving Broadcast receiver from Beacons app about new RSSI signal
 */
public class EvaluationService extends Service{

    private String TAG = "EvaluationService";
    private final IBinder myBinder = new MyLocalBinder();
    private long date;
    private BroadcastReceiver mDataReceiver;
    private static boolean isRunning = false;
    private LinkedList<Beacon> beacons;

    public static boolean isRunning()
    {
        return isRunning;
    }

    public class MyLocalBinder extends Binder {
        EvaluationService getService() {
            return EvaluationService.this;
        }
    }

    public EvaluationService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        this.isRunning = true;
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
//        this.isRunning = true;

        this.beacons = new LinkedList<Beacon>();
//        this.loadConfigurationHome(this.beacons);
        this.load316(this.beacons);

        this.mDataReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                double rssi = intent.getIntExtra("RSSI", 0);
                int major = intent.getIntExtra("Major", 0);
                int minor = intent.getIntExtra("Minor", 0);
                Beacon beacon = getBeaconByMM(major, minor);
                if (beacon != null) {
                    beacon.setCurrentSignalAndAddToHistory(new CSVEntry(System.currentTimeMillis(), (int)rssi));
                }
            }
        };

        Log.d(TAG, "before register");
        this.registerReceiver(this.mDataReceiver, new IntentFilter("com.aware.plugin.beacons.SCAN_RESULT_ACTION_PRIM"));
    }

    private Beacon getBeaconByMM(int major, int minor) {
        for (Beacon beacon : this.beacons) {
            if (beacon.getMajor() == major && beacon.getMinor() == minor) return beacon;
        }
        return null;
    }

    @Override
    public void onDestroy() {
        this.isRunning=false;
        Log.d(TAG, "onDestroy");
        unregisterReceiver(mDataReceiver);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return myBinder;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnBind");

        return super.onUnbind(intent);
    }

    public LinkedList<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(LinkedList<Beacon> beacons) {
        this.beacons = beacons;
    }


    /***
     * FRAGMENT TO CHANGE ACCORDING TO CURRENT BEACONS LOCATIONS.
     */

    /****
     * Function used to load ids of beacons (name, major, minor) and their location (here - custom location)
     * @param target
     */
    public void loadConfigurationHome(LinkedList<Beacon> target) {
        target.add(new Beacon("5Zg6", 51800, 62059, 0.75, 4.6)); //5Zg6
        target.add(new Beacon("QBtX", 2068, 37705, 0.0, 0.0)); //QBtX
        target.add(new Beacon("1RJB", 14925, 50618, 2.75, 4.6)); //1RJB
    }

    /****
     * Function used to load ids of beacons (name, major, minor) and their location
     * here - room 316 (8mx6m)  with beacons set in square 6mx6m (coordinates from point 0,0)
     *
     * @param target
     */
    public void load316(LinkedList<Beacon> target) {
        target.add(new Beacon("5Zg6", 51800, 62059, 0.0, 0.0)); //5Zg6
        target.add(new Beacon("QBtX", 2068, 37705, 6.0, 0.0)); //QBtX
        target.add(new Beacon("1RJB", 14925, 50618, 0.0, 6.0)); //1RJB
        target.add(new Beacon("Y7qt", 5484 , 63741, 6.0, 6.0)); //Y7qt
    }

}
