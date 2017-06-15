package com.maximilian.romasdiploma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Maximilian on 24.05.2017.
 */

public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        Button submit = (Button)findViewById(R.id.submitIp);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText ip = (EditText)findViewById(R.id.editIp);
                Intent intent = new Intent(Settings.this, MainActivity.class);
                intent.putExtra("ip", ip.getText().toString());
                startActivity(intent);
            }
        });

    }

}
