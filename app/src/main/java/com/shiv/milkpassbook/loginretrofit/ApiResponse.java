package com.shiv.milkpassbook.loginretrofit;

import com.google.gson.annotations.SerializedName;
import com.shiv.milkpassbook.mstparty.MstParty;

import java.util.List;

public class ApiResponse {
    @SerializedName("data") // or whatever field contains your array
    private List<MstParty> parties;

    public List<MstParty> getParties() {
        return parties;
    }
}