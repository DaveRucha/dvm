package com.example.digital_vehicle_maintenance;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyHolder_1> {

    List<String> name,status,image;
    Button b1;
    RelativeLayout relativeLayout;
    private Context context;

    public OrderAdapter(List<String> name, List<String> status, List<String> image, Context context) {
        this.name = name;
        this.status = status;
        this.image = image;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter.MyHolder_1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.order_recyclerview_layout,null);
        OrderAdapter.MyHolder_1 holder=new OrderAdapter.MyHolder_1(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyHolder_1 holder, final int position) {
        holder.tx_name.setText(name.get(position));
        holder.tx_status.setText(status.get(position));

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
               Intent i=new Intent(context.getApplicationContext(),OrderDetailActivity.class);
                i.putExtra("service_name",name.get(position));
                i.putExtra("service_status",status.get(position));
                i.putExtra("service_image",image.get(position));
                context.startActivity(i);
            }

        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    class MyHolder_1 extends RecyclerView.ViewHolder{
        TextView tx_name,tx_status;
        ImageView iv;
        MyHolder_1(View v)
        {
            super(v);
            tx_name=v.findViewById(R.id.name);
            tx_status=v.findViewById(R.id.status);
            iv=v.findViewById(R.id.order_image);
            relativeLayout=v.findViewById(R.id.layout_id);
        }
    }
}
