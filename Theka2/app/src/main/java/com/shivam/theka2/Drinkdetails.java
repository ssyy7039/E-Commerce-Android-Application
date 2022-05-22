package com.shivam.theka2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Drinkdetails extends AppCompatActivity {

    TextView Name,Price,Description,size;
    ImageView imageView;
    String key="";
    String imageUrl="",Shopname;
    Button Buy,cart;
    private String Id = "";
    ElegantNumberButton elegantNumberButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinkdetails);
        //  edit=findViewById(R.id.button4);
        Id=getIntent().getStringExtra("Drinkname");
        elegantNumberButton = findViewById(R.id.numberbutton);
        Name = (TextView) findViewById(R.id.textView3);
        checkConnection();
        Price = (TextView) findViewById(R.id.textView4);
        size = (TextView) findViewById(R.id.textView14);
        cart = findViewById(R.id.cart);
        Description = (TextView) findViewById(R.id.textView5);
        imageView = (ImageView) findViewById(R.id.imageView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
         //   Name.setText(bundle.getString("Name"));
            key = bundle.getString("KeyValue");
            imageUrl = bundle.getString("Image");
            //size.setText(bundle.getString("Size"));
            //Price.setText(bundle.getString("Price"));
            //Description.setText(bundle.getString("Description"));
             Shopname = bundle.getString("Shopname");
            Glide.with(this).
                    load(bundle.getString("Image")).into(imageView);
             details(key);

            final String Size = bundle.getString("Size");
//            Buy = findViewById(R.id.button5);
//            Buy.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    checkConnection();
//                    startActivity(new Intent(com.example.theka2.Drinkdetails.this, Purchase.class)
//                            .putExtra("Name", Name.getText().toString())
//                            .putExtra("Price", Price.getText().toString())
//                            .putExtra("Description", Description.getText().toString())
//                            .putExtra("Imageurl", imageUrl)
//                            .putExtra("key", key)
//                            .putExtra("Shopname", Shopname)
//                            .putExtra("Size", Size)
//
//                    );
//
//                }
//            });

            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  String Number=elegantNumberButton.getNumber();
                  String saveCurrentTime, saveCurrentDate;
                  Calendar calForDate=Calendar.getInstance();
                    SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd,yyyy");
                    saveCurrentDate=currentdate.format(calForDate.getTime());
                    SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
                    saveCurrentTime=currentdate.format(calForDate.getTime());
                    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                    String uid=firebaseAuth.getUid();
                    String myCurrentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                    DatabaseReference cartList=FirebaseDatabase.getInstance().getReference("All Customers").child(uid).child("Cart Items").child(Shopname);
                    final HashMap<String,Object>cartmap=new HashMap<>();
                    cartmap.put("drinkName",Name.getText().toString());
                    cartmap.put("drinkPrice",Price.getText().toString());
                    cartmap.put("drinkSize",Size);
                    cartmap.put("image",imageUrl);
                    cartmap.put("key",key);
                    cartmap.put("shopname",Shopname);
                    cartmap.put("quantity",elegantNumberButton.getNumber());
                    cartmap.put("time",myCurrentDateTime);
                    cartmap.put("uid",uid);

                   cartList.child(key)
                           .updateChildren(cartmap)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   Intent intent=new Intent(getApplicationContext(),Alldrinks.class);
                                   intent.putExtra("Key",key);
                                   intent.putExtra("Shopname",Shopname);
                                   startActivity(intent);
                                   Toast.makeText(Drinkdetails.this, "Drink is successfully added into cart", Toast.LENGTH_SHORT).show();
                                   finish();
                               }
                           });


                }
            });

        }

    }
    void details(String key2){
        DatabaseReference databaseReferences=FirebaseDatabase.getInstance().getReference("All Drinks").child(Shopname);
        databaseReferences.child(key2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    Drinkdata drinkdata= snapshot.getValue(Drinkdata.class);
                    Name.setText(drinkdata.getDrinkname());
                    size.setText(drinkdata.getDrinkSize());
                    Price.setText(drinkdata.getDrinkPrice());
                    Description.setText(drinkdata.getDescription());
                    Glide.with(Drinkdetails.this).
                            load(drinkdata.getImageurl()).into(imageView);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

}

    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.drinkdetailsnackbar);
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
