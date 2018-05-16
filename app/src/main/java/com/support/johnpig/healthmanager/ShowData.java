package com.support.johnpig.healthmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.support.johnpig.healthmanager.URLUtils.MY_HTTP_URL;

public class ShowData extends AppCompatActivity {

    private UserData userData;
    private TextView name;
    private TextView Sex;
    private TextView Temperature;
    private TextView Weight;
    private TextView HeartRate;
    private TextView HighPressure;
    private TextView LowPressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        Toolbar toolbar = findViewById(R.id.toolbar_showData);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.name);
        Sex = findViewById(R.id.sex);
        Temperature = findViewById(R.id.temperature);
        Weight = findViewById(R.id.weight);
        HeartRate = findViewById(R.id.heartRate);
        HighPressure = findViewById(R.id.HighPressure);
        LowPressure = findViewById(R.id.lowPressure);

        Intent intent = getIntent();
        updateData(intent);

        findViewById(R.id.uploadData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData(userData);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateData(Intent intent) {
        String userDataJson = intent.getStringExtra("userData");
        userData = new Gson().fromJson(userDataJson, UserData.class);

        name.setText(userData.account);
        Sex.setText(userData.sex);

        Weight.setText(String.valueOf(userData.weight));
        Temperature.setText(String.valueOf(userData.temperature));
        HeartRate.setText(String.valueOf(userData.heart_rate));
        HighPressure.setText(String.valueOf(userData.high_pressure));
        LowPressure.setText(String.valueOf(userData.low_pressure));
    }

    private void uploadData(UserData userData) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("name", userData.account)
                .add("sex", userData.sex)
                .add("temperature", String.valueOf(userData.temperature))
                .add("weight", String.valueOf(userData.weight))
                .add("heart_rate", String.valueOf(userData.heart_rate))
                .add("high_pressure", String.valueOf(userData.high_pressure))
                .add("low_pressure", String.valueOf(userData.low_pressure))
                .build();

        String url = URLUtils.MY_HTTP_URL;
        if (HttpUrl.parse(url) != null) {
            Request request = new Request.Builder().post(formBody).url(url).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e("error:", e.getMessage());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();
                        final String result = responseBody.string();
                        Log.e("MainActivity", "onResponse: " + result);
                        ShowData.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (result.equals("{\"result\": \"ok\"}")) {
                                    Toast.makeText(ShowData.this,
                                            "上传成功", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ShowData.this,
                                            "上传失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else if (!response.isSuccessful()) {
                        ShowData.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ShowData.this,
                                        "上传失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        } else {
            ShowData.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ShowData.this,
                            "URL无效", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
