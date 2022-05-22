package com.shivam.shop;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Drinkdetails extends AppCompatActivity {

    TextView Name,Price,Description,size;
    ImageView imageView;
    String key="";
    String imageUrl="",Size;
    Button delete,edit;
String Shopname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinkdetails);
        checkConnection();
        edit=findViewById(R.id.button4);
        Name=(TextView)findViewById(R.id.textView3);
        size=(TextView)findViewById(R.id.textView14);

        Price=(TextView)findViewById(R.id.textView4);
        Description=(TextView)findViewById(R.id.textView5);
        imageView=(ImageView)findViewById(R.id.imageView);

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            Name.setText(bundle.getString("Name"));
            key=bundle.getString("KeyValue");
            imageUrl=bundle.getString("Image");
            size.setText(bundle.getString("Size")+"ml");
            Size=bundle.getString("Size");
            Price.setText(bundle.getString("Price"));
            Description.setText(bundle.getString("Description"));

            Glide.with(this).
                    load(bundle.getString("Image")).into(imageView);



        }


        delete=findViewById(R.id.button5);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkConnection();
                Dialog();
            }
        });


        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
       FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        final String uid=firebaseAuth.getCurrentUser().getUid();
        DatabaseReference databaseReference=firebaseDatabase.getReference("Owners").child(uid).child("profile");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                RegisteredData registeredData = dataSnapshot.getValue(RegisteredData.class);
                Shopname = registeredData.getShopName();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.shivam.shop.Drinkdetails.this, com.shivam.shop.Updatedrink.class)
                        .putExtra("Name",Name.getText().toString())
                        .putExtra("Price",Price.getText().toString())
                        .putExtra("Description",Description.getText().toString())
                        .putExtra("Imageurl",imageUrl)
                        .putExtra("key",key)
                        .putExtra("Size",Size)
                        .putExtra("Shopname",Shopname)

                );
            }
        });
    }
    public void Dialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure You want to Delete the Drink?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkConnection();
                final ProgressDialog progressDialog=new ProgressDialog(com.shivam.shop.Drinkdetails.this);
                progressDialog.setTitle("Deleting Drink");
                progressDialog.setMessage("Please wait......");
                progressDialog.setCancelable(false);

                progressDialog.show();
                FirebaseAuth  firebaseAuth= FirebaseAuth.getInstance();
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                if(firebaseUser!=null){
                    //startActivity(new Intent(Registeration.this,Home.class));
                }

                String uid=firebaseAuth.getCurrentUser().getUid();
                final DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference("All Drinks").child(Shopname);
                final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Owners").child(uid).child("All Drinks");
                databaseReference.child(key).removeValue();
                databaseReference2.child(key).removeValue();
                progressDialog.dismiss();
                Toast.makeText(com.shivam.shop.Drinkdetails.this, "Your Drink Successfully Deleted ", Toast.LENGTH_SHORT).show();
                //  startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

//                FirebaseStorage storage=FirebaseStorage.getInstance();
//                StorageReference storageReference=storage.getReferenceFromUrl(imageUrl);
//                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//
//                           }
//                });




            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        AlertDialog dialog=builder.show();
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
