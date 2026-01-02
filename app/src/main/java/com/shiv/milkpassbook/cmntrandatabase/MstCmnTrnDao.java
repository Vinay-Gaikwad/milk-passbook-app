package com.shiv.milkpassbook.cmntrandatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MstCmnTrnDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MstCmnTrnEntity> items);

    @Query("SELECT * FROM mst_cmn_trn")
    List<MstCmnTrnEntity> getAll();

    @Query("DELETE FROM mst_cmn_trn")
    void deleteAll();
}
