package com.shiv.milkpassbook.mstcmntrnretrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MstCmnTrnApiService {
    @GET("index.php/api/mst_cmn_trn")
    Call<MstCmnTrnResponse> getCmnTrnList();
}
