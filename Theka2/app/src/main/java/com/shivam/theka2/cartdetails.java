package com.shivam.theka2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class cartdetails extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<cartdata> cartdataList;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    TextView cartshopname,Totalprice;
    String Shopname,Price,Quantity,CMName,Address,Contact,Key,time;
    private int overtotalprice=0,Amount;
    Button Order;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartdetails);
        checkConnection();
        cartshopname=findViewById(R.id.cartshopname);
        Totalprice=findViewById(R.id.cartprice);
        Order=findViewById(R.id.placeorder);

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null) {
            cartshopname.setText(bundle.getString("Shopname"));
             Shopname = bundle.getString("Shopname");
             Key=bundle.getString("Key");
        }
            recyclerView=(RecyclerView)findViewById(R.id.cartrecyclerview);
        // recyclerView.showIfEmpty(view2);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(cartdetails.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        cartdataList=new ArrayList<>();
        final Cartadapter cartadapter= new Cartadapter(cartdetails.this,cartdataList);
        recyclerView.setAdapter(cartadapter);

        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        String uid=firebaseAuth.getCurrentUser().getUid();


        databaseReference = FirebaseDatabase.getInstance().getReference("All Customers").child(uid).child("Cart Items").child(Shopname);
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                cartdataList.clear();

                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){

                    cartdata cartdata1= itemSnapshot.getValue(cartdata.class);
                    cartdata1.setKey(itemSnapshot.getKey());
                    cartdataList.add(cartdata1);

                     Price=cartdata1.getDrinkPrice();
                     Quantity=cartdata1.getQuantity();
                    // Name=cartdata1.getDrinkName();
                     Amount= Integer.valueOf((int) (Double.valueOf(String.valueOf(Price))*Double.valueOf(String.valueOf(Quantity))));
                    overtotalprice=overtotalprice + Amount;
                    Totalprice.setText("Total Price = "+String.valueOf(overtotalprice)+" $");

                }
                cartadapter.notifyDataSetChanged();

if(cartadapter.getItemCount()==0){
    Order.setVisibility(View.INVISIBLE);
    Totalprice.setVisibility(View.INVISIBLE);
    findViewById(R.id.nodatacart).setVisibility(View.VISIBLE);
   // Toast.makeText(cartdetails.this, "Please add drink into your cart to place the order", Toast.LENGTH_SHORT).show();
}
if(cartadapter.getItemCount()!=0){
                    Order.setVisibility(View.VISIBLE);
    Totalprice.setVisibility(View.VISIBLE);
    findViewById(R.id.nodatacart).setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });

        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            Intent intent= new Intent(cartdetails.this,confirmorderdetails.class);
//                            intent.putExtra("Quantity",Quantity);
//                            intent.putExtra("Name",Name);
                            intent.putExtra("Key",Key);
                            intent.putExtra("Totalprice",Totalprice.getText().toString());
                            intent.putExtra("Shopname",Shopname);
                            startActivity(intent);



            }
        });


    }

    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.snackbarcart);
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
        Intent intent= new Intent(cartdetails.this,Alldrinks.class);

        intent.putExtra("Key",Key);
      //  intent.putExtra("Totalprice",Totalprice.getText().toString());
        intent.putExtra("Shopname",Shopname);
        startActivity(intent);

        finish();
    }
}
