package com.example.digital_vehicle_maintenance;

import android.util.Log;
import android.view.View;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetroFit {
    private static Retrofit retrofit = null;
    private static String url = "http://192.168.110.20/DigitalVehicleMaintenance/";

    public static Retrofit getRetrofit() {
        Log.d("log3",url);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}


