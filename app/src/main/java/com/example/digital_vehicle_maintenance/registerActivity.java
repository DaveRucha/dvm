package com.example.digital_vehicle_maintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class registerActivity extends AppCompatActivity {
    EditText name, email, password, mobile;
    String s_name, s_email, s_password, s_mobile;
    APIInterface apiInterface;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mobile = findViewById(R.id.mobile);


        retrofit = MyRetroFit.getRetrofit();
        apiInterface=retrofit.create(APIInterface.class);
    }

    public void registration(View view) {
        s_name = name.getText().toString();
        s_email = email.getText().toString();
        s_password = password.getText().toString();
        s_mobile = mobile.getText().toString();

        Call<String> c=apiInterface.register(s_name,s_email,s_password,s_mobile);
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("Success"))
                {
                    Toast.makeText(registerActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(registerActivity.this, LoginActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(registerActivity.this, "Cannot insert data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(registerActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                Log.d("mylog",t.toString());
            }
        });
    }

    public void loginpage(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);

    }

}