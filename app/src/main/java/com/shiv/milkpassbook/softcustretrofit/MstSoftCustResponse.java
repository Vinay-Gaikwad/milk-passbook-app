package com.shiv.milkpassbook.softcustretrofit;

import com.google.gson.annotations.SerializedName;
import com.shiv.milkpassbook.softcustdatabase.MstSoftCustEntity;

import java.util.List;

public class MstSoftCustResponse {
    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
    private List<MstSoftCustEntity> data;

    public boolean isStatus() {
        return status;
    }

    public List<MstSoftCustEntity> getData() {
        return data;
    }
}
