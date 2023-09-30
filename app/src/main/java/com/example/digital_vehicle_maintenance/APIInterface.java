package com.example.digital_vehicle_maintenance;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIInterface {

    @Multipart
    @POST("uploadfile.php")
    Call<String> uploadFile(@Part MultipartBody.Part file, @Query("name") String name, @Query("desc") String desc, @Query("price")float price);

    @GET("loaddata.php")
    Call<ArrayList<MyResponse>> getData();


    @GET("InsertData.php")
    Call<String> register(@Query("name") String a,@Query("email") String b,@Query("password") String c,@Query("mobile") String d);

    @GET("Login.php")
    Call<ArrayList<String>> login(@Query("Name") String a,@Query("Password") String b);

    @GET("UpdateData.php")
    Call<String> update(@Query("ID") int a,@Query("Name") String b,@Query("Email") String c,@Query("Mobile") String d);

    @GET("GetService.php")
    Call<ArrayList<String>> get_service();

    @GET("GetOrder.php")
    Call<ArrayList<String>> get_order(@Query("User_ID") int a);

    @GET("GetOrder1.php")
    Call<ArrayList<String>> get_order1(@Query("User_ID") int a);

    @GET("GetOrder2.php")
    Call<ArrayList<String>> get_order2(@Query("User_ID") int a);


    @GET("GetCar.php")
    Call<ArrayList<String>> get_car();

    @GET("GetPart.php")
    Call<ArrayList<String>> get_part();

    @GET("Service_Detail.php")
    Call<ArrayList<String>> service_detail(@Query("Service_Name") String a,@Query("Service_Price") String b);

    @GET("Car_Detail.php")
    Call<ArrayList<String>> car_detail(@Query("Car_Id") int a);


    @GET("Part_Detail.php")
    Call<ArrayList<String>> part_detail(@Query("Part_Name") String a,@Query("Part_Price") String b);

    @GET("Book_service.php")
    Call<String> book_service(@Query("User_ID") int a, @Query("User_Name") String b, @Query("User_Mobile") String c,@Query("User_Address") String add, @Query("Service_ID") int d, @Query("Service_Name") String name,@Query("Service_Image") String image, @Query("Booking_Date") String e, @Query("Booking_Time") String f,@Query("Add_Req") String Add_Req);

    @GET("Car_Give.php")
    Call<String> car_give(@Query("User_ID") int user,@Query("Car_Maker") String a, @Query("Car_Model") String b, @Query("Car_Capacity") int c, @Query("Car_Fuel") String d, @Query("Car_From") String e, @Query("Car_To") String f,@Query("Car_Cost") int g,@Query("Car_Feature") String h,@Query("Car_Image") String i);


    @GET("Book_part.php")
    Call<String> book_part(@Query("Part_ID") int a,@Query("Part_Name") String name,@Query("Part_Image") String image,@Query("User_ID") int a2, @Query("User_Name") String b, @Query("User_Mobile") String c, @Query("User_Pincode") int d, @Query("User_Address") String e);

    @GET("Book_Car.php")
    Call<String> book_car(@Query("Car_Id") int a,@Query("Car_Maker") String maker,@Query("Car_Model") String model,@Query("Car_Image") String image,@Query("User_ID") int a2, @Query("User_Name") String b);
}

