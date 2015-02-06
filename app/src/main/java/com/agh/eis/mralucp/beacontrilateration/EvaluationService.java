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

public class EvaluationService extends IntentService {

    private String TAG = "EvaluationService";
    private final IBinder myBinder = new MyLocalBinder();
    private long date;
    private BroadcastReceiver mDataReceiver;
//    private static boolean isRunning = false;
    private LinkedList<Beacon> beacons;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public EvaluationService(String name) {
        super(name);
    }

//    public static boolean isRunning()
//    {
//        return isRunning;
//    }

    public class MyLocalBinder extends Binder {
        EvaluationService getService() {
            return EvaluationService.this;
        }
    }
    public EvaluationService() {
        super("EvaluationService");
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.d(TAG, "onStartCommand");
//        this.isRunning = true;
//        return Service.START_NOT_STICKY;
////        return super.onStartCommand(intent, flags, startId);
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
//        this.isRunning = true;

        this.beacons = new LinkedList<Beacon>();
        this.mDataReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                double rssi = 0;//  = returnRSSI(intent.getDoubleExtra("RSSI", 0.0));
//                Object rssi = intent.getParcelableExtra("RSSI");
                long id = 0; //TODO return id value from intent broadcast
//                Object beaconUID = intent.getSerializableExtra("UUID");
                Beacon beacon = getBeaconById(id);
                if (beacon != null) {
                    beacon.setCurrentSignalAndAddToHistory(new CSVEntry(System.currentTimeMillis(), (int)rssi));
                }
                if (BeaconHandler.getActiveBeaconsNumber(beacons) >= 3) {
                    System.out.println(PathFinder.findPoint(beacons));
                }
//                Beacon beacon = findBeacon(beaconUID);
//                if(beacon!=null){
//                  beforeTrilateration(beacon, signal);
//
//                }
                Toast.makeText(context, "broadcast ", Toast.LENGTH_LONG);//+signal+" "+obj, Toast.LENGTH_LONG);
            }
        };
        this.registerReceiver(this.mDataReceiver, new IntentFilter("com.aware.plugin.beacons.SCAN_RESULT_ACTION"));
    }

    private Point beforeTrilateration(Beacon beacon, double signal){
//                dopisanie do historii do beacona
//                wywolanie funkcji csvhandler
//                zwraca punkt lub nulla
        return null;
    }

    private boolean containsBeaconById(long id) {
        for (Beacon beacon : this.beacons) {
            if (beacon.getId() == id) return true;
        }
        return false;
    }

    private Beacon getBeaconById(long id) {
        for (Beacon beacon : this.beacons) {
            if (beacon.getId() == id) return beacon;
        }
        return null;
    }

    private double returnRSSI(double rssi) {
        return DistanceRSSIConverter.convertDistance(rssi);
    }

    @Override
    public void onDestroy() {
//        this.isRunning=false;
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
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = new Bundle();
        bundle.putFloat("x",400);
        bundle.putFloat("y",400);
        Log.d(TAG, "onHandleIntent");
//        bundle.putSerializable("key", "data to send");
//        ResultReceiver receiver = intent.getParcelableExtra("key");
        ResultReceiver receiver = intent.getParcelableExtra("key");
        receiver.send(0, bundle);
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

}
