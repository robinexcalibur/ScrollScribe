package com.example.robin.scrollscribe.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.robin.scrollscribe.data.RumorsContract.*;

import static com.example.robin.scrollscribe.selectstyle.userID;


public class RumorsDbHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "rumors.db";

    private static final int DATABASE_VERSION = 7; //It took a few tries to get it right.

    public RumorsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_RUMOR_TABLE = "CREATE TABLE " + RumorsEntry.TABLE_NAME + " (" +
                RumorsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RumorsEntry.COLUMN_RNAME + " TEXT NOT NULL, " +
                RumorsEntry.COLUMN_RSUMMERY + " TEXT NOT NULL, " +
                RumorsEntry.USERID + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_RUMOR_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RumorsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
