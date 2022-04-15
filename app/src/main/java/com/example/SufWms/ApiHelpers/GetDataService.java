package com.example.SufWms.ApiHelpers;

import com.example.SufWms.Classes.BookingIn;
import com.example.SufWms.Classes.BookingInUpdate;
import com.example.SufWms.Classes.BookingMasterIn;
import com.example.SufWms.Classes.BookingMasterOut;
import com.example.SufWms.Classes.BookingOut;
import com.example.SufWms.Classes.CustomerInfo;
import com.example.SufWms.Classes.LocationDetails;
import com.example.SufWms.Classes.Passwd;
import com.example.SufWms.Models.DamageRepairParts;
import com.example.SufWms.Models.InsertionMessage;
import com.example.SufWms.Models.PartDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetDataService {

//    @POST("/api/users")
//    Call<User> createUser(@Body User user);
//
    //when sending parameter through url, use @Query in the get method.user ? at the end
    @GET("getpartdetails?")
    Call<List<PartDetails>> doGetPartDetails(@Query("categoryid") String categoryid);

    //in the post method use @Field to send parameter.
    //@FormUrlEncoded
    @POST("insertrepairpartdetails")
    Call<List<InsertionMessage>> doInsertPartDetails(@Body List<DamageRepairParts> drp);
    //Call<List<InsertionMessage>> doInsertPartDetails(@Field("tstpar") String tstpar);

//
//    @FormUrlEncoded
//    @POST("/api/users?")
//    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);

    //Authorize User
    @GET("ap")
    Call<List<Passwd>> doGetAuth(@Query("empid") String empid,@Query("passwd") String passwd);

    //Get Booking in
    @GET("bookingin")
    Call<List<BookingIn>> doGetBookingIn(@Query("BookingId") String BookingId);

    //Get Booking Out
    @GET("bookingout")
    Call<List<BookingOut>> doGetBookingOut(@Query("BookingId") String BookingId);

    //Get Booking Master In
    @GET("bookingmasterin")
    Call<List<BookingMasterIn>> doGetBookingMasterIn(@Query("rec_id") String rec_id);

    //Get Booking Master In
    @GET("bookingmasterout")
    Call<List<BookingMasterOut>> doGetBookingMasterOut(@Query("rec_id") String rec_id);

    //Get Booking Master In
    @GET("customerinfo")
    Call<List<CustomerInfo>> doGetCustomerInfo();

    //Get Location Details
    @GET("locationdetails")
    Call<List<LocationDetails>> doGetLocationDetails(@Query("BarcodeNo") String BarcodeNo);

    // Update Booking In
    @POST("updatebookingin")
    Call<List<InsertionMessage>> doUpdateBookingIn(@Body List<BookingInUpdate> drp);
    //Call<List<InsertionMessage>> doInsertPartDetails(@Field("tstpar") String tstpar);

}
