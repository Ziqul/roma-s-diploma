package com.maximilian.qr2life;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Maximilian on 05.06.2017.
 */

public class OptionsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_activity);

        Button submit = (Button) findViewById(R.id.option_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ip = (EditText) findViewById(R.id.option_ip);
                EditText password = (EditText) findViewById(R.id.option_password);

                SharedPreferences preferences = getSharedPreferences("global", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("ip", ip.getText().toString());
                editor.putString("password", password.getText().toString());

                editor.apply();

                Intent i = new Intent(OptionsActivity.this, DetectorActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
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
