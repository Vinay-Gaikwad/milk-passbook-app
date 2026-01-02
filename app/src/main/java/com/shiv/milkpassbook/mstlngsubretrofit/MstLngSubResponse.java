package com.shiv.milkpassbook.mstlngsubretrofit;

import com.google.gson.annotations.SerializedName;
import com.shiv.milkpassbook.lngdatabase.MstLngSubEntity;

import java.util.List;

public class MstLngSubResponse {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<MstLngSubEntity> data;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public List<MstLngSubEntity> getData() { return data; }
}
