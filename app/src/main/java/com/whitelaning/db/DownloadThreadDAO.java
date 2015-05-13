package com.whitelaning.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.whitelaning.bean.DownloadThreadInfoBean;
import com.whitelaning.interfaces.DownloadThreadDAOInterface;

import java.util.ArrayList;
import java.util.List;

public class DownloadThreadDAO implements DownloadThreadDAOInterface {

    private DBHelper dbHelper;

    public DownloadThreadDAO(Context context) {
        dbHelper = DBHelper.getInstance(context, DBHelper.DB_NAME, null, DBHelper.VERSION);
    }

    @Override
    public synchronized void insertThread(DownloadThreadInfoBean threadInfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("insert into " + DBHelper.TABLE_THREAD_INFO_NAME + " (thread_id,url,start,end,finished) values (?,?,?,?,?)",
                new Object[] {threadInfo.getId(), threadInfo.getUrl(), threadInfo.getStart(), threadInfo.getEnd(), threadInfo.getFinished()});
        db.close();
    }


    @Override
    public synchronized void deleteThread(String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from " + DBHelper.TABLE_THREAD_INFO_NAME + " where url = ?",
                new Object[] {url});
        db.close();
    }

    @Override
    public synchronized void updateThread(String url, int thread_id, int finished) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("update " + DBHelper.TABLE_THREAD_INFO_NAME + " set finished = ? where url = ? and thread_id = ?",
                new Object[] {finished, url, thread_id});
        db.close();
    }

    @Override
    public synchronized List<DownloadThreadInfoBean> getThreads(String url) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List<DownloadThreadInfoBean> threadInfoList = new ArrayList();
        Cursor cursor = db.rawQuery("select * from " + DBHelper.TABLE_THREAD_INFO_NAME + " where url = ?", new String[] {url});

        while (cursor.moveToNext()) {
            DownloadThreadInfoBean threadInfoBean = new DownloadThreadInfoBean();
            threadInfoBean.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadInfoBean.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadInfoBean.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            threadInfoBean.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            threadInfoBean.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            threadInfoList.add(threadInfoBean);
        }

        cursor.close();
        db.close();
        return threadInfoList;
    }

    @Override
    public synchronized boolean IsExistsThread(String url, int thread_id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + DBHelper.TABLE_THREAD_INFO_NAME + " where url = ? and thread_id = ?", new String[] {url, thread_id + ""});

        boolean exists = cursor.moveToNext();

        cursor.close();
        db.close();

        return exists;
    }
}
