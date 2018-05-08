package com.support.johnpig.healthmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText accountText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountText = findViewById(R.id.account);
        passwordText = findViewById(R.id.password);

        findViewById(R.id.login_button).setOnClickListener(this);
        findViewById(R.id.RegisterButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                String inputAccount = accountText.getText().toString().trim();
                String inputPassword = passwordText.getText().toString().trim();

                if (inputAccount.isEmpty()) {
                    Toast.makeText(this, "请输入正确的用户名", Toast.LENGTH_SHORT).show();
                    return;
                }

                int count = (int) SQLite.select().from(User.class).
                        where(User_Table.account.eq(inputAccount),
                                User_Table.password.eq(inputPassword)).count();

                if (count > 0) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("account", inputAccount);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    passwordText.setText("");
                }

                break;
            case R.id.RegisterButton:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
