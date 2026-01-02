package com.shiv.milkpassbook.milkbillretrofit;

import com.google.gson.annotations.SerializedName;
import com.shiv.milkpassbook.tempmilkbilldatabase.TempMilkBillEntity;

import java.util.List;

public class TempMilkBillResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<TempMilkBillEntity> data;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public List<TempMilkBillEntity> getData() { return data; }
}
