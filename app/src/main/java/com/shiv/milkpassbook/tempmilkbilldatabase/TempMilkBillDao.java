package com.shiv.milkpassbook.tempmilkbilldatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TempMilkBillDao {

    @Query("SELECT * FROM temp_milk_bill")
    List<TempMilkBillEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TempMilkBillEntity> list);

    @Query("DELETE FROM temp_milk_bill")
    void deleteAll();
}
