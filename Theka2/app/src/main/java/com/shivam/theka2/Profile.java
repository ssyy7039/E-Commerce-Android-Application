package com.shivam.theka2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
private Button orders,MyAccount;
ImageButton logout;
TextView name,contact,address,email;
Button share;
FirebaseAuth firebaseAuth;
FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name=findViewById(R.id.profilename);
        contact=findViewById(R.id.profilecontact);
        address=findViewById(R.id.profileaddress);
        email=findViewById(R.id.profileemail);
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
                    name.setText(registeredData.getName());
                    email.setText(registeredData.getEmail());
                    contact.setText(registeredData.getContactNo());
                    //   Password.setText("Password::"+registeredData.getPassword());
                    address.setText(registeredData.getAddress());

                    //Toast.makeText(Profile2.this, "Entered.......", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(com.shivam.theka2.Profile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });

        findViewById(R.id.profilefloat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this,Updatedetails.class);
                intent.putExtra("Contact",contact.getText().toString().trim());
                startActivity(intent);
            }
        });
        logout=findViewById(R.id.logout);
        checkConnection();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
            }
        });
//        MyAccount=findViewById(R.id.details);
//        MyAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(com.example.theka2.Profile.this, com.example.theka2.Myaccount.class));
//            }
//        });
        orders=findViewById(R.id.details);
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.shivam.theka2.Profile.this, com.shivam.theka2.Allorders.class));
            }
        });
        findViewById(R.id.Appinfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.shivam.theka2.Profile.this, com.shivam.theka2.Appinfo.class));
            }
        });
//        share=findViewById(R.id.Shaire);
//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                 String sharebody="Your body here";
////                String sharesub="Your Subject here";
//               ApplicationInfo api=getApplicationContext().getApplicationInfo();
//                String apkpath=api.sourceDir;
//                Intent intent=new Intent(Intent.ACTION_SEND);
//                intent.setType("application/vnd.android.package-archive");
//
////                intent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
////                intent.putExtra(Intent.EXTRA_SUBJECT,sharebody);
//               intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(new File(apkpath)));
//                startActivity(Intent.createChooser(intent,"ShareVia"));
//            }
//        });

    }
    public void Dialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure You want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkConnection();
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

                firebaseAuth.getInstance().signOut();


               Intent intent= new Intent(com.shivam.theka2.Profile.this, com.shivam.theka2.Register.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
               startActivity(intent);
                Toast.makeText(com.shivam.theka2.Profile.this, "You are successfully logged out", Toast.LENGTH_SHORT).show();
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
    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.profilesnackbar);
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
