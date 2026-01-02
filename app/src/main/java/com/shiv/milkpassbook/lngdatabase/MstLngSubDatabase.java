package com.shiv.milkpassbook.lngdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MstLngSubEntity.class}, version = 1, exportSchema = false)
public abstract class MstLngSubDatabase extends RoomDatabase {

    private static MstLngSubDatabase instance;

    public abstract MstLngSubDao mstLngSubDao();

    public static synchronized MstLngSubDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MstLngSubDatabase.class, "lng_sub_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
