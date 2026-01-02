package com.shiv.milkpassbook.inserttempmilkdatabase;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {InsertTempTrnMilkEntity.class}, version = 1)
public abstract class InsertTempTrnMilkDatabase extends RoomDatabase {

    private static InsertTempTrnMilkDatabase instance;

    public abstract InsertTempTrnMilkDao insertTempTrnMilkDao();

    public static synchronized InsertTempTrnMilkDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    InsertTempTrnMilkDatabase.class,
                    "insert_temp_trn_milk_db"
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
