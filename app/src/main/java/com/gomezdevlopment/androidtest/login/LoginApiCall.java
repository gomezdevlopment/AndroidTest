package com.gomezdevlopment.androidtest.login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApiCall {
    String apiString = "login.php";
    @POST(apiString)
    @FormUrlEncoded
    Call<LoginResponse> post(@Field("email") String email, @Field("password") String password);
}
