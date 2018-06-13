package com.example.user.smarthomeui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CheckActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Appliances> appliancesList = new ArrayList<Appliances>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_layout);
        initAppliances(); // 初始化数据
        AppliancesAdapter adapter = new AppliancesAdapter(CheckActivity.this, R.layout.appliances_item, appliancesList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        TextView check_view = (TextView) findViewById(R.id.new_appliance_textview);
        check_view.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Appliances appliances = appliancesList.get(position);
                Toast.makeText(CheckActivity.this, appliances.getName() + "连接中......", Toast.LENGTH_SHORT).show();
                switch (appliances.getName()) {
                    case "台灯":
                        Intent light_intent = new Intent(CheckActivity.this, LightActivity.class);
                        startActivity(light_intent);
                        break;
                    case "空调":
                        Intent conditioner_intent = new Intent(CheckActivity.this, ConditionerActivity.class);
                        startActivity(conditioner_intent);
                        break;
                    case "风扇":
                        Intent warning_intent = new Intent(CheckActivity.this, WarningActivity.class);
                        startActivity(warning_intent);
                        break;
                    case "监控":
                        Intent monitor_intent = new Intent(CheckActivity.this, MonitorActivity.class);
                        startActivity(monitor_intent);
                        break;
                    case "麦克风":
                        Intent microphone_intent = new Intent(CheckActivity.this, MicrophoneActivity.class);
                        startActivity(microphone_intent);
                        break;
                }


            }
        });
    }

    private void initAppliances() {
        Appliances light = new Appliances("台灯");
        appliancesList.add(light);
        Appliances conditioner = new Appliances("空调");
        appliancesList.add(conditioner);
        Appliances fan = new Appliances("风扇");
        appliancesList.add(fan);
        Appliances monitor = new Appliances("监控");
        appliancesList.add(monitor);
        Appliances speaker = new Appliances("麦克风");
        appliancesList.add(speaker);
    }

    @Override
    public void onClick(View v) {
        Intent connect_intent = new Intent(CheckActivity.this, ConnectActivity.class);
        startActivity(connect_intent);
    }
}
