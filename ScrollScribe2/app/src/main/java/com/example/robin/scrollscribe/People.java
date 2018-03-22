package com.example.robin.scrollscribe;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.robin.scrollscribe.data.PeopleDbHelper;
import com.example.robin.scrollscribe.data.RumorsContract;

public class People extends AppCompatActivity {

    private PeopleListAdapter mAdapter;
    private SQLiteDatabase mDb;
    public static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userID = selectstyle.userID;


        //set up the recycler view stuff:
        RecyclerView peopleRecyclerView;
        peopleRecyclerView = (RecyclerView) this.findViewById(R.id.peopleListView);
        peopleRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        PeopleDbHelper dbHelper = new PeopleDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        //is used to restart the database.
        //TestUtil.insertFakeData(mDb);


        Cursor cursor = getAllPeople();
        mAdapter = new PeopleListAdapter(this, cursor);
        peopleRecyclerView.setAdapter(mAdapter);


        //---- this here is used for returning back from a created person -----
        //If NULL is returned for either of these values, nothing is done.
        //Note to self that this has to go AFTER I innitialize everything about the database
        Intent returningBack = getIntent();
        String newName;
        String newSummary;
        String newAge;
        String newRank;
        String newRelation;
        String entryID;

        if (returningBack.hasExtra("sentName") && returningBack.hasExtra("sentSummary")) {
            newName = returningBack.getStringExtra("sentName");
            newSummary = returningBack.getStringExtra("sentSummary");
            newAge = returningBack.getStringExtra("sentAge");
            newRank = returningBack.getStringExtra("sentRank");
            newRelation = returningBack.getStringExtra("sentRelation");
            if (returningBack.hasExtra("entryID")) { //makes it update
                entryID = returningBack.getStringExtra("entryID");
                updatePeople(newName, newSummary, newAge, newRank, newRelation, entryID);
            } else { //it's a new entry, make a new thing
                newPeople(newName, newSummary, newAge, newRank, newRelation);
            }

            mAdapter.swapCursor(getAllPeople());
        }

        //---- person made or updated -----

        //---- used for deleting data -----
        String deleteInfo;
        if (returningBack.hasExtra("deleteInfo")) {
            deleteInfo = returningBack.getStringExtra("deleteInfo");
            mDb.delete(RumorsContract.PeopleEntry.TABLE_NAME, RumorsContract.PeopleEntry._ID + "=" + deleteInfo, null);
        }
        //---- data deleted ---

    }

    public void onClick(View v) {
        Context context = People.this;

        switch (v.getId()) {
            case R.id.fabPeople: //new entry!
                Class startPeopleEdit = PeopleEdit.class;
                Intent startPeopleEditIntent = new Intent(context, startPeopleEdit);
                startPeopleEditIntent.putExtra("sentName", "Person Name");
                startPeopleEditIntent.putExtra("sentInfo", "00 | RANK | RELATION");
                startPeopleEditIntent.putExtra("sentSummery", "Text and other information about the character can be placed here.");

                startActivity(startPeopleEditIntent);
                break;
            case R.id.back: //goes back to the selectstyle activity
                Class startSelectStyle = selectstyle.class;
                Intent startSelectStyleIntent = new Intent(context, startSelectStyle);

                startActivity(startSelectStyleIntent);
                break;
        }
    }

    private void updatePeople(String newName, String newSummary, String newAge, String newRank, String newRelation, String Id) {
        try
        {
            mDb.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(RumorsContract.PeopleEntry.COLUMN_PNAME, newName);
            cv.put(RumorsContract.PeopleEntry.COLUMN_PAGE, newAge);
            cv.put(RumorsContract.PeopleEntry.COLUMN_PRANK, newRank);
            cv.put(RumorsContract.PeopleEntry.COLUMN_PRELATION, newRelation);
            cv.put(RumorsContract.PeopleEntry.COLUMN_PSUMMERY, newSummary);
            String entryFinder = "_id='" + Id + "'";
            mDb.update(RumorsContract.PeopleEntry.TABLE_NAME, cv, entryFinder, null);

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

    private void newPeople(String newName, String newSummary, String newAge, String newRank, String newRelation) {
        try
        {
            mDb.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(RumorsContract.PeopleEntry.COLUMN_PNAME, newName);
            cv.put(RumorsContract.PeopleEntry.COLUMN_PAGE, newAge);
            cv.put(RumorsContract.PeopleEntry.COLUMN_PRANK, newRank);
            cv.put(RumorsContract.PeopleEntry.COLUMN_PRELATION, newRelation);
            cv.put(RumorsContract.PeopleEntry.COLUMN_PSUMMERY, newSummary);
            cv.put(RumorsContract.PeopleEntry.USERID, userID);
            //note: USERID is only needed here since it won't change; there's no need to update it in updatePeople.
            mDb.insertOrThrow(RumorsContract.PeopleEntry.TABLE_NAME, null, cv);
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


    private Cursor getAllPeople() {
        return mDb.query(
                RumorsContract.PeopleEntry.TABLE_NAME,
                null,
                "storedUserID='" + userID + "'",
                null,
                null,
                null,
                RumorsContract.PeopleEntry.COLUMN_PNAME,
                null
        );
    }

}
