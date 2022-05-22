package com.shivam.theka;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    Button logout, orders, MyAccount;
    Button share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        checkConnection();
        logout = findViewById(R.id.logout1);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
            }
        });
        MyAccount = findViewById(R.id.details);
        MyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Myaccount.class));
            }
        });
        orders = findViewById(R.id.orders);
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Allorders.class));
            }
        });
        findViewById(R.id.info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Appinfo.class));
            }
        });

//        share=findViewById(R.id.button8);
//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                 String sharebody="Your body here";
////                String sharesub="Your Subject here";
//                ApplicationInfo api=getApplicationContext().getApplicationInfo();
//                String apkpath=api.sourceDir;
//                Intent intent=new Intent(Intent.ACTION_SEND);
//                intent.setType("application/vnd.android.package-archive");
//
////                intent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
////                intent.putExtra(Intent.EXTRA_SUBJECT,sharebody);
//                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
//                startActivity(Intent.createChooser(intent,"ShareVia"));
//            }
//        });
    }

    public void Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure You want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkConnection();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    firebaseAuth.signOut();
                    Intent intent = new Intent(Profile.this, Signin.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    Toast.makeText(Profile.this, "You are successfully logged out", Toast.LENGTH_SHORT).show();

                }


            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        AlertDialog dialog = builder.show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork = manager.getActiveNetworkInfo();
        View view = findViewById(R.id.profilsnackbar);
        Snackbar snackbar = Snackbar.make(view, "No Internet connection available", Snackbar.LENGTH_LONG);
        snackbar.setDuration(10000000);

        if (actionNetwork != null) {
            if (actionNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                snackbar.dismiss();
            }
            if (actionNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                snackbar.dismiss();
            }
        } else {
            snackbar.show();

            Toast.makeText(this, "Internet connection may not available", Toast.LENGTH_SHORT).show();
        }
    }
}
