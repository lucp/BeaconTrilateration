package com.agh.eis.mralucp.beacontrilateration;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.agh.eis.mralucp.beacontrilateration.handlers.BeaconHandler;
import com.agh.eis.mralucp.beacontrilateration.handlers.PathFinder;
import com.agh.eis.mralucp.beacontrilateration.model.Beacon;
import com.agh.eis.mralucp.beacontrilateration.model.Point;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * Drawing activity
 */
public class URHereActivity extends Activity {

    private String TAG = "URHereActivity";
    DrawView drawView;
    Intent intent;
    EvaluationService mService;
    boolean isBound = false;
    boolean first = true;
    URHereThread urHereThread;


    Handler mHandler =  new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message msg) {
            if(first) {
                first = false;
                if (mService != null) {
                    drawView.addBeacons(mService.getBeacons());
                }
            }
            Bundle data = msg.getData();
            float x = data.getFloat("Xcoordinate");
            float y = data.getFloat("Ycoordinate");
            drawView.addPoint(x,y);
            }
    };

    private ServiceConnection myConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            EvaluationService.MyLocalBinder binder = (EvaluationService.MyLocalBinder) service;
            mService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
            isBound = false;
        }

    };

    void doBindService(boolean start) {
        Intent intent = new Intent(this, EvaluationService.class);
        if (start){
            startService(intent);
        }
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
        isBound = true;
    }

    void doUnbindService(boolean stop) {
        Log.d(TAG, "unbinding");
        if (isBound) {
            unbindService(myConnection);
            if (stop)
                Log.d(TAG, "URHereActivity: stopSerice");
                stopService(new Intent(this, EvaluationService.class));
            isBound = false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (mService.isRunning()) {
            Log.d(TAG,"running binding");
            doBindService(false);
        }
        else {
            Log.d(TAG,"starting binding");
            doBindService(true);
        }

        this.urHereThread = new URHereThread(this);
        Thread thread = new Thread(this.urHereThread);
        thread.start();

        drawView = new DrawView(this);
        setContentView(drawView);
        drawView.requestFocus();

    }

    @Override
    protected void onStop() {
        super.onStop();
//        unbindService(myConnection);
        doUnbindService(true);
        this.urHereThread.finish();
    }


}


