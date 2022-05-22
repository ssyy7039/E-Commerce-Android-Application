package com.shivam.theka2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Cartadapter2 extends RecyclerView.Adapter<cartViewHolder2> {
    private Context context;
    private List<cartdata> cartdataList;

    public Cartadapter2(Context context, List<cartdata> cartdataList) {
        this.context = context;
        this.cartdataList = cartdataList;
    }

    public cartViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart,parent,false);


        return  new cartViewHolder2(view);    }

    @Override
    public void onBindViewHolder(@NonNull cartViewHolder2 holder, int position) {
        Glide.with(context)
                .load(cartdataList.get(position).getImage())
                .into(holder.imageView);
        //holder.imageView.setImageResource(Integer.parseInt(myVehicaleList.get(position).getItemImage()));
        holder.Name.setText(cartdataList.get(position).getDrinkName());
        holder.size.setText(cartdataList.get(position).getDrinkSize()+"ml");
        holder.Prize.setText(cartdataList.get(position).getDrinkPrice()+"$");
        holder.Quantity.setText("Quantity="+cartdataList.get(position).getQuantity());

        
    }

    @Override
    public int getItemCount() {
        return cartdataList.size();
    }
}

class cartViewHolder2 extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView Name,Prize,size,Quantity;
    CardView cardView;
    // FloatingActionButton edit ,delete;
    public cartViewHolder2(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.ivImage);
        size=itemView.findViewById(R.id.size2);
        Name=itemView.findViewById(R.id.name);
        Prize=itemView.findViewById(R.id.price2);
        Quantity=itemView.findViewById(R.id.quantity);
        cardView=itemView.findViewById(R.id.cartCardView);
    }
}
