package com.shiv.milkpassbook.deddatabase;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MstDedEntity.class}, version = 1)
public abstract class MstDedDatabase extends RoomDatabase {

    private static MstDedDatabase instance;

    public abstract MstDedDao mstDedDao();

    public static synchronized MstDedDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MstDedDatabase.class, "mst_ded_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
