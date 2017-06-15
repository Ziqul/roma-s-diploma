package com.maximilian.qr2life;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.os.Bundle;
import android.os.StatFs;
import android.util.Log;

/**
 * Created by Maximilian on 05.06.2017.
 */

public class DisplayActivity extends Activity {
    private String qrData;
    private String ip;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        qrData = getIntent().getExtras().getString("qrData");

        if (qrData == null) {
            Log.i("savedInstanceState", "awd");
            Intent i = new Intent(this, DetectorActivity.class);
            startActivity(i);
        } else {
            Log.i("DISPLAY", qrData);
        }

        setContentView(R.layout.display_activity);

        SharedPreferences preferences = getSharedPreferences("global", MODE_PRIVATE);

        ip = preferences.getString("ip", null);
        password = preferences.getString("password", null);

        if (ip == null || password == null) {
            Intent i = new Intent(this, OptionsActivity.class);
            startActivity(i);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
