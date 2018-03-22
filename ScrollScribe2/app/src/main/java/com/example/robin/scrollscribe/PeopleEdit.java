package com.example.robin.scrollscribe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class PeopleEdit extends AppCompatActivity {

    EditText rName;
    EditText rSummary;
    EditText rAge;
    EditText rRank;
    EditText rRelation;

    String oldName;
    String oldSummary;
    String oldAge;
    String oldRank;
    String oldRelation;
    String ID;

    String newName;
    String newSummary;
    String newAge;
    String newRank;
    String newRelation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_edit);

        rName = (EditText) findViewById(R.id.person_name);
        rSummary = (EditText) findViewById(R.id.person_summery);
        rAge = (EditText) findViewById(R.id.person_age);
        rRank = (EditText) findViewById(R.id.person_rank);
        rRelation = (EditText) findViewById(R.id.person_relation);

        Intent oldWritings = getIntent();
        //Grab the old data
        if (oldWritings.hasExtra("sentName")) {
            oldName = oldWritings.getStringExtra("sentName");
            //display the last name in the Edit text
            rName.setText(oldName);
        }
        if (oldWritings.hasExtra("sentAge")) {
            oldAge = oldWritings.getStringExtra("sentAge");
            rAge.setText(oldAge);
        }
        if (oldWritings.hasExtra("sentRank")) {
            oldRank = oldWritings.getStringExtra("sentRank");
            rRank.setText(oldRank);
        }
        if (oldWritings.hasExtra("sentRelation")) {
            oldRelation = oldWritings.getStringExtra("sentRelation");
            rRelation.setText(oldRelation);
        }
        if (oldWritings.hasExtra("sentSummery")) {
            oldSummary = oldWritings.getStringExtra("sentSummery");
            //display the last summary in the edit text
            rSummary.setText(oldSummary);
        }
        if (oldWritings.hasExtra("entryID")) {
            ID = oldWritings.getStringExtra("entryID");
        }
    }


    public void onClick(View v) {
        //useful since these buttons start the same activity
        Context context = PeopleEdit.this;
        Class startPeople = People.class;
        Intent startPeopleIntent = new Intent(context, startPeople);
        rName = (EditText) findViewById(R.id.person_name);
        rSummary = (EditText) findViewById(R.id.person_summery);
        rAge = (EditText) findViewById(R.id.person_age);
        rRank = (EditText) findViewById(R.id.person_rank);
        rRelation = (EditText) findViewById(R.id.person_relation);

        switch (v.getId()) {
            case R.id.undo: //goes back to Lander
                startActivity(startPeopleIntent);
                //this doesn't send anything back with the intent as the destination activity will
                //use a check on the sentName and sentSummary; if both are null, that means
                //the user decided not to update and thus the database doesn't need to be update.
                break;
            case R.id.done: //starts the rumors activity
                newName = rName.getText().toString();
                newSummary = rSummary.getText().toString();
                newAge = rAge.getText().toString();
                newRank = rRank.getText().toString();
                newRelation = rRelation.getText().toString();
                startPeopleIntent.putExtra("sentName", newName);
                startPeopleIntent.putExtra("sentSummary", newSummary);
                startPeopleIntent.putExtra("sentAge", newAge);
                startPeopleIntent.putExtra("sentRank", newRank);
                startPeopleIntent.putExtra("sentRelation", newRelation);
                if (ID != null) {
                    startPeopleIntent.putExtra("entryID", ID);
                }
                ID = null;

                Log.d("Debug", "done, sending back " + newName);
                startActivity(startPeopleIntent);
                break;
        }
    }


}
