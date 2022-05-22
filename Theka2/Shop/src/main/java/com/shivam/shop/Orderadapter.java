package com.shivam.shop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Orderadapter extends RecyclerView.Adapter<OrderViewHolder>{
    private Context context;
    private List<Requests> orderDataList;

    public Orderadapter(Context context, List<Requests> orderDataList) {
        this.context = context;
        this.orderDataList = orderDataList;
    }

    @Override
    public com.shivam.shop.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardorder2,parent,false);


        return  new com.shivam.shop.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final com.shivam.shop.OrderViewHolder holder, int position) {
        holder.time.setText(orderDataList.get(position).getTime());
        holder.orderid.setText(orderDataList.get(position).getOrderId());
        holder.Name.setText(orderDataList.get(position).getName());
        holder.address.setText(orderDataList.get(position).getAddress());
        holder.status.setText(orderDataList.get(position).getStatus());

        holder.product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Orderproducts.class);
                intent.putExtra("OrderId",orderDataList.get(holder.getAdapterPosition()).getOrderId());
                intent.putExtra("Contact",orderDataList.get(holder.getAdapterPosition()).getContact());
                intent.putExtra("Address",orderDataList.get(holder.getAdapterPosition()).getAddress());
                intent.putExtra("Amount",orderDataList.get(holder.getAdapterPosition()).getAmount());
                intent.putExtra("Time",orderDataList.get(holder.getAdapterPosition()).getTime());
                intent.putExtra("Key",orderDataList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("Name",orderDataList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("UID",orderDataList.get(holder.getAdapterPosition()).getUid());
                intent.putExtra("Shopname",orderDataList.get(holder.getAdapterPosition()).getShopname());
                intent.putExtra("Status",orderDataList.get(holder.getAdapterPosition()).getStatus());


                context.startActivity(intent);



            }
        });
    }

    @Override
    public int getItemCount() {
        return orderDataList.size();
    }
    public void filteredList(ArrayList<Requests> filterList) {
        orderDataList=filterList;
        notifyDataSetChanged();
    }
}

class OrderViewHolder extends RecyclerView.ViewHolder{
    TextView time,orderid,Name,address,status;
    Button product;
    CardView cardView;

    public OrderViewHolder(View itemView) {
        super(itemView);
        time=itemView.findViewById(R.id.cardtime);
        orderid=itemView.findViewById(R.id.cardorderid);
        product=itemView.findViewById(R.id.orderproduct);
        status=itemView.findViewById(R.id.cardstatus);
        Name=itemView.findViewById(R.id.cardname);
        address=itemView.findViewById(R.id.cardaddress);
        cardView=itemView.findViewById(R.id.ordercard);
    }
}