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
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);



    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            // Create the new table
            database.execSQL(
                    "CREATE TABLE anime_table_new (animeName String, animeSeason int, animeEpisode int, completed boolean, PRIMARY KEY(AnimeID))");

            // Copy the data
            database.execSQL(
                    "INSERT INTO anime_table_new (AnimeID, animeName, animeSeason, animeEpisode, completed) SELECT AnimeID, animeName, animeSeason, animeEpisode, completed FROM anime_table");

            // Remove the old table
            database.execSQL("DROP TABLE anime_table");

            // Change the table name to the correct one
            database.execSQL("ALTER TABLE anime_table_new RENAME TO anime_table");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            // Create the new table
            database.execSQL(
                    "CREATE TABLE anime_table_new (animeName String, animeSeason int, animeEpisode int, completed boolean, PRIMARY KEY(AnimeID))");

            // Copy the data
            database.execSQL(
                    "INSERT INTO anime_table_new (AnimeID, animeName, animeSeason, animeEpisode, completed) SELECT AnimeID, animeName, animeSeason, animeEpisode, completed FROM anime_table");

            // Remove the old table
            database.execSQL("DROP TABLE anime_table");

            // Change the table name to the correct one
            database.execSQL("ALTER TABLE anime_table_new RENAME TO anime_table");
        }
    };



    static AnimeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AnimeRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AnimeRoomDatabase.class, "anime_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .addCallback(sRoomDatabaseCallback)
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static final Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                AnimeDao dao = INSTANCE.animeDao();
                dao.deleteAll();
            });
        }
    };
}
