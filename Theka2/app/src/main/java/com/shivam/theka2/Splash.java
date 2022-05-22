package com.shivam.theka2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseApp.initializeApp(this);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if (firebaseUser !=null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(com.shivam.theka2.Splash.this, com.shivam.theka2.MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },SPLASH_TIME_OUT);
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(com.shivam.theka2.Splash.this, com.shivam.theka2.Register.class);
                    startActivity(intent);
                    finish();
                }
            },SPLASH_TIME_OUT);


        }


    }
}
