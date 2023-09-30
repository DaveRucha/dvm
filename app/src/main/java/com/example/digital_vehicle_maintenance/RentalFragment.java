package com.example.digital_vehicle_maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

public class RentalFragment extends Fragment {
    Button b1,b2;
    FragmentTransaction ft;
    SliderLayout slider;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.rental_layout,null);

        b1=view.findViewById(R.id.btn2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(view.getContext(),GiveCarActivity.class);
                startActivity(i);
            }
        });
        b2=view.findViewById(R.id.car_btn);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft=getActivity().getSupportFragmentManager().beginTransaction();
                CarFragment f=new CarFragment();
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
