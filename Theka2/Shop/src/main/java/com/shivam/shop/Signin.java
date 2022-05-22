package com.shivam.shop;

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

    Button textView;
    private Button button;
    private EditText edid,edpass;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
   // private TextView forgot;
    String Shopname;
    ProgressDialog progressDialog;
    private long backtime;
    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        checkConnection();
        textView=findViewById(R.id.register1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.shivam.shop.Signin.this,Register.class));
            }
        });
        findViewById(R.id.forgotid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.shivam.shop.Signin.this,Forgotpassword.class));
            }
        });
        button = (Button) findViewById(R.id.button2);
        edid=(EditText)findViewById(R.id.editText8);
        edpass=(EditText)findViewById(R.id.editText9);
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth=FirebaseAuth.getInstance();
        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                if (firebaseUser!=null){
                    // Toast.makeText(Signin.this,"please enter your valid EmailID and Password",Toast.LENGTH_SHORT).show();

                }


            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email=edid.getText().toString().trim();
                String password=edpass.getText().toString().trim();

                 progressDialog=new ProgressDialog(com.shivam.shop.Signin.this);
                 progressDialog.setTitle("Signing......");
                progressDialog.setMessage("Please wait.....");
               progressDialog.setCancelable(false);
             //  progressDialog.setMax(100);

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
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(com.shivam.shop.Signin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                if (firebaseAuth.getCurrentUser().isEmailVerified()){
                                    Intent i2=new Intent(com.shivam.shop.Signin.this,MainActivity.class);
                                    // i2.putExtra("Shopname",Shopname);
                                    startActivity(i2);
                                    finish();
                                    progressDialog.dismiss();
                                    Toast.makeText(com.shivam.shop.Signin.this, "You are Signed In Successfully", Toast.LENGTH_SHORT).show();

                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(com.shivam.shop.Signin.this, "Please verify your email through link.send on your registered emailid", Toast.LENGTH_SHORT).show();
                                }
                                }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(com.shivam.shop.Signin.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                            }


                        }
                    });
                }


            }
        });
        checkConnection();

    }
    @Override
    protected void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthStateListener);
    }

public void checkConnection(){
    ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
    View view=findViewById(R.id.snackbarr);
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
}
