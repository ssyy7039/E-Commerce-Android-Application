package com.shivam.theka2;

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

import java.util.List;

public class Orderadapter extends RecyclerView.Adapter<OrderViewHolder>{
    private Context context;
    private List<Requests> orderDataList;

    public Orderadapter(Context context, List<Requests> orderDataList) {
        this.context = context;
        this.orderDataList = orderDataList;
    }

    @Override
    public com.shivam.theka2.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardorder2,parent,false);


        return  new com.shivam.theka2.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final com.shivam.theka2.OrderViewHolder holder, int position) {
        holder.time.setText(orderDataList.get(position).getTime());
        holder.orderid.setText(orderDataList.get(position).getOrderId());
      //  holder.drinkname.setText(orderDataList.get(position).getDrinkname());
        holder.shopname.setText(orderDataList.get(position).getShopname());
        holder.status.setText(orderDataList.get(position).getStatus());
        holder.product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, com.shivam.theka2.Orderproducts.class);
                intent.putExtra("OrderId",orderDataList.get(holder.getAdapterPosition()).getOrderId());
               intent.putExtra("Contact",orderDataList.get(holder.getAdapterPosition()).getContact());
                intent.putExtra("Address",orderDataList.get(holder.getAdapterPosition()).getAddress());
               intent.putExtra("Name",orderDataList.get(holder.getAdapterPosition()).getName());
        //        intent.putExtra("Drinksize",orderDataList.get(holder.getAdapterPosition()).getDrinkSize());
//                intent.putExtra("Quantity",orderDataList.get(holder.getAdapterPosition()).getQuantity());
               intent.putExtra("UID",orderDataList.get(holder.getAdapterPosition()).getUid());
                intent.putExtra("Amount",orderDataList.get(holder.getAdapterPosition()).getAmount());
                intent.putExtra("Time",orderDataList.get(holder.getAdapterPosition()).getTime());
                intent.putExtra("Key",orderDataList.get(holder.getAdapterPosition()).getKey());

                intent.putExtra("Status",orderDataList.get(holder.getAdapterPosition()).getStatus());

                intent.putExtra("Shopname",orderDataList.get(holder.getAdapterPosition()).getShopname());

                context.startActivity(intent);



            }
        });

    }

    @Override
    public int getItemCount() {
        return orderDataList.size();
    }
}

class OrderViewHolder extends RecyclerView.ViewHolder{
    TextView time,orderid,shopname,status;
    Button product;
    CardView cardView;

    public OrderViewHolder(View itemView) {
        super(itemView);
        time=itemView.findViewById(R.id.cardtime);
        orderid=itemView.findViewById(R.id.cardorderid);
        product=itemView.findViewById(R.id.orderproduct);
        status=itemView.findViewById(R.id.cardstatus);
        shopname=itemView.findViewById(R.id.cardshopname);
        cardView=itemView.findViewById(R.id.ordercard);
    }
}