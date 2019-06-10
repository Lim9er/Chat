package com.chat.hechat;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chat.hechat.login.DatabaseHelper;

public class CreateContactActivity extends AppCompatActivity {

    private EditText text_id;
    private EditText text_nickname;
    private EditText text_info;
    private EditText text_comment;
    private Button add_button;

    private DatabaseHelper dbHelper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        dbHelper = new DatabaseHelper(this,"contacts.db",null,2);
        text_id = (EditText)findViewById(R.id.real_name);
        text_nickname = (EditText)findViewById(R.id.real_nickname);
        text_info = (EditText)findViewById(R.id.real_info);
        text_comment = (EditText)findViewById(R.id.real_comment);


        add_button = (Button)findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i = text_id.getText().toString();
                String nickname = text_nickname.getText().toString();
                String info = text_info.getText().toString();
                String comment = text_comment.getText().toString();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                final int id = Integer.parseInt(i);
                values.put("id",id);
                values.put("nickname",nickname);
                values.put("info",info);
                values.put("comment",comment);
                db.insert("contact",null,values);
                values.clear();
                finish();
            }
        });
    }
}
