package com.shivam.theka2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class confirmorderdetails extends AppCompatActivity {
TextView shopname;
EditText name,contact,address;
Button Confirm;
String orderid,Amount,Status="Order placed",Quantity="",Shopname,Key,uid,Price;
    private List<cartdata> cartdataList;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorderdetails);
        checkConnection();
        shopname=findViewById(R.id.Shopnameorder);
        name=findViewById(R.id.nameorder);
        contact=findViewById(R.id.contactorder);
        address=findViewById(R.id.nameaddress);
        Confirm=findViewById(R.id.placeorder);
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null) {
            shopname.setText(bundle.getString("Shopname"));
            Shopname=bundle.getString("Shopname");
            Key=bundle.getString("Key");
            Amount=bundle.getString("Totalprice");

        }
         cartdataList=new ArrayList<>();
        final Cartadapter cartadapter= new Cartadapter(confirmorderdetails.this,cartdataList);
       // recyclerView.setAdapter(cartadapter);

        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();

         uid=firebaseAuth.getCurrentUser().getUid();


        databaseReference = FirebaseDatabase.getInstance().getReference("All Customers").child(uid).child("Cart Items").child(Shopname);

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                cartdataList.clear();

                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){
         checkConnection();
                    cartdata cartdata1= itemSnapshot.getValue(cartdata.class);
                    cartdata1.setKey(itemSnapshot.getKey());
                    cartdataList.add(cartdata1);

                   Price=cartdata1.getDrinkPrice();
                    Quantity=cartdata1.getQuantity();
                    // Name=cartdata1.getDrinkName();
//                    int Amount= Integer.valueOf((int) (Double.valueOf(String.valueOf(Price))*Double.valueOf(String.valueOf(Quantity))));
//                    overtotalprice=overtotalprice + Amount;
//                    Totalprice.setText("Total Price = "+String.valueOf(overtotalprice)+" $");


                }
               // cartadapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });


        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name=name.getText().toString().trim();
                String Contact=contact.getText().toString().trim();
                String Address=address.getText().toString().trim();
                if(Name.isEmpty()) {
                    name.setError("Please enter your Name");
                    name.requestFocus();
                    return;
                }
                if (Contact.isEmpty() || Contact.length()!=10) {
                    contact.setError("Please enter your contact number");
                    contact.requestFocus();
                    return;
                }
                if (Address.isEmpty() || Address.length()<15) {
                    address.setError("Please provide your proper delivery address");
                    address.requestFocus();
                    return;
                }
                orderid=String.valueOf(System.currentTimeMillis());
              //  orderid = String.valueOf(Double.valueOf(String.valueOf(contact.getText())) + Double.valueOf(String.valueOf(Price)) + 786);
                String myCurrentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                Requests requests1=new Requests(Name,Contact,Address,myCurrentDateTime,uid,Shopname,Key,orderid,cartdataList,Amount,Status);
                FirebaseDatabase.getInstance().getReference("All Customers")
                        .child(uid).child("All Orders").child(myCurrentDateTime).setValue(requests1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(confirmorderdetails.this, "Your Order is Successfully placed ", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(confirmorderdetails.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        checkConnection();
                        // Toast.makeText(com.example.theka2.cartdetails.this, "Registeration failed", Toast.LENGTH_SHORT).show();
                    }
                });
                FirebaseDatabase.getInstance().getReference("All Customers Orders").child(Shopname)
                        .child(myCurrentDateTime).setValue(requests1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                          checkConnection();
                        // Toast.makeText(com.example.theka2.cartdetails.this, "Registeration failed", Toast.LENGTH_SHORT).show();
                    }
                });
                final DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference("All Customers").child(uid).child("Cart Items").child(Shopname);
                databaseReference2.removeValue();

            }
        });

    }
    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.confirm);
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