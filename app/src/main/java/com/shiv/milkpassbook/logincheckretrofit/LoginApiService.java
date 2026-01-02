package com.shiv.milkpassbook.logincheckretrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApiService {

    @POST("index.php/api/login")
    Call<LoginResponse> loginWithDevice(@Body LoginRequest loginRequest);
}
