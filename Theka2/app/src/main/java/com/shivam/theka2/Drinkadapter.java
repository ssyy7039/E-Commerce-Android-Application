package com.shivam.theka2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Drinkadapter extends RecyclerView.Adapter<DrinkViewHolder> {
    private Context context;
    private List<com.shivam.theka2.Drinkdata> drinkdataList;

    public Drinkadapter(Context context, List<com.shivam.theka2.Drinkdata> drinkdataList) {
        this.context = context;
        this.drinkdataList = drinkdataList;
    }

    @Override
    public com.shivam.theka2.DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.carddrink,parent,false);


        return  new com.shivam.theka2.DrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final com.shivam.theka2.DrinkViewHolder holder, int position) {
        Glide.with(context)
                .load(drinkdataList.get(position).getImageurl())
                .into(holder.imageView);
        //holder.imageView.setImageResource(Integer.parseInt(myVehicaleList.get(position).getItemImage()));
        holder.Name.setText(drinkdataList.get(position).getDrinkname());
        holder.size.setText(drinkdataList.get(position).getDrinkSize()+"ml");
        holder.Prize.setText(drinkdataList.get(position).getDrinkPrice()+"$");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, com.shivam.theka2.Drinkdetails.class);
                intent.putExtra("Image",drinkdataList.get(holder.getAdapterPosition()).getImageurl());
                intent.putExtra("KeyValue",drinkdataList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("Name",drinkdataList.get(holder.getAdapterPosition()).getDrinkname());
                intent.putExtra("Price",drinkdataList.get(holder.getAdapterPosition()).getDrinkPrice());
                intent.putExtra("Size",drinkdataList.get(holder.getAdapterPosition()).getDrinkSize());
                intent.putExtra("Shopname",drinkdataList.get(holder.getAdapterPosition()).getShopname());
                intent.putExtra("Description",drinkdataList.get(holder.getAdapterPosition()).getDescription());
                context.startActivity(intent);



            }
        });


    }


    @Override
    public int getItemCount() {
        return drinkdataList.size();

    }
    public void filteredList(ArrayList<com.shivam.theka2.Drinkdata> filterList) {
        drinkdataList=filterList;
        notifyDataSetChanged();
    }
}

class DrinkViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView  Name,Prize,size;
    CardView cardView;

    public DrinkViewHolder(View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.ivImage);
        size=itemView.findViewById(R.id.size);
        Name=itemView.findViewById(R.id.name);
        Prize=itemView.findViewById(R.id.prize);
        cardView=itemView.findViewById(R.id.myCardView);
    }
}
