package com.shiv.milkpassbook.softcustdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MstSoftCustDao {

    @Update
    void update(MstSoftCustEntity entity);

    @Update
    void update(List<MstSoftCustEntity> list);

    @Update
    void updateAll(List<MstSoftCustEntity> list);

    @Delete
    void delete(List<MstSoftCustEntity> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MstSoftCustEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MstSoftCustEntity> list);

    @Query("SELECT * FROM mst_soft_cust")
    List<MstSoftCustEntity> getAll();

    @Query("DELETE FROM mst_soft_cust")
    void deleteAll();

    @Query("SELECT * FROM mst_soft_cust WHERE SoftCustCode = :softCustCode LIMIT 1")
    MstSoftCustEntity getBySoftCustCode(String softCustCode);
}
