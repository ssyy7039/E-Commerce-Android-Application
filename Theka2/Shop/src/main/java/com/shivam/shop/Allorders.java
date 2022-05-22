package com.shivam.shop;

import android.app.ProgressDialog;
import android.content.Context;
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

public class Allorders extends AppCompatActivity {

    RecyclerView recyclerView;
    private List<Requests> orderDataList;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;
    EditText txt_Search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allorders);
        checkConnection();
txt_Search=findViewById(R.id.search);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView3);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(com.shivam.shop.Allorders.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Orders...");
progressDialog.setCancelable(false);
        orderDataList=new ArrayList<>();
        final Orderadapter orderadapter= new Orderadapter(com.shivam.shop.Allorders.this,orderDataList);
        recyclerView.setAdapter(orderadapter);
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            //startActivity(new Intent(Registeration.this,Home.class));
        }
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        final String uid=firebaseAuth.getCurrentUser().getUid();
        DatabaseReference databaseReference1=firebaseDatabase.getReference("Owners").child(uid).child("profile");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                com.shivam.shop.RegisteredData registeredData = dataSnapshot.getValue(com.shivam.shop.RegisteredData.class);
                String Shopname = registeredData.getShopName();
                databaseReference = FirebaseDatabase.getInstance().getReference("All Customers Orders").child(Shopname);
                progressDialog.show();
                eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        orderDataList.clear();

                        for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){


                            Requests orderData= itemSnapshot.getValue(Requests.class);
                            orderData.setKey(itemSnapshot.getKey());
                            orderDataList.add(orderData);

                        }
                        orderadapter.notifyDataSetChanged();
                        if (orderadapter.getItemCount()==0){
                            findViewById(R.id.noorder3).setVisibility(View.VISIBLE);
                            txt_Search.setVisibility(View.INVISIBLE);
                            findViewById(R.id.imageView2).setVisibility(View.INVISIBLE);
                        }
                        if (orderadapter.getItemCount()!=0){
                            findViewById(R.id.noorder3).setVisibility(View.INVISIBLE);
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                ArrayList<Requests> filterList=new ArrayList<>();
                for (Requests item: orderDataList){
                    if(item.getOrderId().toLowerCase().contains(text.toLowerCase())
                            || item.getAddress().toLowerCase().contains(text.toLowerCase()) || item.getName().toLowerCase().contains(text.toLowerCase())){
                        filterList.add(item);

                    }


                }
                orderadapter.filteredList(filterList);

            }
        });


    }
    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.allordersnakcbar);
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
