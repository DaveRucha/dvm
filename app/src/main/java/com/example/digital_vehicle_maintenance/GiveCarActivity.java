package com.example.digital_vehicle_maintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GiveCarActivity extends AppCompatActivity {

    String ar[] = {"Petrol", "Diesel", "Gasoline"};
    int mYear, mMonth, mDay;
    APIInterface apiInterface;
    Retrofit retrofit;

    EditText ed_from, ed_to;
    EditText ed_capacity, ed_model, ed_maker, ed_cost, ed_feature;
    String s_model, s_maker, s_fuel, s_feature, s_from, s_to, s_image;
    int capacity, cost,u_id;
    Spinner sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_car);

        sp1 = findViewById(R.id.sp1);
        ed_from = findViewById(R.id.from_date);
        ed_to = findViewById(R.id.to_date);
        ed_capacity = findViewById(R.id.car_capacity);
        ed_model = findViewById(R.id.car_model);
        ed_maker = findViewById(R.id.car_maker);
        ed_cost = findViewById(R.id.car_cost);
        ed_feature = findViewById(R.id.car_feature);

        retrofit = MyRetroFit.getRetrofit();
        apiInterface = retrofit.create(APIInterface.class);

        ArrayAdapter<String> ad;
        ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ar);

        sp1.setAdapter(ad);


    }

    public void from(View view) {


        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        ed_from.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    public void to(View view) {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        ed_to.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void submit(View view) {

       s_from = ed_from.getText().toString();
        s_to = ed_to.getText().toString();
        capacity = Integer.parseInt(ed_capacity.getText().toString());
        s_model = ed_model.getText().toString();
        s_maker = ed_maker.getText().toString();
        cost = Integer.parseInt(ed_cost.getText().toString());
        s_fuel = sp1.getSelectedItem().toString();
        s_feature = ed_feature.getText().toString();
        s_image = "images/honda.png";
        SharedPreferences sharedpreferences = getSharedPreferences("Mypreference", MODE_PRIVATE);
        u_id = sharedpreferences.getInt("Id", 1);
        Call<String> c = apiInterface.car_give(2,s_maker, s_model, capacity, s_fuel, s_from, s_to, cost, s_feature, s_image);
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("Success")) {
                    Toast.makeText(GiveCarActivity.this, "Car Rented", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(GiveCarActivity.this, HomeActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(GiveCarActivity.this, "Cannot Rent Car", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(GiveCarActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}