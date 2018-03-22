package com.example.robin.scrollscribe;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.robin.scrollscribe.data.RumorsContract;
import com.example.robin.scrollscribe.data.RumorsDbHelper;

public class Rumors extends AppCompatActivity {


    private RumorsListAdapter mAdapter;
    private SQLiteDatabase mDb;
    public static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rumors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userID = selectstyle.userID;


        //An old attempt at this
//        //used to create new rumor
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Context context = Rumors.this;
//                Class startRumorsEdit = RumorsEdit.class;
//                Intent startRumorsEditIntent = new Intent(context, startRumorsEdit);
//                startRumorsEditIntent.putExtra("sentName", "Rumor Name");
//                startRumorsEditIntent.putExtra("sentSummery", "Text and other information about your rumor can be placed here.");
//
//                startActivity(startRumorsEditIntent);
//                Log.d("Debug", "Rumors button userID: " + userID);
//            }
//        });


        //set up the recycler view stuff:
        RecyclerView rumorRecyclerView;
        rumorRecyclerView = (RecyclerView) this.findViewById(R.id.rumorsListView);
        rumorRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        RumorsDbHelper dbHelper = new RumorsDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        //is used to restart the database.
        //TestUtil.insertFakeData(mDb);


        Cursor cursor = getAllRumors();
        mAdapter = new RumorsListAdapter(this, cursor);
        rumorRecyclerView.setAdapter(mAdapter);


        //---- this here is used for returning back from a created rumor -----
        //If NULL is returned for either of these values, nothing is done.
        //Note to self that this has to go AFTER I innitialize everything about the database
        Intent returningBack = getIntent();
        String newName;
        String newSummary;
        String entryID;

        if (returningBack.hasExtra("sentName") && returningBack.hasExtra("sentSummary")) {
            newName = returningBack.getStringExtra("sentName");
            newSummary = returningBack.getStringExtra("sentSummary");
            if (returningBack.hasExtra("entryID")) { //makes it update
                entryID = returningBack.getStringExtra("entryID");
                updateRumor(newName, newSummary, entryID);
            } else { //it's a new entry, make a new thing
                newRumor(newName, newSummary);
            }

            mAdapter.swapCursor(getAllRumors());
        }

        //---- rumor made or updated -----

        //---- used for deleting data -----
        String deleteInfo;
        if (returningBack.hasExtra("deleteInfo")) {
            deleteInfo = returningBack.getStringExtra("deleteInfo");
            mDb.delete(RumorsContract.RumorsEntry.TABLE_NAME, RumorsContract.RumorsEntry._ID + "=" + deleteInfo, null);
        }
        //---- data deleted ---

    }

    public void onClick(View v) {
        Context context = Rumors.this;

        switch (v.getId()) {
            case R.id.fab: //new entry!
                Class startRumorsEdit = RumorsEdit.class;
                Intent startRumorsEditIntent = new Intent(context, startRumorsEdit);
                startRumorsEditIntent.putExtra("sentName", "Rumor Name");
                startRumorsEditIntent.putExtra("sentSummery", "Text and other information about your rumor can be placed here.");

                startActivity(startRumorsEditIntent);
                break;
            case R.id.back: //goes back to the selectstyle activity
                Class startSelectStyle = selectstyle.class;
                Intent startSelectStyleIntent = new Intent(context, startSelectStyle);

                startActivity(startSelectStyleIntent);
                break;
        }
    }

    private void updateRumor(String newName, String newSummary, String Id) {
        try
        {
            mDb.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(RumorsContract.RumorsEntry.COLUMN_RNAME, newName);
            cv.put(RumorsContract.RumorsEntry.COLUMN_RSUMMERY, newSummary);
            Log.d("Debug", "updateRumor name, summary: " + newName + newSummary + Id);
            String entryFinder = "_id='" + Id + "'";
            mDb.update(RumorsContract.RumorsEntry.TABLE_NAME, cv, entryFinder, null);

            mDb.setTransactionSuccessful();
        }
        catch (SQLException e) {
            Log.d("Debug", "transaction caught");
        }
        finally
        {
            mDb.endTransaction();
            Log.d("Debug", "successful transaction!");
        }
    }


    private void newRumor(String newName, String newSummary) {
        try
        {
            mDb.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(RumorsContract.RumorsEntry.COLUMN_RNAME, newName);
            cv.put(RumorsContract.RumorsEntry.COLUMN_RSUMMERY, newSummary);
            cv.put(RumorsContract.RumorsEntry.USERID, userID);
            mDb.insertOrThrow(RumorsContract.RumorsEntry.TABLE_NAME, null, cv);
            mDb.setTransactionSuccessful();
        }
        catch (SQLException e) {
            Log.d("Debug", "transaction caught");
        }
        finally
        {
            mDb.endTransaction();
            Log.d("Debug", "successful transaction!");
        }
    }


    private Cursor getAllRumors() {
        Log.d("Debug", "Rumors getallgests userID: " + userID);
        return mDb.query(
                RumorsContract.RumorsEntry.TABLE_NAME,
                null,
                "storedUserID='" + userID + "'",
                null,
                null,
                null,
                RumorsContract.RumorsEntry.COLUMN_RNAME,
                null
        );
        //note to self on the above: the column is actually called "storedUserID", not USERID as the variable is used.
    }

}
