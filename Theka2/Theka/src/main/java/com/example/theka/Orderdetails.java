package com.shivam.theka;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Orderdetails extends AppCompatActivity {
    TextView time, orderid, name, contact, address, drinkname, size, quantity, price, amount;
    Button delete;
    String shopname, UID;
    String Key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);
        checkConnection();
        time = findViewById(R.id.time2);
        orderid = findViewById(R.id.orderid2);
        name = findViewById(R.id.name5);
        contact = findViewById(R.id.Contact);
        address = findViewById(R.id.address);
        drinkname = findViewById(R.id.drinkname);
        size = findViewById(R.id.drinksize);
        quantity = findViewById(R.id.drinkquantity);
        price = findViewById(R.id.drinkprice);
        amount = findViewById(R.id.Amount);
        delete = findViewById(R.id.delete);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            checkConnection();
            time.setText(bundle.getString("Time"));
            orderid.setText(bundle.getString("OrderId"));
            shopname = bundle.getString("Shopname");
            drinkname.setText(bundle.getString("Drinkname"));
            size.setText(bundle.getString("Drinksize"));
            quantity.setText(bundle.getString("Quantity"));
            price.setText(bundle.getString("Price"));
            amount.setText(bundle.getString("Amount"));
            contact.setText(bundle.getString("Contact"));
            address.setText(bundle.getString("Address"));
            name.setText(bundle.getString("Name"));
            Key = bundle.getString("Key");
            UID = bundle.getString("UID");
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkConnection();
                Dialog();
            }
        });
    }

    public void Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure You want to Delete the Order? " +
                "When you delete the order from your side it also gets deleted from the Customer side" +
                "  So before cancelling or deleting the order kindly contact with the Customer to avoid any difficulties");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkConnection();
                final ProgressDialog progressDialog = new ProgressDialog(Orderdetails.this);
                progressDialog.setMessage("Deleting Order......");

                progressDialog.show();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    //startActivity(new Intent(Registeration.this,Home.class));
                }


                final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("All orders").child(shopname);
                databaseReference2.child(Key).removeValue();
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("All Customers").child(UID).child("All Orders");
                databaseReference.child(Key).removeValue();
                progressDialog.dismiss();
                Toast.makeText(Orderdetails.this, "Your Order deleted", Toast.LENGTH_SHORT).show();
                //  startActivity(new Intent(getApplicationContext(),Allorders.class));
                finish();


            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        AlertDialog dialog = builder.show();
    }

    public void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork = manager.getActiveNetworkInfo();
        View view = findViewById(R.id.orderdetailssnackbar);
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
