package com.example.animelist.DataStore;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Anime.class}, version = 3)
public abstract class AnimeRoomDatabase extends RoomDatabase {

    public abstract AnimeDao animeDao();

    private static volatile AnimeRoomDatabase INSTANCE;

    static AnimeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AnimeRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AnimeRoomDatabase.class, "anime_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
