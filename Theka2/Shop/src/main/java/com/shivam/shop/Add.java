package com.shivam.shop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class Add extends AppCompatActivity {

    TextView name,prize,description,Size,shopname;
    Button Add;
    FloatingActionButton camera;
    ImageView Drinkimage;
    Uri uri;
    String imageUrl,Name,Prize,Description,DrinkSize;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        checkConnection();
        Size=findViewById(R.id.editText11);
        Drinkimage=findViewById(R.id.imageView);
        description=findViewById(R.id.editText14);
        name=findViewById(R.id.editText15);
        prize=findViewById(R.id.editText13);
        Add=findViewById(R.id.button3);
        camera=findViewById(R.id.camera1);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photopicker=new Intent(Intent.ACTION_PICK);
                photopicker.setType("image/*");
                startActivityForResult(photopicker,1);
            }
        });
        progressDialog=new ProgressDialog(com.shivam.shop.Add.this);
        progressDialog.setTitle("Drink Uploading.");
        progressDialog.setMessage("Please wait......");
        progressDialog.setCancelable(false);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                Name=name.getText().toString().trim();
                Prize=prize.getText().toString().trim();
                Description=description.getText().toString().trim();
                DrinkSize=Size.getText().toString().trim();
                if (Name.isEmpty()){
                    name.setError("Enter the name of the drink");
                    name.requestFocus();
                    return;
                }
                if (uri==null){
                    Toast.makeText(com.shivam.shop.Add.this, "Please Upload drink Image", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Prize.isEmpty()){
                    prize.setError("Enter the prize of the drink");
                    prize.requestFocus();
                    return;
                }
                if (DrinkSize.isEmpty()){
                    Size.setError("Enter the Size of the drink");
                    Size.requestFocus();
                    return;
                }
                progressDialog.show();

                StorageReference storageReference= FirebaseStorage.getInstance()
                        .getReference().child("Drink Image").child(uri.getLastPathSegment());
                storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri urlImage = uriTask.getResult();
                        imageUrl = urlImage.toString();
                      //  uploadData();
                        uploadData2();
                        progressDialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                    }
                });

            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            //startActivity(new Intent(Registeration.this,Home.class));
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            uri = data.getData();
            Drinkimage.setImageURI(uri);

        }
        else Toast.makeText(this, "You haven't picked image", Toast.LENGTH_SHORT).show();

    }


//    public void uploadData(){
//        firebaseAuth=FirebaseAuth.getInstance();
//
//        Drinkdata drinkdata=new Drinkdata(Name,Prize,Description,imageUrl);
//        String myCurrentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
//        String uid=firebaseAuth.getCurrentUser().getUid();
//
//        FirebaseDatabase.getInstance().getReference("Users").child("Owners").child(uid)
//                .child("AllDrinks").child(myCurrentDateTime).setValue(drinkdata).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()){
//                    Toast.makeText(Add.this, "Drink Uploaded", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(Add.this, "uploading of the Drink is Failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//    }
    public void uploadData2(){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        final String uid=firebaseAuth.getCurrentUser().getUid();
        DatabaseReference databaseReference=firebaseDatabase.getReference("Owners").child(uid).child("profile");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    RegisteredData registeredData = dataSnapshot.getValue(RegisteredData.class);
                    String Shopname = registeredData.getShopName();
                    String myCurrentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                    Drinkdata drinkdata = new Drinkdata(Shopname,Name,Prize,DrinkSize,Description,imageUrl);

                    FirebaseDatabase.getInstance().getReference("Owners").child(uid)
                            .child("All Drinks").child(myCurrentDateTime).setValue(drinkdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(com.shivam.shop.Add.this, "Drink Uploaded", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(com.shivam.shop.Add.this, "uploading of the Drink is Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                FirebaseDatabase.getInstance().getReference("All Drinks")
                        .child(Shopname).child(myCurrentDateTime).setValue(drinkdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(com.shivam.shop.Add.this, "Drink Uploaded", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(com.shivam.shop.Add.this, "uploading of the Drink is Failed", Toast.LENGTH_SHORT).show();
                    }
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.addsnackbar);
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

