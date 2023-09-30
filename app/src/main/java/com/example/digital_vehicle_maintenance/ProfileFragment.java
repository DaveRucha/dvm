package com.example.digital_vehicle_maintenance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    EditText ed1, ed2, ed3;
    String s1, s2, s3;
    int id;
    Button b1, b2,b3;
    APIInterface apiInterface;
    Retrofit retrofit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.profile_layout, null);

        ed1 = view.findViewById(R.id.name);
        ed2 = view.findViewById(R.id.email);
        ed3 = view.findViewById(R.id.mobile);
        b1 = view.findViewById(R.id.button_save);
        b2 = view.findViewById(R.id.button_logout);
        b3 = view.findViewById(R.id.button_order);
        SharedPreferences sharedpreferences = getContext().getSharedPreferences("Mypreference", MODE_PRIVATE);
        s1 = sharedpreferences.getString("Name", "");
        ed1.setText(s1);
        s2 = sharedpreferences.getString("Email", "");
        ed2.setText(s2);
        s3 = sharedpreferences.getString("Mobile", "");
        ed3.setText(s3);
        id = sharedpreferences.getInt("Id",1);

        retrofit = MyRetroFit.getRetrofit();
        apiInterface=retrofit.create(APIInterface.class);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = getContext().getSharedPreferences("Mypreference", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedpreferences.edit();
                myEdit.putString("status", "Logout");
                myEdit.commit();
                Toast.makeText(view.getContext(), "Logout", Toast.LENGTH_SHORT).show();
                Intent i= new Intent(view.getContext(),LoginActivity.class);
                startActivity(i);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                s1=ed1.getText().toString();
                s2=ed2.getText().toString();
                s3=ed3.getText().toString();
                Call<String> c=apiInterface.update(id,s1,s2,s3);
                c.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("Success"))
                        {
                            Toast.makeText(view.getContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedpreferences = getActivity().getSharedPreferences("Mypreference", MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedpreferences.edit();

                            myEdit.putString("Name", s1);
                            myEdit.putString("Email", s2);
                            myEdit.putString("Mobile", s3);
                            myEdit.commit();
                            s1 = sharedpreferences.getString("Name", "");
                            ed1.setText(s1);
                            s2 = sharedpreferences.getString("Email", "");
                            ed2.setText(s2);
                            s3 = sharedpreferences.getString("Mobile", "");
                            ed3.setText(s3);

                        }
                        else
                        {
                            Toast.makeText(view.getContext(), "Cannot update data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(view.getContext(), "Failure", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(),YourOrdersActivity.class);
                startActivity(i);
            }
        });

        return view;
    }
}
