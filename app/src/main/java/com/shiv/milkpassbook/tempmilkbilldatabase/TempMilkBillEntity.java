package com.shiv.milkpassbook.tempmilkbilldatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "temp_milk_bill")
public class TempMilkBillEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("SoftCustCode")
    private String softCustCode;

    @SerializedName("TrDate")
    private String trDate;

    @SerializedName("PartyCode")
    private int partyCode;

    @SerializedName("MlkTypeCode")
    private int mlkTypeCode;

    @SerializedName("mQty")
    private double mQty;

    @SerializedName("mFat")
    private double mFat;

    @SerializedName("mSNF")
    private double mSNF;

    @SerializedName("mWater")
    private double mWater;

    @SerializedName("mRate")
    private double mRate;

    @SerializedName("mAmt")
    private double mAmt;

    @SerializedName("eQty")
    private double eQty;

    @SerializedName("eFat")
    private double eFat;

    @SerializedName("eSNF")
    private double eSNF;

    @SerializedName("eWater")
    private double eWater;

    @SerializedName("eRate")
    private double eRate;

    @SerializedName("eAmt")
    private double eAmt;

    @SerializedName("SrNo")
    private int srNo;

    @SerializedName("DedCode")
    private int dedCode;

    @SerializedName("DedAmt")
    private double dedAmt;

    @SerializedName("BalAmt")
    private double balAmt;

    @SerializedName("CurAdvAmt")
    private double curAdvAmt;

    @SerializedName("BalOpAmt")
    private double balOpAmt;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSoftCustCode() { return softCustCode; }
    public void setSoftCustCode(String softCustCode) { this.softCustCode = softCustCode; }

    public String getTrDate() { return trDate; }
    public void setTrDate(String trDate) { this.trDate = trDate; }

    public int getPartyCode() { return partyCode; }
    public void setPartyCode(int partyCode) { this.partyCode = partyCode; }

    public int getMlkTypeCode() { return mlkTypeCode; }
    public void setMlkTypeCode(int mlkTypeCode) { this.mlkTypeCode = mlkTypeCode; }

    public double getMQty() { return mQty; }
    public void setMQty(double mQty) { this.mQty = mQty; }

    public double getMFat() { return mFat; }
    public void setMFat(double mFat) { this.mFat = mFat; }

    public double getMSNF() { return mSNF; }
    public void setMSNF(double mSNF) { this.mSNF = mSNF; }

    public double getMWater() { return mWater; }
    public void setMWater(double mWater) { this.mWater = mWater; }

    public double getMRate() { return mRate; }
    public void setMRate(double mRate) { this.mRate = mRate; }

    public double getMAmt() { return mAmt; }
    public void setMAmt(double mAmt) { this.mAmt = mAmt; }

    public double getEQty() { return eQty; }
    public void setEQty(double eQty) { this.eQty = eQty; }

    public double getEFat() { return eFat; }
    public void setEFat(double eFat) { this.eFat = eFat; }

    public double getESNF() { return eSNF; }
    public void setESNF(double eSNF) { this.eSNF = eSNF; }

    public double getEWater() { return eWater; }
    public void setEWater(double eWater) { this.eWater = eWater; }

    public double getERate() { return eRate; }
    public void setERate(double eRate) { this.eRate = eRate; }

    public double getEAmt() { return eAmt; }
    public void setEAmt(double eAmt) { this.eAmt = eAmt; }

    public int getSrNo() { return srNo; }
    public void setSrNo(int srNo) { this.srNo = srNo; }

    public int getDedCode() { return dedCode; }
    public void setDedCode(int dedCode) { this.dedCode = dedCode; }

    public double getDedAmt() { return dedAmt; }
    public void setDedAmt(double dedAmt) { this.dedAmt = dedAmt; }

    public double getBalAmt() { return balAmt; }
    public void setBalAmt(double balAmt) { this.balAmt = balAmt; }

    public double getCurAdvAmt() { return curAdvAmt; }
    public void setCurAdvAmt(double curAdvAmt) { this.curAdvAmt = curAdvAmt; }

    public double getBalOpAmt() { return balOpAmt; }
    public void setBalOpAmt(double balOpAmt) { this.balOpAmt = balOpAmt; }
}
