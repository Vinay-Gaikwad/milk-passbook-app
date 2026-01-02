package com.shiv.milkpassbook.softcustdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Add your entity here with updated version
@Database(entities = {MstSoftCustEntity.class}, version = 2)
public abstract class MstSoftCustDatabase extends RoomDatabase {

    private static volatile MstSoftCustDatabase instance;

    public abstract MstSoftCustDao mstSoftCustDao();

    // Singleton instance with fallback to destructive migration
    public static synchronized MstSoftCustDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MstSoftCustDatabase.class,
                            "mst_soft_cust_db"
                    )
                    .fallbackToDestructiveMigration() // Clears and recreates DB if schema changes
                    .build();
        }
        return instance;
    }
}
