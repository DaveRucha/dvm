package com.example.digital_vehicle_maintenance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class YourOrdersActivity extends AppCompatActivity {
    Retrofit retrofit;
    APIInterface apiInterface;
    RecyclerView recyclerView;
    List<String> name, status,image;
    String[] s_name=new String[50];
    String[] s_image=new String[50];
    String[] s_status=new String[50];
    String[] p_name=new String[50];
    String[] p_image=new String[50];
    String[] p_status=new String[50];
    String[] c_name=new String[50];
    String[] c_image=new String[50];
    String[] c_status=new String[50];

    Button b1;
    FragmentManager fm;
    FragmentTransaction ft;
    int u_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_orders);

        recyclerView = findViewById(R.id.my_recycler_view1);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        retrofit = MyRetroFit.getRetrofit();
        apiInterface = retrofit.create(APIInterface.class);

        SharedPreferences sharedpreferences = getSharedPreferences("Mypreference", MODE_PRIVATE);

        u_id = sharedpreferences.getInt("Id", 1);


        setRecyclerView();

    }

    private void setRecyclerView() {
        name = new ArrayList<>();
        status = new ArrayList<>();
        image = new ArrayList<>();


        Call<ArrayList<String>> c1 = apiInterface.get_order1(u_id);
        c1.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                ArrayList<String> array_list = response.body();
                int l=array_list.size();
                int c1=0,c2=1,c3=2;
                for(int n=0;n<(l/3);n++)
                {
                    p_name[n] = array_list.get(c1);
                    c1=c1+3;
                }
                for(int n=0;n<(l/3);n++)
                {
                    p_status[n] = array_list.get(c2);
                    c2=c2+3;
                }
                for(int n=0;n<(l/3);n++)
                {
                    p_image[n] = array_list.get(c3);
                    c3=c3+3;
                }
                for (int i = 0; i < (l/3); i++) {
                    name.add(p_name[i]);
                    status.add(p_status[i]);
                    image.add(p_image[i]);
                }
                OrderAdapter a = new OrderAdapter(name, status,image, recyclerView.getContext());
                recyclerView.setAdapter(a);
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Toast.makeText(YourOrdersActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });

        Call<ArrayList<String>> c = apiInterface.get_order(u_id);
        c.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                ArrayList<String> array_list = response.body();
                int l=array_list.size();
                int c1=0,c2=1,c3=2;
                for(int n=0;n<(l/3);n++)
                {
                    s_name[n] = array_list.get(c1);
                    c1=c1+3;
                }
                for(int n=0;n<(l/3);n++)
                {
                    s_status[n] = array_list.get(c2);
                    c2=c2+3;
                }
                for(int n=0;n<(l/3);n++)
                {
                    s_image[n] = array_list.get(c3);
                    c3=c3+3;
                }
                for (int i = 0; i < (l/3); i++) {
                    name.add(s_name[i]);
                    status.add(s_status[i]);
                    image.add(s_image[i]);
                }
                OrderAdapter a = new OrderAdapter(name, status,image, recyclerView.getContext());
                recyclerView.setAdapter(a);
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Toast.makeText(YourOrdersActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });

        Call<ArrayList<String>> c2 = apiInterface.get_order2(u_id);
        c2.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                ArrayList<String> array_list = response.body();
                int l=array_list.size();
                int c1=0,c2=1,c3=2;
                for(int n=0;n<(l/3);n++)
                {
                    c_name[n] = array_list.get(c1);
                    c1=c1+3;
                }
                for(int n=0;n<(l/3);n++)
                {
                    c_status[n] = array_list.get(c2);
                    c2=c2+3;
                }
                for(int n=0;n<(l/3);n++)
                {
                    c_image[n] = array_list.get(c3);
                    c3=c3+3;
                }
                for (int i = 0; i < (l/3); i++) {
                    name.add(c_name[i]);
                    status.add(c_status[i]);
                    image.add(c_image[i]);
                }
                OrderAdapter a = new OrderAdapter(name, status,image, recyclerView.getContext());
                recyclerView.setAdapter(a);
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Toast.makeText(YourOrdersActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });


    }
}