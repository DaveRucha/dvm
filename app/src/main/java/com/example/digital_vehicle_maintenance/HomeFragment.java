package com.example.digital_vehicle_maintenance;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    FragmentManager fm;
    FragmentTransaction ft;
    Button b1,b2;
    SliderLayout slider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.home_layout,null);
        b1= view.findViewById(R.id.service_btn);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ft=getActivity().getSupportFragmentManager().beginTransaction();
                ServiceFragment f=new ServiceFragment();
                ft.replace(R.id.frm,f);
                ft.commit();
            }
        });
        b2= view.findViewById(R.id.part_button);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft=getActivity().getSupportFragmentManager().beginTransaction();
                PartFragment f=new PartFragment();
                ft.replace(R.id.frm,f);
                ft.commit();
            }
        });

        slider = view.findViewById(R.id.slider);

        SliderView slide1 = new SliderView(getContext());
        SliderView slide2 = new SliderView(getContext());
        SliderView slide3 = new SliderView(getContext());
        SliderView slide4 = new SliderView(getContext());

        slide1.setImageDrawable(R.drawable.pic2);
        slide2.setImageDrawable(R.drawable.pic3);
        slide3.setImageDrawable(R.drawable.pic4);
        slide4.setImageDrawable(R.drawable.pic5);

        slider.addSliderView(slide1);
        slider.addSliderView(slide2);
        slider.addSliderView(slide3);
        slider.addSliderView(slide4);



        return view;
    }

}
