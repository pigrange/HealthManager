package com.support.johnpig.healthmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

public class RegisterActivity extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private EditText setAccount;
    private EditText setPassword;
    private EditText ensurePassword;

    private boolean isMale = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setAccount = findViewById(R.id.setAccount);
        setPassword = findViewById(R.id.setPassword);
        ensurePassword = findViewById(R.id.ensurePassword);

        RadioGroup radioGroup = findViewById(R.id.setSex);
        radioGroup.setOnCheckedChangeListener(this);
        findViewById(R.id.RegisterButton).setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar_register);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.setMale:
                isMale = true;
                break;
            case R.id.setFemale:
                isMale = false;
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        String account = setAccount.getText().toString();
        String password = setPassword.getText().toString();

        if (account.isEmpty()) {
            Toast.makeText(this, "请输入正确用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "请设置密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(ensurePassword.getText().toString())) {
            Toast.makeText(this, "输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        long count = SQLite.select()
                .from(User.class)
                .where(User_Table.account.eq(account))
                .count();

        if (count != 0) {
            Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show();
            return;
        }

        final String sex = isMale ? "男" : "女";
        createAccount(account, password, sex);
    }

    private void createAccount(String account, String password, String sex) {


        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setSex(sex);

        if (user.save()) {
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
