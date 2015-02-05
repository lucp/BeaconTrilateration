package com.agh.eis.mralucp.beacontrilateration;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.agh.eis.mralucp.beacontrilateration.handlers.DistanceRSSIConverter;
import com.agh.eis.mralucp.beacontrilateration.model.Beacon;
import com.agh.eis.mralucp.beacontrilateration.model.Point;

public class EvaluationService extends Service {

    private String TAG = "EvaluationService";
    private final IBinder myBinder = new MyLocalBinder();
    private long date;
    private BroadcastReceiver mDataReceiver;
    private static boolean isRunning = false;

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
//        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        this.mDataReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                double signal  = returnRSSI(intent.getDoubleExtra("RSSI", 0.0));
//                Object beaconUID = intent.getSerializableExtra("UUID");
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
    private double returnRSSI(double rssi) {
        return DistanceRSSIConverter.convertDistance(rssi);
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
}
