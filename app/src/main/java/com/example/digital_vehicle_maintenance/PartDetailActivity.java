package com.example.digital_vehicle_maintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PartDetailActivity extends AppCompatActivity {

    Retrofit retrofit;
    APIInterface apiInterface;
    String str_image;
    TextView tw_name,tw_price,tw_number,tw_brand;
    ImageView iv;
    String str_name;
    LinearLayout layout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_detail);

        retrofit = MyRetroFit.getRetrofit();
        apiInterface = retrofit.create(APIInterface.class);

        tw_name=findViewById(R.id.part_name);
        tw_price=findViewById(R.id.part_price);
        tw_number=findViewById(R.id.part_number);
        tw_brand=findViewById(R.id.part_brand);
        iv=findViewById(R.id.img_part);
        layout1=findViewById(R.id.ll1);

        Intent intent = getIntent();
        str_name = intent.getStringExtra("part_name");
        String str_price = intent.getStringExtra("part_price");
        str_image = intent.getStringExtra("part_image");
        Call<ArrayList<String>> c = apiInterface.part_detail(str_name,str_price);
        c.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                ArrayList<String> array_list = response.body();
                SharedPreferences sharedpreferences = getSharedPreferences("Part_detail", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedpreferences.edit();

                myEdit.putInt("Id", Integer.parseInt(array_list.get(0)));
                myEdit.putString("Name", array_list.get(1));
                myEdit.putString("Image", str_image);
                myEdit.commit();

                tw_name.setText(array_list.get(1));
                tw_number.setText(array_list.get(2));
                tw_brand.setText(array_list.get(3));
                tw_price.setText("\u20B9 "+array_list.get(4));
                Glide.with(PartDetailActivity.this)
                        .load(PartDetailActivity.this.getString(R.string.url)+str_image)
                        .centerCrop()
                        // .placeholder(R.drawable.loading_spinner)
                        .into(iv);
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Toast.makeText(PartDetailActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void back(View view) {
        Intent i=new Intent(this,PartFragment.class);
        startActivity(i);
    }

    public void proceed(View view) {
        Intent i=new Intent(this,PartBookingActivity.class);
        i.putExtra("part_name",str_name);
        i.putExtra("part_image",str_image);
        startActivity(i);
    }
}