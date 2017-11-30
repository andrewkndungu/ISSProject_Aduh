package com.example.sonnie.issproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView logout_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logout_tv = (TextView) findViewById(R.id.logout_tv);
        logout_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==logout_tv){
            FirebaseAuth.getInstance().signOut();
                   finish();
                    Intent intent=new Intent(this,Login.class);
                    startActivity(intent);

                }
            }
        }


