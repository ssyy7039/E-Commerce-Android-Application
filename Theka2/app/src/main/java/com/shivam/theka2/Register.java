package com.shivam.theka2;

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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class Register extends AppCompatActivity {

    private Button register,verify;
    private EditText editname,editemail,editcontact,editpassword,editaddress;
    private FirebaseAuth firebaseAuth;
    String Name,Email,Contact,Address,Password;
    CheckBox checkBox;
    private CountryCodePicker countryCodePicker;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private long backtime;
    Toast toast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        checkConnection();
        editname=(EditText)findViewById(R.id.editText);
      //  otp=(EditText)findViewById(R.id.otp);
        countryCodePicker=findViewById(R.id.ccp);
        editemail=(EditText)findViewById(R.id.editText3);
        editcontact=(EditText)findViewById(R.id.editText2);
      //  editpassword=(EditText)findViewById(R.id.editText4);
        editaddress=(EditText)findViewById(R.id.editText5);
      //  verify=findViewById(R.id.verify);
       countryCodePicker.registerCarrierNumberEditText(editcontact);
        checkBox=findViewById(R.id.checkbox1);
        register = (Button) findViewById(R.id.button);
//        findViewById(R.id.signin4).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkConnection();
//                Intent intent=new Intent(com.example.theka2.Register.this,Signin.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//
//
//            }
//        });
        findViewById(R.id.condtion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.shivam.theka2.Register.this,Termscondition.class));
            }
        });

      //  firebaseAuth= FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                Name=editname.getText().toString().trim();
                Email=editemail.getText().toString().trim();
                Contact=countryCodePicker.getFullNumberWithPlus().replace(" ","");
             //   Password=editpassword.getText().toString().trim();
                Address=editaddress.getText().toString().trim();
                final ProgressDialog progressDialog=new ProgressDialog(com.shivam.theka2.Register.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait");
                progressDialog.setTitle("Registering.....");
                if (editcontact.getText().toString().isEmpty() || (editcontact.getText().toString().length()<10)){
                    editcontact.setError("please enter your valid ContactNo.");
                    editcontact.requestFocus();
                    return;
                }
                if (Name.isEmpty()){
                    editname.setError("please enter your name.");
                    editname.requestFocus();
                    return;
                }
                if (Email.isEmpty()){
                    editemail.setError("please enter your valid EmailId.");
                    editemail.requestFocus();
                    return;
                }

//                if (Password.isEmpty()){
//                    editpassword.setError("please enter your Password.");
//                    editpassword.requestFocus();
//                    return;
//                }
//                if (Password.length()<6){
//                    editpassword.setError("Password character must be greater than 6");
//                    editpassword.requestFocus();
//                    return;
//                }

                if (Address.isEmpty()){
                    editaddress.setError("please enter your address");
                    editaddress.requestFocus();
                    return;
                }
                if (Address.length()<15){
                    editaddress.setError("please enter your full address with proper location and valid pincode");
                    editaddress.requestFocus();
                    return;
                }
                if (!checkBox.isChecked()){
                    Toast.makeText(com.shivam.theka2.Register.this, "Please accept Terms and Conditions", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.show();
                Intent intent=new Intent(com.shivam.theka2.Register.this,Otp.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Name",Name);
                intent.putExtra("Contact",Contact);
                intent.putExtra("Emailid",Email);
              //  intent.putExtra("Password",Password);
                intent.putExtra("Address",Address);
                startActivity(intent);
                finish();
                Toast.makeText(Register.this, "OTP has been sent to your given number", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();



//                if(!(Email.isEmpty())){
//                    firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()){
//                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//
//
//                                            sendUserData1();
//                                            progressDialog.dismiss();
//
//                                            Intent i = new Intent(Register.this, Signin.class);
//                                            //  i.putExtra("mobile", Contact);
//                                            startActivity(i);
//
//
//                                            Toast.makeText(Register.this, "Emailid verification link has been sent on your registered emailid", Toast.LENGTH_SHORT).show();
//                                        }else {
//                                            progressDialog.dismiss();
//                                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//
//                            }
//                            else{
//                                checkConnection();
//                                progressDialog.dismiss();
//                                Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//
//                            }
//
//                        }
//                    });
//
//                }

            }
        });
//        firebaseAuth=FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
//        if(firebaseUser!=null){
//            //startActivity(new Intent(Registeration.this,Home.class));
//        }





    }


//    public void sendUserData1(){
//
//        customerdata customerdata = new customerdata(Name,Contact,Email,Password,Address);
//
//       String uid=firebaseAuth.getCurrentUser().getUid();
//        FirebaseDatabase.getInstance().getReference("All Customers")
//                .child(uid).child("profile").setValue(customerdata).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()){
//                    finish();
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                checkConnection();
//                Toast.makeText(com.example.theka2.Register.this, "Registeration failed", Toast.LENGTH_SHORT).show();
//            }
//        });


 //   }
    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.registersnackbar);
        Snackbar snackbar= Snackbar.make(view,"No Internet connection available", Snackbar.LENGTH_LONG);
        snackbar.setDuration(10000);

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
//    @Override
//    protected void onStart(){
//        super.onStart();
//        firebaseAuth.addAuthStateListener(mAuthStateListener);
//    }
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

