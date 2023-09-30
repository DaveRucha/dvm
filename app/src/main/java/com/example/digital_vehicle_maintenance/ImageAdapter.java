package com.example.digital_vehicle_maintenance;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyHoldera> {
    ArrayList<MyResponse> list;
    Context context;

    public ImageAdapter(ArrayList<MyResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageAdapter.MyHoldera onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_mydesign,null);
        MyHoldera holder1 = new MyHoldera(view);
        return holder1;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.MyHoldera holder, int position) {
        MyResponse response = list.get(position);
        holder.tx_name.setText(response.getName());
        holder.tx_price.setText(response.getPrice()+"");
        holder.tx_desc.setText(response.getDesc());
        Log.d("mylog",response.getImage_name());
        Glide.with(context)
                .load(context.getString(R.string.url)+response.getImage_name())
                .centerCrop()
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHoldera extends RecyclerView.ViewHolder
    {
        TextView tx_name,tx_price,tx_desc;
        ImageView img;
        public MyHoldera(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tx_name = itemView.findViewById(R.id.name);
            tx_desc = itemView.findViewById(R.id.desc);
            tx_price = itemView.findViewById(R.id.price);
        }
    }
}
