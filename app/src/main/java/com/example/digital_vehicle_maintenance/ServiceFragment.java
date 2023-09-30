package com.example.digital_vehicle_maintenance;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

public class ServiceFragment extends Fragment {
    Retrofit retrofit;
    APIInterface apiInterface;
    RecyclerView recyclerView;
    List<String> name, price,image;
    String[] s_name=new String[50];
    String[] s_image=new String[50];
    int[] s_price=new int[50];
    Button b1;
    FragmentManager fm;
    FragmentTransaction ft;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.service_layout, null);

        recyclerView = view.findViewById(R.id.my_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        retrofit = MyRetroFit.getRetrofit();
        apiInterface = retrofit.create(APIInterface.class);

        setRecyclerView();

        return view;
    }

    private void setRecyclerView() {

        name = new ArrayList<>();
        price = new ArrayList<>();
        image = new ArrayList<>();

        Call<ArrayList<String>> c = apiInterface.get_service();
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
                    s_price[n] = Integer.parseInt(array_list.get(c2));
                    c2=c2+3;
                }
                for(int n=0;n<(l/3);n++)
                {
                    s_image[n] = array_list.get(c3);
                    c3=c3+3;
                }
                for (int i = 0; i < (l/3); i++) {
                    name.add(s_name[i]);
                    price.add(String.valueOf(s_price[i]));
                    image.add(s_image[i]);
                }
                MyAdapter a = new MyAdapter(name, price,image, recyclerView.getContext());
                recyclerView.setAdapter(a);
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
