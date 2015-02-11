package com.agh.eis.mralucp.beacontrilateration;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/***
 * Main activity
 */
public class BeaconTrilaterationActivity extends ActionBarActivity {

    private String TAG = "BeaconTrilaterationActivity";
    private Button showPathButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_trilateration);
        Log.d(TAG,"onCreate");
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_beacon_trilateration, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
//        unbindService(myConnection);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onShowPathButtonClick(View view){
    }

    public void onURHereClick(View view){
        Intent intent = new Intent(this, URHereActivity.class);
        startActivity(intent);
    }

}
