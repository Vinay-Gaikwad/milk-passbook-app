package com.shiv.milkpassbook.MstSoftCustReg.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MstSoftCustRegResponse {
    @SerializedName("status")
    public boolean status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public List<MstSoftCustRegData> data;

    public static class MstSoftCustRegData {
        @SerializedName("SoftCustCode")
        public String SoftCustCode;

        @SerializedName("Mob")
        public String Mob;

        @SerializedName("PurDate")
        public String PurDate;

        @SerializedName("RenwDate")
        public String RenwDate;
    }
}