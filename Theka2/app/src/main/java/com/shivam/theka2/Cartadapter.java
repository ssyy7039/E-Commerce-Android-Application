package com.shivam.theka2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Cartadapter extends RecyclerView.Adapter<cartViewHolder> {
    private Context context;
    private List<cartdata> cartdataList;

    public Cartadapter(Context context, List<cartdata> cartdataList) {
        this.context = context;
        this.cartdataList = cartdataList;
    }

    public cartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart,parent,false);


        return  new cartViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull cartViewHolder holder, int position) {
        Glide.with(context)
                .load(cartdataList.get(position).getImage())
                .into(holder.imageView);
        //holder.imageView.setImageResource(Integer.parseInt(myVehicaleList.get(position).getItemImage()));
        holder.Name.setText(cartdataList.get(position).getDrinkName());
        holder.size.setText(cartdataList.get(position).getDrinkSize()+"ml");
        holder.Prize.setText(cartdataList.get(position).getDrinkPrice()+"$");
        holder.Quantity.setText("Quantity="+cartdataList.get(position).getQuantity());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence options[]=new CharSequence[]
                        {
                                "Edit",
                                "Remove"
                        };
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                        builder.setTitle("Cart Options:");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which ==0)
                                {
                                    Intent intent=new Intent(context, Drinkdetails.class);
                                    intent.putExtra("Name",cartdataList.get(position).getDrinkName());
                                    intent.putExtra("Size",cartdataList.get(position).getDrinkSize());
                                    intent.putExtra("Price",cartdataList.get(position).getDrinkPrice());
                                    intent.putExtra("Image",cartdataList.get(position).getImage());
                                    intent.putExtra("KeyValue",cartdataList.get(position).getKey());
                                    intent.putExtra("Shopname",cartdataList.get(position).getShopname());

                                    context.startActivity(intent);

                                }
                                if (which == 1){
                                    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference("All Customers").child(uid).child("Cart Items").child(cartdataList.get(position).getShopname());
                                    databaseReference2.child(cartdataList.get(position).getKey()).removeValue();
                                    Intent intent=new Intent(context,cartdetails.class);
                                    intent.putExtra("Shopname",cartdataList.get(position).getShopname());
                                 //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                    Toast.makeText(context, "Drink removed  Successfully", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

builder.show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return cartdataList.size();
    }
}
class cartViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView Name,Prize,size,Quantity;
    CardView cardView;
   // FloatingActionButton edit ,delete;
    public cartViewHolder(@NonNull View itemView) {
        super(itemView);
          imageView=itemView.findViewById(R.id.ivImage);
        size=itemView.findViewById(R.id.size2);
        Name=itemView.findViewById(R.id.name);
        Prize=itemView.findViewById(R.id.price2);
        Quantity=itemView.findViewById(R.id.quantity);
        cardView=itemView.findViewById(R.id.cartCardView);
    }
}
