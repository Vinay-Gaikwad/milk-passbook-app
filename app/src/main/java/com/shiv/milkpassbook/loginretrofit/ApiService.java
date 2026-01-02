package com.shiv.milkpassbook.loginretrofit;

import com.shiv.milkpassbook.mstparty.MstParty;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("mst_party")
    Call<ApiResponse> getMstParties();
}