package com.example.digital_vehicle_maintenance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ServiceBookingActivity extends AppCompatActivity {

    EditText ed_name,ed_contact,ed_time,ed_date,ed_add_req,ed_address;
    String s1,s2;
    String u_name,u_contact;
    String s_time;
    String s_date,s_add,s_add_req;
    int u_id,s_id;
    String img,name;
    int hour,minute;
    Button b1;
    APIInterface apiInterface;
    Retrofit retrofit;
    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_booking);

        retrofit = MyRetroFit.getRetrofit();
        apiInterface = retrofit.create(APIInterface.class);

        ed_name=findViewById(R.id.name);
        ed_contact=findViewById(R.id.contact);
        ed_time=findViewById(R.id.time);
        ed_date=findViewById(R.id.date);
        ed_add_req=findViewById(R.id.add_req);
        ed_address=findViewById(R.id.address);

        b1=findViewById(R.id.button_time);

        SharedPreferences sharedpreferences = getSharedPreferences("Mypreference", MODE_PRIVATE);
        s1 = sharedpreferences.getString("Name", "");
        ed_name.setText(s1);

        s2 = sharedpreferences.getString("Mobile", "");
        ed_contact.setText(s2);

        u_id = sharedpreferences.getInt("Id", 1);

        SharedPreferences sharedpreferences1 = getSharedPreferences("Service_detail", MODE_PRIVATE);
        s_id = sharedpreferences1.getInt("Id", 1);
        name = sharedpreferences1.getString("Name","");
        img = sharedpreferences1.getString("Image","");

        Button button=(Button) findViewById(R.id.button);
        Button button_t=(Button) findViewById(R.id.button_time);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*DialogFragment datePicker = new DatePickerFragment();
                //datePicker.show(getSupportFragmentManager(),"date picker");
                datePicker.show(getSupportFragmentManager(),"date picker");*/
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                ed_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" +year );

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        button_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");*/
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                ed_time.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });
    }

    public void book_service(View view) {

        u_name=ed_name.getText().toString();
        u_contact=ed_contact.getText().toString();
        s_time= ed_time.getText().toString();
        s_date= ed_date.getText().toString();
        s_add= ed_address.getText().toString();
        s_add_req= ed_add_req.getText().toString();

        Call<String> c=apiInterface.book_service(u_id,u_name,u_contact,s_add,s_id,name,img,s_date,s_time,s_add_req);
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("Success"))
                {
                    Toast.makeText(ServiceBookingActivity.this, "Service Booked", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(ServiceBookingActivity.this,HomeActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(ServiceBookingActivity.this, "Cannot Book Service", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ServiceBookingActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }
}