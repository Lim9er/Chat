package com.chat.hechat.login;


import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chat.hechat.R;
import com.chat.hechat.chat.ChatActivity;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private DBOpenHelper mDBOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText mEtLoginactivityUsername = findViewById(R.id.et_username);
        final EditText mEtLoginactivityPassword = findViewById(R.id.et_loginactivity_password);

        TextView tv_loginactivity_register=findViewById(R.id.tv_loginactivity_register);
        tv_loginactivity_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
//                break;
            }
        });

        Button bt_loginactivity_login=(Button)findViewById(R.id.bt_loginactivity_login);
        bt_loginactivity_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEtLoginactivityUsername.getText().toString().trim();
                String password = mEtLoginactivityPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    ArrayList<User> data = mDBOpenHelper.getAllData();
                    boolean match = false;
                    for(int i=0;i<data.size();i++) {
                        User user = data.get(i);
                        if (name.equals(user.getName()) && password.equals(user.getPassword())){
                            match = true;
                            break;
                        }else{
                            match = false;
                        }
                    }
                    if (match) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
                        startActivity(intent);
                        finish();//销毁此Activity
                    }else {
                        Toast.makeText(LoginActivity.this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }
//                break;
            }
        });

        mDBOpenHelper = new DBOpenHelper(this);
    }
}
