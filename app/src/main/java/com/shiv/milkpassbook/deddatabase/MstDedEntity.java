package com.shiv.milkpassbook.deddatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "mst_ded")
public class MstDedEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("SoftCustCode")
    private int softCustCode;

    @SerializedName("DedCode")
    private int dedCode;

    @SerializedName("DedName")
    private String dedName;

    @SerializedName("DedTypeCode")
    private int dedTypeCode;

    @SerializedName("DedEarnCode")
    private int dedEarnCode;

    @SerializedName("DEPrcCode")
    private int dePrcCode;

    @SerializedName("MilkTypeCode")
    private int milkTypeCode;

    @SerializedName("DedRate")
    private double dedRate;

    @SerializedName("OrdNo")
    private int ordNo;

    @SerializedName("AcCode")
    private int acCode;

    @SerializedName("StsCode")
    private int stsCode;

    @SerializedName("DedAppCode")
    private int dedAppCode;

    @SerializedName("ShBalCode")
    private int shBalCode;

    // Getters and Setters for all fields
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSoftCustCode() { return softCustCode; }
    public void setSoftCustCode(int softCustCode) { this.softCustCode = softCustCode; }

    public int getDedCode() { return dedCode; }
    public void setDedCode(int dedCode) { this.dedCode = dedCode; }

    public String getDedName() { return dedName; }
    public void setDedName(String dedName) { this.dedName = dedName; }

    public int getDedTypeCode() { return dedTypeCode; }
    public void setDedTypeCode(int dedTypeCode) { this.dedTypeCode = dedTypeCode; }

    public int getDedEarnCode() { return dedEarnCode; }
    public void setDedEarnCode(int dedEarnCode) { this.dedEarnCode = dedEarnCode; }

    public int getDePrcCode() { return dePrcCode; }
    public void setDePrcCode(int dePrcCode) { this.dePrcCode = dePrcCode; }

    public int getMilkTypeCode() { return milkTypeCode; }
    public void setMilkTypeCode(int milkTypeCode) { this.milkTypeCode = milkTypeCode; }

    public double getDedRate() { return dedRate; }
    public void setDedRate(double dedRate) { this.dedRate = dedRate; }

    public int getOrdNo() { return ordNo; }
    public void setOrdNo(int ordNo) { this.ordNo = ordNo; }

    public int getAcCode() { return acCode; }
    public void setAcCode(int acCode) { this.acCode = acCode; }

    public int getStsCode() { return stsCode; }
    public void setStsCode(int stsCode) { this.stsCode = stsCode; }

    public int getDedAppCode() { return dedAppCode; }
    public void setDedAppCode(int dedAppCode) { this.dedAppCode = dedAppCode; }

    public int getShBalCode() { return shBalCode; }
    public void setShBalCode(int shBalCode) { this.shBalCode = shBalCode; }
}
