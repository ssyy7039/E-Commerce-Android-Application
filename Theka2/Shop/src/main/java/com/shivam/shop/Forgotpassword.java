package com.shivam.shop;

import android.content.Context;
import android.content.Intent;
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

public class Forgotpassword extends AppCompatActivity {
    EditText email;
    Button button;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        checkConnection();
        email=findViewById(R.id.forgot);
        button=findViewById(R.id.link);
        firebaseAuth= FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                String Email=email.getText().toString().trim();
                if(Email.isEmpty()){
                    email.setError("enter email");
                    email.requestFocus();
                }
                else {
                    firebaseAuth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(com.shivam.shop.Forgotpassword.this, "Password reset email has been sent", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(com.shivam.shop.Forgotpassword.this, com.shivam.shop.Signin.class));
                        }
                    });
                }
            }
        });
    }
    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.forgotsnackbar);
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
