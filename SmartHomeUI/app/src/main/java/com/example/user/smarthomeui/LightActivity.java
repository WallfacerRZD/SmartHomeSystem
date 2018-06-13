package com.example.user.smarthomeui;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import com.example.user.smarthomeui.utils.HttpUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LightActivity extends AppCompatActivity {
    private Switch mswitch = null;

    private String hostIP = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 允许在主线程发送http请求
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_layout);

        mswitch = (Switch) findViewById(R.id.light_switch);
        mswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MyApplication app = (MyApplication) getApplication();
                hostIP = app.getIpAddr();
                if (hostIP != null) {
                    String response = null;
                    try {
                        if (b) {
                            response = HttpUtil.httpPost("http://" + hostIP + ":8080/Connect", "appliance=light&command=on");
                        } else {
                            response = HttpUtil.httpPost("http://" + hostIP + ":8080/Connect", "appliance=light&command=off");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        showResponse(jsonObject.get("state").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LightActivity.this, "请设置服务器IP地址", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }

        });
    }

    private void showResponse(final String state) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if ("success".equals(state)) {
                    Toast.makeText(LightActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                } else if ("failed".equals(state)) {
                    Toast.makeText(LightActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LightActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
