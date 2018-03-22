package com.example.robin.scrollscribe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class selectstyle extends AppCompatActivity {

    public static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectstyle);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        displayNameChange(account);

        userID = getID();
    }


    //changes the welcome message name according to the name on the google account
    private void displayNameChange(GoogleSignInAccount account) {
        //Update greeting
        TextView greeting = findViewById(R.id.greeting);
        String personName = "Name";

        if (account != null) {
            personName = account.getDisplayName();
        }

        greeting.setText("Welcome, " + personName + "!");

    }


    public void onClick(View v) {
        Context context = selectstyle.this;

        switch (v.getId()) {
            case R.id.back: //goes back to Lander
                Class startLander = Lander.class;
                Intent startLanderIntent = new Intent(context, startLander);

                startActivity(startLanderIntent);
                break;
            case R.id.rumors: //starts the rumors activity
                Class startRumors = Rumors.class;
                Intent startRumorsIntent = new Intent(context, startRumors);
                startRumorsIntent.putExtra(Intent.EXTRA_TEXT, userID);

                startActivity(startRumorsIntent);
                break;
            case R.id.people: //starts the rumors activity
                Class startPeople = People.class;
                Intent startPeopleIntent = new Intent(context, startPeople);
                startPeopleIntent.putExtra(Intent.EXTRA_TEXT, userID);

                startActivity(startPeopleIntent);
                break;
        }
    }

    //useed to grab google ID
    public String getID() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        String acctNumber = "";
        if (account != null) {
            acctNumber = account.getId();
        }
        return acctNumber;

    }

}
