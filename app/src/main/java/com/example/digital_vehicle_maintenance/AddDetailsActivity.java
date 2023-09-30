package com.example.digital_vehicle_maintenance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class AddDetailsActivity extends AppCompatActivity {
    ImageView imgView;
    ProgressDialog progressDialog;
    File imagefile;
    Uri uri;
    ImageView img;
    EditText ed1, ed2, ed3;
    int IMAGE_REQUEST = 0;
    private APIInterface getResponse;

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        ed1 = findViewById(R.id.name);
        ed2 = findViewById(R.id.desc);
        ed3 = findViewById(R.id.price);
        img = findViewById(R.id.img);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });
        String permission[] = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermissions(permission, IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == IMAGE_REQUEST) {
            Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "PERMISSION NOT TAKEN", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            try {
                uri = data.getData();
                imagefile = FileUtil.from(this, uri);
                imgView.setImageURI(uri);
            } catch (Exception ex) {
                Log.d("fileUpload+Exception175", ex.toString());
            }
        } else {
            Toast.makeText(this, "image not selected...", Toast.LENGTH_LONG).show();
        }
    }

    public void submit(View view) {
        String name = ed1.getText().toString();
        String desc = ed2.getText().toString();
        float price = Float.parseFloat(ed3.getText().toString());
        APIInterface apiInterface;
        Retrofit retrofit;

        progressDialog.show();

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), imagefile);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", imagefile.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), imagefile.getName());

        retrofit = MyRetroFit.getRetrofit();
        apiInterface = retrofit.create(APIInterface.class);

        Call<String> call = getResponse.uploadFile(fileToUpload, name, desc, price);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String str = response.body();
                Log.d("Response", str);
                if (str.equals("true")) {
                    Toast.makeText(AddDetailsActivity.this, "done", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.v("Response", str);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Response", t.toString());
                progressDialog.dismiss();
            }
        });
    }
}