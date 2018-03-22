package com.example.robin.scrollscribe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RumorsEdit extends AppCompatActivity {

    EditText rName;
    EditText rSummary;

    String oldName;
    String oldSummary;
    String ID;

    String newName;
    String newSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rumors_edit);

        rName = (EditText) findViewById(R.id.rumor_name);
        rSummary = (EditText) findViewById(R.id.rumor_summery);

        Intent oldWritings = getIntent();
        //Grab the old data
        if (oldWritings.hasExtra("sentName")) {
            oldName = oldWritings.getStringExtra("sentName");
            //display the last name in the Edit text
            rName.setText(oldName);
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
        Context context = RumorsEdit.this;
        Class startRumors = Rumors.class;
        Intent startRumorsIntent = new Intent(context, startRumors);
        rName = (EditText) findViewById(R.id.rumor_name);
        rSummary = (EditText) findViewById(R.id.rumor_summery);

        switch (v.getId()) {
            case R.id.undo: //goes back to Lander
                startActivity(startRumorsIntent);
                //this doesn't send anything back with the intent as the destination activity will
                //use a check on the sentName and sentSummary; if both are null, that means
                //the user decided not to update and thus the database doesn't need to be update.
                break;
            case R.id.done: //starts the rumors activity
                newName = rName.getText().toString();
                newSummary = rSummary.getText().toString();
                startRumorsIntent.putExtra("sentName", newName);
                startRumorsIntent.putExtra("sentSummary", newSummary);
                if (ID != null) {
                    startRumorsIntent.putExtra("entryID", ID);
                }
                ID = null;

                Log.d("Debug", "done, sending back " + newName);
                startActivity(startRumorsIntent);
                break;
        }
    }
}
