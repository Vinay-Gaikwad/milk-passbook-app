package com.shiv.milkpassbook.mstcmntrnretrofit;

import com.google.gson.annotations.SerializedName;
import com.shiv.milkpassbook.cmntrandatabase.MstCmnTrnEntity;

import java.util.List;

public class MstCmnTrnResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<MstCmnTrnEntity> data;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<MstCmnTrnEntity> getData() {
        return data;
    }
}
