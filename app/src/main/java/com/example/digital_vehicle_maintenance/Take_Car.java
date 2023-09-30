package com.example.digital_vehicle_maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Take_Car extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.take_car_f2, container, false);
        TextView textView=v.findViewById(R.id.text);
        textView.setText("Second Fragment");
        return v;
    }
}
