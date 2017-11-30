package com.example.sonnie.issproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Signup extends AppCompatActivity implements View.OnClickListener{
    private EditText signup_email_et;
    private EditText signup_password_et;
    private Button signup_button;
    private TextView login_tv;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private CheckBox showpassword_checkBox;
    private String userID;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        signup_email_et=(EditText) findViewById(R.id.signup_email_et);
        signup_password_et=(EditText) findViewById(R.id.signup_password_et);
        signup_button=(Button) findViewById(R.id.signup_button);
        login_tv=(TextView) findViewById(R.id.login_tv);
        showpassword_checkBox=(CheckBox) findViewById(R.id.showpassword_checkBox);

        progressDialog = new ProgressDialog(this);

        signup_button.setOnClickListener(this);
        login_tv.setOnClickListener(this);

        showpassword_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    signup_password_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    signup_password_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


    }
    private void registerUser(){


        final String email = signup_email_et.getText().toString().trim();
        final String password  = signup_password_et.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter your email address",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)  ){
           Toast.makeText(this,"Please enter your password",Toast.LENGTH_LONG).show();
            return;
        }

        if (password.length() < 8 ){
            Toast.makeText(  getApplicationContext(),"Paswword is short ",Toast.LENGTH_LONG).show();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Invalid email address. Enter a valid email.",Toast.LENGTH_LONG).show();
            return;

        }





        progressDialog.setMessage("Registering. Please wait...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                           Toast.makeText(Signup.this,"Registration successful.",Toast.LENGTH_LONG);
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            mFirebaseDatabase = FirebaseDatabase.getInstance();
                            myRef = mFirebaseDatabase.getReference();
                            FirebaseUser useri = firebaseAuth.getCurrentUser();
                            userID = useri.getUid();


                            User user=new User(email,password);
                            myRef.child("Users").child(userID).setValue(user);

                        }
                        else{

                           Toast.makeText(Signup.this,"Registration error.",Toast.LENGTH_LONG);
                        }
                        progressDialog.dismiss();

                    }
                });

    }

    @Override
    public void onClick(View v) {
        if(v == signup_button){
            registerUser();
        }
        if(v==login_tv){
            finish();
            startActivity(new Intent(getApplicationContext(), Login.class));
                Toast.makeText(this,"Welcome.",Toast.LENGTH_LONG);
        }
    }
}
