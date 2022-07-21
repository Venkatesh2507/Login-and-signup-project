package com.example.loginandsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    Button logout;   /* initializing the widgets*/

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        mAuth = FirebaseAuth.getInstance(); //assigning the id's
        logout = findViewById(R.id.logOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut(); //logouts the current user
                startActivity(new Intent(HomeActivity.this,LogInActivity.class)); //redirects user to login page
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser(); //gets the current user logged in
        if(user==null){ //checks weather the user is logged in or not
            startActivity(new Intent(HomeActivity.this,SignUpActivity.class)); // if not logged in redirects the user to register the account
            finish();
        } //if logged in then the HomeActivity is displayed
    }
}