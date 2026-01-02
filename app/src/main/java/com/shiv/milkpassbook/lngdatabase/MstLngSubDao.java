package com.shiv.milkpassbook.lngdatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MstLngSubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MstLngSubEntity> data);

    @Query("SELECT * FROM mst_lng_sub")
    List<MstLngSubEntity> getAll();

    @Query("DELETE FROM mst_lng_sub")
    void deleteAll();
}
