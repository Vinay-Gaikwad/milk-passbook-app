package com.shiv.milkpassbook.dashboardretrofit;

import com.google.gson.annotations.SerializedName;

public class DashboardResponse {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Data data;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public Data getData() { return data; }

    public static class Data {
        @SerializedName("totalBills")
        private int totalBills;

        public int getTotalBills() { return totalBills; }
    }
}
