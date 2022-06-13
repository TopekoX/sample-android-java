package com.topekox.demoservice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!foregroundServiceIsRunning()) {
            Intent intentService = new Intent(this, MyForegroundService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && intentService == null) {
                startForegroundService(intentService);
            } else {
                MainActivity.this.startService(intentService);
            }
        }
    }

    private boolean foregroundServiceIsRunning() {
        ActivityManager activityManager =
                (ActivityManager) getSystemService(MainActivity.this.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service :
                activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (MyForegroundService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}