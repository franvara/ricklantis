package com.franvara.ricklantis.data.repository.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.franvara.ricklantis.data.repository.local.daos.CharactersDao;
import com.franvara.ricklantis.domain.entities.Character;

@Database(entities = {Character.class}, version = 1)
abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "ricklantis";
    private static final Object LOCK = new Object();

    private static volatile AppDatabase sInstance;


    static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                }
            }
        }
        return sInstance;
    }

    abstract CharactersDao getCharactersDao();

}
