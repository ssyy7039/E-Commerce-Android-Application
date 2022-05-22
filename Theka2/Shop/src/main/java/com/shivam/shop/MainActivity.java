package com.shivam.shop;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private FloatingActionButton button;
    RecyclerView recyclerView;
    private List<Drinkdata> drinkdataList;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;
    EditText txt_Search;
    private long backtime;
    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_Search=findViewById(R.id.editText10);
        checkConnection();
        findViewById(R.id.imageButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.shivam.shop.MainActivity.this, com.shivam.shop.Profile.class));
            }
        });
        findViewById(R.id.imageButton4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.shivam.shop.MainActivity.this,Chat.class));
            }
        });

        button=findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.shivam.shop.MainActivity.this, com.shivam.shop.Add.class));
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(com.shivam.shop.MainActivity.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Drinks are Loading...");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        drinkdataList=new ArrayList<>();
        final com.shivam.shop.Drinkadapter drinkadapter = new com.shivam.shop.Drinkadapter(com.shivam.shop.MainActivity.this,drinkdataList);
        recyclerView.setAdapter(drinkadapter);
       FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            //startActivity(new Intent(Registeration.this,Home.class));
        }
        String uid=firebaseAuth.getCurrentUser().getUid();

         checkConnection();
        databaseReference = FirebaseDatabase.getInstance().getReference("Owners").child(uid).child("All Drinks");
        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                drinkdataList.clear();

                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){

                    Drinkdata drinkdata = itemSnapshot.getValue(Drinkdata.class);
                    drinkdata.setKey(itemSnapshot.getKey());
                    drinkdataList.add(drinkdata);

                }
         checkConnection();
                drinkadapter.notifyDataSetChanged();
                if (drinkadapter.getItemCount()==0){
                    recyclerView.setVisibility(View.INVISIBLE);
                    findViewById(R.id.nodrink).setVisibility(View.VISIBLE);
                }
                if (drinkadapter.getItemCount()!=0){
                    findViewById(R.id.nodrink).setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
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
                ArrayList<Drinkdata> filterList=new ArrayList<>();
                for (Drinkdata item: drinkdataList){
                    if(item.getDrinkname().toLowerCase().contains(text.toLowerCase())){
                        filterList.add(item);
                    }

                }
                drinkadapter.filteredList(filterList);

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
        View view=findViewById(R.id.mainsnackbar);
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
