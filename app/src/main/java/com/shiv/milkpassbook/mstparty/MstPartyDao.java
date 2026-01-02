package com.shiv.milkpassbook.mstparty;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


@Dao
public interface MstPartyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MstParty> partyList);

    @Query("SELECT * FROM mst_party WHERE partyCode = :partyCode AND mob = :mobile")
    MstParty getParty(String partyCode, String mobile);

    // Add this new method for fetching by mobile number only
    @Query("SELECT * FROM mst_party WHERE mob = :mobileNumber LIMIT 1")
    MstParty getPartyByMobile(String mobileNumber);

    @Query("SELECT * FROM mst_party")
    List<MstParty> getAllParties();

    @Query("SELECT COUNT(*) FROM mst_party")
    int getCount();
}
