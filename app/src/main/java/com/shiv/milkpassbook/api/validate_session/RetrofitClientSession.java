package com.shiv.milkpassbook.api.validate_session;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientSession {

    private static Retrofit retrofit = null;

    public static SessionApi getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.sanadeinfotech.in/index.php/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(SessionApi.class);
    }
}
