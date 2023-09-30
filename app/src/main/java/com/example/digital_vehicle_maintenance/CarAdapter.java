package com.example.digital_vehicle_maintenance;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyHolder1>{//RecyclerView.Adapter<CarAdapter.MyHolder1>  {
    List<String> cmodel,cmaker,cimage;
    List<Integer> cid;
    Button b1;
    RelativeLayout relativeLayout;
    private Context context;

    public CarAdapter(List<String> cmodel, List<String> cmaker,List<String> cimage,List<Integer> cid,Context context) {
        this.cmodel = cmodel;
        this.cmaker = cmaker;
        this.cimage = cimage;
        this.cid = cid;
        this.context=context;
    }

    @NonNull
    @Override
    public MyHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.car_recyclerview_layout,null);
        MyHolder1 holder=new MyHolder1(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder1 holder, final int position) {
        holder.tx_cname.setText(this.cmodel.get(position));
        holder.tx_cprice.setText(this.cmaker.get(position));
        Glide.with(context)
                .load(context.getString(R.string.url)+cimage.get(position))
                .centerCrop()
                // .placeholder(R.drawable.loading_spinner)
                .into(holder.iv);

        this.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context.getApplicationContext(),CarBookingActivity.class);
                i.putExtra("car_maker",cmaker.get(position));
                i.putExtra("car_model",cmodel.get(position));
                i.putExtra("car_image",cimage.get(position));
                i.putExtra("car_id",cid.get(position));
                context.startActivity(i);
            }

        });
    }

    @Override
    public int getItemCount() {
        return cmaker.size();
    }


    class MyHolder1 extends RecyclerView.ViewHolder{
        TextView tx_cname,tx_cprice;
        ImageView iv;
        MyHolder1(View v)
        {
            super(v);
            tx_cname=v.findViewById(R.id.cname);
            tx_cprice=v.findViewById(R.id.cprice);
            iv=v.findViewById(R.id.image1);
            relativeLayout=v.findViewById(R.id.layout_id1);
        }
    }
}
