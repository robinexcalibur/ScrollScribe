package com.example.robin.scrollscribe.data;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.robin.scrollscribe.selectstyle;

import java.util.ArrayList;
import java.util.List;


public class TestUtil {
    public static void insertFakeData(SQLiteDatabase db){
        String userID = selectstyle.userID;
        if(db == null){
            return;
        }
        //some fake test code
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(RumorsContract.RumorsEntry.COLUMN_RNAME, "One");
        cv.put(RumorsContract.RumorsEntry.COLUMN_RSUMMERY, "Bring to the table win-win survival strategies to ensure proactive domination.");
        cv.put(RumorsContract.RumorsEntry.USERID, userID);
        list.add(cv);

        cv = new ContentValues();
        cv.put(RumorsContract.RumorsEntry.COLUMN_RNAME, "Two");
        cv.put(RumorsContract.RumorsEntry.COLUMN_RSUMMERY, "At the end of the day, going forward, a new normal " +
                "that has evolved from generation X is on");
        cv.put(RumorsContract.RumorsEntry.USERID, userID);
        list.add(cv);

        cv = new ContentValues();
        cv.put(RumorsContract.RumorsEntry.COLUMN_RNAME, "Three");
        cv.put(RumorsContract.RumorsEntry.COLUMN_RSUMMERY, "the runway heading towards a streamlined cloud solution. " +
                "User generated content in real-time will have multiple touchpoints for offshoring.");
        cv.put(RumorsContract.RumorsEntry.USERID, userID);
        list.add(cv);

        cv = new ContentValues();
        cv.put(RumorsContract.RumorsEntry.COLUMN_RNAME, "Four");
        cv.put(RumorsContract.RumorsEntry.COLUMN_RSUMMERY, "Capitalize on low hanging fruit to identify a ballpark value " +
                "added activity to beta test. Override the digital divide with additional clickthroughs from DevOps. " +
                "Nanotechnology immersion along the information highway will close the loop on focusing solely on the bottom line.");
        cv.put(RumorsContract.RumorsEntry.USERID, userID);
        list.add(cv);

        cv = new ContentValues();
        cv.put(RumorsContract.RumorsEntry.COLUMN_RNAME, "Five");
        cv.put(RumorsContract.RumorsEntry.COLUMN_RSUMMERY, "At the end of the day, going forward, a new normal " +
                "that has evolved from generation X is on");
        cv.put(RumorsContract.RumorsEntry.USERID, userID);
        list.add(cv);
        try
        {
            db.beginTransaction();
            db.delete (RumorsContract.RumorsEntry.TABLE_NAME,null,null);
            Log.d("Debug", "Testutil userID: " + userID);
            for(ContentValues c:list){
                db.insert(RumorsContract.RumorsEntry.TABLE_NAME, null, c);
                Log.d("Debug", "inserted!");
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            Log.d("Debug", "userID caught");
        }
        finally
        {
            db.endTransaction();
            Log.d("Debug", "successful transaction!");
        }

    }
}
