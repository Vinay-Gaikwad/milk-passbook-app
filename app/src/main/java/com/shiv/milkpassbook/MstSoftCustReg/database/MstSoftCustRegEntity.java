package com.shiv.milkpassbook.MstSoftCustReg.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mst_soft_cust_reg")
public class MstSoftCustRegEntity {
    @PrimaryKey
    @NonNull
    public String SoftCustCode;
    public String Mob;
    public String PurDate;
    public String RenwDate;
    public long lastUpdated = System.currentTimeMillis();

    public MstSoftCustRegEntity(@NonNull String SoftCustCode, String Mob, String PurDate, String RenwDate) {
        this.SoftCustCode = SoftCustCode;
        this.Mob = Mob;
        this.PurDate = PurDate;
        this.RenwDate = RenwDate;
    }
}