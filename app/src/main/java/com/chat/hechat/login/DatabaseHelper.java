package com.chat.hechat.login;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Administrator on 2019/6/3.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    public static final String CREATE_DATABASE = "create table contact ("
            +"id integer primary key autoincrement,"
            +"nickname text,"
            +"info text,"
            +"comment text)";
    public DatabaseHelper (Context context, String name, SQLiteDatabase.CursorFactory cursorFactory,int version){
        super(context,name,cursorFactory,version);
        mContext = context;
    }
    static String name="user.db";
    static int dbVersion=1;
    public DatabaseHelper(Context context) {
        super(context, name, null, dbVersion);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql="create table user(id integer primary key autoincrement,username varchar(20),password varchar(20),age integer,sex varchar(2))";
        db.execSQL(sql);
        db.execSQL(CREATE_DATABASE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}