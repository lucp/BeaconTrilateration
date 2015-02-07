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

public class EvaluationService extends Service{//IntentService {

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
//        super("EvaluationService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        this.isRunning = true;
        return Service.START_NOT_STICKY;
//        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
//        this.isRunning = true;

        this.beacons = new LinkedList<Beacon>();
        this.loadConfigurationHome(this.beacons);

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

//    @Override
//    protected void onHandleIntent(Intent intent) {
//        Bundle bundle = new Bundle();
//        bundle.putFloat("x",400);
//        bundle.putFloat("y",400);
//        Log.d(TAG, "onHandleIntent");
//        this.mDataReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent){
//                double rssi = returnRSSI(intent.getDoubleExtra("RSSI", 0.0));
////                Object rssi = intent.getParcelableExtra("RSSI");
//                long id = 0; //TODO return id value from intent broadcast
////                Object beaconUID = intent.getSerializableExtra("UUID");
//                Beacon beacon = getBeaconById(id);
//                if (beacon != null) {
//                    beacon.setCurrentSignalAndAddToHistory(new CSVEntry(System.currentTimeMillis(), (int)rssi));
//                }
//                if (BeaconHandler.getActiveBeaconsNumber(beacons) >= 3) {
//                    System.out.println(PathFinder.findPoint(beacons));
//                }
////                Beacon beacon = findBeacon(beaconUID);
////                if(beacon!=null){
////                  beforeTrilateration(beacon, signal);
////
////                }
//                Log.d(TAG, "onReceive");
//                Toast.makeText(context, "broadcast ", Toast.LENGTH_LONG);//+signal+" "+obj, Toast.LENGTH_LONG);
//            }
//        };
//        Log.d(TAG, "register");
//        this.registerReceiver(this.mDataReceiver, new IntentFilter("com.aware.plugin.beacons.SCAN_RESULT_ACTION"));
//
////        bundle.putSerializable("key", "data to send");
////        ResultReceiver receiver = intent.getParcelableExtra("key");
//        ResultReceiver receiver = intent.getParcelableExtra("key");
//        receiver.send(0, bundle);
//    }

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

    public void loadConfigurationHome(LinkedList<Beacon> target) {
        target.add(new Beacon(51800, 62059, 0.75, 4.6)); //5Zg6
        target.add(new Beacon(2068, 37705, 0.0, 0.0)); //QBtX
        target.add(new Beacon(14925, 50618, 2.55, 3.1)); //1RJB
    }

}
