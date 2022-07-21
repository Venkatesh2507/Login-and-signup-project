package com.example.loginandsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
//initializing the widgets
EditText emailEt,passwordEt;
Button logInBtn;
TextView signUpTv;
ProgressDialog progressDialog;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //condition to hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //assigning the id's
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        logInBtn = findViewById(R.id.logInBtn);
        signUpTv = findViewById(R.id.signUpTv);
        //declaring the FirebaseAuth
        mAuth =FirebaseAuth.getInstance();
        //allocating the progressbar
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        signUpTv.setOnClickListener(new View.OnClickListener() { //if user do not have the account for app
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInActivity.this,SignUpActivity.class)); //by clicking the textview user will be directed to the signup page
            }
        });
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser();
            } //after the clicking the log in button the details or credentials entered by user will be validated or checked
        });
    }
    String email,password;
    private void validateUser() { //this function checks weather the user has entered correct details or not
            email = emailEt.getText().toString().trim();
            password = passwordEt.getText().toString().trim();
            if(TextUtils.isEmpty(email)){ //checks weather the email is empty or not
                emailEt.setError("E-mail cannot be empty");
                emailEt.requestFocus();
            }
            else if(TextUtils.isEmpty(password)){ //checks weather the password is empty or not
                passwordEt.setError("Password cannot be empty");
                passwordEt.requestFocus();
            }
            else{
                logInUser(); //user will be logged in through firebase
            }

    }

    private void logInUser() {

            progressDialog.setMessage("Logging in....");
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() { //method to sign in the user
                @Override
                public void onSuccess(AuthResult authResult) { // if the user is successfully signed in
                    Toast.makeText(LogInActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LogInActivity.this,HomeActivity.class)); //user is directed to home activity
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(LogInActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show(); //if  logging in is unsuccessful then the exception will be toasted
                }
            });

    }


}