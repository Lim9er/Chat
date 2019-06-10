package com.chat.hechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chat.hechat.chat.ChatActivity;
import com.chat.hechat.login.DatabaseHelper;
import com.chat.hechat.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this,"contacts.db",null,1);
        Button createDatabase = (Button)findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
                Intent intent = new Intent(MainActivity.this,ContactActivity.class);
                startActivity(intent);
            }
        });

        Button contacts = (Button)findViewById(R.id.view_contact_list);
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (MainActivity.this,ContactActivity.class);
                startActivity(intent1);
            }
        });

        findViewById(R.id.button_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (MainActivity.this, ChatActivity.class);
                startActivity(intent1);
            }
        });
        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (MainActivity.this, LoginActivity.class);
                startActivity(intent1);
            }
        });
    }
}
