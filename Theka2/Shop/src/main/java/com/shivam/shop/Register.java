package com.shivam.shop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class Register extends AppCompatActivity {
    private Button register,verify;
    private EditText editname,editemail,editcontact,editpassword,editshopname,editgst,editaddress;
    private FirebaseAuth firebaseAuth;
    String Name,Email,Contact,Address,Password,Shopname,GSTNO;
    CheckBox checkBox;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById(R.id.signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(com.shivam.shop.Register.this, com.shivam.shop.Signin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);



            }
        });
        verify=findViewById(R.id.verify);
        checkConnection();
findViewById(R.id.condtion).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(com.shivam.shop.Register.this,Termscondtions.class));
    }
});
        editname=(EditText)findViewById(R.id.editText);
        editemail=(EditText)findViewById(R.id.editText3);
        editcontact=(EditText)findViewById(R.id.editText2);
        editpassword=(EditText)findViewById(R.id.editText4);
        editshopname=(EditText)findViewById(R.id.editText6);
        editgst=(EditText)findViewById(R.id.editText7);
        editaddress=(EditText)findViewById(R.id.editText5);
        register = (Button) findViewById(R.id.button);
        checkBox=findViewById(R.id.checkbox1);
        progressDialog=new ProgressDialog(com.shivam.shop.Register.this);
        progressDialog.setTitle("Verifying ");
        progressDialog.setMessage("Please wait.....");
        progressDialog.setCancelable(false);

        firebaseAuth= FirebaseAuth.getInstance();
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                Email=editemail.getText().toString().trim();
                Contact=editcontact.getText().toString().trim();
                Password=editpassword.getText().toString().trim();
                Name=editname.getText().toString().trim();
                Shopname=editshopname.getText().toString().trim();
                Address=editaddress.getText().toString().trim();
                GSTNO=editgst.getText().toString().trim();
                if (Shopname.isEmpty()){
                    editshopname.setError("please enter your Shopname");
                    editshopname.requestFocus();
                    return;
                }
                if ( GSTNO.isEmpty() || GSTNO.length()<15){
                    editgst.setError("Please enter valid GST Number of the shop");
                    editgst.requestFocus();
                    return;
                }

                if (Contact.isEmpty() || (Contact.length()<10)){
                    editcontact.setError("please enter your valid ContactNo.");
                    editcontact.requestFocus();
                    return;
                }
                if (Email.isEmpty()){
                    editemail.setError("please enter your valid EmailId.");
                    editemail.requestFocus();
                    return;
                }
                if (Password.isEmpty()){
                    editpassword.setError("please enter your Password.");
                    editpassword.requestFocus();
                    return;
                }
                if (Password.length()<6){
                    editpassword.setError("Password character must be greater than 6");
                    editpassword.requestFocus();
                    return;
                }
                if (Name.isEmpty()){
                    editname.setError("please enter your name.");
                    editname.requestFocus();
                    return;
                }


                if (Address.isEmpty()){
                    editaddress.setError("please enter your address");
                    editaddress.requestFocus();
                    return;
                }
                if (Address.length()<15){
                    editaddress.setError("please enter your full address with proper location or pincode");
                    editaddress.requestFocus();
                    return;
                }

                if (!checkBox.isChecked()){
                   Toast.makeText(com.shivam.shop.Register.this, "please accept the Terms and Conditions", Toast.LENGTH_SHORT).show();
                   return;
               }
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(com.shivam.shop.Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        register.setVisibility(View.VISIBLE);
                                        verify.setVisibility(View.GONE);
                                        progressDialog.dismiss();
                                        Toast.makeText(com.shivam.shop.Register.this, "Verification Email has been sent to your registered Emailid.Please verify", Toast.LENGTH_SHORT).show();

                                    }else {
                                        progressDialog.dismiss();
                                        Toast.makeText(com.shivam.shop.Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(com.shivam.shop.Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            checkConnection();
                        }

                    }
                });

            }
        });
       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             ProgressDialog  progressDialog2=new ProgressDialog(com.shivam.shop.Register.this);
               progressDialog2.setTitle("Registering");
               progressDialog2.setMessage("Please wait.....");
               progressDialog2.setCancelable(false);

               progressDialog2.show();
               if (!(Email.isEmpty() && Password.isEmpty())){
                   firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(com.shivam.shop.Register.this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()){
                               if (firebaseAuth.getCurrentUser().isEmailVerified()){
                                   sendUserData1();
                                   Intent intent=new Intent(Register.this,MainActivity.class);
                                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                   startActivity(intent);
                                   finish();
                                   progressDialog2.dismiss();
                                   Toast.makeText(com.shivam.shop.Register.this, "You are Registered Successfully", Toast.LENGTH_SHORT).show();

                               }else {
                                   progressDialog2.dismiss();
                                   Toast.makeText(com.shivam.shop.Register.this, "Please verify your email through link.send on your registered emailid", Toast.LENGTH_SHORT).show();
                               }
                           }
                           else {
                               progressDialog2.dismiss();
                               Toast.makeText(com.shivam.shop.Register.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                           }


                       }
                   });
               }



           }
       });


    }
    public void sendUserData1(){
        String Key = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        com.shivam.shop.RegisteredData registeredData = new com.shivam.shop.RegisteredData(Name,Contact,Email,Password,Shopname,GSTNO,Address,Key);

       String uid=firebaseAuth.getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("Owners")
                .child(uid).child("profile").setValue(registeredData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(com.shivam.shop.Register.this, "Registeration failed", Toast.LENGTH_SHORT).show();
            }
        });
        FirebaseDatabase.getInstance().getReference("All Owners")
        .child(Key).setValue(registeredData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(com.shivam.shop.Register.this, "Registeration failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.registersnackbar);
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

