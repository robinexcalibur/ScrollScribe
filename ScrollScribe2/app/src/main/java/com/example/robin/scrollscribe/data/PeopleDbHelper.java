package com.example.robin.scrollscribe.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.robin.scrollscribe.data.RumorsContract.*;

import static com.example.robin.scrollscribe.selectstyle.userID;


public class PeopleDbHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "people.db";

    private static final int DATABASE_VERSION = 2;

    public PeopleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_PEOPLE_TABLE = "CREATE TABLE " + PeopleEntry.TABLE_NAME+ " (" +
                PeopleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PeopleEntry.COLUMN_PNAME + " TEXT NOT NULL, " +
                PeopleEntry.COLUMN_PAGE + " TEXT NOT NULL, " +
                PeopleEntry.COLUMN_PRANK + " TEXT NOT NULL, " +
                PeopleEntry.COLUMN_PRELATION + " TEXT NOT NULL, " +
                PeopleEntry.COLUMN_PSUMMERY + " TEXT NOT NULL, " +
                PeopleEntry.USERID + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_PEOPLE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PeopleEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
