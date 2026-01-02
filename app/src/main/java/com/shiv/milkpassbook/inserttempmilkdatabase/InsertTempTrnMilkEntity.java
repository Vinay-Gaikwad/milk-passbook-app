package com.shiv.milkpassbook.inserttempmilkdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "insert_temp_trn_milk")
public class InsertTempTrnMilkEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("SoftCustCode")
    private int softCustCode;

    @SerializedName("ATrNo")
    private int aTrNo;

    @SerializedName("TrNo")
    private int trNo;

    @SerializedName("TrDate")
    private String trDate;

    @SerializedName("MECode")
    private int meCode;

    @SerializedName("SampNo")
    private int sampNo;

    @SerializedName("SrNo")
    private int srNo;

    @SerializedName("PartyCode")
    private int partyCode;

    @SerializedName("MlkTypeCode")
    private int mlkTypeCode;

    @SerializedName("Qty")
    private double qty;

    @SerializedName("Fat")
    private double fat;

    @SerializedName("Deg")
    private double deg;

    @SerializedName("SNF")
    private double snf;

    @SerializedName("Water")
    private double water;

    @SerializedName("Rate")
    private double rate;

    @SerializedName("Amt")
    private double amt;

    @SerializedName("BlTypeCode")
    private int blTypeCode;

    @SerializedName("BrCode")
    private int brCode;

    // Getters and setters (only sample few shown, rest similar)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSoftCustCode() { return softCustCode; }
    public void setSoftCustCode(int softCustCode) { this.softCustCode = softCustCode; }

    public int getATrNo() { return aTrNo; }
    public void setATrNo(int aTrNo) { this.aTrNo = aTrNo; }

    public int getTrNo() { return trNo; }
    public void setTrNo(int trNo) { this.trNo = trNo; }

    public String getTrDate() { return trDate; }
    public void setTrDate(String trDate) { this.trDate = trDate; }

    public int getMeCode() { return meCode; }
    public void setMeCode(int meCode) { this.meCode = meCode; }

    public int getSampNo() { return sampNo; }
    public void setSampNo(int sampNo) { this.sampNo = sampNo; }

    public int getSrNo() { return srNo; }
    public void setSrNo(int srNo) { this.srNo = srNo; }

    public int getPartyCode() { return partyCode; }
    public void setPartyCode(int partyCode) { this.partyCode = partyCode; }

    public int getMlkTypeCode() { return mlkTypeCode; }
    public void setMlkTypeCode(int mlkTypeCode) { this.mlkTypeCode = mlkTypeCode; }

    public double getQty() { return qty; }
    public void setQty(double qty) { this.qty = qty; }

    public double getFat() { return fat; }
    public void setFat(double fat) { this.fat = fat; }

    public double getDeg() { return deg; }
    public void setDeg(double deg) { this.deg = deg; }

    public double getSnf() { return snf; }
    public void setSnf(double snf) { this.snf = snf; }

    public double getWater() { return water; }
    public void setWater(double water) { this.water = water; }

    public double getRate() { return rate; }
    public void setRate(double rate) { this.rate = rate; }

    public double getAmt() { return amt; }
    public void setAmt(double amt) { this.amt = amt; }

    public int getBlTypeCode() { return blTypeCode; }
    public void setBlTypeCode(int blTypeCode) { this.blTypeCode = blTypeCode; }

    public int getBrCode() { return brCode; }
    public void setBrCode(int brCode) { this.brCode = brCode; }
}
