package com.shivam.theka2;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allorders);
        checkConnection();

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView3);
       // recyclerView.showIfEmpty(view2);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(com.shivam.theka2.Allorders.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Orders are Loading...");

        orderDataList=new ArrayList<>();
        final Orderadapter orderadapter= new Orderadapter(com.shivam.theka2.Allorders.this,orderDataList);
        recyclerView.setAdapter(orderadapter);

        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            //startActivity(new Intent(Registeration.this,Home.class));
        }
        String uid=firebaseAuth.getCurrentUser().getUid();


        databaseReference = FirebaseDatabase.getInstance().getReference("All Customers").child(uid).child("All Orders");
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
                }
                if (orderadapter.getItemCount()!=0){
                    findViewById(R.id.noorder3).setVisibility(View.INVISIBLE);
                }
                progressDialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });


    }
    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.Allordersnackbar);
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
