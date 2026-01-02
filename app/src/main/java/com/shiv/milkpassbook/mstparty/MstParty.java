package com.shiv.milkpassbook.mstparty;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "mst_party")
public class MstParty {

    @PrimaryKey
    @NonNull
    @SerializedName("PartyCode")
    public String partyCode;

    @SerializedName("SoftCustCode")
    public String softCustCode;

    @SerializedName("PartyName")
    public String partyName;

    @SerializedName("PartyNameOth")
    public String partyNameOth;

    @SerializedName("SbTypeCode")
    public int sbTypeCode;

    @SerializedName("Address")
    public String address;

    @SerializedName("RtGrpCode")
    public int rtGrpCode;

    @SerializedName("BrCode")
    public int brCode;

    @SerializedName("MembTypeCode")
    public int membTypeCode;

    @SerializedName("BkCode")
    public int bkCode;

    @SerializedName("BkBranch")
    public String bkBranch;

    @SerializedName("IFSCCode")
    public String ifscCode;

    @SerializedName("BkAcNo")
    public String bkAcNo;

    @SerializedName("AcNo")
    public int acNo;

    @SerializedName("MlkTypeCode")
    public int milkTypeCode;

    @SerializedName("BillGrpCode")
    public int billGrpCode;

    @SerializedName("SxTypeCode")
    public int sxTypeCode;

    @SerializedName("Mob")
    public String mob;

    @SerializedName("StsCode")
    public int steCode;

    @SerializedName("AadharNo")
    public String aadharNo;

    @SerializedName("TaxNo")
    public String taxNo;

    @SerializedName("PanNo")
    public String panNo;

    @SerializedName("RtCode")
    public int rtCode;

    @SerializedName("FarmId")
    public String farmId;  // Changed from int to String based on JSON showing empty string

    @SerializedName("CityCode")
    public int cityCode;

    @SerializedName("FatType")
    public int partyType;  // Renamed from partyType to match JSON's FatType

    @Ignore
    public String getPartyNameOth() {
        return partyNameOth;
    }

    @Ignore
    public String getPartyCode() {
        return partyCode;
    }

    @Ignore
    public String getSoftCustCode() {
        return softCustCode;
    }

    @Ignore
    public String getMob() {
        return mob;
    }
}
