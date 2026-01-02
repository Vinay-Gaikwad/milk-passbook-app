package com.shiv.milkpassbook.inserttempmilkretrofit;

import com.shiv.milkpassbook.inserttempmilkdatabase.InsertTempTrnMilkEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InsertTempTrnMilkApiService {
    @GET("insertTempTrnMilk")
    Call<InsertTempTrnMilkResponse> getMilkTrnList();
}
