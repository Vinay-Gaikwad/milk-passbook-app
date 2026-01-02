package com.shiv.milkpassbook.cmntrandatabase;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MstCmnTrnEntity.class}, version = 1, exportSchema = false)
public abstract class MstCmnTrnDatabase extends RoomDatabase {

    private static MstCmnTrnDatabase instance;

    public abstract MstCmnTrnDao mstCmnTrnDao();

    public static synchronized MstCmnTrnDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MstCmnTrnDatabase.class, "cmn_trn_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
