package com.shiv.milkpassbook.dashboardretrofit;

public class DashboardRequest {
    private String softCustCode;
    private String device_id;

    public DashboardRequest(String softCustCode, String device_id) {
        this.softCustCode = softCustCode;
        this.device_id = device_id;
    }
}
