package com.shiv.milkpassbook.softcustretrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientSoftCust {
    private static final String BASE_URL = "https://api.sanadeinfotech.in/index.php/api/";
    private static Retrofit retrofit;

    public static MstSoftCustApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(MstSoftCustApiService.class);
    }
}
