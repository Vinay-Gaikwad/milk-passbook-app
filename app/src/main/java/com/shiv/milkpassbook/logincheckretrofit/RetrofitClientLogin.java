package com.shiv.milkpassbook.logincheckretrofit;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientLogin {
    private static Retrofit retrofit = null;

    public static LoginApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.sanadeinfotech.in/") // change this
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(LoginApiService.class);
    }
}
