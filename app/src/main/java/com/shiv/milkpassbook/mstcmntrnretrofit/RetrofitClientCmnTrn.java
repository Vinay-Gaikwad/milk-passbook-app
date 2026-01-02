package com.shiv.milkpassbook.mstcmntrnretrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientCmnTrn {

    private static Retrofit retrofit;

    public static MstCmnTrnApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.sanadeinfotech.in/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(MstCmnTrnApiService.class);
    }
}
