package com.shiv.milkpassbook.mstdedretrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientDed {
    private static final String BASE_URL = "https://api.sanadeinfotech.in/";
    private static Retrofit retrofit;

    public static MstDedApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(MstDedApiService.class);
    }
}
