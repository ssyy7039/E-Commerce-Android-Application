package com.shivam.shop;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class Appinfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appinfo);
        checkConnection();
        findViewById(R.id.term).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.shivam.shop.Appinfo.this,Termscondtions.class));
            }
        });
    }
    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.appinfosnackbar);
        Snackbar snackbar= Snackbar.make(view,"No Internet connection available", Snackbar.LENGTH_LONG);
        snackbar.setDuration(10000000);

        if (actionNetwork!=null){
            if (actionNetwork.getType() == ConnectivityManager.TYPE_WIFI){
                snackbar.dismiss();
            }
            if (actionNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
                snackbar.dismiss();
            }
        }else {
            snackbar.show();

            Toast.makeText(this, "Internet connection may not available", Toast.LENGTH_SHORT).show();
        }
    }
}
