package com.shiv.milkpassbook.cmntrandatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "mst_cmn_trn")
public class MstCmnTrnEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;  // Local ID (optional)

    @SerializedName("SoftCustCode")
    private String softCustCode;

    @SerializedName("CmnTrnCode")
    private String cmnTrnCode;

    @SerializedName("CmnTrnName")
    private String cmnTrnName;

    @SerializedName("CmnTrnNameOth")
    private String cmnTrnNameOth;

    @SerializedName("RefCode")
    private String refCode;

    @SerializedName("CmnTrnTblCode")
    private String cmnTrnTblCode;

    @SerializedName("FrDate")
    private String frDate;

    @SerializedName("ToDate")
    private String toDate;

    @SerializedName("PostDate")
    private String postDate;

    @SerializedName("DueDate")
    private String dueDate;

    // 👉 Getters
    public int getId() { return id; }
    public String getSoftCustCode() { return softCustCode; }
    public String getCmnTrnCode() { return cmnTrnCode; }
    public String getCmnTrnName() { return cmnTrnName; }
    public String getCmnTrnNameOth() { return cmnTrnNameOth; }
    public String getRefCode() { return refCode; }
    public String getCmnTrnTblCode() { return cmnTrnTblCode; }
    public String getFrDate() { return frDate; }
    public String getToDate() { return toDate; }
    public String getPostDate() { return postDate; }
    public String getDueDate() { return dueDate; }

    // 👉 Setters
    public void setId(int id) { this.id = id; }
    public void setSoftCustCode(String softCustCode) { this.softCustCode = softCustCode; }
    public void setCmnTrnCode(String cmnTrnCode) { this.cmnTrnCode = cmnTrnCode; }
    public void setCmnTrnName(String cmnTrnName) { this.cmnTrnName = cmnTrnName; }
    public void setCmnTrnNameOth(String cmnTrnNameOth) { this.cmnTrnNameOth = cmnTrnNameOth; }
    public void setRefCode(String refCode) { this.refCode = refCode; }
    public void setCmnTrnTblCode(String cmnTrnTblCode) { this.cmnTrnTblCode = cmnTrnTblCode; }
    public void setFrDate(String frDate) { this.frDate = frDate; }
    public void setToDate(String toDate) { this.toDate = toDate; }
    public void setPostDate(String postDate) { this.postDate = postDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
}
