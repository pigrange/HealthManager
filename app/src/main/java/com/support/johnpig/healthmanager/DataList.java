package com.support.johnpig.healthmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class DataList extends AppCompatActivity {

    private List<UserData> dataList;
    private UserDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);
        EventBus.getDefault().register(this);

        Toolbar toolbar = findViewById(R.id.toolbar_data_list);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String account = intent.getStringExtra("account");

        dataList = SQLite.select().from(UserData.class)
                .where(UserData_Table.account.eq(account)).queryList();

        RecyclerView recyclerView = findViewById(R.id.dataList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = new UserDataAdapter(dataList);
        adapter.setOnItemClickListener(new UserDataAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                Intent intent1 = new Intent(DataList.this, ShowData.class);
                intent1.putExtra("userData", new Gson().toJson(dataList.get(pos)));
                startActivity(intent1);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        UserData userData = messageEvent.getUserData();
        Log.e("DataList", "Get userData");
        int oldLast = dataList.size();
        adapter.notifyItemInserted(oldLast);
        dataList.add(userData);
    }
}


