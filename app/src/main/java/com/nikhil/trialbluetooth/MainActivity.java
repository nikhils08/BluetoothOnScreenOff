package com.nikhil.trialbluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    BluetoothChecker bluetoothCheck;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothCheck);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bluetoothCheck = new BluetoothChecker();

        IntentFilter screenFilter = new IntentFilter();
        screenFilter.addAction(Intent.ACTION_SCREEN_OFF);
        screenFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(bluetoothCheck, screenFilter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public class BluetoothChecker extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if(intent.getAction().equals(intent.ACTION_SCREEN_OFF)){
                if(!mBluetoothAdapter.isEnabled()){
                    mBluetoothAdapter.enable();
                }
            } else if(intent.getAction().equals(intent.ACTION_SCREEN_ON)){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(300000);
                            if(mBluetoothAdapter.isEnabled()){
                                mBluetoothAdapter.disable();
                            }
                        } catch (InterruptedException ie){
                           ie.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }

}

