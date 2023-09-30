package com.example.digital_vehicle_maintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CarBookingActivity extends AppCompatActivity {
    TextView tv_fuel,tv_fromto,tv_capacity,tv_name,tv_price;
    Retrofit retrofit;
    APIInterface apiInterface;
    String str_image;
    String str_maker,str_model;
    ImageView iv;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_booking);
        tv_capacity=findViewById(R.id.car_capacity);
        tv_fromto=findViewById(R.id.car_fromto);
        tv_fuel=findViewById(R.id.car_fuel);
        tv_name=findViewById(R.id.car_name);
        tv_price=findViewById(R.id.car_price);
        retrofit = MyRetroFit.getRetrofit();
        apiInterface = retrofit.create(APIInterface.class);
        iv=findViewById(R.id.img_car);

        Intent intent = getIntent();
        str_maker= intent.getStringExtra("car_maker");
        str_model = intent.getStringExtra("car_model");
        id=intent.getIntExtra("car_id",1);
        //int id = Integer.parseInt(intent.getStringExtra("car_id"));

        str_image = intent.getStringExtra("car_image");

        Call<ArrayList<String>> c = apiInterface.car_detail(id);
        c.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                ArrayList<String> array_list = response.body();

                tv_name.setText(array_list.get(0)+" "+array_list.get(1));
                tv_capacity.setText(array_list.get(2)+" persons");
                tv_fuel.setText(array_list.get(3));
                tv_fromto.setText("From "+array_list.get(4)+" To "+array_list.get(5));
                tv_price.setText("\u20B9 "+array_list.get(6));

                Glide.with(CarBookingActivity.this)
                        .load(CarBookingActivity.this.getString(R.string.url)+str_image)
                        .centerCrop()
                        // .placeholder(R.drawable.loading_spinner)
                        .into(iv);

            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Toast.makeText(CarBookingActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Book(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences("Mypreference", MODE_PRIVATE);
        String u_name = sharedpreferences.getString("Name", "");
        int u_id = sharedpreferences.getInt("Id", 1);
        apiInterface = MyRetroFit.getRetrofit().create(APIInterface.class);
        
        Call<String> c = apiInterface.book_car(id,str_maker,str_model,str_image, u_id, u_name);
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("Success")) {
                    Toast.makeText(CarBookingActivity.this, "Car Booked", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(CarBookingActivity.this,HomeActivity.class);
                    startActivity(i);
                    finish();

                } else {
                    Toast.makeText(CarBookingActivity.this, "Cannot Book Car", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(CarBookingActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void terms(View view) {
        Intent i=new Intent(view.getContext(),TermsActivity.class);
        startActivity(i);
    }
}