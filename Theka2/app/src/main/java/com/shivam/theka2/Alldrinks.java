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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Alldrinks extends AppCompatActivity {

    private FloatingActionButton button;
    RecyclerView recyclerView;
    private List<com.shivam.theka2.Drinkdata> drinkdataList;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;
    EditText txt_Search;
    String Shopname,Key;
    TextView shopname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alldrinks);
        checkConnection();
        txt_Search=findViewById(R.id.editText10);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(com.shivam.theka2.Alldrinks.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Drinks are Loading...");
        progressDialog.setCancelable(false);
        shopname=findViewById(R.id.drinkshopname);
        drinkdataList=new ArrayList<>();
        final Drinkadapter drinkadapter = new Drinkadapter(com.shivam.theka2.Alldrinks.this,drinkdataList);
        recyclerView.setAdapter(drinkadapter);
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            Key=bundle.getString("Key");
          Shopname=bundle.getString("Shopname");
          shopname.setText(Shopname);

        databaseReference = FirebaseDatabase.getInstance().getReference("All Drinks").child(Shopname);
        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                drinkdataList.clear();

                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){

                    com.shivam.theka2.Drinkdata drinkdata = itemSnapshot.getValue(com.shivam.theka2.Drinkdata.class);
                    drinkdata.setKey(itemSnapshot.getKey());
                    drinkdataList.add(drinkdata);

                }

                drinkadapter.notifyDataSetChanged();
                if (drinkadapter.getItemCount()==0){
                    findViewById(R.id.nodrink).setVisibility(View.VISIBLE);
                    txt_Search.setVisibility(View.INVISIBLE);
                    findViewById(R.id.imageView2).setVisibility(View.INVISIBLE);
                }
                if (drinkadapter.getItemCount()!=0){
                    findViewById(R.id.nodrink).setVisibility(View.INVISIBLE);
                    txt_Search.setVisibility(View.VISIBLE);
                    findViewById(R.id.imageView2).setVisibility(View.VISIBLE);
                }
                progressDialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });
        }
        findViewById(R.id.cartfloat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Alldrinks.this,cartdetails.class);
                intent.putExtra("Shopname",Shopname);
                intent.putExtra("Key",Key);
                startActivity(intent);
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
                ArrayList<com.shivam.theka2.Drinkdata> filterList=new ArrayList<>();
                for (com.shivam.theka2.Drinkdata item: drinkdataList){
                    if(item.getDrinkname().toLowerCase().contains(text.toLowerCase()) || item.getDrinkSize().toLowerCase().contains(text.toLowerCase())){
                        filterList.add(item);
                    }

                }
                drinkadapter.filteredList(filterList);

            }
        });
    }
    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.Alldrinksnackbar);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(new Intent(Alldrinks.this,MainActivity.class));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
