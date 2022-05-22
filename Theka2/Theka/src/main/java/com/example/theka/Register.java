package com.shivam.theka;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class Register extends AppCompatActivity {
    String Name, Email, Contact, Address, Password, Shopname, GSTNO;
    CheckBox checkBox;
    private Button register;
    private EditText editname, editemail, editcontact, editpassword, editshopname, editgst, editaddress;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById(R.id.signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Signin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


            }
        });
        checkConnection();
        findViewById(R.id.condtion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Termscondtions.class));
            }
        });
        editname = (EditText) findViewById(R.id.editText);
        editemail = (EditText) findViewById(R.id.editText3);
        editcontact = (EditText) findViewById(R.id.editText2);
        editpassword = (EditText) findViewById(R.id.editText4);
        editshopname = (EditText) findViewById(R.id.editText6);
        editgst = (EditText) findViewById(R.id.editText7);
        editaddress = (EditText) findViewById(R.id.editText5);
        register = (Button) findViewById(R.id.button);
        checkBox = findViewById(R.id.checkbox1);

        firebaseAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                Name = editname.getText().toString().trim();
                Email = editemail.getText().toString().trim();
                Contact = editcontact.getText().toString().trim();
                Password = editpassword.getText().toString().trim();
                Shopname = editshopname.getText().toString().trim();
                Address = editaddress.getText().toString().trim();
                GSTNO = editgst.getText().toString().trim();
                final ProgressDialog progressDialog = new ProgressDialog(Register.this);
                progressDialog.setTitle("Registering");
                progressDialog.setMessage("Please wait.....");
                progressDialog.setCancelable(false);
                if (Name.isEmpty()) {
                    editname.setError("please enter your name.");
                    editname.requestFocus();
                    return;
                }
                if (Email.isEmpty()) {
                    editemail.setError("please enter your valid EmailId.");
                    editemail.requestFocus();
                    return;
                }
                if (Contact.isEmpty() || (Contact.length() < 10)) {
                    editcontact.setError("please enter your valid ContactNo.");
                    editcontact.requestFocus();
                    return;
                }
                if (Password.isEmpty()) {
                    editpassword.setError("please enter your Password.");
                    editpassword.requestFocus();
                    return;
                }
                if (Password.length() < 6) {
                    editpassword.setError("Password character must be greater than 6");
                    editpassword.requestFocus();
                    return;
                }
                if (Shopname.isEmpty()) {
                    editshopname.setError("please enter your Shopname");
                    editshopname.requestFocus();
                    return;
                }
                if (Address.isEmpty()) {
                    editaddress.setError("please enter your address");
                    editaddress.requestFocus();
                    return;
                }
                if (Address.length() < 15) {
                    editaddress.setError("please enter your full address with proper location or pincode");
                    editaddress.requestFocus();
                    return;
                }
                if (GSTNO.isEmpty() || GSTNO.length() < 15) {
                    editgst.setError("Please enter valid GST Number of the shop");
                    editgst.requestFocus();
                    return;
                }
                if (!checkBox.isChecked()) {
                    Toast.makeText(Register.this, "please accept the Terms and Conditions", Toast.LENGTH_SHORT).show();
                    return;
                }


                progressDialog.show();
                if (!(GSTNO.isEmpty())) {
                    firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            sendUserData1();
                                            progressDialog.dismiss();

                                            Intent i = new Intent(Register.this, Signin.class);
                                            //    i.putExtra("Shopname",Shopname);
                                            startActivity(i);
                                            finish();


                                            Toast.makeText(Register.this, "Verification Email has been sent to your registered Emailid.Please verify", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                checkConnection();
                            }

                        }
                    });

                }

            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            //startActivity(new Intent(Registeration.this,Home.class));
        }

//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    register.setEnabled(true);
//                }else {
//                    register.setEnabled(false);
//                }
//            }
//        });
//


    }

    public void sendUserData1() {

        RegisteredData registeredData = new RegisteredData(Name, Contact, Email, Password, Shopname, GSTNO, Address);

        String uid = firebaseAuth.getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("Owners")
                .child(uid).child("profile").setValue(registeredData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, "Registeration failed", Toast.LENGTH_SHORT).show();
            }
        });

        String myCurrentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("All Owners")
                .child(myCurrentDateTime).setValue(registeredData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, "Registeration failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork = manager.getActiveNetworkInfo();
        View view = findViewById(R.id.registersnackbar);
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

