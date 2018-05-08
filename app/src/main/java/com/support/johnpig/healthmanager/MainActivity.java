package com.support.johnpig.healthmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private User user;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        findViewById(R.id.showData).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showData:
                //todo
                Intent intent = new Intent(MainActivity.this, DataList.class);
                intent.putExtra("account", account);
                startActivity(intent);
                break;
            case R.id.openSever:

                //todo
                break;

            default:
                break;
        }
    }


}
