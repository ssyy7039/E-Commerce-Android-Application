package com.shivam.theka;

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

public class Orderadapter extends RecyclerView.Adapter<OrderViewHolder> {
    private Context context;
    private List<OrderData> orderDataList;

    public Orderadapter(Context context, List<OrderData> orderDataList) {
        this.context = context;
        this.orderDataList = orderDataList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardorder, parent, false);


        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, int position) {
        holder.time.setText(orderDataList.get(position).getTime());
        holder.orderid.setText(orderDataList.get(position).getOrderId());
        holder.drinkname.setText(orderDataList.get(position).getDrinkname());
        holder.customername.setText(orderDataList.get(position).getName());
        holder.address.setText(orderDataList.get(position).getAddress());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Orderdetails.class);
                intent.putExtra("OrderId", orderDataList.get(holder.getAdapterPosition()).getOrderId());
                intent.putExtra("Contact", orderDataList.get(holder.getAdapterPosition()).getContactNo());
                intent.putExtra("Address", orderDataList.get(holder.getAdapterPosition()).getAddress());
                intent.putExtra("Drinkname", orderDataList.get(holder.getAdapterPosition()).getDrinkname());
                intent.putExtra("Drinksize", orderDataList.get(holder.getAdapterPosition()).getDrinkSize());
                intent.putExtra("Quantity", orderDataList.get(holder.getAdapterPosition()).getQuantity());
                intent.putExtra("Price", orderDataList.get(holder.getAdapterPosition()).getDrinkPrice());
                intent.putExtra("Amount", orderDataList.get(holder.getAdapterPosition()).getAmount());
                intent.putExtra("Time", orderDataList.get(holder.getAdapterPosition()).getTime());
                intent.putExtra("Key", orderDataList.get(holder.getAdapterPosition()).getKey());

                intent.putExtra("Name", orderDataList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("UID", orderDataList.get(holder.getAdapterPosition()).getUid());
                intent.putExtra("Shopname", orderDataList.get(holder.getAdapterPosition()).getShopname());

                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return orderDataList.size();
    }

    public void filteredList(ArrayList<OrderData> filterList) {
        orderDataList = filterList;
        notifyDataSetChanged();
    }
}

class OrderViewHolder extends RecyclerView.ViewHolder {
    TextView time, orderid, drinkname, customername, address;
    CardView cardView;

    public OrderViewHolder(View itemView) {
        super(itemView);
        time = itemView.findViewById(R.id.time);
        orderid = itemView.findViewById(R.id.orderid);
        drinkname = itemView.findViewById(R.id.drinkname);
        customername = itemView.findViewById(R.id.customername);
        address = itemView.findViewById(R.id.address);

        cardView = itemView.findViewById(R.id.myCardView2);
    }
}