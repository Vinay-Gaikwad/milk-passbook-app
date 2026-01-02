package com.shiv.milkpassbook.inserttempmilkdatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface InsertTempTrnMilkDao {
    @Insert
    void insertAll(List<InsertTempTrnMilkEntity> list);

    @Query("DELETE FROM insert_temp_trn_milk")
    void deleteAll();

    @Query("SELECT * FROM insert_temp_trn_milk")
    List<InsertTempTrnMilkEntity> getAll();

    @Query("SELECT * FROM insert_temp_trn_milk WHERE partyCode = :partyCode AND softCustCode = :softCustCode AND mlkTypeCode = :mlkTypeCode")
    List<InsertTempTrnMilkEntity> getByPartyAndSoftCustAndMilkType(String partyCode, String softCustCode, int mlkTypeCode);

    // Add this new query method
    @Query("SELECT * FROM insert_temp_trn_milk WHERE partyCode = :partyCode AND softCustCode = :softCustCode")
    List<InsertTempTrnMilkEntity> getByPartyAndSoftCust(String partyCode, String softCustCode);
}