package com.shiv.milkpassbook.MstSoftCustReg.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MstSoftCustRegEntity.class}, version = 1, exportSchema = false)
public abstract class MstSoftCustRegDatabase extends RoomDatabase {
    public abstract MstSoftCustRegDao mstSoftCustRegDao();

    private static volatile MstSoftCustRegDatabase INSTANCE;

    public static MstSoftCustRegDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MstSoftCustRegDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MstSoftCustRegDatabase.class, "mst_soft_cust_reg_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}