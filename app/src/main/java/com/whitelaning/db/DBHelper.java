package com.whitelaning.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static DBHelper mDBHelper;

    public static final String DB_NAME = "download.db";
    public static final int VERSION = 1;

    public static final String TABLE_THREAD_INFO_NAME = "thread_info";

    private static final String SQL_CREATE_TABLE_THREAD_INFO = "create table " + TABLE_THREAD_INFO_NAME + "(_id integer primary key autoincrement," +
            "thread_id integer, url text, start integer, end integer, finished integer)";
    private static final String SQL_DROP_TABLE_THREAD_INFO = "drop table if exists " + TABLE_THREAD_INFO_NAME;

    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DBHelper getInstance(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        if (mDBHelper == null) {
            mDBHelper = new DBHelper(context, DBHelper.DB_NAME, null, DBHelper.VERSION);
        }
        return mDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_THREAD_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_THREAD_INFO);
        db.execSQL(SQL_CREATE_TABLE_THREAD_INFO);
    }
}
