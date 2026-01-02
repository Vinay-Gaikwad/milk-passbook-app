package com.shiv.milkpassbook.milkbillretrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientMilkBill {
    private static final String BASE_URL = "https://api.sanadeinfotech.in/";
    private static Retrofit retrofit;

    public static TempMilkBillApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(TempMilkBillApiService.class);
    }
}
