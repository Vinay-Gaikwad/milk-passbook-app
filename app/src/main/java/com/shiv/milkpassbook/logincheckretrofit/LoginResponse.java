package com.shiv.milkpassbook.logincheckretrofit;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    // Optional setters if you want to modify later
    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
