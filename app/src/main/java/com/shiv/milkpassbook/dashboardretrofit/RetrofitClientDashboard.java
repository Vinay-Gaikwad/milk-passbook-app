package com.shiv.milkpassbook.dashboardretrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientDashboard {
    private static Retrofit retrofit = null;

    public static DashboardApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.sanadeinfotech.in/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(DashboardApiService.class);
    }
}
