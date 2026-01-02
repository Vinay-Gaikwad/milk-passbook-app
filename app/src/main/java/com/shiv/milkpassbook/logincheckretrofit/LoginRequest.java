package com.shiv.milkpassbook.logincheckretrofit;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("softCustCode")
    private String softCustCode;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("device_id")
    private String device_id;

    public LoginRequest(String softCustCode, String mobile, String device_id) {
        this.softCustCode = softCustCode;
        this.mobile = mobile;
        this.device_id = device_id;
    }

    // Optional getters & setters
    public String getSoftCustCode() { return softCustCode; }
    public String getMobile() { return mobile; }
    public String getDevice_id() { return device_id; }

    public void setSoftCustCode(String softCustCode) { this.softCustCode = softCustCode; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public void setDevice_id(String device_id) { this.device_id = device_id; }
}
