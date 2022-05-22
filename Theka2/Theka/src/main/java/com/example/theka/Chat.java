package com.shivam.theka;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Calendar;

public class Chat extends AppCompatActivity {
    EditText chat;
    Button button, email, Facebook;
    String Shopname, Chat, Contact, Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        checkConnection();
        chat = findViewById(R.id.Chatt);
        email = findViewById(R.id.email1);
//        Facebook=findViewById(R.id.facebook1);
//        Facebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkConnection();
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.facebook.com/profile.php?id=100014196195756")));
//            }
//        });

        button = findViewById(R.id.button6);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final String uid = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Owners").child(uid).child("profile");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                RegisteredData registeredData = dataSnapshot.getValue(RegisteredData.class);
                Shopname = registeredData.getShopName();
                Contact = registeredData.getContact();
                Email = registeredData.getEmail();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipient = "thelitheka2@gmail.com";
                String subject = Shopname + "\t\tHas a query," + "\n" + Contact + "\t\t" + Email;
                //  String message="Hii sir/madam my name is"+Name+"and my contactNo and mailid is"+Contact+Email;

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                //   intent.putExtra(Intent.EXTRA_TEXT,message);
                try {
                    startActivity(Intent.createChooser(intent, "Choose an Email Client"));
                    //   Toast.makeText(Chat.this, "Sending....", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    checkConnection();
                    Toast.makeText(com.shivam.theka.Chat.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                Chat = chat.getText().toString().trim();
                if (Chat.isEmpty()) {
                    chat.setError("Please provide your feedback");
                    chat.requestFocus();
                    return;
                }
                String myCurrentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                Feedbackdata feedbackdata = new Feedbackdata(Chat, Shopname, Contact, Email, uid, myCurrentDateTime);

                FirebaseDatabase.getInstance().getReference("FeedBacks").child("Owners")
                        .child(Shopname).child(myCurrentDateTime).setValue(feedbackdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(com.shivam.theka.Chat.this, "Thanks for the Feedback. we will get back to you soon!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(com.shivam.theka.Chat.this, "Feedback did not submit at this time", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork = manager.getActiveNetworkInfo();
        View view = findViewById(R.id.chatsnackbar);
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
