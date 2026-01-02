package com.shiv.milkpassbook.retrofit;

import com.shiv.milkpassbook.MstSoftCustReg.model.MstSoftCustRegResponse;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface MstSoftCustRegApi {
    @GET("mstSoftCustReg")
    Call<MstSoftCustRegResponse> getAllSoftCustReg();

    @GET("mstSoftCustReg")
    Call<MstSoftCustRegResponse> getSoftCustRegByCode(@Query("SoftCustCode") String softCustCode);

    @POST("mstSoftCustReg")
    Call<MstSoftCustRegResponse> createSoftCustReg(@Body Map<String, String> request);

    @PUT("mstSoftCustReg")
    Call<MstSoftCustRegResponse> updateSoftCustReg(@Body Map<String, String> request);

    @DELETE("mstSoftCustReg")
    Call<MstSoftCustRegResponse> deleteSoftCustReg(@Query("SoftCustCode") String softCustCode);
}