package com.shiv.milkpassbook.milkbillretrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TempMilkBillApiService {
    @GET("index.php/api/TempMilkBill")
    Call<TempMilkBillResponse> getMilkBillData();
}
