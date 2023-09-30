package com.example.digital_vehicle_maintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ServiceDetailActivity extends AppCompatActivity {
    Retrofit retrofit;
    APIInterface apiInterface;
    String str_image;
    TextView tw_name,tw_price,tw_warranty,tw_duration;
    ImageView iv;
    TextView tx;
    LinearLayout layout1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        retrofit = MyRetroFit.getRetrofit();
        apiInterface = retrofit.create(APIInterface.class);

        tw_name=findViewById(R.id.service_name);
        tw_price=findViewById(R.id.service_price);
        tw_warranty=findViewById(R.id.service_warranty);
        tw_duration=findViewById(R.id.service_duration);
        iv=findViewById(R.id.img_service);
        layout1=findViewById(R.id.ll1);

        Intent intent = getIntent();
        String str_name = intent.getStringExtra("service_name");
        String str_price = intent.getStringExtra("service_price");
        str_image = intent.getStringExtra("service_image");
        tx=(TextView) (LayoutInflater.from(this).inflate(R.layout.service_design,null));
        Call<ArrayList<String>> c = apiInterface.service_detail(str_name,str_price);
        c.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                ArrayList<String> array_list = response.body();
                SharedPreferences sharedpreferences = getSharedPreferences("Service_detail", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedpreferences.edit();

                myEdit.putInt("Id", Integer.parseInt(array_list.get(0)));
                myEdit.putString("Name", array_list.get(1));
                myEdit.putString("Image", str_image);
                myEdit.commit();

                tw_name.setText(array_list.get(1));
                tw_price.setText("\u20B9 "+array_list.get(2)+" /-");
                tw_duration.setText(array_list.get(3)+" Taken");
                tw_warranty.setText(array_list.get(4)+" Warranty");
                Glide.with(ServiceDetailActivity.this)
                        .load(ServiceDetailActivity.this.getString(R.string.url)+str_image)
                        .centerCrop()
                        // .placeholder(R.drawable.loading_spinner)
                        .into(iv);


                StringTokenizer tokens=new StringTokenizer(array_list.get(6),";");
                while (tokens.hasMoreElements()){
                    String token=tokens.nextToken();
                    Log.d("log2",token);
                    tx=(TextView) (LayoutInflater.from(ServiceDetailActivity.this).inflate(R.layout.service_design,null));
                    tx.setText(token);
                    //final ViewGroup.LayoutParams l=(ViewGroup.LayoutParams)tx.getLayoutParams();
                    layout1.addView(tx);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Toast.makeText(ServiceDetailActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void proceed(View view) {
        Intent i=new Intent(this,ServiceBookingActivity.class);
        startActivity(i);
        finish();
    }
}