package com.support.johnpig.healthmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");

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
                if (!isChecked) {
                    // TODO: 2018/5/10
                    Toast.makeText(MainActivity.this, "开启服务器", Toast.LENGTH_SHORT).show();
                } else {
                    // TODO: 2018/5/10
                    Toast.makeText(MainActivity.this, "关闭服务器", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
