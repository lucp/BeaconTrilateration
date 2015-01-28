package com.agh.eis.mralucp.beacontrilateration;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class EvaluationService extends Service {

    private BroadcastReceiver mDataReceiver;

    public EvaluationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mDataReceiver = new DataReceiver();
        this.registerReceiver(this.mDataReceiver, new IntentFilter("com.aware.plugin.beacons.SCAN_RESULT_ACTION"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }




}
