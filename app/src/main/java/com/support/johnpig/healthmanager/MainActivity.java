package com.support.johnpig.healthmanager;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends AppCompatActivity implements CallBack {

    private TextView showSensorStatus;
    private String account;
    private MyService.serviceBinder serviceBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceBinder = (MyService.serviceBinder) service;
            serviceBinder.openServer(MainActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBinder.closeServer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = getIntent();
        account = intent.getStringExtra("account");

        showSensorStatus = findViewById(R.id.sensor_status);
        TextView login_user = findViewById(R.id.login_user);
        login_user.setText(account);

        findViewById(R.id.receivedData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DataList.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

        Switch mSwitch = findViewById(R.id.openServer);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // TODO: 2018/5/10
                    Intent openServer = new Intent(MainActivity.this, MyService.class);
                    bindService(openServer, connection, BIND_AUTO_CREATE);
                    Toast.makeText(MainActivity.this, "开启服务器", Toast.LENGTH_SHORT).show();
                } else {
                    // TODO: 2018/5/10
                    unbindService(connection);
                    stopService(new Intent(MainActivity.this, MyService.class));
                    Toast.makeText(MainActivity.this, "关闭服务器", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    @Override
    public void setSensorStatus(final boolean status) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (status) {
                    showSensorStatus.setText("已连接");
                }else {
                    showSensorStatus.setText("未连接");
                }
            }
        });
    }

    @Override
    public void onDataReceived() {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
               Toast.makeText(MainActivity.this,"已收到数据",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
