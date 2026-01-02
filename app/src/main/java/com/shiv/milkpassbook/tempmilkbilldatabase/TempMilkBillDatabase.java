package com.shiv.milkpassbook.tempmilkbilldatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TempMilkBillEntity.class}, version = 1)
public abstract class TempMilkBillDatabase extends RoomDatabase {

    private static TempMilkBillDatabase instance;

    public abstract TempMilkBillDao tempMilkBillDao();

    public static synchronized TempMilkBillDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    TempMilkBillDatabase.class,
                    "temp_milk_bill_db"
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
