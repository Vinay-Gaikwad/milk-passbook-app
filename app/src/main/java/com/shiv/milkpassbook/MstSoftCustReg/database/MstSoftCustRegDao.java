package com.shiv.milkpassbook.MstSoftCustReg.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface MstSoftCustRegDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MstSoftCustRegEntity> softCustRegs);



    @Query("SELECT * FROM mst_soft_cust_reg WHERE SoftCustCode = :softCustCode")
    MstSoftCustRegEntity getBySoftCustCode(String softCustCode);

    @Query("DELETE FROM mst_soft_cust_reg")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM mst_soft_cust_reg")
    int getCount();

    @Query("SELECT * FROM mst_soft_cust_reg LIMIT 5")
    List<MstSoftCustRegEntity> getSampleRecords();

    @Query("SELECT * FROM mst_soft_cust_reg")
    LiveData<List<MstSoftCustRegEntity>> getAll();

    @Query("SELECT RenwDate FROM mst_soft_cust_reg WHERE SoftCustCode = :softCustCode")
    String getRenewalDate(String softCustCode);

    @Query("SELECT * FROM mst_soft_cust_reg")
    List<MstSoftCustRegEntity> getAllSync();
}