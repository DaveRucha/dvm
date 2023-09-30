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

public class CarFragment extends Fragment {
    Retrofit retrofit;
    APIInterface apiInterface;
    RecyclerView recyclerView;
    List<String> cmaker,cmodel,cimage;
    List<Integer> cid;
    Button b1;
    FragmentManager fm;
    FragmentTransaction ft;
    String[] c_maker=new String[50];
    String[] c_image=new String[50];
    String[] c_model=new String[50];
    int[] c_id=new int[10];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view=inflater.inflate(R.layout.car_layout,null);

        recyclerView=view.findViewById(R.id.my_recycler_view1);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        retrofit = MyRetroFit.getRetrofit();
        apiInterface = retrofit.create(APIInterface.class);
        setRecyclerView();

        return view;
    }

    private void setRecyclerView() {

        cmodel=new ArrayList<>();
        cmaker=new ArrayList<>();
        cimage=new ArrayList<>();
        cid=new ArrayList<>();

        Call<ArrayList<String>> c = apiInterface.get_car();
        c.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                ArrayList<String> array_list = response.body();
                int l=array_list.size();
                int c1=0,c2=1,c3=2,c4=3;
                for(int n=0;n<(l/4);n++)
                {
                    c_maker[n] = array_list.get(c1);
                    c1=c1+4;
                }
                for(int n=0;n<(l/4);n++)
                {
                    c_model[n] = array_list.get(c2);
                    c2=c2+4;
                }
                for(int n=0;n<(l/4);n++)
                {
                    c_image[n] = array_list.get(c3);
                    c3=c3+4;
                }
                for(int n=0;n<(l/4);n++)
                {
                    c_id[n] = Integer.parseInt(array_list.get(c4));
                    c4=c4+4;
                }
                for (int i = 0; i < (l/4); i++) {
                    cmaker.add(c_maker[i]);
                    cmodel.add(c_model[i]);
                    cimage.add(c_image[i]);
                    cid.add(c_id[i]);
                }
                CarAdapter a = new CarAdapter(cmaker,cmodel,cimage,cid,recyclerView.getContext());
                recyclerView.setAdapter(a);
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
