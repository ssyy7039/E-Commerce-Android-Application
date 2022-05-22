package com.shivam.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Updatedrink extends AppCompatActivity {

    EditText name, price, description,size;
    Button update;
    FloatingActionButton camera;
    ImageView image;
    Uri uri;
    String url;
    String Name, Price, Description,Size,Shopname;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    String key;

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//
//            uri = data.getData();
//            image.setImageURI(uri);
//
//
//        } else Toast.makeText(this, "You haven't picked image", Toast.LENGTH_SHORT).show();
//
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatedrink);
        image = findViewById(R.id.imageView);
        name = findViewById(R.id.editText15);
        price = findViewById(R.id.editText13);
        size = findViewById(R.id.editText11);
        description = findViewById(R.id.editText14);
        update = findViewById(R.id.button3);
        camera = findViewById(R.id.camera2);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Glide.with(com.shivam.shop.Updatedrink.this)
                    .load(bundle.getString("Imageurl"))
                    .into(image);
            name.setText(bundle.getString("Name"));
            price.setText(bundle.getString("Price"));
            size.setText(bundle.getString("Size"));
            description.setText(bundle.getString("Description"));
            key = bundle.getString("key");
            Shopname=bundle.getString("Shopname");
            url = bundle.getString("Imageurl");

        }
//        final DatabaseReference databaseReference=firebaseDatabase.getReference("Users").child(uid).child("Added_Vehicales");
//        final DatabaseReference databaseReference1=firebaseDatabase.getReference("Added_Vehicals");

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photopicker = new Intent(Intent.ACTION_PICK);
                photopicker.setType("image/*");
                startActivityForResult(photopicker, 1);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = name.getText().toString().trim();
                Price = price.getText().toString().trim();
                Description = description.getText().toString().trim();
                Size=size.getText().toString().trim();
                final ProgressDialog progressDialog = new ProgressDialog(com.shivam.shop.Updatedrink.this);
                progressDialog.setMessage("Drink Updating.....");
                progressDialog.show();
                progressDialog.setCancelable(false);
                uploadData();
                progressDialog.dismiss();
//                storageReference = FirebaseStorage.getInstance()
//                        .getReference().child("Drink Image").child(uri.getLastPathSegment());
//                storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
//                        while (!uriTask.isComplete()) ;
//                        Uri urlImage = uriTask.getResult();
//                        url = urlImage.toString();
//                        uploadData();
//
//                        progressDialog.dismiss();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        progressDialog.dismiss();
//                    }
//                });
            }
        });
    }

    public void uploadData() {

        Drinkdata drinkdata = new Drinkdata(Shopname,Name,Price,Size,Description,url);
        String uid=firebaseAuth.getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Owners").child(uid).child("All Drinks").child(key);
        databaseReference.setValue(drinkdata).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                StorageReference storageReference1 = FirebaseStorage.getInstance().getReferenceFromUrl(url);
            //    storageReference1.delete();
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

                Toast.makeText(com.shivam.shop.Updatedrink.this, "Drink Data Updated", Toast.LENGTH_SHORT).show();

            }
        });
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("All Drinks").child(Shopname).child(key);
        databaseReference1.setValue(drinkdata).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                Toast.makeText(com.shivam.shop.Updatedrink.this, "Drink Data Updated", Toast.LENGTH_SHORT).show();

            }
        });


    }
}