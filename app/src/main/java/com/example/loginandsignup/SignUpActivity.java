package com.example.loginandsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    //initializing the widgets
    EditText emailEt, usernameEt, passwordEt, confirmPasswordEt;
    Button signUp;
    ProgressDialog progressDialog;
    TextView logInTv;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //assigning the id's
        emailEt = findViewById(R.id.emailEt);
        usernameEt = findViewById(R.id.usernameEt);
        passwordEt= findViewById(R.id.passwordEt);
        confirmPasswordEt = findViewById(R.id.confirmPasswordEt);
        signUp = findViewById(R.id.registerBtn);
        logInTv = findViewById(R.id.logInTv);
        logInTv.setOnClickListener(new View.OnClickListener() { //if user has already a account
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LogInActivity.class)); //directed to log in activity
            }
        });
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance(); //firebase auth is instantiated
        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  authenticateUser();
            } //the details or credentials entered by user will be validated or checked
        });

    }
   String email,password,username,confirmPassword;
    private void authenticateUser() { //this function contains the statements that checks the credentials entered by the user
        email = emailEt.getText().toString().trim();
        password = passwordEt.getText().toString().trim();
        username = usernameEt.getText().toString().trim();
        confirmPassword = confirmPasswordEt.getText().toString().trim();
        if (TextUtils.isEmpty(username)){ //checks weather username is empty
            usernameEt.setError("Username cannot be empty");
            usernameEt.requestFocus();
        }
        else if(TextUtils.isEmpty(email)){
            emailEt.setError("E-mail cannot be empty");//checks weather e-mail is empty
            emailEt.requestFocus();

        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEt.setError("Invalid E-mail pattern"); //checks weather e-mail pattern  is correct or not
            emailEt.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            passwordEt.setError("Password cannot be empty");//checks weather password is empty
            passwordEt.requestFocus();
        }
        else if(!password.equals(confirmPassword)){
            Toast.makeText(this, "Password does'nt matches confirmed password", Toast.LENGTH_SHORT).show(); //checks weather password and confirm password matches or not
        }
        else{
            createUser(); //creates the user account
        }
    }

    private void createUser() {
       progressDialog.setMessage("Registering user account....");
       progressDialog.show();
       mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() { //method to create the user account
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()){
                   Toast.makeText(SignUpActivity.this, "User account registered successfully", Toast.LENGTH_SHORT).show(); //user is directed to log in activity
                   startActivity(new Intent(SignUpActivity.this,LogInActivity.class));
           }
               else{
                   Toast.makeText(SignUpActivity.this, "Error! "+task.getException(), Toast.LENGTH_SHORT).show(); //if registration in is unsuccessful then the exception will be toasted
               }
       }
       });
    }
}

