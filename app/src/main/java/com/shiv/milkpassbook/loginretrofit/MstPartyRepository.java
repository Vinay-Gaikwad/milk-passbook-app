package com.shiv.milkpassbook.loginretrofit;

import android.content.Context;
import android.util.Log;

import com.shiv.milkpassbook.mstparty.AppDatabase;
import com.shiv.milkpassbook.mstparty.MstParty;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MstPartyRepository {
    private ApiService apiService;
    private AppDatabase db;

    public MstPartyRepository(Context context) {
        apiService = RetrofitClient.getApiService();
        db = AppDatabase.getInstance(context);
    }

    public void fetchAndStoreMstParties(Runnable onComplete) {
        apiService.getMstParties().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {  // Changed == to !=
                    List<MstParty> parties = response.body().getParties();
                    Log.d("API_RESPONSE", "Received " + parties.size() + " items");
                    new Thread(() -> {
                        db.mstPartyDao().insertAll(parties);
                        // Verify insertion
                        int count = db.mstPartyDao().getCount();
                        Log.d("DATABASE", "Inserted " + count + " records");
                        if (onComplete != null) {
                            onComplete.run();
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
            }
        });
    }

    public MstParty getParty(String code, String mob) {
        return db.mstPartyDao().getParty(code, mob);
    }



}