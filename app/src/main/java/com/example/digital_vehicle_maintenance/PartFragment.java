package com.example.digital_vehicle_maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PartFragment extends Fragment {
    RecyclerView recyclerView;
    List<String> pname,pprice,pimage;
    Button b1;
    FragmentManager fm;
    FragmentTransaction ft;
    Retrofit retrofit;
    APIInterface apiInterface;
    String[] p_name=new String[50];
    String[] p_image=new String[50];
    int[] p_price=new int[50];


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.part_layout,null);

        recyclerView=view.findViewById(R.id.my_recycler_view3);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        retrofit = MyRetroFit.getRetrofit();
        apiInterface = retrofit.create(APIInterface.class);

        setRecyclerView1();

        return view;
    }
    private void setRecyclerView1() {

        pname=new ArrayList<>();
        pprice=new ArrayList<>();
        pimage = new ArrayList<>();

        Call<ArrayList<String>> c = apiInterface.get_part();
        c.enqueue(new Callback<ArrayList<String>>() {

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
                    p_price[n] = Integer.parseInt(array_list.get(c2));
                    c2=c2+3;
                }
                for(int n=0;n<(l/3);n++)
                {
                    p_image[n] = array_list.get(c3);
                    c3=c3+3;
                }
                for (int i = 0; i < (l/3); i++) {
                    pname.add(p_name[i]);
                    pprice.add(String.valueOf(p_price[i]));
                    pimage.add(p_image[i]);
                }

                PartAdapter a = new PartAdapter(pname, pprice,pimage, recyclerView.getContext());
                recyclerView.setAdapter(a);
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
