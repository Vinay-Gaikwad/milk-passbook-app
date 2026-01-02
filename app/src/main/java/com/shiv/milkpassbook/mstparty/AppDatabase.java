package com.shiv.milkpassbook.mstparty;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MstParty.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract MstPartyDao mstPartyDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "milk_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // Use only if not using async
                    .build();
        }
        return instance;
    }
}
