package com.shiv.milkpassbook.mstdedretrofit;

import com.google.gson.annotations.SerializedName;
import com.shiv.milkpassbook.deddatabase.MstDedEntity;

import java.util.List;

public class MstDedResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
    private List<MstDedEntity> data;

    public boolean isStatus() {
        return status;
    }

    public List<MstDedEntity> getData() {
        return data;
    }
}
