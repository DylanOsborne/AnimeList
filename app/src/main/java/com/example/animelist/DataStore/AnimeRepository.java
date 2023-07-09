package com.example.animelist.DataStore;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AnimeRepository {

    private AnimeDao mAnimeDao;
    private LiveData<List<Anime>> mAllAime;


    AnimeRepository(Application application) {
        AnimeRoomDatabase db = AnimeRoomDatabase.getDatabase(application);
        mAnimeDao = db.animeDao();
        mAllAime = mAnimeDao.getAlphabetizedAnimes();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Anime>> getAllAnime() {
        return mAllAime;
    }


    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert (Anime anime) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(() -> {
            mAnimeDao.insert(anime);
        });
    }
}

