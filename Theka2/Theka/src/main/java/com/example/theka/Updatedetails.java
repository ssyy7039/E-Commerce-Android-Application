package com.shivam.theka;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Updatedetails extends AppCompatActivity {
    EditText name, contact, email, password, shopname, gst, address;
    Button submit;
    String NAME, CONTACT, EMAIL, PASSWORD, SHOPNAME, GST, ADDRESS;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatedetails);
        checkConnection();
        name = findViewById(R.id.NAME);
        contact = findViewById(R.id.CONTACT);
        email = findViewById(R.id.EMAIL);
        password = findViewById(R.id.PASSWORD);
        shopname = findViewById(R.id.SHOPNAME);
        gst = findViewById(R.id.GSTNO);
        address = findViewById(R.id.Address);
        submit = findViewById(R.id.SUBMIT);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        final String uid = firebaseAuth.getCurrentUser().getUid();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("Owners").child(uid).child("profile");
        // StorageReference storageReference=firebaseStorage.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    RegisteredData registeredData = dataSnapshot.getValue(RegisteredData.class);
                    name.setText(registeredData.getName());
                    email.setText(registeredData.getEmail());
                    contact.setText(registeredData.getContact());
                    password.setText(registeredData.getPassword());
                    address.setText(registeredData.getAddress());
                    shopname.setText(registeredData.getShopName());
                    gst.setText(registeredData.getGSTNO());

                    //Toast.makeText(Profile2.this, "Entered.......", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Updatedetails.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                NAME = name.getText().toString().trim();
                CONTACT = contact.getText().toString().trim();
                EMAIL = email.getText().toString().trim();
                PASSWORD = password.getText().toString().trim();
                SHOPNAME = shopname.getText().toString().trim();
                GST = gst.getText().toString().trim();
                ADDRESS = address.getText().toString().trim();
                firebaseUser.updatePassword(PASSWORD).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Updatedetails.this, "Password changed", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Updatedetails.this, "Password is not updated", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                firebaseUser.updateEmail(EMAIL).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Updatedetails.this, "Email is Changed", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Updatedetails.this, "Email is not updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                RegisteredData registeredData = new RegisteredData(NAME, CONTACT, EMAIL, PASSWORD, SHOPNAME, GST, ADDRESS);
                databaseReference.setValue(registeredData);

                finish();
                Toast.makeText(Updatedetails.this, "Your profile data updated", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork = manager.getActiveNetworkInfo();
        View view = findViewById(R.id.updatedetailsnackbar);
        Snackbar snackbar = Snackbar.make(view, "No Internet connection available", Snackbar.LENGTH_LONG);
        snackbar.setDuration(10000000);

        if (actionNetwork != null) {
            if (actionNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                snackbar.dismiss();
            }
            if (actionNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                snackbar.dismiss();
            }
        } else {
            snackbar.show();

            Toast.makeText(this, "Internet connection may not available", Toast.LENGTH_SHORT).show();
        }
    }
}
