package com.example.user.smarthomeui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConnectActivity extends AppCompatActivity {
    private Button button = null;

    private EditText editText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_layout);
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipAddr = editText.getText().toString();
                MyApplication app = (MyApplication) getApplication();
                app.setIpAddr(ipAddr);
                Intent backToMain = new Intent(ConnectActivity.this, MainActivity.class);
                startActivity(backToMain);
            }
        });
    }
}
