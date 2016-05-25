package com.nivedhithav.androiddatastorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by nivedhithav on 3/10/16.
 */
public class DataController {

    public static final String NAME="NAME";
    public static final String AUTHOR="AUTHOR";
    public static final String DESC="DESC";
    public static final String TABLE_NAME="Book_Table";
    public static final String DATABASE_NAME="Nivedhitha_sql.db";
    public static final int DATABASE_VERSION=7;
    public static final String TABLE_CREATE="create table "+TABLE_NAME+ "(" +NAME+" text not null, "
            +AUTHOR+" text not null, "+DESC+" text not null);";

    DataBaseHelper dbHelper;
    Context context;
    SQLiteDatabase db;

    public DataController(Context context)
    {
        this.context=context;
        dbHelper=new DataBaseHelper(context);
    }

    public DataController open()
    {
        db=dbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        dbHelper.close();
    }

    public long insert(String name, String author, String desc)
    {
        ContentValues content=new ContentValues();
        content.put(NAME, name);
        content.put(AUTHOR,author);
        content.put(DESC,desc);
        Log.d("Debug: ", String.valueOf(db.getVersion()));
        return db.insertOrThrow(TABLE_NAME, null, content);
    }

    public Cursor retrieve()
    {
        return db.query(TABLE_NAME, new String[]{NAME}, null, null, null, null, null);
    }

    private static class DataBaseHelper extends SQLiteOpenHelper
    {

        public DataBaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            try
            {
                db.execSQL(TABLE_CREATE);
            }
            catch(SQLiteException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
            onCreate(db);
        }

    }
}
