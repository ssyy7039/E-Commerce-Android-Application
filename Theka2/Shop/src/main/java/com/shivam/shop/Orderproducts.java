package com.shivam.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Orderproducts extends AppCompatActivity {
    private List<cartdata> cartdataList;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;

    TextView name,contact,address,Amount;
    RecyclerView recyclerView;
    String Key,Shopname,status1,Status="Order is Cancelled from the Shopkeeper side",Time,Orderid,uid,Status2="You cancelled the order";
    Button cancel,delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderproducts);
        name=findViewById(R.id.cmname);
        contact=findViewById(R.id.cmcontact);
        address=findViewById(R.id.cmaddress);
        Amount=findViewById(R.id.productamount);
        recyclerView=findViewById(R.id.productrecyclerview);
        cancel=findViewById(R.id.cancelorder2);
        delete=findViewById(R.id.deleteorder2);

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            name.setText(bundle.getString("Name"));
            contact.setText(bundle.getString("Contact"));
            address.setText(bundle.getString("Address"));
            Amount.setText(bundle.getString("Amount"));
            Key=bundle.getString("Key");
            Time=bundle.getString("Time");
            uid=bundle.getString("UID");
            Orderid=bundle.getString("OrderId");
            Shopname=bundle.getString("Shopname");
            status1=bundle.getString("Status");
        }
        if (status1.equals("Order placed")){
            delete.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
        }
        if (status1.equals("Order is cancelled from the Customer side")){
            delete.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
        }
        if (status1.equals("You cancelled the order")){
            delete.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
        }

        GridLayoutManager gridLayoutManager=new GridLayoutManager(Orderproducts.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        cartdataList=new ArrayList<>();
        final Cartadapter2 cartadapter= new Cartadapter2(Orderproducts.this,cartdataList);
        recyclerView.setAdapter(cartadapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("All Customers Orders").child(Shopname).child(Key).child("drinks");

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                cartdataList.clear();

                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){

                    cartdata cartdata1= itemSnapshot.getValue(cartdata.class);
                    cartdata1.setKey(itemSnapshot.getKey());
                    cartdataList.add(cartdata1);
                }
                cartadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });
cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Dialogcancel();

    }
});
delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Dialogdelete();
    }
});

    }
    public void Dialogcancel(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Cancel");
        builder.setMessage("Are you sure You want to Cancel the Order");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             //   checkConnection();
                final ProgressDialog progressDialog=new ProgressDialog(com.shivam.shop.Orderproducts.this);
                progressDialog.setMessage("Canceling Order......");

                progressDialog.show();
                Requests requests = new Requests(name.getText().toString(),contact.getText().toString(),address.getText().toString(),Time,uid,Shopname,Key,Orderid,cartdataList,Amount.getText().toString(),Status);

                FirebaseDatabase.getInstance().getReference("All Customers")
                        .child(uid).child("All Orders").child(Key).setValue(requests).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                      //  Toast.makeText(com.shivam.shop.Orderproducts.this, "Registeration failed", Toast.LENGTH_SHORT).show();
                    }
                });
//                final DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference("All Customers Orders").child(Shopname);
//                databaseReference2.child(Key).removeValue();
                Requests requests2 = new Requests(name.getText().toString(),contact.getText().toString(),address.getText().toString(),Time,uid,Shopname,Key,Orderid,cartdataList,Amount.getText().toString(),Status2);

                FirebaseDatabase.getInstance().getReference("All Customers Orders")
                        .child(Shopname).child(Key).setValue(requests2).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(com.shivam.shop.Orderproducts.this, "Order is not cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                progressDialog.dismiss();
                Toast.makeText(com.shivam.shop.Orderproducts.this, "Your Order is cancelled ", Toast.LENGTH_SHORT).show();
                //  startActivity(new Intent(getApplicationContext(),Allorders.class));
                finish();




            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        AlertDialog dialog=builder.show();
    }
    public void Dialogdelete(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure You want to Delete the Order? " );
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //   checkConnection();
                final ProgressDialog progressDialog=new ProgressDialog(com.shivam.shop.Orderproducts.this);
                progressDialog.setMessage("Deleting Order......");

                progressDialog.show();

                final DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference("All Customers Orders").child(Shopname);
                databaseReference2.child(Key).removeValue();
                progressDialog.dismiss();
                Toast.makeText(com.shivam.shop.Orderproducts.this, "Your Order deleted", Toast.LENGTH_SHORT).show();
                //  startActivity(new Intent(getApplicationContext(),Allorders.class));
                finish();




            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        AlertDialog dialog=builder.show();
    }


}