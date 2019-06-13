package com.chat.hechat.contact;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.chat.hechat.R;
import com.chat.hechat.login.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ContactFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private Contact contact;
    private ListView listView;
    private List<Contact> contactList = new ArrayList<Contact>();
    private TextView textView;
    private Button temp_button;
    private Button dynamic_button;
    private View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.activity_list,container,false);

        dynamic_button = (Button)v.findViewById(R.id.dynamic);
        dynamic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        });
        temp_button = (Button)v.findViewById(R.id.temp_create);
        temp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CreateContactActivity.class);
                startActivity(intent);
            }
        });
        dbHelper = new DatabaseHelper(this.getContext());
        //initContacts();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("contact",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
                String info = cursor.getString(cursor.getColumnIndex("info"));
                String comment =  cursor.getString(cursor.getColumnIndex("comment"));
                Contact contact = new Contact(id,nickname,comment,info,R.drawable.ic_account_circle_black_24dp);
                contactList.add(contact);;
            }while(cursor.moveToNext());
        }

        ContactAdapter adapter = new ContactAdapter(ContactFragment.this.getContext(),R.layout.mixed_list,contactList);
        listView = (ListView)v.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = contactList.get(position);
                //Sending clicked item to next activity
                SharedPreferences.Editor editor = getContext().getSharedPreferences("contact",MODE_PRIVATE).edit();
                editor.putInt("id",contact.getId());
                editor.putString("nickname",contact.getNickname());
                editor.putString("info",contact.getInfo());
                editor.putString("comment",contact.getComment());
                editor.commit();
                //Send Complete
                Intent intent = new Intent(getActivity(),ContactDetailActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = contactList.get(position);
                final String delete_id = String.valueOf(contact.getId());

                ButtonDialogFragment buttonDialogFragment = new ButtonDialogFragment();
                buttonDialogFragment.show("警告", "真的要删除该用户吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete("contact","id=?",new String[] {delete_id});
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "取消 " + which, Toast.LENGTH_SHORT).show();
                    }
                }, ((AppCompatActivity) getActivity()).getSupportFragmentManager());
                return true;
            }
        });
        return v;
    }


    @Override
    public void onResume(){
        super.onResume();
        Log.d("Warning","The listview should be refreshed");
        listView = (ListView)v.findViewById(R.id.list_view);
        ContactAdapter adapter = new ContactAdapter(getActivity(),R.layout.mixed_list,contactList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.invalidateViews();
    }

}
