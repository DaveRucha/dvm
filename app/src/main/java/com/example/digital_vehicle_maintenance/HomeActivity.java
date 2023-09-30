package com.example.digital_vehicle_maintenance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FragmentManager fm;
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        fm=getSupportFragmentManager();

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        home();
    }

    public void home()
    {
        ft=fm.beginTransaction();
        HomeFragment f=new HomeFragment();
        ft.replace(R.id.frm,f);
        ft.commit();
    }
    public void rental()
    {
        ft=fm.beginTransaction();
        RentalFragment f=new RentalFragment();
        ft.replace(R.id.frm,f);
        ft.commit();
    }
    public void profile()
    {
        ft=fm.beginTransaction();
        ProfileFragment f=new ProfileFragment();
        ft.replace(R.id.frm,f);
        ft.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            home();
                            return true;
                        case R.id.navigation_Rental:
                            rental();
                            return true;
                        case R.id.navigation_Profile:
                            profile();
                            return true;
                    }
                    return false;
                }
            };

}

