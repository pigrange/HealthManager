package com.support.johnpig.healthmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

public class DataList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);

        Intent intent = getIntent();
        String account = intent.getStringExtra("account");
        List<UserData> dataList = SQLite.select().from(UserData.class)
                .where(UserData_Table.account.eq(account)).queryList();

        RecyclerView recyclerView = findViewById(R.id.dataList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        UserDataAdapter adapter = new UserDataAdapter(dataList);
        recyclerView.setAdapter(adapter);
    }
}
