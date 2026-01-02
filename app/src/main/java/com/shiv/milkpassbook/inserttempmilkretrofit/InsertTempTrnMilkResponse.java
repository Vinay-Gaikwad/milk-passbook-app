package com.shiv.milkpassbook.inserttempmilkretrofit;

import com.google.gson.annotations.SerializedName;
import com.shiv.milkpassbook.inserttempmilkdatabase.InsertTempTrnMilkEntity;

import java.util.List;

public class InsertTempTrnMilkResponse {
    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
    private List<InsertTempTrnMilkEntity> data;

    public boolean isStatus() { return status; }

    public List<InsertTempTrnMilkEntity> getData() { return data; }
}
