package com.example.pairumani.appbuild;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.FacebookSdk;




import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private ImageView photoImageView;
    private TextView nameTextView;
    private TextView emailTextView;


    private GoogleApiClient googleApiClient;

    private FirebaseAuth firebaseAuth;
    private  FirebaseAuth.AuthStateListener firebaseAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        photoImageView = (ImageView) findViewById(R.id.photoImageView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
     //   idTextView = (TextView) findViewById(R.id.idTextView);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                    if( user!=null)
                    {
                        setUserData(user);

                    }
                    else {
                    goLogInScreen();
                    }
            }
            };


    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    private void setUserData(FirebaseUser user) {

        nameTextView.setText(user.getDisplayName());
        emailTextView.setText(user.getEmail());
       // idTextView.setText(user.getUid());

        Glide.with(this).load(user.getPhotoUrl()).into(photoImageView);


    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }



    private void goLogInScreen() {

        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public void logOut(View view) {
        firebaseAuth.signOut();

        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();



        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), "No se pudo Cerrar Sesión", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void revoke(View view) {
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), "No se Pudo Olvidar Sesión", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseAuth !=null)
        {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }

    }



    public void nexthome2(View view) {

        Intent intent = new Intent(this, HomeActivity.class);

        startActivity(intent);
    }


    public void Mposteria(View view) {


        Intent intent = new Intent(this, MamposteriaActivity.class);

        startActivity(intent);
    }
    public void Losa(View view) {


        Intent intent = new Intent(this, LosaActivity.class);

        startActivity(intent);
    }
    public void Radier(View view) {


        Intent intent = new Intent(this, RadierActivity.class);

        startActivity(intent);
    }
}
