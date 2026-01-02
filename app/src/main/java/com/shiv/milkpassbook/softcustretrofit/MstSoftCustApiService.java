package com.shiv.milkpassbook.softcustretrofit;

import com.shiv.milkpassbook.loginretrofit.ApiResponse;
import com.shiv.milkpassbook.softcustdatabase.MstSoftCustEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface MstSoftCustApiService {
    @GET("mstsoftcust")
    Call<MstSoftCustResponse> getSoftCustList();



}
