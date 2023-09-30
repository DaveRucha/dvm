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

public class PartAdapter extends RecyclerView.Adapter<PartAdapter.PartHolder> {
    List<String> pname,pprice,pimage;
    Button btn;
    RelativeLayout relativeLayout;
    private Context context;

    public PartAdapter(List<String> pname, List<String> pprice,List<String> pimage,Context context) {
        this.pname = pname;
        this.pprice = pprice;
        this.pimage = pimage;
        this.context = context;
    }

    @NonNull
    @Override
    public PartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.part_recyclerview_layout,null);
        PartHolder holder=new PartHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PartHolder holder, final int position) {

        holder.tx_pname.setText(pname.get(position));
        holder.tx_pprice.setText("\u20B9 "+pprice.get(position));

        Log.d("log2",context.getString(R.string.url)+pimage.get(position));
        Glide.with(context)
                .load(context.getString(R.string.url)+pimage.get(position))
                .centerCrop()
                // .placeholder(R.drawable.loading_spinner)
                .into(holder.iv);

        this.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context.getApplicationContext(),PartDetailActivity.class);
                i.putExtra("part_name",pname.get(position));
                i.putExtra("part_price",pprice.get(position));
                i.putExtra("part_image",pimage.get(position));
                //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }

        });

    }


    @Override
    public int getItemCount() {
        return pname.size();
    }

    class PartHolder extends RecyclerView.ViewHolder {
        TextView tx_pname, tx_pprice;
        ImageView iv;
        PartHolder(View v) {
            super(v);
            tx_pname = v.findViewById(R.id.pname);
            tx_pprice = v.findViewById(R.id.pprice);
            iv=v.findViewById(R.id.pimage);
            relativeLayout = v.findViewById(R.id.layout_id2);
        }
    }
}
