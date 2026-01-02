package com.shiv.milkpassbook.lngdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "mst_lng_sub")
public class MstLngSubEntity {

    @PrimaryKey
    @SerializedName("LngSubCode")
    private int lngSubCode;

    @SerializedName("LngSubName")
    private String lngSubName;

    @SerializedName("LngCode")
    private int lngCode;

    @SerializedName("FontName")
    private String fontName;

    // Getters and Setters
    public int getLngSubCode() { return lngSubCode; }
    public void setLngSubCode(int lngSubCode) { this.lngSubCode = lngSubCode; }

    public String getLngSubName() { return lngSubName; }
    public void setLngSubName(String lngSubName) { this.lngSubName = lngSubName; }

    public int getLngCode() { return lngCode; }
    public void setLngCode(int lngCode) { this.lngCode = lngCode; }

    public String getFontName() { return fontName; }
    public void setFontName(String fontName) { this.fontName = fontName; }
}
