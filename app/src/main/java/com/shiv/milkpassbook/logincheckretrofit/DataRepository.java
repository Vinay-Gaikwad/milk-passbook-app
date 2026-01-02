package com.shiv.milkpassbook.logincheckretrofit;

import android.content.Context;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    private final Context context;

    public DataRepository(Context context) {
        this.context = context;
    }

    public interface LoginCallback {
        void onResponse(LoginResponse response);

        void onFailure(Throwable t);
    }

    public void loginWithDevice(String softCustCode, String mobile, String deviceId, final LoginCallback callback) {
        LoginApiService apiService = RetrofitClientLogin.getApiService();
        LoginRequest request = new LoginRequest(softCustCode, mobile, deviceId);
        Call<LoginResponse> call = apiService.loginWithDevice(request);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResponse(response.body());
                } else {
                    callback.onResponse(null);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                callback.onResponse(null);
            }
        });
    }
}
