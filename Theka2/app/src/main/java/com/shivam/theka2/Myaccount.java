package com.shivam.theka2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Myaccount extends AppCompatActivity {

    private TextView Name,Email,Contact,Password,Address;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);
        checkConnection();
        Name= findViewById(R.id.Name);
        Contact=findViewById(R.id.Contact1);
        Email=findViewById(R.id.Email);
        Password=findViewById(R.id.Password);
         Address=findViewById(R.id.Address);
        edit=findViewById(R.id.button7);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                startActivity(new Intent(com.shivam.theka2.Myaccount.this, com.shivam.theka2.Updatedetails.class));
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        String uid=firebaseAuth.getCurrentUser().getUid();
        DatabaseReference databaseReference=firebaseDatabase.getReference("All Customers").child(uid).child("profile");
        // StorageReference storageReference=firebaseStorage.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot itemSnapshot:dataSnapshot.getChildren())
                {
                    com.shivam.theka2.customerdata registeredData = dataSnapshot.getValue(com.shivam.theka2.customerdata.class);
                    Name.setText("Name::"+registeredData.getName());
                    Email.setText("EmailId::"+registeredData.getEmail());
                    Contact.setText("ContactNo::"+registeredData.getContactNo());
                 //   Password.setText("Password::"+registeredData.getPassword());
                    Address.setText("Address::"+registeredData.getAddress());

                    //Toast.makeText(Profile2.this, "Entered.......", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(com.shivam.theka2.Myaccount.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.myaccountsnackbar);
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
