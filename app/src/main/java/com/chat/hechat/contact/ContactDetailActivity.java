package com.chat.hechat.contact;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chat.hechat.R;
import com.chat.hechat.chat.ChatFragment;
import com.chat.hechat.login.DatabaseHelper;

public class ContactDetailActivity extends AppCompatActivity {

    private EditText text_id;
    private EditText text_info;
    private EditText text_comment;
    private Button edit_button;
    private Button chat_button;
    private DatabaseHelper dbHelper;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        text_id = findViewById(R.id.real_name);
        text_info = findViewById(R.id.real_info);
        text_comment = findViewById(R.id.real_comment);
        dbHelper = new DatabaseHelper(this);
        setSupportActionBar(myToolbar);

        //Getting information from ContactFragment.java
        SharedPreferences pref = getSharedPreferences("contact",MODE_PRIVATE);
        int id = pref.getInt("id",0);
        String nickname = pref.getString("nickname","");
        String info = pref.getString("info","Empty");
        String comment = pref.getString("comment","");
        //Information got.
        setTitle(nickname);
        text_id.setText(String.valueOf(id));text_id.setEnabled(false);
        text_info.setText(info);text_info.setEnabled(false);
        text_comment.setText(comment);text_comment.setEnabled(false);

        boolean editing = false;
        edit_button = (Button)findViewById(R.id.edit_button);
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //text_info.setEnabled(true);
                //INFO and ID shouldn't be editable
                text_comment.setEnabled(true);
                edit_button.setText("保存");
                edit_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nickname = getTitle().toString();
                        String new_id = text_id.getText().toString();
                        String new_info = text_info.getText().toString();
                        String new_comment = text_comment.getText().toString();

                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        int new_id_int = Integer.parseInt(new_id);
                        values.put("id",new_id_int);
                        values.put("info",new_info);
                        values.put("comment",new_comment);
                        db.update("contact",values,"nickname=?",new String[] {nickname});
                        finish();
                    }
                });
            }
        });

        chat_button = (Button)findViewById(R.id.chat_button);
        chat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactDetailActivity.this, ChatFragment.class);
                startActivity(intent);
                finish();
            }
        });

    }


}
