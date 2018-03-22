package com.example.robin.scrollscribe;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;



// keytool -list -v -keystore "%USERPROFILE%\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android




public class Lander extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;

    int RC_SIGN_IN = 3; //can be any number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lander);


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail() //this is where requests for additional APIs will go
                .requestIdToken("277312942259-blg24pf92fdirrjcbpais2s29bqbq7th.apps.googleusercontent.com")
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("debug", "Clicked!");
                signIn();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void signIn() {
        Log.d("debug", "in sign in");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("debug", "in onActivityResults");

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        Log.d("debug", "in handleSignInResults");
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d("debug", "Success!");

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            Log.d("debug", "caught");
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //Log.w(log, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }


    private void updateUI(GoogleSignInAccount account) {
        TextView message = findViewById(R.id.loggedInAs);
        String personName = "name not found";

        Button start = findViewById(R.id.start);

        //makes variables from google account
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            personName = acct.getDisplayName();
        }

        if (account == null) {
            message.setText(R.string.notLoggedIn);
            start.setClickable(false);
        } else {
            message.setText("Signed in as " + personName + "!");
            start.setClickable(true);
        }
    }

    //signs the user out on button press
    public void signOut(View view) {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
        TextView message = findViewById(R.id.loggedInAs);

        //makes the start button unclickable until logged in
        Button start = findViewById(R.id.start);
        start.setClickable(false);
        message.setText(R.string.notLoggedIn);
    }


    public void toSelectStyle(View view) {
        Context context = Lander.this;
        Class startSelectStyle = selectstyle.class;
        Intent startChildActivityIntent = new Intent(context, startSelectStyle);

        startActivity(startChildActivityIntent);
    }
}


