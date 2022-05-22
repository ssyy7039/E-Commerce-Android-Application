package com.shivam.theka2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private List<com.shivam.theka2.RegisteredData> registeredDataList;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;
    EditText txt_Search;
    private long backtime;
    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkConnection();
        txt_Search=findViewById(R.id.editText10);
        findViewById(R.id.imageButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(com.shivam.theka2.MainActivity.this, com.shivam.theka2.Profile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
        findViewById(R.id.imageButton4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.shivam.theka2.MainActivity.this, com.shivam.theka2.Chat.class));
            }
        });


        recyclerView=(RecyclerView)findViewById(R.id.recyclerView2);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(com.shivam.theka2.MainActivity.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Shops are Loading...");
        progressDialog.setMessage("Please wait...");

        registeredDataList=new ArrayList<>();
        final com.shivam.theka2.Shopadapter shopadapter = new com.shivam.theka2.Shopadapter(com.shivam.theka2.MainActivity.this,registeredDataList);
        recyclerView.setAdapter(shopadapter);
       FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            //startActivity(new Intent(Registeration.this,Home.class));
        }
        String uid=firebaseAuth.getUid();


        databaseReference = FirebaseDatabase.getInstance().getReference("All Owners");
        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                registeredDataList.clear();

                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){


                    com.shivam.theka2.RegisteredData registeredData = itemSnapshot.getValue(com.shivam.theka2.RegisteredData.class);
                    registeredData.setKey(itemSnapshot.getKey());
                    registeredDataList.add(registeredData);

                }

                shopadapter.notifyDataSetChanged();
                progressDialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });

        txt_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }

            private void filter(String text) {
                ArrayList<com.shivam.theka2.RegisteredData> filterList=new ArrayList<>();
                for (com.shivam.theka2.RegisteredData item: registeredDataList){
                    if(item.getShopName().toLowerCase().contains(text.toLowerCase()) || item.getContact().toLowerCase().contains(text.toLowerCase())
                    || item.getAddress().toLowerCase().contains(text.toLowerCase())){
                        filterList.add(item);
                    }

                }
                shopadapter.filteredList(filterList);

            }
        });
    }
    @Override
    public void onBackPressed() {
        if (backtime+2000>System.currentTimeMillis()){
            toast.cancel();
            super.onBackPressed();
            return;
        }
        else {
            toast= Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT);
            toast.show();
        }
        backtime=System.currentTimeMillis();

    }
    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.mainsmackbar);
        Snackbar snackbar= Snackbar.make(view,"No Internet connection available", Snackbar.LENGTH_LONG);
        snackbar.setDuration(1000);

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
