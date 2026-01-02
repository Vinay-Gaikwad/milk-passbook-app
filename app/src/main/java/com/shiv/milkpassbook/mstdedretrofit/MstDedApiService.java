package com.shiv.milkpassbook.mstdedretrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MstDedApiService {
    @GET("index.php/api/mst_ded")
    Call<MstDedResponse> getDeductions();
}
