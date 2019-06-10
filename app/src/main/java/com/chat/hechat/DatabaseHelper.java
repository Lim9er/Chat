package com.chat.hechat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_DATABASE = "create table contact ("
            +"id integer primary key autoincrement,"
            +"nickname text,"
            +"info text,"
            +"comment text)";

    private Context mContext;

    public DatabaseHelper (Context context, String name, SQLiteDatabase.CursorFactory cursorFactory,int version){
        super(context,name,cursorFactory,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_DATABASE);
        Toast.makeText(mContext,"Create Successfully",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){}
}