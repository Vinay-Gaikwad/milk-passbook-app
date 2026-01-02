package com.shiv.milkpassbook.mstlngsubretrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MstLngSubApiService {

    @GET("index.php/api/mst_lng_sub")
    Call<MstLngSubResponse> getLanguageSubs();
}
