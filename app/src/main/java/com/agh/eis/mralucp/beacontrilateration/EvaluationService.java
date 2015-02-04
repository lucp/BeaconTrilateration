package com.agh.eis.mralucp.beacontrilateration;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.agh.eis.mralucp.beacontrilateration.handlers.DistanceRSSIConverter;
import com.agh.eis.mralucp.beacontrilateration.model.Beacon;
import com.agh.eis.mralucp.beacontrilateration.model.Point;

public class EvaluationService extends Service {

    private final IBinder myBinder = new MyLocalBinder();

    private BroadcastReceiver mDataReceiver;

    public class MyLocalBinder extends Binder {
        EvaluationService getService() {
            return EvaluationService.this;
        }
    }
    public EvaluationService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
//        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this.getApplicationContext(), "onCreate Service ", Toast.LENGTH_LONG);//+signal+" "+obj, Toast.LENGTH_LONG);

        this.mDataReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
//                int signal  = returnRSSI(intent.getIntExtra("RSSI", 0));
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
    private double returnRSSI(int rssi) {
        return DistanceRSSIConverter.convertDistance(rssi);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mDataReceiver);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {

        return super.onUnbind(intent);
    }
}
