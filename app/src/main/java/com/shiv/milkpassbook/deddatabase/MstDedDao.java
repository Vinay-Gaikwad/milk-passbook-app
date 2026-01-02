package com.shiv.milkpassbook.deddatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MstDedDao {
    @Insert
    void insertAll(List<MstDedEntity> list);

    @Query("SELECT * FROM mst_ded")
    List<MstDedEntity> getAll();

    @Query("DELETE FROM mst_ded")
    void deleteAll();
}
