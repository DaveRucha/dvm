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

public class LoginActivity extends AppCompatActivity {
    EditText name, password;
    Retrofit retrofit;
    APIInterface apiInterface;
    String s1,s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        name = findViewById(R.id.inputName);
        password = findViewById(R.id.inputPassword);


        retrofit = MyRetroFit.getRetrofit();
        apiInterface = retrofit.create(APIInterface.class);

    }

    public void Login(View view) {
        final String s_name = name.getText().toString();
        final String s_password = password.getText().toString();
        Call<ArrayList<String>> c = apiInterface.login(s_name, s_password);


        c.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                ArrayList<String> array_list = response.body();
                if (s_name.length()==0 && s_password.length()==0)
                {
                    Toast.makeText(LoginActivity.this, "Enter Username and Password", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }

                else if (s_password.length()==0)
                {
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }
                else if(s_name.length()==0)
                {
                    Toast.makeText(LoginActivity.this, "Enter Username", Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                }
                else if (array_list.get(0).equals("Success")) {
                    SharedPreferences sharedpreferences = getSharedPreferences("Mypreference", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedpreferences.edit();

                    myEdit.putInt("Id", Integer.parseInt(array_list.get(1)));
                    myEdit.putString("status","login");
                    myEdit.putString("Name", array_list.get(2));
                    myEdit.putString("Email", array_list.get(3));
                    myEdit.putString("Password", array_list.get(4));
                    myEdit.putString("Mobile", array_list.get(5));
                    s1=array_list.get(3);
                    s2=array_list.get(4);
                    myEdit.commit();
                    Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Wrong Username and password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                Log.d("mylog", t.toString());
            }
        });
    }

    public void Register(View view) {
        Intent intent = new Intent(this, registerActivity.class);
        startActivity(intent);
    }
}