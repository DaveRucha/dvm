package com.example.digital_vehicle_maintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PartBookingActivity extends AppCompatActivity {

    EditText ed1, ed2, ed3, ed4;
    String s1, s2;
    String s_address;
    int s_pincode;
    String u_name, u_contact;
    int u_id, p_id;
    String p_name,p_image;
    APIInterface apiInterface;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_booking);

        ed1 = findViewById(R.id.name);
        ed2 = findViewById(R.id.contact);
        ed3 = findViewById(R.id.pincode);
        ed4 = findViewById(R.id.address);

        SharedPreferences sharedpreferences = getSharedPreferences("Mypreference", MODE_PRIVATE);
        s1 = sharedpreferences.getString("Name", "");
        ed1.setText(s1);

        s2 = sharedpreferences.getString("Mobile", "");
        ed2.setText(s2);

        u_id = sharedpreferences.getInt("Id", 1);

        SharedPreferences sharedpreferences_1 = getSharedPreferences("Part_detail", MODE_PRIVATE);
        p_id = sharedpreferences_1.getInt("Id", 1);
        p_name = sharedpreferences_1.getString("Name", "");
        p_image = sharedpreferences_1.getString("Image", "");

    }


    public void book_part(View view) {

        u_name = ed1.getText().toString();
        u_contact = ed2.getText().toString();
        s_pincode = Integer.parseInt(ed3.getText().toString());
        s_address = ed4.getText().toString();

        apiInterface = MyRetroFit.getRetrofit().create(APIInterface.class);

        Call<String> c = apiInterface.book_part(p_id,p_name,p_image, u_id, u_name, u_contact, s_pincode, s_address);
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("Success")) {
                    Toast.makeText(PartBookingActivity.this, "Part Booked", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PartBookingActivity.this, HomeActivity.class);
                    startActivity(i);

                } else {
                    Toast.makeText(PartBookingActivity.this, "Cannot Book Part", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(PartBookingActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });

        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}