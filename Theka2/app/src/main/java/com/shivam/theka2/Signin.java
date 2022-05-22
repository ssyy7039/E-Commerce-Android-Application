package com.shivam.theka2;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signin extends AppCompatActivity {

    Button register;
    private Button button;
    private EditText edid,edpass;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
   // private TextView forgot;
    private long backtime;
    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        register=findViewById(R.id.register);
        checkConnection();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.shivam.theka2.Signin.this, com.shivam.theka2.Register.class));
            }
        });
        findViewById(R.id.forgott).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                startActivity(new Intent(com.shivam.theka2.Signin.this, com.shivam.theka2.Forgotpassword.class));
            }
        });
        button = (Button) findViewById(R.id.button2);
        edid=(EditText)findViewById(R.id.editText8);
        edpass=(EditText)findViewById(R.id.editText9);
        databaseReference= FirebaseDatabase.getInstance().getReference("All Customers");
        firebaseAuth=FirebaseAuth.getInstance();
        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                if (firebaseUser!=null){
                   // startActivity(new Intent(Signin.this,MainActivity.class));

                }


            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                String email=edid.getText().toString().trim();
                String password=edpass.getText().toString().trim();
                final ProgressDialog progressDialog=new ProgressDialog(com.shivam.theka2.Signin.this);
                progressDialog.setMessage("Please wait");
                progressDialog.setTitle("Signing.....");
                progressDialog.setCancelable(false);
                if(email.isEmpty()) {
                    edid.setError("Please enter your Emailid");
                    edid.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    edpass.setError("Please enter your Password");
                    edpass.requestFocus();
                    return;
                }

//                if (email.equals("Admin@Shivam") && password.equals("shivam@revati")){
//                    Intent i=new Intent(Signin.this,Admin.class);
//                    startActivity(i);
//                    return;
//                }
                progressDialog.show();
                if (!(email.isEmpty() && password.isEmpty())){
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(com.shivam.theka2.Signin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                if (firebaseAuth.getCurrentUser().isEmailVerified()){
                                    Intent i2=new Intent(com.shivam.theka2.Signin.this,MainActivity.class);
                                    // i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    //i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i2);
                                    finish();
                                    progressDialog.dismiss();
                                    Toast.makeText(com.shivam.theka2.Signin.this, "You are Signed In Successfully", Toast.LENGTH_SHORT).show();

                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(com.shivam.theka2.Signin.this, "Please verify your Emailid through link send on your registered Emailid", Toast.LENGTH_SHORT).show();
                                }
                                 }
                            else {
                                checkConnection();
                                progressDialog.dismiss();
                                Toast.makeText(com.shivam.theka2.Signin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }


                        }
                    });
                }


            }
        });


    }
    @Override
    protected void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthStateListener);
    }
    @Override
    public void onBackPressed() {
        if (backtime+2000>System.currentTimeMillis()){
            toast.cancel();
            super.onBackPressed();
            return;
        }
        else {
            toast= Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT);
            toast.show();
        }
        backtime=System.currentTimeMillis();

    }
    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.signinsnackbar);
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
