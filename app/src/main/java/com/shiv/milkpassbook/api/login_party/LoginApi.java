package com.shiv.milkpassbook.api.login_party;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {
    @POST("login_party")
    Call<LoginResponse> login(@Body LoginRequest request);


}
