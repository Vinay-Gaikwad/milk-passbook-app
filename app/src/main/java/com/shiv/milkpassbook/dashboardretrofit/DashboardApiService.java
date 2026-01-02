package com.shiv.milkpassbook.dashboardretrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DashboardApiService {
    @POST("index.php/api/get_dashboard_data")
    Call<DashboardResponse> getDashboardData(@Body DashboardRequest request);
}
