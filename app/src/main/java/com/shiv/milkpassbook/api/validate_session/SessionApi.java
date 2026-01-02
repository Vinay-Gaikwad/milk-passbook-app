package com.shiv.milkpassbook.api.validate_session;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SessionApi {
    @POST("validate_session")
    Call<SessionResponse> validate(@Body SessionRequest request);
}
