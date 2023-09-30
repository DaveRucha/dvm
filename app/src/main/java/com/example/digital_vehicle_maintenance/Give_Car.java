package com.example.digital_vehicle_maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Give_Car extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.give_car_f1, container, false);
        TextView textView=v.findViewById(R.id.text);
        textView.setText("First Fragment");
        return v;
    }
}
