package com.agh.eis.mralucp.beacontrilateration;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;


public class URHereActivity extends Activity {

    private String TAG = "URHereActivity";
    DrawView drawView;
    Intent intent;
//    EvaluationService mService;
//    boolean isBound = false;

//    private ServiceConnection myConnection = new ServiceConnection() {
//
//        public void onServiceConnected(ComponentName className,
//                                       IBinder service) {
//            EvaluationService.MyLocalBinder binder = (EvaluationService.MyLocalBinder) service;
//            mService = binder.getService();
//            isBound = true;
//        }
//
//        public void onServiceDisconnected(ComponentName arg0) {
//            mService = null;
//            isBound = false;
//        }
//
//    };
//    void doBindService(boolean start) {
//        Intent intent = new Intent(this, EvaluationService.class);
//        if (start){
//            startService(intent);
//        }
//        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
//
//        isBound = true;
//    }
//    void doUnbindService(boolean stop) {
//        Log.d(TAG, "unbinding");
//        if (isBound) {
//            // Detach our existing connection.
//            unbindService(myConnection);
//            if (stop)
//                stopService(new Intent(this, EvaluationService.class));
//            isBound = false;
//        }
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set full screen view
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        if (mService.isRunning()) {
//            Log.d(TAG,"running binding");
//            doBindService(false);
//        }
//        else {
//            Log.d(TAG,"starting binding");
//            doBindService(true);
//        }

        intent = new Intent(this, EvaluationService.class);
        intent.putExtra("key", resultReceiver);

        Log.d(TAG,"przed start sevice");
        startService(intent);
        Log.d(TAG,"po start sevice");
        drawView = new DrawView(this);
        setContentView(drawView);
        drawView.requestFocus();
//        drawView.addPoint(400,400);
    }
    @Override
    protected void onStop() {
        super.onStop();
        stopService(intent);
//        unbindService(myConnection);
//        doUnbindService(true);
    }
    private final ResultReceiver resultReceiver = new ResultReceiver(
            new Handler()) {
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
//            progressBar.setVisibility(View.GONE);
            //do functionality
            Log.d(TAG, "onReceiveResult");
            float x = resultData.getFloat("x");
            float y = resultData.getFloat("y");

            drawView.addPoint(x,y);
        };
    };
}
