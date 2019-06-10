package com.chat.hechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;


import com.chat.hechat.login.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private Contact contact;
    private ListView listView;
    private List<Contact> contactList = new ArrayList<Contact>();
    private TextView textView;
    private Button temp_button;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        temp_button = (Button)findViewById(R.id.temp_create);
        temp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this,CreateContactActivity.class);
                startActivity(intent);
            }
        });
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        dbHelper = new DatabaseHelper(this,"contacts.db",null,2);
        //initContacts();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("contact",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
                String info = cursor.getString(cursor.getColumnIndex("info"));
                String comment =  cursor.getString(cursor.getColumnIndex("comment"));
                Contact contact = new Contact(id,nickname,comment,info,R.drawable.ic_person_black_48dp);
                contactList.add(contact);;
            }while(cursor.moveToNext());
        }

        ContactAdapter adapter = new ContactAdapter(ContactActivity.this,R.layout.mixed_list,contactList);
        listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = contactList.get(position);
                //Sending clicked item to next activity
                SharedPreferences.Editor editor = getSharedPreferences("contact",MODE_PRIVATE).edit();
                editor.putInt("id",contact.getId());
                editor.putString("nickname",contact.getNickname());
                editor.putString("info",contact.getInfo());
                editor.putString("comment",contact.getComment());
                editor.commit();
                //Send Complete
                Intent intent = new Intent(ContactActivity.this,ContactDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("test","back from last app");
    }

}
