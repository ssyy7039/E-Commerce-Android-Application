package com.shivam.theka2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Shopadapter extends RecyclerView.Adapter<ShopViewHolder> {
    private Context context;
    private List<RegisteredData> registeredDataList;

    public Shopadapter(Context context, List<RegisteredData> registeredDataList) {
        this.context = context;
        this.registeredDataList = registeredDataList;
    }

    @Override
    public com.shivam.theka2.ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardshop,parent,false);


        return  new com.shivam.theka2.ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final com.shivam.theka2.ShopViewHolder holder, int position) {
        holder.Name.setText(registeredDataList.get(position).getShopName());
        holder.Location.setText(registeredDataList.get(position).getAddress());
        holder.contact1.setText(registeredDataList.get(position).getContact());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Alldrinks.class);
                intent.putExtra("Shopname",registeredDataList.get(holder.getAdapterPosition()).getShopName());
                intent.putExtra("Key",registeredDataList.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);



            }
        });

    }

    @Override
    public int getItemCount() {
        return registeredDataList.size();
    }
    public void filteredList(ArrayList<RegisteredData> filterList) {
        registeredDataList=filterList;
        notifyDataSetChanged();
    }
}

class ShopViewHolder extends RecyclerView.ViewHolder{
    TextView Name,Location,contact1;
    CardView cardView;

    public ShopViewHolder(View itemView) {
        super(itemView);
        Name=itemView.findViewById(R.id.shopname);
        contact1=itemView.findViewById(R.id.shopcontact);
        Location=itemView.findViewById(R.id.shoplocation);
        cardView=itemView.findViewById(R.id.myCardView2);
    }
}
