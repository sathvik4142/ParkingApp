package com.parkingapp.parkingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;

public class ProfileActivity extends AppCompatActivity {

    public static final String GOOGLE_ACCOUNT = "google_account";
    private TextView profileEmail;
    private Button signOut;
    private GoogleSignInClient googleSignInClient;
    Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileEmail = findViewById(R.id.profile_email);
        signOut = findViewById(R.id.sign_out);
        mapButton = findViewById(R.id.mapButton);
        Button searchButton = findViewById(R.id.searchButton);
        System.out.println("Profile Activity");

        setDataOnView();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*
              Sign-out is initiated by simply calling the googleSignInClient.signOut API. We add a
              listener which will be invoked once the sign out is the successful
               */
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //On Succesfull signout we navigate the user back to LoginActivity
                        Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });
    }

    private void setDataOnView() {
        GoogleSignInAccount googleSignInAccount = getIntent().getParcelableExtra(GOOGLE_ACCOUNT);
        profileEmail.setText("Hi " + googleSignInAccount.getDisplayName());
    }
}
