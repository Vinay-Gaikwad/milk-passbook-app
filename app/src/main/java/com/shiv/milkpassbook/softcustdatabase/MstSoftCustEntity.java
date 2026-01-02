package com.shiv.milkpassbook.softcustdatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "mst_soft_cust")
public class MstSoftCustEntity {

    @PrimaryKey
    @SerializedName("SoftCustCode")
    private int softCustCode;

    @SerializedName("SoftCustName")
    private String softCustName;

    @SerializedName("SoftCustNameSh")
    private String softCustNameSh;

    @SerializedName("LngSubCode")
    private int lngSubCode;

    @SerializedName("DevlpBy")
    @Expose
    @ColumnInfo(name = "DevlpBy")
    private String devlpBy;


    // Getters & Setters
    public int getSoftCustCode() { return softCustCode; }
    public void setSoftCustCode(int softCustCode) { this.softCustCode = softCustCode; }

    public String getSoftCustName() { return softCustName; }
    public void setSoftCustName(String softCustName) { this.softCustName = softCustName; }

    public String getSoftCustNameSh() { return softCustNameSh; }
    public void setSoftCustNameSh(String softCustNameSh) { this.softCustNameSh = softCustNameSh; }

    public int getLngSubCode() { return lngSubCode; }
    public void setLngSubCode(int lngSubCode) { this.lngSubCode = lngSubCode; }

    public String getDevlpBy() {
        return devlpBy;
    }

    public void setDevlpBy(String devlpBy) {
        this.devlpBy = devlpBy;
    }

}
