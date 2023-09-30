package com.example.digital_vehicle_maintenance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    List<String> name,price,image;
    Button b1;
    RelativeLayout relativeLayout;
    private Context context;


    public MyAdapter(List<String> name, List<String> price,List<String> image,Context c) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.context=c;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.service_recyclerview_layout,null);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        holder.tx_name.setText(name.get(position));
        holder.tx_price.setText("\u20B9 "+price.get(position));

        Log.d("log1",context.getString(R.string.url)+image.get(position));
        Glide.with(context)
                .load(context.getString(R.string.url)+image.get(position))
                .centerCrop()
               // .placeholder(R.drawable.loading_spinner)
                .into(holder.iv);
        //holder.iv.setImageResource(Integer.parseInt(image.get(position)));
        this.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context.getApplicationContext(),ServiceDetailActivity.class);
                i.putExtra("service_name",name.get(position));
                i.putExtra("service_price",price.get(position));
                i.putExtra("service_image",image.get(position));
                context.startActivity(i);
            }

        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView tx_name,tx_price;
        ImageView iv;
        MyHolder(View v)
        {
            super(v);
            tx_name=v.findViewById(R.id.name);
            tx_price=v.findViewById(R.id.price);
            iv=v.findViewById(R.id.service_image);
            relativeLayout=v.findViewById(R.id.layout_id);
        }
    }
}
